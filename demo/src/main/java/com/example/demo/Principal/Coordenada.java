package com.example.demo.Principal;

public class Coordenada {
    //atributos
    public int x;
    public int y;

    //métodos
    public Coordenada(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setCoordenada(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void soma(int x, int y){
        this.x += x;
        this.y += y;
    }
    public void subtracao(int x, int y){
        this.x -= x;
        this.y -= y;
    }
}
