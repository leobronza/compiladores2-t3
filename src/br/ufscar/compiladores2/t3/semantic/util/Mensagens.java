/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.compiladores2.t3.semantic.util;

/**
 *
 * @author daniel
 */
public class Mensagens {
    public static void erroVariavelNaoExiste(int numLinha, int numColuna, String variavel) {
        Saida.println(numLinha+","+(numColuna+1)+":Variavel "+variavel+" nao amarrada");
    }
}
