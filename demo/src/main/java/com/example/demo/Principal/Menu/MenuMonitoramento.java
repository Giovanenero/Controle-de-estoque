package com.example.demo.Principal.Menu;

import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MenuMonitoramento extends Menu {
    private JScrollPane scrollPane;
    private DefaultTableModel modeloTabela;

    public MenuMonitoramento(int id, String nome) {
        super(123456, "menuMonitoramento");
        criarComponentes();
    }
    
    @Override
    public void renderizarComponentes() {
        //renderizar componentes
        gerenciadorGrafico.atualizarJanela();
    }
    @Override
    public void removerComponentes() {
        //remover componentes
    }
    @Override
    public void criarComponentes() {

    }
    @Override
    public void actionPerformed(ActionEvent event) {

    }
}
