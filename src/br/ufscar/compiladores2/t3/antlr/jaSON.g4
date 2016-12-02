grammar jaSON;

program :
    (class_definition)*;

class_definition :
    modifiers 'class' IDENT ('extends' IDENT)? '{' class_body '}';

class_body :
    variables* constructors+ function_main?;

variables :
    type IDENT mais_var ';';

mais_var: ',' IDENT mais_var |
;

constructors :
    modifiers IDENT '(' (arguments) ')' '{' constructor_body '}' ;

constructor_body :
    ('super('parametros')' ';')?
    assignment*;

assignment :
    attribute '=' (IDENT /*argumentos do construtor*/ | STRING | NUM_INT | NUM_FLOAT | BOOLEAN) ';' ;

attribute :
    ('this' '.')? IDENT/*variaveis da classe*/;

arguments:
    type IDENT (',' arguments)* | /* nome do argumento*/
    ;

type:
    'String' | 'int' | 'float' | 'boolean' | IDENT/* Uma classe criada pode ser um tipo para uma variavel de outra classe*/ ;

function_main:
    'public' 'static' 'void' 'main' '(' 'String' 'args' '[]' ')' '{' function_body '}';


modifiers:
    'public' | 'private' |
;

function_body:
    variables*
    (IDENT/*tipo*/ IDENT /*objeto*/ '=' 'new' IDENT /*construtor*/ '(' (parametros) ')' ';' )+
    /* aqui são as variaveis que serão passadas por parametro para o construtor*/
    ;

parametros:
    ((IDENT | NUM_FLOAT | NUM_INT | STRING)(',' (IDENT | NUM_FLOAT | NUM_INT | STRING))*) |
    /* Aqui são as variaveis declaradas em function_body*/
;
IDENT:
    ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

NUM_INT:
    '0'..'9'+;

NUM_FLOAT:
    '0'..'9'+ '.''0'..'9'+;

BOOLEAN:
    'true' | 'false';

STRING:
  '\'' ~('\n' | '\r' | '\'')* '\''
  | '"' ~('\n' | '\r' | '"')* '"';

WS:
    (' ' | '\n' | '\r' | '\t') -> skip;

COMMENTS:
    '{' ~('\n')*  '}' -> skip;
