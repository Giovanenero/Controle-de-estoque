package com.example.demo.Principal.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.example.demo.Principal.Componentes.CaixaTexto;
import com.example.demo.Principal.Entidade.Usuario;
import com.example.demo.Principal.Gerenciador.GerenciadorEstado;
import com.example.demo.Principal.Gerenciador.GerenciadorGrafico;

public class MenuLogin extends Menu {
    //atributos
    private Vector<CaixaTexto> caixasTexto;

    //métodos
    public MenuLogin(){
        super(Long.parseLong("1"), "menuLogin");
        gerenciadorGrafico = GerenciadorGrafico.getGerenciadorGrafico();
        if(gerenciadorGrafico == null){
            System.out.println("Gerenciador Gráfico eh null");
            System.exit(1);
        }
        titulo = new JLabel("Login");
        titulo.setBounds(bounds.width / 2 - 250, 75, 500, 125);
        centralizarTexto();
        titulo.setFont(new Font("Arial", Font.BOLD, 100));
        caixasTexto = new Vector<>();
        criarComponentes();
    }

    @Override
    public void criarComponentes(){
        int width = 300;
        int height = 25;

        Rectangle caixa = new Rectangle();
        Rectangle label = new Rectangle();

        label.setBounds(
            bounds.width / 2 - (width / 2),
            bounds.height / 2 - 50,
            width,
            height
        );

        caixa.setBounds(
            label.getBounds().x,
            label.getBounds().y + label.getBounds().height,
            width,
            height
        );

        CaixaTexto nomeCaixa = new CaixaTexto("Login:", caixa, label);
        caixasTexto.add(nomeCaixa);

        label.setBounds(
            caixa.getBounds().x,
            caixa.getBounds().y + caixa.getBounds().height,
            width,
            height
        );

        caixa.setBounds(
            label.getBounds().x,
            label.getBounds().y + label.getBounds().height,
            width,
            height
        );

        CaixaTexto senhaCaixa = new CaixaTexto("Senha:", caixa, label);
        caixasTexto.add(senhaCaixa);

        //Criando botão entrar;
        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(
            bounds.width / 2 - (width / 2),
            label.getBounds().y + caixa.getBounds().height * 3,
            width / 2 - 10,
            height
        );
        botaoEntrar.setFont(font);
        botaoEntrar.setBackground(Color.LIGHT_GRAY);
        botaoEntrar.addActionListener(this);
        vectorBotaos.add(botaoEntrar);

        //Criando botão cadastrar
        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(
            bounds.width / 2 + 10,
            label.getBounds().y + caixa.getBounds().height * 3,
            width / 2 - 10,
            height
        );
        botaoCadastrar.setBackground(Color.LIGHT_GRAY);
        titulo.setForeground(Color.LIGHT_GRAY);
        botaoCadastrar.setFont(font);
        botaoCadastrar.addActionListener(this);
        vectorBotaos.add(botaoCadastrar);
    }
    
    @Override
    public void renderizarComponentes(){
        for(int i = 0; i < caixasTexto.size(); i++){
            CaixaTexto caixaTexto = caixasTexto.get(i);
            gerenciadorGrafico.add(caixaTexto.getCaixa());
            gerenciadorGrafico.add(caixaTexto.getLabel());
        }
        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.add(botao);
        }
        gerenciadorGrafico.add(titulo);
        gerenciadorGrafico.atualizarJanela();
    }

    @Override
    public void removerComponentes(){
        for(int i = 0; i < caixasTexto.size(); i++){
            CaixaTexto caixaTexto = caixasTexto.get(i);
            gerenciadorGrafico.remove(caixaTexto.getLabel());
            gerenciadorGrafico.remove(caixaTexto.getCaixa());
        }
        for(int i = 0; i < vectorBotaos.size(); i++){
            JButton botao = vectorBotaos.get(i);
            gerenciadorGrafico.remove(botao);
        }
        gerenciadorGrafico.remove(titulo);
    }

    public static void alterarEstado(String nome){
        GerenciadorEstado.getGerenciadorEstado().alterarEstado(nome);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        String caixaLogin = caixasTexto.get(0).getCaixa().getText();
        String caixaSenha = caixasTexto.get(1).getCaixa().getText();
        String login = caixaLogin.trim();
        String senha = caixaSenha.trim();
        if(!login.equals("") && !senha.equals("")){
            Usuario usuario = gerenciadorMongoDB.usuarioExiste(login, senha);
            JButton botaoEntrar = vectorBotaos.get(0);
            if(event.getSource() == botaoEntrar){
                if(usuario != null){
                    GerenciadorEstado gerenciador = GerenciadorEstado.getGerenciadorEstado();
                    gerenciadorUsuario.setUsuario(usuario);
                    gerenciadorMongoDB.addMonitoramento(null, usuario, "login");
                    Boolean entrou = gerenciador.alterarEstado("menuHome");
                    caixasTexto.get(0).getCaixa().setText("");
                    caixasTexto.get(1).getCaixa().setText("");
                    if(!entrou){
                        System.out.println("Não foi possível fazer login");
                        System.exit(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário não existe!");
                }
            } else {
                if(usuario == null){
                    gerenciadorMongoDB.registrarUsuario(login, senha);
                    gerenciadorMongoDB.addMonitoramento(null, usuario, "criar conta");
                    caixasTexto.get(0).getCaixa().setText("");
                    caixasTexto.get(1).getCaixa().setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário já existe!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
        }
    }
}
