package com.example.demo.Principal.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.example.demo.Principal.Gerenciador.Estado;

public abstract class Menu extends Estado implements ActionListener{

    protected JLabel titulo;
    protected Vector<JButton> vectorBotaos;

    public Menu(Long id, String nome){
        super(id, nome);
        vectorBotaos = new Vector<>();
    }

    public abstract void renderizarComponentes();
    public abstract void removerComponentes();
    public abstract void criarComponentes();
    public abstract void actionPerformed(ActionEvent event);
    protected void centralizarTexto() {
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setVerticalAlignment(SwingConstants.CENTER);
    }
    
}
