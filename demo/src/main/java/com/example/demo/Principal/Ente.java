package com.example.demo.Principal;

public class Ente {
    //atributos
    protected int id;
    protected String nome;

    //métodos
    public Ente(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
}
