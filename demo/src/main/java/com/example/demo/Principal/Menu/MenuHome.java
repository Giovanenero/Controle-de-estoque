package com.example.demo.Principal.Menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Gerenciador.GerenciadorEstado;
import com.example.demo.Principal.Gerenciador.GerenciadorMongoDB;
import com.example.demo.Principal.Gerenciador.GerenciadorUsuario;

public class MenuHome extends Menu {
    //atributos
    private JTextField caixaPesquisa;
    private JScrollPane scrollPane;
    private List<Produto> produtos;
    private DefaultTableModel modeloTabela;
    private JButton botaoMonitoramento;

    //métodos
    public MenuHome() {
        super(Long.parseLong("2"), "menuHome");
        criarComponentes();
    }

    @Override
    public void renderizarComponentes() {
        atualizarLista();
        for(int i = 0; i < vectorBotaos.size() - 1; i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.add(botao);
        }
        gerenciadorGrafico.add(caixaPesquisa);
        gerenciadorGrafico.add(scrollPane);
        if(gerenciadorUsuario.ehAdministrador()){
            gerenciadorGrafico.add(botaoMonitoramento);
        }
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void removerComponentes() {
        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.remove(botao);
        }
        gerenciadorGrafico.remove(caixaPesquisa);
        gerenciadorGrafico.remove(scrollPane);
        gerenciadorGrafico.remove(botaoMonitoramento);
    }

    /**
     * 
     */
    public void atualizarLista(){
        produtos = new GerenciadorMongoDB().getListProdutos();
        //modeloTabela = (DefaultTableModel)modeloTabela.getModel();
        modeloTabela.setNumRows(0);
        for(int i = 0; i < produtos.size(); i++){
            Produto produto = produtos.get(i);
            Object [] linha = {
                produto.getId(),
                produto.getNome(), 
                produto.getLote(),
                produto.getPreco(),
                produto.getQtd(),
                produto.getDataModificacao(),
                produto.getValorTotal()
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
    public void criarComponentes() {
        caixaPesquisa = new JTextField();
        caixaPesquisa.setBounds(
            25,
            58,
            200,
            25
        );
        caixaPesquisa.setFont(font);

        int heightBotao = 50;
        int widthBotao = 150;

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

        heighPesquisa = heighPesquisa + 20;

        //ImageIcon iconEntrada;
        JButton botaoEntrada = new JButton("Entrada");
        botaoEntrada.setBounds(
            bounds.width / 2 - 50,
            heighPesquisa,
            widthBotao,
            heightBotao
        );
        botaoEntrada.addActionListener(this);

        JButton botaoSaida = new JButton("Saida");
        botaoSaida.setBounds(
            botaoEntrada.getBounds().x + botaoEntrada.getBounds().width + 25,
            heighPesquisa,
            widthBotao,
            heightBotao
        );
        botaoSaida.addActionListener(this);

        JButton botaoVoltar = new JButton("Sair");
        botaoVoltar.setBounds(
            botaoSaida.getBounds().x + botaoSaida.getBounds().width + 25,
            heighPesquisa,
            widthBotao,
            heightBotao
        );
        botaoVoltar.addActionListener(this);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("CÓDIGO");
        modeloTabela.addColumn("NOME");
        modeloTabela.addColumn("LOTE");
        modeloTabela.addColumn("PREÇO");
        modeloTabela.addColumn("QUANTIDADE");
        modeloTabela.addColumn("ÚLTIMA MODIFICAÇÃO");
        modeloTabela.addColumn("TOTAL");

        botaoMonitoramento = new JButton("Monitoramento");
        botaoMonitoramento.setBounds(
            bounds.width / 2 - 225,
            heighPesquisa,
            widthBotao,
            heightBotao
        );
        botaoMonitoramento.addActionListener(this);

        caixaPesquisa.setBackground(Color.LIGHT_GRAY);
        caixaPesquisa.setForeground(Color.WHITE);
        caixaPesquisa.setCaretColor(Color.WHITE);

        vectorBotaos.add(botaoPesquisa);
        vectorBotaos.add(botaoEntrada);
        vectorBotaos.add(botaoSaida);
        vectorBotaos.add(botaoVoltar);
        vectorBotaos.add(botaoMonitoramento);

        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            botao.setBackground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Boolean entrou = true;
        JButton botaoPesquisa = vectorBotaos.get(0);
        JButton botaoEntrada = vectorBotaos.get(1);
        JButton botaoVoltar = vectorBotaos.get(3);
        JButton botaoMonitoramento = vectorBotaos.get(4);
        String nomeEstado = "";
        entrou = !entrou;

        if(event.getSource() == botaoPesquisa){
            System.out.println("Pesquisa\n");
            entrou = true;
        } else if(event.getSource() == botaoVoltar){
            nomeEstado = "menuLogin";
        } else if(event.getSource() == botaoMonitoramento){
            nomeEstado = "menuMonitoramento";
        } else {
            if(GerenciadorUsuario.getGerenciadorUsuario().getUsuario().getAdministrador()){
                nomeEstado = event.getSource() == botaoEntrada ? "menuEntrada" : "menuSaida";
            }
        }
        if(nomeEstado != ""){
            entrou = GerenciadorEstado.getGerenciadorEstado().alterarEstado(nomeEstado);
        } else if(!entrou){
            JOptionPane.showMessageDialog(null, "Função habilitada apenas para os Administradores.");
        }
    }
    
}
