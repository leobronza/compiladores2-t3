# Trabalho 3 :neckbeard:
Trabalho 3  da diciplinas de compiladores 2, implementação de uma linguagem inventada, cujo objetivo
é de compilar um programa simplificado java, verificando sintaxe e semântica, com a finalidade
de gerar um JSON dos objetos declarados no programa.

O trabalho 2 da diciplina foi a especificação da linguagem. Segue o link dos slides com a 
documentação: https://docs.google.com/presentation/d/1j9q5S4gzA3nOO6MbfHrlGkDy0tw0JdnKhK3ndFTbesw/edit?usp=sharing

# Semântica

O analisador semântico, consegue verificar:
* Atribuição de tipos incompatíveis
* Uso de uma classe não declarada
* Parametros enviados errado para o classe no momento da instanciação do objeto
* Herança de classes não declaradas anteriormente
* e outros


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
 
Essa tabela funciona da mesma forma que a tabela global com uma diferença
de ao invés de armazenar os atributos da classe, ele armaze os parametros
do construtor da classe
 
Nome | Tipo | Classe | ehHerdado |
----|  ----|  -----|   ------    | 
A   | classe | A   | null      |
 y    | String | A | null
x    | int   | A   | null      |
 B     | classe | B | A |
 x | int| B | A
 y | String | B | A
 z | double | B | null

> além dessas tabelas principais foi implementado uma tabela de main para 
armazenas os objetos criados.

# Gerador de Código

O gerador de código cria uma tabela de simbolos como a seguinte:

* tabela se sim de geração

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


