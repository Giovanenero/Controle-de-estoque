package com.example.demo.Principal.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
//import java.awt.Rectangle;
import java.util.Date;
//import java.util.Vector;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.example.demo.Principal.Componentes.CaixaTexto;
import com.example.demo.Principal.Entidade.Produto;
//import com.example.demo.Principal.Componentes.CaixaTexto;
import com.example.demo.Principal.Gerenciador.Estado;
import com.example.demo.Principal.Gerenciador.GerenciadorEstado;

public class MenuEntrada extends Estado implements ActionListener {
    
    //atributos
    private JButton botaoConfirmar;
    private JButton botaoCancelar;
    private Vector<CaixaTexto> caixasTexto;
    private JLabel titulo;
    private Date data;

    //métodos
    public MenuEntrada(){
        super(3, "menuEntrada");
        caixasTexto = new Vector<>();
        titulo = new JLabel("Entrada");
        titulo.setBounds(bounds.width / 2 - 200, 50, 400, 80);
        titulo.setFont(new Font("Arial", Font.BOLD, 100));
        data = new Date();

        criarComponentes();
    }

    @Override
    public void criarComponentes() {
        //criando caixa de texto e label para o nome
        int paddingX = 100;
        int paddingY = 200;

        int marginY = 25;

        Rectangle caixa = new Rectangle();
        Rectangle label = new Rectangle();

        caixa.setBounds(
            paddingX,
            paddingY,
            bounds.width / 3,
            25
        );

        label.setBounds(
            paddingX,
            paddingY - 25,
            bounds.width / 3,
            25
        );

        CaixaTexto caixaNome = new CaixaTexto("Nome:", caixa, label);
        caixasTexto.add(caixaNome);

        label.setBounds(
            paddingX,
            caixa.getBounds().y + caixa.getBounds().height + marginY,
            bounds.width / 3,
            25
        );

        caixa.setBounds(
            paddingX,
            label.getBounds().y + label.getBounds().height,
            bounds.width / 3,
            25
        );

        CaixaTexto caixaCodigo = new CaixaTexto("Código:", caixa, label);
        caixasTexto.add(caixaCodigo);

        label.setBounds(
            paddingX,
            caixa.getBounds().y + caixa.getBounds().height + marginY,
            bounds.width / 3,
            25
        );

        caixa.setBounds(
            paddingX,
            label.getBounds().y + label.getBounds().height,
            bounds.width / 3,
            25
        );

        CaixaTexto caixaLote = new CaixaTexto("Lote:", caixa, label);
        caixasTexto.add(caixaLote);

        label.setBounds(
            2 * paddingX + bounds.width / 3,
            paddingY - 25,
            bounds.width / 3,
            25
        );

        caixa.setBounds(
            2 * paddingX + bounds.width / 3,
            label.getBounds().y + marginY,
            bounds.width / 3,
            25
        );

        CaixaTexto caixaQtd = new CaixaTexto("Quantidade:", caixa, label);
        caixasTexto.add(caixaQtd);

        label.setBounds(
            2 * paddingX + bounds.width / 3,
            caixa.getBounds().y + caixa.getBounds().height + marginY,
            bounds.width / 3,
            25
        );

        caixa.setBounds(
            2 * paddingX + bounds.width / 3,
            label.getBounds().y + label.getBounds().height,
            bounds.width / 3,
            25
        );

        CaixaTexto caixaPreco = new CaixaTexto("Preço por unidade:", caixa, label);
        caixasTexto.add(caixaPreco);

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

        titulo.setForeground(Color.LIGHT_GRAY);
    }

    @Override
    public void renderizarComponentes() {
        for(int i = 0; i < caixasTexto.size(); i++){
            CaixaTexto aux = caixasTexto.get(i);
            gerenciadorGrafico.add(aux.getCaixa());
            gerenciadorGrafico.add(aux.getLabel());
        }
        gerenciadorGrafico.add(botaoConfirmar);
        gerenciadorGrafico.add(botaoCancelar);
        gerenciadorGrafico.add(titulo);
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void removerComponentes() {
        for(int i = 0; i < caixasTexto.size(); i++){
            CaixaTexto aux = caixasTexto.get(i);
            aux.apagarTexto();
            gerenciadorGrafico.remove(aux.getCaixa());
            gerenciadorGrafico.remove(aux.getLabel());
        }
        gerenciadorGrafico.remove(botaoConfirmar);
        gerenciadorGrafico.remove(botaoCancelar);
        gerenciadorGrafico.remove(titulo);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == botaoConfirmar){
            boolean tudoCerto = true;
            int i = 0;
            while(i < caixasTexto.size() && tudoCerto){
                JTextField caixa = caixasTexto.get(i).getCaixa();
                if(caixa.getText().equals("")){
                    tudoCerto = false;
                }
                i++;
            }
            if(!tudoCerto){
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos primeiro!");
            } else {
                String nome = caixasTexto.get(0).getCaixa().getText();
                int codigo = Integer.parseInt(caixasTexto.get(1).getCaixa().getText());
                String lote = caixasTexto.get(2).getCaixa().getText();
                int qtd = Integer.parseInt(caixasTexto.get(3).getCaixa().getText());
                float preco = Float.parseFloat(caixasTexto.get(4).getCaixa().getText());
                SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
                String data = formatacao.format(this.data);
                Produto produto = new Produto(codigo, nome, qtd, lote, preco, data);
                gerenciadorMongoDB.addProduto(produto);
                Boolean entrou = GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuHome");
                if(!entrou){
                    System.out.println("Não foi possível acessar o menu home");
                    System.exit(0);
                }
            }
        } else {
            GerenciadorEstado.getGerenciadorEstado().alterarEstado("menuHome");
        }
    }
}
