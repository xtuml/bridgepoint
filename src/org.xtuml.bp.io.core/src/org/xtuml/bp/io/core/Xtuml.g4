grammar Xtuml;

target                   : 'within' pkg=scoped_name 'is'
                           'interface' iface_name=name 'is'
                             message_declaration*
                           'end' 'interface' ';'
                           'end' ';'
                         ;

message_declaration      : 'message' msg_name=name
                           '(' parameter_list? ')'
                           ( 'return' type_name=name )?
                           ( to='to' | from='from' ) 'provider' ';'
                         ;

parameter_list           : parameter ( ',' parameter )*;

parameter                : param_name=name ':' type_name=name;

scoped_name              : name ( '::' name );

name                     : ID;


ID                       : ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;

Comment                  : '//' ~('\n'|'\r')* '\r'? '\n' -> skip;
SqlComment               : '--' ~('\n'|'\r')* '\r'? '\n' -> skip;
BlockComment             : '/*' .*? '*/' -> skip;
Whitespace               : (' ' | '\t' | '\f' | '\n' | '\r' )+ -> skip;
