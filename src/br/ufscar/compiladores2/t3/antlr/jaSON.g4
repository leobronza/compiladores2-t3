grammar jaSON;

program :
    (class_definition)*;

class_definition :
    modifiers 'class' IDENT ('extends' IDENT)? '{' class_body '}';

class_body :
    variables* constructors+ function_main?;

variables :
    type id1=IDENT (',' id2=IDENT)* ';';

constructors :
    modifiers IDENT '(' parameters? ')' '{' constructor_body '}' ;

constructor_body :
    ('super('arguments')' ';')?
    assignment*;

assignment :
    attribute '=' (IDENT /*argumentos do construtor*/ | STRING | NUM_INT | NUM_FLOAT | BOOLEAN) ';' ;

attribute :
    ('this' '.')? IDENT/*variaveis da classe*/;

parameters:
    t1=type id1=IDENT (',' t2=type id2=IDENT)* ;

type:
    'String' | 'int' | 'float' | 'boolean' | IDENT/* Uma classe criada pode ser um tipo para uma variavel de outra classe*/ ;

function_main:
    'public' 'static' 'void' 'main' '(' 'String' 'args' '[]' ')' '{' function_body '}';


modifiers:
    'public' | 'private' |
;

function_body:
    variables*
    (IDENT/*tipo*/ IDENT /*objeto*/ '=' 'new' IDENT /*construtor*/ '(' (arguments)? ')' ';' )+
    /* aqui são as variaveis que serão passadas por parametro para o construtor*/
    ;

arguments:
    v1=value (',' v2=value)*
    /* Aqui são as variaveis declaradas em function_body*/
;

value:
    IDENT | NUM_FLOAT | NUM_INT | STRING
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
