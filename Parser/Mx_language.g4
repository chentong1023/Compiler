grammar Mx_language;

prog: (variable_definition_line | class_definition | function_definition)* EOF;

variable_definition_line: variable_type variable_definition? (',' variable_definition)* ';';

variable_definition: Identifier ('=' expression)? ;

class_definition : 'class' name = Identifier '{' (variable_definition_line | function_definition)* '}';

function_definition : return_type = variable_type? name = Identifier '(' (parameter(','parameter)*)? ')' block;

common_type: type = ('bool' | 'int' | 'string' | 'void');

variable_type: (Identifier | common_type) ('['']')*;

block: '{' statement* '}';

statement:  block                           # block_stmt
        |   variable_definition_line        # var_def_stmt
        |   if_block                        # if_stmt
        |   for_block                       # for_stmt
        |   while_block                     # while_stmt
        |   expression ';'                  # expr_stmt
        |   'continue' ';'                  # continue_stmt
        |   'break' ';'                     # break_stmt
        |   'return' expression?            # return_stmt
        |   ';'                             # blank_stmt
        ;

if_block: 'if' '(' expression ')' statement ('else' statement)? ;

while_block: 'while' '(' expression ')' statement;

for_block: 'for' '(' init = expression? ';' cond = expression ? ';' incr = expression? ')' statement;

parameter: variable_type Identifier;

expression: primary                                     # primary_expr
        |   expression'.'Identifier                     # member_expr
        |   'new' creator                               # new_expr
        |   expression'['expression']'                  # aref_expr
        |   expression'('expression')'                  # funcall_expr
        |   expression op=('++' | '--')                 # suffix_expr
        |   op=('+'|'-'|'++'|'--')expression            # prefix_expr
        |   op=('~'|'!')expression                      # prefix_expr
        |   expression op=('*'|'/'|'%') expression      # binary_expr
        |   expression op=('+'|'-') expression          # binary_expr
        |   expression op=('<<'|'>>') expression        # binary_expr
        |   expression op='&' expression                # binary_expr
        |   expression op='|' expression                # binary_expr
        |   expression op='^' expression                # binary_expr
        |   expression '&&' expression                  # logical_and_expr
        |   expression '||' expression                  # logical_or_expr
        |   <assoc=right> expression '=' expression     # assign_expr
        ;

expression_list: expression (',' expression)*;

primary: '(' expression ')' # sub_expr
    |   'this'              # this_expr
    |   Identifier          # variable_expr
    |   literal             # literal_expr
    ;

literal: DecimalInteger     # int_const
    |   StringLiteral       # string_const
    |   'true'              # bool_const
    |   'false'             # bool_const
    |   'NULL'              # null_const
    ;

creator: (Identifier | common_type) ('['expression']')+ ('['']')*   # array_creator
    |   Identifier '(' ')'                                          # constructor
    |   Identifier                                                  # single_creator
    ;

StringLiteral : '"' StringCharacter* '"';

fragment
StringCharacter: ~["\\\n\r] | '\\' ["n\\];

Identifier: [a-zA-Z_] [a-zA-Z_0-9]*;

DecimalInteger: [1-9] [0-9]* | '0';

WS: [ \t\r\n]+ -> skip;

BLOCK_COMMENT: '/*' .*? '*/' -> skip;

LINE_COMMENT: '//' ~[\r\n]* -> skip;