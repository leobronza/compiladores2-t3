# compiladores2-t3
trabalho 3  da diciplinas de compiladores 2, implementação de uma linguagem inventada.

# Semântica

## Tabela de Simbolos

foram implementadas duas tabelas de simbolos no analizador semântico. A primeira,
chamada _global_, armazena os nomes das classes e atributos dessas classes e a segunda,
 chamada _parameters_, armazena as classes e a seguencia dos atributos declarados nos
 construtores dessas classes. Um exemplo dessas tabelas segue abaixo:
 
 * tabela global
 
 Nome | Tipo | Classe | ehHerdado |
 ----|  ----|  -----|   ------    | 
 A   | classe | A   | null      |
 x    | int   | A   | null      |
 
 
 * tabela de parametros
 
Nome | Tipo | Classe | ehHerdado |
----|  ----|  -----|   ------    | 
A   | classe | A   | null      |
x    | int   | A   | null      |