package com.example.demo.Principal.Gerenciador;

import java.awt.Font;
import java.awt.Rectangle;

import com.example.demo.Principal.Ente;

public abstract class Estado extends Ente {
    //atributos
    private String nome;
    protected Font font;
    protected Rectangle bounds;
    protected static GerenciadorMongoDB gerenciadorMongoDB = null;
    protected static GerenciadorGrafico gerenciadorGrafico = null;
    //métodos
    protected Estado(int id, String nome){
        super(id, nome);
        this.nome = nome;
        gerenciadorMongoDB = GerenciadorMongoDB.getGerenciadorMongoDB();
        gerenciadorGrafico = GerenciadorGrafico.getGerenciadorGrafico();
        font = new Font("Arial", Font.ITALIC, 15);
        bounds = gerenciadorGrafico.getBounds();
    }

    public abstract void renderizarComponentes();
    public abstract void removerComponentes();
    public abstract void criarComponentes();

    public String getNome(){
        return nome;
    }
}
