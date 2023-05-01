package com.example.demo.Principal.Menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Gerenciador.Estado;
import com.example.demo.Principal.Gerenciador.GerenciadorEstado;

public class MenuHome extends Estado implements ActionListener {
    //atributos
    private JTextField caixaPesquisa;
    private JButton botaoPesquisa;
    private JButton botaoEntrada;
    private JButton botaoSaida;
    private JButton botaoVoltar;
    private JScrollPane scrollPane;
    private List<Produto> produtos;
    private DefaultTableModel modeloTabela;


    //métodos
    public MenuHome() {
        super(2, "menuHome");
        criarComponentes();
    }

    @Override
    public void renderizarComponentes() {
        atualizarLista();
        gerenciadorGrafico.add(caixaPesquisa);
        gerenciadorGrafico.add(botaoPesquisa);
        gerenciadorGrafico.add(botaoEntrada);
        gerenciadorGrafico.add(botaoSaida);
        gerenciadorGrafico.add(botaoVoltar);
        gerenciadorGrafico.add(scrollPane);
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void removerComponentes() {
        gerenciadorGrafico.remove(caixaPesquisa);
        gerenciadorGrafico.remove(botaoPesquisa);
        gerenciadorGrafico.remove(botaoEntrada);
        gerenciadorGrafico.remove(botaoSaida);
        gerenciadorGrafico.remove(botaoVoltar);
        gerenciadorGrafico.remove(scrollPane);
    }

    public void atualizarLista(){
        produtos = gerenciadorMongoDB.getProdutos();
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
            25 + 25,
            bounds.width / 3,
            25
        );
        caixaPesquisa.setFont(font);

        ImageIcon iconPesquisa = new ImageIcon(getClass().getResource("./../../assets/lupa.jpg"));
        int widthPesquisa = caixaPesquisa.getBounds().width;
        int heighPesquisa = caixaPesquisa.getBounds().height;
        iconPesquisa.setImage(iconPesquisa.getImage().getScaledInstance(heighPesquisa - 1, heighPesquisa - 2, 1));
        botaoPesquisa = new JButton(iconPesquisa);
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
        botaoEntrada = new JButton("Entrada");
        botaoEntrada.setBounds(
            bounds.width / 2 - 50,
            heighPesquisa,
            150,
            50
        );
        botaoEntrada.addActionListener(this);

        botaoSaida = new JButton("Saida");
        botaoSaida.setBounds(
            botaoEntrada.getBounds().x + botaoEntrada.getBounds().width + 25,
            heighPesquisa,
            150,
            50
        );
        botaoSaida.addActionListener(this);

        botaoVoltar = new JButton("Sair");
        botaoVoltar.setBounds(
            botaoSaida.getBounds().x + botaoSaida.getBounds().width + 25,
            heighPesquisa,
            150,
            50
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

        botaoEntrada.setBackground(Color.LIGHT_GRAY);
        botaoSaida.setBackground(Color.LIGHT_GRAY);
        botaoVoltar.setBackground(Color.LIGHT_GRAY);
        botaoPesquisa.setBackground(Color.GRAY);
        caixaPesquisa.setBackground(Color.LIGHT_GRAY);
        caixaPesquisa.setForeground(Color.WHITE);
        caixaPesquisa.setCaretColor(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Boolean entrou = true;

        if(event.getSource() == botaoPesquisa){
            System.out.println("Pesquisa\n");
        } else if(event.getSource() == botaoVoltar){
            System.out.println("Voltar");
            entrou = GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuLogin");
            GerenciadorEstado.getGerenciadorEstado().setAdministrador(false);
        } else {
            entrou = !entrou;
            if(event.getSource() == botaoEntrada){
                entrou = GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuEntrada");
            } else {
                entrou = GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuSaida");
            }
        }
        if(!entrou){
            JOptionPane.showMessageDialog(null, "Função habilitada apenas para os Administradores.");
        }
    }
    
}
