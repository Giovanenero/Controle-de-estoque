package com.example.demo.Principal.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.example.demo.Principal.Componentes.CaixaTexto;
import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Gerenciador.Estado;
import com.example.demo.Principal.Gerenciador.GerenciadorEstado;

public class MenuSaida extends Estado implements ActionListener {
    //atributos
    private JLabel titulo;
    private JButton botaoConfirmar;
    private JButton botaoCancelar;
    private JButton botaoPesquisar;
    private Vector<CaixaTexto> caixasTexto;
    private List<Produto> listProdutos;
    private Produto produto;

    public MenuSaida() {
        super(4, "menuSaida");
        caixasTexto = new Vector<>();
        listProdutos = new ArrayList<>();
        criarComponentes();
    }

    @Override
    public void renderizarComponentes() {
        gerenciadorGrafico.add(titulo);
        gerenciadorGrafico.add(botaoConfirmar);
        gerenciadorGrafico.add(botaoCancelar);
        gerenciadorGrafico.add(caixasTexto.get(0).getCaixa());
        gerenciadorGrafico.add(caixasTexto.get(0).getLabel());
        gerenciadorGrafico.add(botaoPesquisar);
        listProdutos = gerenciadorMongoDB.getProdutos();
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void removerComponentes() {
        gerenciadorGrafico.remove(titulo);
        gerenciadorGrafico.remove(botaoConfirmar);
        gerenciadorGrafico.remove(botaoCancelar);
        for(int i =0; i < caixasTexto.size(); i++){
            CaixaTexto caixaTexto = caixasTexto.get(i);
            gerenciadorGrafico.remove(caixaTexto.getCaixa());
            gerenciadorGrafico.remove(caixaTexto.getLabel());
        }
        gerenciadorGrafico.remove(botaoPesquisar);
    }

    @Override
    public void criarComponentes() {
        titulo = new JLabel("Saida");
        titulo.setBounds(bounds.width / 2 - 135, 50, 270, 80);
        titulo.setFont(new Font("Arial", Font.BOLD, 100));

        int paddingY = 200;

        Rectangle caixa = new Rectangle();
        Rectangle label = new Rectangle();

        label.setBounds(
            bounds.width / 2 - 150,
            paddingY,
            300,
            25
        );

        caixa.setBounds(
            bounds.width / 2 - 150,
            label.getBounds().y + label.getBounds().height,
            300,
            25
        );

        CaixaTexto caixaCod = new CaixaTexto("Código do produto:", caixa, label);
        caixasTexto.add(caixaCod);

        ImageIcon iconPesquisa = new ImageIcon(getClass().getResource("./../../assets/lupa.jpg"));
        int widthPesquisa = caixaCod.getCaixa().getBounds().width;
        int heighPesquisa = caixaCod.getCaixa().getBounds().height;
        iconPesquisa.setImage(iconPesquisa.getImage().getScaledInstance(heighPesquisa - 1, heighPesquisa - 2, 1));
        botaoPesquisar = new JButton(iconPesquisa);
        botaoPesquisar.setBounds(
            widthPesquisa + caixaCod.getCaixa().getBounds().x,
            caixaCod.getCaixa().getBounds().y,
            heighPesquisa,
            heighPesquisa
        );
        botaoPesquisar.setBorderPainted(false);
        botaoPesquisar.addActionListener(this);

        botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setBounds(
            bounds.width / 2 - 200 - 20,
            bounds.height - 150,
            200,
            50
        );
        botaoConfirmar.addActionListener(this);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(
            botaoConfirmar.getBounds().x + botaoConfirmar.getBounds().width + 40,
            bounds.height - 150,
            200,
            50
        );
        botaoCancelar.addActionListener(this);

        label.setBounds(
            caixa.getBounds().x,
            caixa.getBounds().y + caixa.getBounds().height + 25,
            300,
            25
        );

        caixa.setBounds(
            label.getBounds().x,
            label.getBounds().y + label.getBounds().height,
            300,
            25
        );

        CaixaTexto caixaQtd = new CaixaTexto("", caixa, label);
        caixasTexto.add(caixaQtd);

        titulo.setForeground(Color.LIGHT_GRAY);

    }

    private void renderizarProduto(){
        CaixaTexto caixaTexto = caixasTexto.get(1);
        caixaTexto.setTexto("Quantidade atual " + produto.getQtd());
        gerenciadorGrafico.add(caixaTexto.getCaixa());
        gerenciadorGrafico.add(caixaTexto.getLabel());
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botaoConfirmar){
            String textoQtd = caixasTexto.get(1).getCaixa().getText().trim();
            if(textoQtd.equals("")){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            } else {
                int qtd;
                try {
                    qtd = Integer.parseInt(textoQtd);
                    if(qtd > produto.getQtd()){
                        JOptionPane.showMessageDialog(null, "A saída deste produto é superior a quantidade existente.");
                    } else {
                        gerenciadorMongoDB.alterarProduto(produto, qtd);
                        GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuHome");
                        caixasTexto.get(1).getCaixa().setText("");
                    }

                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Por favor, coloque somente números inteiros");
                }
            }
        } else if(event.getSource() == botaoCancelar){
            GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuHome");
        } else {
            String textCodigo = caixasTexto.get(0).getCaixa().getText().trim();
            if(textCodigo.equals("")){
                JOptionPane.showMessageDialog(null, "Por favor, preencha a barra de pesquisa!");
            } else {
                int codigo;
                try {
                    codigo = Integer.parseInt(textCodigo);
                    int i = 0;
                    Boolean naoAchou = true;
                    while(i < listProdutos.size() && naoAchou){
                        if(listProdutos.get(i).getId() == codigo){
                            naoAchou = !naoAchou;
                            produto = listProdutos.get(i);
                        }
                        i++;
                    }
                    if(naoAchou){
                        JOptionPane.showMessageDialog(null, "Código invalido.");
                    } else {
                        caixasTexto.get(0).getCaixa().setText("");
                        renderizarProduto();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Por favor, coloque somente números inteiros");
                }
            }
        }
    }
    
}