package com.example.demo.Principal.Gerenciador;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Entidade.Usuario;
import com.example.demo.Principal.Estruturas.Modificacao;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class GerenciadorMongoDB {
    //atributos
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;
    private MongoCollection<org.bson.Document> collectionProduto = null;
    private MongoCollection<org.bson.Document> collectionUsuario = null;
    private MongoCollection<org.bson.Document> collectionMonitoramento = null;
    private MongoCursor<Document> cursor; 

    //métodos
    public GerenciadorMongoDB(){
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("Projeto");
        collectionProduto = database.getCollection("Produtos");
        collectionUsuario = database.getCollection("Usuarios");
        collectionMonitoramento = database.getCollection("Monitoramento");
    }

    public Produto getProduto(Long id){
        List<Produto> produtos = getListProdutos();
        for(int i = 0; i < produtos.size(); i++){
            Produto produto = produtos.get(i);
            if(produto.getId() == id){
                return produto;
            }
        }
        return null;
    }

    public Usuario getUsuario(Long id){
        List<Usuario> usuarios = getListUsuarios();
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuario = usuarios.get(i);
            if(usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }

    public List<Produto> getListProdutos(){
        cursor = collectionProduto.find().iterator();
        List<Produto> produtos = new ArrayList<>();
        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                Long id = Long.parseLong(document.get("id").toString());
                String nome = document.get("nome").toString();
                float preco = Float.parseFloat(document.get("preco").toString());
                int qtd = Integer.parseInt(document.get("quantidade").toString());
                String lote = document.get("lote").toString();
                String data = document.get("dataModificacao").toString();
                Produto produto = new Produto(id, nome, qtd, lote, preco, data);
                produtos.add(produto);
            }
        } finally {
            cursor.close();
        }
        return produtos;
    }

    public List<Usuario> getListUsuarios(){
        cursor = collectionUsuario.find().iterator();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                String nome = document.get("nome").toString();
                Long id = Long.parseLong(document.get("id").toString());
                String senha = document.get("senha").toString();
                Boolean ehAdministrador = Boolean.parseBoolean(document.get("ehAdministrador").toString());
                Usuario usuario = new Usuario(id, nome, senha, ehAdministrador);
                usuarios.add(usuario);
            }
        } finally {
            cursor.close();
        }
        return usuarios;
    }

    public List<Modificacao> getListModificacao(){
        cursor = collectionMonitoramento.find().iterator();
        List<Modificacao> modificacoes = new ArrayList<>();
        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                Long idProduto = document.get("idProduto") != null ? Long.parseLong(document.get("idProduto").toString()) : null;
                int saldoAnterior = document.get("saldoAnterior") != null ? Integer.parseInt(document.get("saldoAnterior").toString()) : 0;
                int novoSaldo = document.get("novoSaldo") != null ? Integer.parseInt(document.get("novoSaldo").toString()) : 0;
                Long idUsuario = Long.parseLong(document.get("idUsuario").toString());
                String dataModificacao = document.get("dataModificacao").toString();
                String tipoModificacao = document.get("tipoModificacao").toString();
                Modificacao modificacao = new Modificacao(idProduto, idUsuario, saldoAnterior, novoSaldo, tipoModificacao, dataModificacao);
                modificacoes.add(modificacao);
            }
        } finally {
            cursor.close();
        }
        return modificacoes;
    }

    public Usuario usuarioExiste(String login, String senha){
        int i = 0;
        senha = criptografar(senha);
        List<Usuario> usuarios = getListUsuarios();
        while(i < usuarios.size()){
            Usuario usuario = usuarios.get(i);
            String loginUser = usuario.getNome().trim();
            String senhaUser = usuario.getSenha().trim();
            if(login.equals(loginUser) && senha.equals(senhaUser)){
                return usuario;
            }
            i++;
        }
        return null;
    }

    private String criptografar(String senha){
        String senhaCriptografada = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            senhaCriptografada = hash.toString(16);
        } catch (Exception e){
            System.out.println("Nao foi possivel fazer a criptografia");
            System.exit(1);
        }
        return senhaCriptografada;
    }

    public Usuario registrarUsuario(String nome, String senha){
        Random random = new Random();
        Long id = random.nextLong(1000000, 9999999);
        boolean ehAdministrador = false;
        senha = criptografar(senha);
        Document document = new Document();
        document.put("id", id);
        document.put("nome", nome);
        document.put("senha", senha);
        document.put("ehAdministrador", ehAdministrador);
        collectionUsuario.insertOne(document);
        return new Usuario(id, nome, senha, ehAdministrador);
    }

    public void addProduto(Produto produto){
        Document document = new Document();
        document.put("id", produto.getId());
        document.put("nome", produto.getNome());
        document.put("lote", produto.getLote());
        document.put("preco", produto.getPreco());
        document.put("quantidade", produto.getQtd());
        document.put("valorTotal", produto.getValorTotal());
        document.put("dataModificacao", produto.getDataModificacao());
        collectionProduto.insertOne(document);
        addMonitoramento(produto, GerenciadorUsuario.getGerenciadorUsuario().getUsuario(), "acidionar produto", 0);
    }

    //altera o produto no mongoDB
    public void alterarProduto(Produto produto, int subQtd){
        try {
            Document found = (Document) collectionProduto.find(new Document("id", produto.getId())).first();
            int qtd = produto.getQtd() - subQtd;
            Bson updateValue = new Document("quantidade", qtd)
                .append("dataModificacao", produto.getDataModificacao())
                .append("valorTotal", produto.getValorTotal());
            Bson updateOperation = new Document("$set", updateValue);
            collectionProduto.updateOne(found, updateOperation);
            produto.setQtd(qtd);
            addMonitoramento(produto, GerenciadorUsuario.getGerenciadorUsuario().getUsuario(), "alterar produto", qtd + subQtd);
        } catch(Exception e){
            System.out.println("Erro: não foi possível encontrar o produto");
        }
    }

    //arrumar
    public void addMonitoramento(Produto produto, Usuario usuario, String nomeModificacao, int saldoAnterior){
        Date data = new Date();
        SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
        Document document = new Document();
        document.put("idProduto", produto == null ? null : produto.getId());
        document.put("novoSaldo", produto == null ? null : produto.getQtd());
        document.put("saldoAnterior", saldoAnterior);
        document.put("idUsuario", usuario == null ? null : usuario.getId());
        document.put("dataModificacao", formatacao.format(data));
        document.put("tipoModificacao", nomeModificacao);
        collectionMonitoramento.insertOne(document);
    }

    public void addMonitoramento(Usuario usuario, String nomeModificacao){
        Date data = new Date();
        SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
        Document document = new Document();
        document.put("idProduto", null);
        document.put("saldoAnterior", null);
        document.put("novoSaldo", null);
        document.put("idUsuario", usuario.getId());
        document.put("dataModificacao", formatacao.format(data));
        document.put("tipoModificacao", nomeModificacao);
        collectionMonitoramento.insertOne(document);
    }

    /*
     * Retorna uma Lista de Produtos do Usuario pelo seu id
     */

    public List<Modificacao> getListModificacaoUsuario(Long id){
        List<Modificacao> modificacoes = getListModificacao();
        List<Modificacao> modificacoesUsuario = new ArrayList<>();
        for(int i = 0; i < modificacoes.size(); i++){
            Modificacao modificacao = modificacoes.get(i);
            if(modificacao.getIdUsuario().toString().equals(id.toString())){
                modificacoesUsuario.add(modificacao);
            }
        }
        return modificacoesUsuario;
    }
}
