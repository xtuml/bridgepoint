grammar Xtuml;

target                   : definition
                           | discontiguous_definition
                         ;

discontiguous_definition : 'within' parent_name=scoped_name 'is'
                             definition
                           'end' ';'
                         ;
                         
definition               : package_definition
                           | interface_definition
                         ; 
                         
package_definition       : description? mark*
                           'package' pkg_name=name (
                             'is'
                               package_item+
                             'end' 'package'
                           )? ';'
                         ;
                         
package_item             : package_definition
                           // | other_package_item...
                         ;
                         
interface_definition     : description? mark*
                           'interface' iface_name=name 'is'
                             message_declaration*
                           'end' 'interface' ';'
                         ;

message_declaration      : description? mark*
                           'message' msg_name=name
                           '(' parameter_list? ')'
                           ( 'return' type_reference )?
                           ( direction='to' | direction='from' ) 'provider' ';'
                         ;

parameter_list           : parameter ( ',' parameter )*;

parameter                : mark* param_name=name ':' (by_ref='in' | by_ref='out') type_reference;

scoped_name              : name ( '::' name )*;

name                     : ID;

type_reference           : named_type_reference
                           | array_type_reference
                           | inst_type_reference
                           | inst_set_type_reference
                         ;

named_type_reference     : scoped_name;

array_type_reference     : 'sequence' ( '(' bound=const_expression ')' )? 'of' type_reference;

inst_type_reference      : 'instance' 'of' scoped_name;

inst_set_type_reference  : 'set' 'of' inst_type_reference;

description              : DescriptionLine+;

mark                     : MarkName ( '(' mark_arguments ')' )? ';';
                         
mark_arguments           : mark_argument ( ',' mark_argument )*;

mark_argument            : const_expression
                           | mark_name=ID '=' const_expression;

const_expression         : scoped_name | StringLiteral | IntegerLiteral;  // TODO need to develop this more

// Terminals
MarkName                 : '@' ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
ID                       : ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
DescriptionLine          : '//!' ~('\n'|'\r')* '\r'? '\n';
StringLiteral            : '"' ( ~('\\'|'"') )* '"';
IntegerLiteral           : '0' | '-'? ( '1' .. '9' ) ( '0' .. '9' )*;
                         

// comments and white space
Comment                  : '//' ~('\n'|'\r')* '\r'? '\n' -> skip;
SqlComment               : '--' ~('\n'|'\r')* '\r'? '\n' -> skip;
BlockComment             : '/*' .*? '*/' -> skip;
Whitespace               : (' ' | '\t' | '\f' | '\n' | '\r' )+ -> skip;
