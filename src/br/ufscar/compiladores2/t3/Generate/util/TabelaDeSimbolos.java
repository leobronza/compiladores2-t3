/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.compiladores2.t3.Generate.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class TabelaDeSimbolos {
    private String escopo;
    private ArrayList<EntradaTabelaDeSimbolos> simbolos;
    
    public TabelaDeSimbolos(String escopo) {
        simbolos = new ArrayList<EntradaTabelaDeSimbolos>();
        this.escopo = escopo;
    }
    
    public void adicionarSimbolo(String nome, String tipo) {

        simbolos.add(new EntradaTabelaDeSimbolos(nome,tipo));
    }

    public void adicionarSimbolo(EntradaTabelaDeSimbolos edts) {
        simbolos.add(edts);
    }

    public void adicionarSimbolo(String nome, String tipo, String classe) {

        simbolos.add(new EntradaTabelaDeSimbolos(nome,tipo, classe));
    }

    public void adicionarSimbolo(String nome, String tipo, String classe, String classeHerdada) {

        simbolos.add(new EntradaTabelaDeSimbolos(nome,tipo, classe, classeHerdada));
    }

    /*
    * Retorna a ultima posicao da tabela ocupada por um simbolo, retorna -1 se a tabela estiver vazia
    * */
    public int getUltimaPosicaoOcupada(){
        return (simbolos.size()==0)? -1: simbolos.size()-1;
    }

    public String getUltimaClasseDeclarada(){
        int i = getUltimaPosicaoOcupada();
        if(simbolos.get(i).getTipo().equalsIgnoreCase("classe")){
            return simbolos.get(i).getNome();
        }else{
            return simbolos.get(i).getClasse();
        }
    }

    //procura o simbolo em todos os escopos, começando do atual para os mais geral até chegar no global
    public boolean existeSimbolo(String nome) {
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    //procura o simbolo em todos os escopos, começando do atual para os mais geral até chegar no global
    public int getPosicaoSimbolo(String nome) {
        int i =0;
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    //Verifica se na tabela de simbolos existe um simbolo com o nome X e tipo Y
    public boolean existeSimboloComTipo(String nome, String tipo) {
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome) && etds.getTipo().equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    //Verifica se na tabela de simbolos existe um simbolo com o nome X e tipo Y
    public boolean existeSimboloComClasse(String nome, String classe) {
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome) && etds.getClasse() != null && etds.getClasse().equals(classe)) {
                return true;
            }
        }
        return false;
    }

    public String getTipoSimboloComClasse(String nome, String classe) {
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome) && etds.getClasse().equals(classe)) {
                return etds.getTipo();
            }
        }
        return null;
    }

       /*
    * Retorna o nome de um simbolo na posicao indicada. Retorna null se a posicao nao existe
    * */
    public String getNomeSimbolo(int posicao){
       if(posicao<simbolos.size()){
           String nome = simbolos.get(posicao).getNome();
           return nome;
       }else{
           return null;
       }
    }
    public String getClasse(int posicao){
        if(posicao<simbolos.size()){
            String classe = simbolos.get(posicao).getClasse();
            return classe;
        }else{
            return null;
        }
    }
    public String getClasseHerdada(int posicao){
        if(posicao<simbolos.size()){
            String classe = simbolos.get(posicao).getClasseHerdada();
            return classe;
        }else{
            return null;
        }
    }
    //retorna todos as entradas com o atributo classe = ao especificado
    public List<EntradaTabelaDeSimbolos> getTodasEntradaDaClasse(String classe){
        List<EntradaTabelaDeSimbolos> syn = new ArrayList<>();

        for (EntradaTabelaDeSimbolos e: simbolos){
            if(e.getClasse() != null && e.getClasse().equalsIgnoreCase(classe) && !e.getTipo().equalsIgnoreCase("classe")){
                syn.add(e);
            }
        }

        return syn;
    }

    public String getEscopo() {
        return escopo;
    }

    @Override
    public String toString() {
        String ret = "Escopo: "+escopo;
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            ret += "\n   "+etds;
        }
        return ret;
    }
}
