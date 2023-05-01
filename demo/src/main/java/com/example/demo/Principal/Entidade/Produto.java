package com.example.demo.Principal.Entidade;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.demo.Principal.Ente;

public class Produto extends Ente {
    //atributos
    private float preco;
    private int qtd;
    private String lote;
    private float valorTotal;
    private String dataModificacao;
    private Date data;

    //métodos
    public Produto(int id, String nome, int qtd, String lote, float preco, String data){
        super(id, nome);
        this.qtd = qtd;
        this.lote = lote;
        this.preco = preco;
        this.dataModificacao = data;
        valorTotal = preco * qtd;
        this.data = new Date();
    }

    public float getPreco(){
        return preco;
    }

    public String getLote(){
        return lote;
    }

    public int getQtd(){
        return qtd;
    }

    public void setQtd(int qtd){
        this.qtd = qtd;
        SimpleDateFormat formatacao = new SimpleDateFormat("dd/MM/yyyy");
        String data = formatacao.format(this.data);
        this.dataModificacao = data;
        this.valorTotal = qtd * preco;
    }


    public float getValorTotal(){
        return valorTotal;
    }

    public String getDataModificacao(){
        return dataModificacao;
    }
}
