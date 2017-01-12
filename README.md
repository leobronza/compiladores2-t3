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
 y    | String | A | null
 B     | classe | B | A |
 x | int| B | A
 y | String | B | A
 z | double | B | null
 
 
 
 * tabela de parametros
 
Nome | Tipo | Classe | ehHerdado |
----|  ----|  -----|   ------    | 
A   | classe | A   | null      |
x    | int   | A   | null      |
 
* tabela de geração

Nome | Tipo | Classe | ehHerdado | token | constutor_param | valor |
----|  ----|  -----|   ------    | ----| -----| ---|
A   | classe | A    | null | [| null | null
x | int| A | null | "x: " | 0 | null
y | String | A | null | "y: " | 2 | null
z | double | A | null| "z: " | 1 |1.5
B | classe | B | A | [ | null | null
x | int| B | A | "x: " | 0 | null
y | String | B | A | "y: " | 2 |  null
z | double | B | A| "z: " | 1 | 1.5
h | double | B | null| "h: " | 3 | null


