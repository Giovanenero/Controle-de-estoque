package com.example.demo.Principal.Componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CaixaTexto {
    //atributos
    private JTextField caixa;
    private JLabel label;
    private Font font;

    //métodos
    public CaixaTexto(String nome, Rectangle boundsCaixa, Rectangle boundsLabel){
        font = new Font("Arial", Font.ITALIC, 15);
        label = new JLabel(nome);
        label.setBounds(boundsLabel);
        label.setFont(font);
        caixa = new JTextField();
        caixa.setBounds(boundsCaixa);
        caixa.setBackground(Color.GRAY);
        caixa.setCaretColor(Color.white);
        caixa.setForeground(Color.WHITE);
        label.setForeground(Color.WHITE);
    }

    public JTextField getCaixa(){
        return caixa;
    }

    public void setTexto(String nome){
        label.setText(nome);
    }

    public JLabel getLabel(){
        return label;
    }

    public void apagarTexto(){
        caixa.setText("");
    }

}
