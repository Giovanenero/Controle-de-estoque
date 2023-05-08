package com.example.demo.Principal.Estruturas;

public final class Modificacao {
    public String nomeProduto;
    public int idProduto;
    public String nomeUsuario;
    public int idUsuario;
    public String data;

    public Modificacao(String nomeProduto, String nomeUsuario, int idProduto, int idUsuario, String data){
        this.nomeProduto = nomeProduto;
        this.nomeUsuario = nomeUsuario;
        this.idProduto = idProduto;
        this.idUsuario = idUsuario;
        this.data = data;
    }

    public String getNomeProduto(){
        return nomeProduto;
    }

    public String nomeUsuario(){
        return nomeUsuario;
    }

    public int getIdProduto(){
        return idProduto;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public String getData(){
        return data;
    }
    
}
