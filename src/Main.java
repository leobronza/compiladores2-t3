import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.RecognitionException;


public class Main {
    private final static String CAMINHO_CASOS_TESTE =
            "";

    private final static String CAMINHO_CASOS_SAIDA =
            "";

    public static void main(String[] args) throws IOException, RecognitionException, NullPointerException{
        File diretorioCasosTeste = new File(CAMINHO_CASOS_TESTE);
        File[] casosTeste = diretorioCasosTeste.listFiles();

        if(casosTeste != null){
            Arrays.sort(casosTeste);
            for (File casoTeste : casosTeste) {

                //ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(casoTeste));
                //GrammarLALexer lexer = new GrammarLALexer(input);
                //CommonTokenStream tokens = new CommonTokenStream(lexer);
                //GrammarLAParser parser = new GrammarLAParser(tokens);
                //GrammarLAParser.ProgramaContext context = parser.programa();
                //AnalisadorSemantico semantico = new AnalisadorSemantico();

                System.out.println("================================================================================");
                System.out.println(casoTeste.getName());
                System.out.println("--------------------------------------------------------------------------------");

                //semantico.visitPrograma(context);

                System.out.println("############################### ESPERADO (ORIGINAL) ##############################");
                List<String> out = Files.readAllLines(Paths.get(CAMINHO_CASOS_SAIDA+"/"+casoTeste.getName()), StandardCharsets.UTF_8);
                for (String t : out){
                    System.out.println(t);
                }

                System.out.println("\n");
            }
        }
    }
}
