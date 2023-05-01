package com.example.demo.Principal.Gerenciador;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Entidade.Usuario;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class GerenciadorMongoDB {
    //atributos
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;
    private MongoCollection<org.bson.Document> collectionProduto = null;
    private MongoCollection<org.bson.Document> collectionUsuario = null;
    private MongoCursor<Document> cursor; 
    private static GerenciadorMongoDB mongoDB = null; 
    private List<Produto> produtos = null;
    private List<Usuario> usuarios = null;

    //métodos
    private GerenciadorMongoDB(){
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("Projeto");
        collectionProduto = database.getCollection("Produtos");
        collectionUsuario = database.getCollection("Usuarios");
        produtos = new ArrayList<>();
        usuarios = new ArrayList<>();
        recuperarUsuarios();
        recuperarProdutos();
    }
    public static GerenciadorMongoDB getGerenciadorMongoDB(){
        if(mongoDB == null){
            mongoDB = new GerenciadorMongoDB();
        }
        return mongoDB;
    }
    private void recuperarProdutos(){
        cursor = collectionProduto.find().iterator();
        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                int id = Integer.parseInt(document.get("id").toString());
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
    }

    private void recuperarUsuarios(){
        cursor = collectionUsuario.find().iterator();
        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                String nome = document.get("nome").toString();
                int id = Integer.parseInt(document.get("id").toString());
                String senha = document.get("senha").toString();
                Boolean ehAdministrador = Boolean.parseBoolean(document.get("ehAdministrador").toString());
                Usuario usuario = new Usuario(id, nome, senha, ehAdministrador);
                usuarios.add(usuario);
            }
        } finally {
            cursor.close();
        }
    }

    public Usuario usuarioExiste(String login, String senha){
        int i = 0;
        senha = criptografar(senha);
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
            System.exit(0);
        }
        return senhaCriptografada;
    }

    public void registrarUsuario(String nome, String senha){
        int id = 123456;
        boolean ehAdministrador = false;
        senha = criptografar(senha);
        Document document = new Document();
        document.put("id", id);
        document.put("nome", nome);
        document.put("senha", senha);
        document.put("ehAdministrador", ehAdministrador);
        collectionUsuario.insertOne(document);
        Usuario newUsuario = new Usuario(id, nome, senha, ehAdministrador);
        usuarios.add(newUsuario);
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
        produtos.add(produto);
    }

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
            int pos = produtos.indexOf(produto);
            produtos.remove(pos);
            produtos.add(pos, produto);
        } catch(Exception e){
            System.out.println("Erro: não foi possível encontrar o produto");
        }
    }

    public List<Produto> getProdutos(){
        return produtos;
    }
}
