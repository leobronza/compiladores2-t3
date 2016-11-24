grammar JaSON;

program :
    (class_definition)*;

class_definition :
    'class' IDENT '{' class_body '}';

class_body :
    variables* constructors+ main_function?;

variables :
    type IDENT;

constructors :
    IDENT '(' arguments? ')' '{' constructor_body '}' ;

constructor_body :
    assignment*;

assignment :
    attribute '=' (IDENT | STRING | NUM_INT | NUM_FLOAT | BOOLEAN) ;

attribute :
    ('this' '.')? IDENT;

arguments:
    ;

type:
    'String' | 'int' | 'float' | 'boolean' ;

function_main:
    'public' 'static' 'void' 'main' '(' 'String' 'args' '[]' ')' '{' function_body '}';

function_body:
    'new'
    ;

IDENT:
    ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*;

NUM_INT:
    '0'..'9'+;

NUM_FLOAT:
    '0'..'9'+ '.' '0'..'9'+;

BOOLEAN:
    'true' | 'false';

STRING:
  '\'' ~('\n' | '\r' | '\'')* '\''
  | '"' ~('\n' | '\r' | '"')* '"';

WS:
    (' ' | '\n' | '\r' | '\t') -> skip;

COMMENTS:
    '{' ~('\n')*  '}' -> skip;
