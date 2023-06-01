package com.example.demo.Principal.Menu;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Entidade.Usuario;
import com.example.demo.Principal.Estruturas.Modificacao;
import com.example.demo.Principal.Gerenciador.GerenciadorMongoDB;

public class MenuMonitoramento extends Menu {

    //atributos
    private JScrollPane scrollPane;
    private DefaultTableModel modeloTabela;
    private JTextField caixaPesquisa;
    List<Produto> produtos;
    List<Usuario> usuarios;

    public MenuMonitoramento() {
        super(Long.parseLong("5"), "menuMonitoramento");
        produtos = null;
        criarComponentes();
    }

    @Override
    public void criarComponentes() {
        caixaPesquisa = new JTextField();
        caixaPesquisa.setBounds(
            25,
            58,
            250,
            25
        );
        caixaPesquisa.setFont(font);

        ImageIcon iconPesquisa = new ImageIcon(getClass().getResource("./../../assets/lupa.jpg"));
        int widthPesquisa = caixaPesquisa.getBounds().width;
        int heighPesquisa = caixaPesquisa.getBounds().height;
        iconPesquisa.setImage(iconPesquisa.getImage().getScaledInstance(heighPesquisa - 1, heighPesquisa - 2, 1));
        JButton botaoPesquisa = new JButton(iconPesquisa);
        botaoPesquisa.setBounds(
            widthPesquisa + caixaPesquisa.getBounds().x,
            caixaPesquisa.getBounds().y,
            heighPesquisa,
            heighPesquisa
        );
        botaoPesquisa.setBorderPainted(false);
        botaoPesquisa.addActionListener(this);
        

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("CÓDIGO");
        modeloTabela.addColumn("USUÁRIO");
        modeloTabela.addColumn("ÚLTIMO EVENTO");
        modeloTabela.addColumn("ADMINISTRADOR");
        modeloTabela.addColumn("DATA");

        vectorBotaos.add(botaoPesquisa);
    }
    
    @Override
    public void renderizarComponentes() {
        atualizarLista();
        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.add(botao);
        }

        gerenciadorGrafico.add(scrollPane);
        gerenciadorGrafico.add(caixaPesquisa);
        gerenciadorGrafico.atualizarJanela();
    }

    public void atualizarLista(){
        GerenciadorMongoDB mongoDB = new GerenciadorMongoDB();
        usuarios = mongoDB.getListUsuarios();
        modeloTabela.setNumRows(0);
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuario = usuarios.get(i);
            List<Modificacao> modificacoes = mongoDB.getListModificacaoUsuario(usuario.getId());
            Modificacao ultimaModificacao = modificacoes.get(0);
            Object [] linha = {
                usuario.getId(),
                usuario.getNome(),
                ultimaModificacao.getTipo(),
                usuario.getAdministrador() ? "Sim" : "Não",
                ultimaModificacao.getData()
            };
            modeloTabela.addRow(linha);
        }
        JTable tabela = new JTable(modeloTabela);
        tabela.setBounds(25, 200, 400, 200);
        tabela.setEnabled(false);

        scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(20, 150, 940, 400);
    }

    @Override
    public void removerComponentes() {
        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.remove(botao);
        }

        gerenciadorGrafico.remove(scrollPane);
        gerenciadorGrafico.remove(caixaPesquisa);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }
}
