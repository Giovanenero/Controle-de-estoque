package com.example.demo.Principal.Gerenciador;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GerenciadorGrafico {
    //atributos
    private static JFrame janela;
    private static GerenciadorGrafico gerenciadorGrafico = null;

    //métodos
    private GerenciadorGrafico(){

    }
    public static GerenciadorGrafico getGerenciadorGrafico(){
        if(gerenciadorGrafico == null){
            gerenciadorGrafico = new GerenciadorGrafico();
        }
        return gerenciadorGrafico;
    }
    public void novoJanela(){
        janela = new JFrame();
        if(janela == null){
            System.out.println("ERROR: janela is null");
            System.exit(0);
        }
        janela.setBounds(0, 0, 1000, 600);
        janela.getContentPane().setBackground(Color.BLACK);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
        janela.setLayout(null);
        atualizarJanela();
    }
    public void add(JTextField caixaTexto){
        janela.add(caixaTexto);
    }
    public void add(JLabel label){
        janela.add(label);
    }
    public void add(JButton botao){
        janela.add(botao);
    }
    public void add(JScrollPane scrollPane){
        janela.add(scrollPane);
    }
    public void atualizarJanela(){
        janela.update(janela.getGraphics());
    }
    public Rectangle getBounds(){
        return janela.getBounds();
    }
    public void remove(JButton botao){
        janela.remove(botao);
    }
    public void remove(JLabel label){
        janela.remove(label);
    }
    public void remove(JTextField caixaTexto){
        janela.remove(caixaTexto);
    }

    public void remove(JScrollPane scrollPane){
        janela.remove(scrollPane);
    }
}
