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
    private PilhaDeTabelas pt;
    private TabelaDeSimbolos class_parameters;


    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {

        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));

//        for (jaSONParser.Class_definitionContext  cdc: ctx.class_definition()){
//            visitClass_definition(cdc);
//        }

        for(jaSONParser.Class_definitionContext  cdc: ctx.class_definition())
        if(cdc.class_body().function_main() != null)
            visitClass_definition(cdc);

        for(jaSONParser.Class_definitionContext  cdc: ctx.class_definition())
            if(cdc.class_body().function_main() == null)
                visitClass_definition(cdc);


        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(ctx.classExtended != null){ //se for uma classe extendida
            if (escopoAtual.existeSimbolo(ctx.classExtended.getText())){
                //escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());
                List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
                for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                    //escopoAtual.adicionarSimbolo(e.getNome(),e.getTipo(),ctx.classe.getText(),ctx.classExtended.getText());
                }
            }
        }else{
            //escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe");
        }
        visitClass_body(ctx.class_body());
        return null;
    }

    @Override
    public String[] visitClass_body(jaSONParser.Class_bodyContext ctx) {

        if(ctx.function_main() !=  null){
            visitFunction_main(ctx.function_main());
        }
        for (jaSONParser.VariablesContext  variable: ctx.variables()){
            visitVariables(variable);
        }
        visitConstructors(ctx.constructors());

        return null;
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();
        String type = visitType(ctx.type())[1];
        String classe = escopoAtual.getUltimaClasseDeclarada();
        for(TerminalNode tn : ctx.IDENT() ){
            //escopoAtual.adicionarSimbolo(tn.toString(), type, classe, null);
        }

        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(visitParameters(ctx.parameters()) != null) {
            String s[] = visitParameters(ctx.parameters());
            int z = 1;
            for (int i = 0; i < escopoAtual.gettabelasize(); i++)
                if (escopoAtual.getClasse(i).equals(ctx.IDENT().toString())) {
                    escopoAtual.editarClasseHerdada(i, s[z]);

                    z++;
                    if(z == s.length)
                        z = 1;
                }

        }

        visitConstructor_body(ctx.constructor_body());
        return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        int size = 0;
        // Todo fazer super()

        //Resolvendo de cima para baixo
        //System.out.println(escopoAtual);
        if(ctx.children != null) {
            System.out.println("{");
            for (int i = 0; i < escopoAtual.gettabelasize(); i++) {
                if (escopoAtual.getClasseHerdada(i) != null)
                    size++;
            }
            for (int i = 0; i < size; i++) {
                System.out.println("\t \"" + escopoAtual.getTipoSimboloComClasse(escopoAtual.getNomeSimbolo(i), escopoAtual.getClasse(i)) + "\": [");


                for (jaSONParser.AssignmentContext context : ctx.assignment()) {
                    visitAssignment(context);
                }

                if (escopoAtual.getClasse(0) != null) {
                    System.out.println("\t],");
                }
                else {
                    System.out.println("\t]");
                    break;
                }
            }
            System.out.println("}");
        }
        return null;
    }


    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();
        String s[] = visitAttribute(ctx.attribute());

        System.out.print("\t\t\"" + s[1] + "\": ");

        String type = null;
        if(ctx.IDENT() != null){
            for(int i = 0; i < escopoAtual.gettabelasize() ; i ++){
                if(escopoAtual.getClasseHerdada(i).equals(ctx.IDENT().getText())) {
                    System.out.println(escopoAtual.getNomeSimbolo(i) + ",");
                    escopoAtual.removeSimbolosAPartir(i,i);
                    break;
                }
            }
        }else if(ctx.STRING() != null){
            System.out.println(ctx.STRING().getText()+ ",\n");
        }else if(ctx.NUM_INT() != null) {
            System.out.println(ctx.NUM_INT().getText()+ ",\n");
        }else if(ctx.NUM_FLOAT() != null){
            System.out.println(ctx.NUM_FLOAT().getText()+ ",\n");
        }else if(ctx.BOOLEAN() != null){
            System.out.println(ctx.BOOLEAN().getText() + ",\n");
        }


        return null;

    }

    @Override
    public String[] visitAttribute(jaSONParser.AttributeContext ctx) {
        String s[] = {"true", ctx.IDENT().toString()};
        return s;
    }

    @Override
    public String[] visitParameters(jaSONParser.ParametersContext ctx) {

        if(ctx != null) {
            String s [] = new String[ctx.IDENT().size()+1];
        s[0] = "true";

        for(int i = 1 ; i < ctx.IDENT().size()+1 ; i++ )
            s[i] = ctx.IDENT(i-1).toString();

            return s;
        }

        return null;

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
        String[] s = visitArguments(ctx.arguments());

        TabelaDeSimbolos escopoAtual = pt.topo();

        for(int i = 1 ; i < s.length; i = i +2)
        escopoAtual.adicionarSimbolo(s[i+1], ctx.name.getText(), ctx.t2.getText(),null);
        return null;
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        String[] returnn = new String[ctx.value().size()*2+1];
        int z = 1;

        returnn[0] = "true";

        for(int i = 0 ; i < ctx.value().size() ; i++) {
            returnn[z]  = visitValue(ctx.value(i))[1];
            z++;
            returnn[z] = visitValue(ctx.value(i))[2];
            z++;

        }
        return returnn;
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        String[] s = {"true",null,null};
        if (ctx.IDENT() != null) {
            s[1] = "ident";
            s[2] = ctx.IDENT().toString();
            return s;
        }else if(ctx.STRING() != null){
            s[1] = "String";
            s[2] = ctx.STRING().toString();
            return s;
        }else if(ctx.NUM_FLOAT() != null){
            s[1] = "double";
            s[2] = ctx.NUM_FLOAT().toString();
            return s;
        }else if(ctx.NUM_INT() != null){
            s[1] = "int";
            s[2] = ctx.NUM_INT().toString();
            return s;
        }

        return null;
    }
}
