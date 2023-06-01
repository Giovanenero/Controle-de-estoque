package com.example.demo.Principal.Gerenciador;

import java.awt.Font;
import java.awt.Rectangle;

import com.example.demo.Principal.Ente;

public abstract class Estado extends Ente {
    //atributos
    protected Font font;
    protected Rectangle bounds;
    protected GerenciadorMongoDB gerenciadorMongoDB = null;
    protected static GerenciadorGrafico gerenciadorGrafico = null;
    protected static GerenciadorUsuario gerenciadorUsuario = null;
    //métodos
    protected Estado(Long id, String nome){
        super(id, nome);
        gerenciadorMongoDB = new GerenciadorMongoDB();
        gerenciadorGrafico = GerenciadorGrafico.getGerenciadorGrafico();
        gerenciadorUsuario = GerenciadorUsuario.getGerenciadorUsuario();
        font = new Font("Arial", Font.ITALIC, 15);
        bounds = gerenciadorGrafico.getBounds();
    }

    public abstract void renderizarComponentes();
    public abstract void removerComponentes();
    public abstract void criarComponentes();
}
