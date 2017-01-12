package br.ufscar.compiladores2.t3.semantic;

import br.ufscar.compiladores2.t3.antlr.jaSONLexer;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lucas on 03/01/17.
 */
public class SemanticMain {

    private final static String CAMINHO_CASOS_TESTE =
//            "/media/leo/421B33D37F4D6D97/Comp2/compiladores2-t3/src/br/ufscar/compiladores2/t3/examples/errados_semanticos_input/Ex3.java";
    "/home/lucas/Dropbox/bcc/2016/2sem/cc2/compiladores2-t3/src/br/ufscar/compiladores2/t3/examples/errados_semanticos_input/Ex3.java";

    public static void main(String[] args) throws IOException, RecognitionException, NullPointerException{
        File file = new File(CAMINHO_CASOS_TESTE);

        System.out.println(file.getName());
        System.out.println("--------------------------------------------------------------------------------");

        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));
        jaSONLexer lexer = new jaSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        jaSONParser parser = new jaSONParser(tokens);
        jaSONParser.ProgramContext context =  parser.program();

        SemanticRules semantico = new SemanticRules();
        semantico.visitProgram(context);

    }
}
