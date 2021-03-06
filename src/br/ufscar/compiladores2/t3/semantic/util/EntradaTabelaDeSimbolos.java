/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.compiladores2.t3.semantic.util;

import java.util.HashMap;

/**
 *
 * @author daniel
 */
public class EntradaTabelaDeSimbolos {
    private  String nome;
    private  String tipo;
    private  String classe;
    private  String  classeHerdada;

    public EntradaTabelaDeSimbolos(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo; //setando o valor do tipo
        this.classe =  null;
        this.classeHerdada = null;
    }

    public EntradaTabelaDeSimbolos(String nome, String tipo, String classe) {
        this.nome = nome;
        this.tipo = tipo; //setando o valor do tipo
        this.classe = classe;
        this.classeHerdada = null;
    }

    public EntradaTabelaDeSimbolos(String nome, String tipo, String classe, String classeHerdada) {
        this.nome = nome;
        this.tipo = tipo; //setando o valor do tipo
        this.classe = classe;
        this.classeHerdada = classeHerdada;
    }




    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasseHerdada() {
        return classeHerdada;
    }

    public void setClasseHerdada(String classeHerdada) {
        this.classeHerdada = classeHerdada;
    }

    @Override
    public String toString() {
        return nome+" | "+tipo.toString()+" | "+classe+" | "+classeHerdada;
    }
}
