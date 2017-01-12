package br.ufscar.compiladores2.t3.Generate;

import br.ufscar.compiladores2.t3.Generate.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.Generate.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.Generate.util.TabelaDeSimbolos;
import br.ufscar.compiladores2.t3.antlr.jaSONBaseVisitor;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import org.antlr.v4.runtime.tree.TerminalNode;


import java.util.List;

/**
 * Created by leo on 10/01/17.
 */

public class generateRules extends jaSONBaseVisitor<String[]> {
    private PilhaDeTabelas pt;

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {
        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));
        System.out.println("{");

        for (jaSONParser.Class_definitionContext  cdc: ctx.class_definition()){
            visitClass_definition(cdc);
        }
        System.out.println("}");
        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(ctx.classExtended != null){ //se for uma classe extendida
        // todo implementar o caso de herança

        // se nao for herança
        }else {
            if (ctx.class_body().constructors().parameters() != null) {
                EntradaTabelaDeSimbolos etds =
                        new EntradaTabelaDeSimbolos(ctx.classe.getText(), "classe", ctx.classe.getText(), null, "[", null, null);
                escopoAtual.adicionarSimbolo(etds);
            }
        }

        visitClass_body(ctx.class_body());

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
            EntradaTabelaDeSimbolos etds =
                    new EntradaTabelaDeSimbolos(tn.toString(), type, classe, null, "\""+tn.toString()+"\":", null, null);
            escopoAtual.adicionarSimbolo(etds);
        }
        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        if(ctx.parameters() != null){
            visitParameters(ctx.parameters());
        }
        visitConstructor_body(ctx.constructor_body());

        return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        TabelaDeSimbolos params = pt.topo();
        TabelaDeSimbolos glboal = pt.getGlobalTable();

        System.out.println(glboal);
        System.out.println(params);

        return null;
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
        String classe = pt.topo().getUltimaClasseDeclarada();
        TabelaDeSimbolos params = new TabelaDeSimbolos("params");

        //add os parametros na tabela
        String type = null;
        String id = null;

        for(int i =0;i<ctx.IDENT().size();i++){
            type = visitType(ctx.type(i))[1];
            id = ctx.IDENT(i).toString();
            params.adicionarSimbolo(id,type,classe);
        }

        pt.empilhar(params);

        return null;

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
    public String[] visitObject_declaration(jaSONParser.Object_declarationContext ctx) {
        return super.visitObject_declaration(ctx);
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
