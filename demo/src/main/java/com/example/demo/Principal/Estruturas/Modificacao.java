package com.example.demo.Principal.Estruturas;

public final class Modificacao {
    public Long idProduto;
    public Long idUsuario;
    public String data;
    public String tipo;
    public int saldoAnterior;
    public int novoSaldo;

    public Modificacao(
        Long idProduto, 
        Long idUsuario, 
        int saldoAnterior,
        int novoSaldo,
        String tipo,
        String data
    ){
        this.idProduto = idProduto;
        this.idUsuario = idUsuario;
        this.data = data;
        this.tipo = tipo;
        this.saldoAnterior = saldoAnterior;
        this.novoSaldo = novoSaldo;
    }

    public Long getIdProduto(){
        return idProduto;
    }

    public Long getIdUsuario(){
        return idUsuario;
    }

    public String getData(){
        return data;
    }

    public String getTipo(){
        return tipo;
    }

    public int getSaldoAnterior(){
        return saldoAnterior;
    }

    public int getNovoSaldo(){
        return novoSaldo;
    }
    
}
