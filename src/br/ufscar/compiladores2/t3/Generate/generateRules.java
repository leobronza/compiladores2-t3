package br.ufscar.compiladores2.t3.Generate;

import br.ufscar.compiladores2.t3.antlr.jaSONBaseVisitor;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import br.ufscar.compiladores2.t3.semantic.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.semantic.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.semantic.util.TabelaDeSimbolos;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.antlr.runtime.tree.TreeWizard;
import org.antlr.v4.runtime.tree.TerminalNode;


import java.util.List;

/**
 * Created by leo on 10/01/17.
 */

public class generateRules extends jaSONBaseVisitor<String[]> {
    private jaSONParser.Constructor_bodyContext ctxx;
    private List<jaSONParser.AssignmentContext> assig;

    private PilhaDeTabelas pt;
    private TabelaDeSimbolos class_parameters;

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
            if (escopoAtual.existeSimbolo(ctx.classExtended.getText())){
                escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());
                List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
                for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                    escopoAtual.adicionarSimbolo(e.getNome(),e.getTipo(),ctx.classe.getText(),ctx.classExtended.getText());
                }
            }
        }else{
            escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe");
        }
        visitClass_body(ctx.class_body());
        return null;
    }

    @Override
    public String[] visitClass_body(jaSONParser.Class_bodyContext ctx) {
        for (jaSONParser.VariablesContext  variable: ctx.variables()){
            visitVariables(variable);
        }
        visitConstructors(ctx.constructors());
        if(ctx.function_main() !=  null){
            visitFunction_main(ctx.function_main());
        }
        return null;
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();
        String type = visitType(ctx.type())[1];
        String classe = escopoAtual.getUltimaClasseDeclarada();
        for(TerminalNode tn : ctx.IDENT() ){
            escopoAtual.adicionarSimbolo(tn.toString(), type, classe, null);
        }

        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        ctxx = ctx.constructor_body();
        visitConstructor_body(ctx.constructor_body());
        return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        if(ctx.assignment().size() > 0)
        assig = ctx.assignment();

        for (jaSONParser.AssignmentContext context: ctx.assignment()){
            //visitAssignment(context);
        }
        return null;
    }

    @Override
    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        String type = null;
        System.out.println(ctx.STRING());
        if(ctx.IDENT() != null){

        }else if(ctx.STRING() != null){
            System.out.println("aqi");
            type = "String";
        }else if(ctx.NUM_INT() != null) {
            type = "int";
        }else if(ctx.NUM_FLOAT() != null){
            type = "float";
        }else if(ctx.BOOLEAN() != null){
            type = "boolean";

        }

        return null;

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
        visitFunction_body(ctx.function_body());
        return null;
    }

    @Override
    public String[] visitFunction_body(jaSONParser.Function_bodyContext ctx) {

        for (jaSONParser.Object_declarationContext obj: ctx.object_declaration()){
            visitObject_declaration(obj);
        }

        return null;
    }

    @Override
    public String[] visitObject_declaration(jaSONParser.Object_declarationContext ctx) {
        //visitConstructor_body(ctxx);
        visitAssignment(assig.get(0));
        //System.out.println(assig);
        return null;
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        return super.visitArguments(ctx);
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        String[] s = {"true",null};
        if (ctx.IDENT() != null) {
            s[1] = "ident";
            return s;
        }else if(ctx.STRING() != null){
            s[1] = "String";
            return s;
        }else if(ctx.NUM_FLOAT() != null){
            s[1] = "double";
            return s;
        }else if(ctx.NUM_INT() != null){
            s[1] = "int";
            return s;
        }

        return null;
    }
}
