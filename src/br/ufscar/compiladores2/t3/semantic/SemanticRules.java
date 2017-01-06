package br.ufscar.compiladores2.t3.semantic;

/**
 * Created by lucas on 03/01/17.
 */

import br.ufscar.compiladores2.t3.antlr.*;
import br.ufscar.compiladores2.t3.semantic.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.semantic.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.semantic.util.TabelaDeSimbolos;
import javafx.scene.control.Tab;

import java.util.List;

public class SemanticRules extends jaSONBaseVisitor<String[]>  {

    private PilhaDeTabelas pt;

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {
        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));

        for (jaSONParser.Class_definitionContext  cdc: ctx.class_definition()){
            visitClass_definition(cdc);
        }
        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(ctx.classExtended != null){ //se for uma classe extendida
            escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());

            List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
            for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                escopoAtual.adicionarSimbolo(e.getNome(),e.getTipo(),ctx.classe.getText(),ctx.classExtended.getText());
            }

        }else {
            escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe");
        }
        visitClass_body(ctx.class_body());
        System.out.println(escopoAtual); //TODO retirar

        return null;
    }

    @Override
    public String[] visitClass_body(jaSONParser.Class_bodyContext ctx) {
        for (jaSONParser.VariablesContext  variable: ctx.variables()){
            visitVariables(variable);
        }

        if(ctx.constructors() != null){
            visitConstructors(ctx.constructors());
        }

        //todo implementar construtor e main

        return null;
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();
        String type = visitType(ctx.type())[1];
        String classe = escopoAtual.getUltimaClasseDeclarada();
        escopoAtual.adicionarSimbolo(ctx.id1.getText(),type, classe, null);

        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        return super.visitConstructor_body(ctx);
    }

    @Override
    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public String[] visitAttribute(jaSONParser.AttributeContext ctx) {
        return super.visitAttribute(ctx);
    }

    @Override
    public String[] visitParameters(jaSONParser.ParametersContext ctx) {
        return super.visitParameters(ctx);
    }

    @Override
    public String[] visitType(jaSONParser.TypeContext ctx) {
        String[] s = {"false", ctx.getText()};
        return s;
    }

    @Override
    public String[] visitFunction_main(jaSONParser.Function_mainContext ctx) {
        return super.visitFunction_main(ctx);
    }

    @Override
    public String[] visitFunction_body(jaSONParser.Function_bodyContext ctx) {
        return super.visitFunction_body(ctx);
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        return super.visitArguments(ctx);
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }
}
