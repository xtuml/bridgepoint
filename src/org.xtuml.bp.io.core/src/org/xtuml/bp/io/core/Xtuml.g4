grammar Xtuml;

target                             : definition
                                     | discontiguous_definition
                                   ;

discontiguous_definition           : 'within' parent_name=scoped_name 'is'
                                       definition
                                     'end' ';'
                                   ;
                                   
definition                         : package_definition
                                     | component_definition
                                     | interface_definition
                                     | class_definition
                                   ; 
                                   
package_definition                 : description? marks?
                                     'package' pkg_name=name (
                                       'is'
                                         package_item*
                                       'end' 'package'
                                     )? ';'
                                   ;
                                   
package_item                       : package_definition
                                   ;
                                   
component_definition               : description? marks?
                                     'component' comp_name=name (
                                       'is'
                                         component_item*
                                       'end' 'component'
                                     )? ';'
                                   ;
                                   
component_item                     : package_definition
                                     | port_definition
                                   ;
                                   
port_definition                    : description? marks?
                                     (direction='provided' | direction='required') 'port' port_name=name (
                                       'implements' iface_name=scoped_name 'is'
                                         message_definition*
                                       'end' 'port'
                                     )? ';'
                                   ;
                                           
                                   
interface_definition               : description? marks?
                                     'interface' iface_name=name 'is'
                                       message_definition*
                                     'end' 'interface' ';'
                                   ;

message_definition                 : description? marks?
                                     'message' msg_name=name
                                     '(' parameter_list? ')'
                                     ( 'return' type_reference )?
                                     ( direction='to' | direction='from' ) 'provider' (
                                       'is'
                                         action_body
                                       'end' 'message'
                                     )? ';'
                                   ;
                                   
class_definition                   : description? marks?
                                     'class' class_name=name (
                                       'is'
                                         class_item*
                                       'end' 'class'
                                     )? ';'
                                   ;
                                   
class_item                         : attribute_definition
                                     | identifier_definition
                                     | operation_definition
                                   ;
                                   
attribute_definition               : base_attribute_definition
                                     | referential_attribute_definition
                                     | derived_attribute_definition
                                   ;
                                   
base_attribute_definition          : description? marks?
                                     unq='unique'? attr_name=name ':' type_reference
                                     ( '=' default_value=const_expression )? ';'
                                   ;
                           
referential_attribute_definition   : description? marks?
                                     attr_name=name ':' 'referential' '('
                                       attribute_reference ( ',' attribute_reference )*
                                     ')' type_reference ';'
                                   ;
                                   
attribute_reference                : relationship_specification '.' attr_name=name;

relationship_specification         : RelName ( '.' class_or_role=name ( '.' class_name=name )? )?;

derived_attribute_definition       : description? marks?
                                     attr_name=name ':' type_reference 'is'
                                       action_body
                                     'end' ';'
                                   ;
                                   
identifier_definition              : description? marks?
                                     pref='preferred'? 'identifier' 'is' '(' 
                                       attrs+=name ( ',' attrs +=name )*
                                     ')' ';'
                                   ;
                                   
operation_definition               : description? marks?
                                     ( class_based='class'
                                       | ( 'deferred' '(' RelName ')' )
                                     )?
                                     'operation' operation_name=name
                                     '(' parameter_list? ')'
                                     'return' type_reference (
                                       'is'
                                         action_body
                                       'end' 'operation'
                                     )? ';'
                                   ;
 

parameter_list                     : parameter ( ',' parameter )*;

parameter                          : marks? param_name=name ':' (by_ref='in' | by_ref='out') type_reference;

scoped_name                        : name ( '::' name )*;

name                               : ID
                                     | Ext_ID
                                   ;
                                   
type_reference                     : named_type_reference
                                     | array_type_reference
                                     | inst_type_reference
                                     | inst_set_type_reference
                                   ;

named_type_reference               : scoped_name;

array_type_reference               : 'sequence' ( '(' bound=const_expression ')' )? 'of' type_reference;

inst_type_reference                : 'instance' 'of' scoped_name;

inst_set_type_reference            : 'set' 'of' inst_type_reference;

description                        : DescriptionLine+;

marks                              : mark+;

mark                               : MarkName ( '(' mark_arguments ')' )? ';';
                                   
mark_arguments                     : mark_argument ( ',' mark_argument )*;

mark_argument                      : const_expression
                                     | arg_name=ID '=' const_expression;

const_expression                   : scoped_name | StringLiteral | IntegerLiteral | BooleanLiteral;  // TODO need to develop this more

action_body                        : UnparsedActions
                                     | // empty body
                                   ;

// Terminals
MarkName                           : '@' ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
RelName                            : 'R' ( '1' .. '9' ) ( '0' .. '9' )*;
DescriptionLine                    : '//!' ~('\n'|'\r')* '\r'? '\n';
StringLiteral                      : '"' ( ~('\\'|'"') )* '"';
IntegerLiteral                     : '0' | '-'? ( '1' .. '9' ) ( '0' .. '9' )*;
BooleanLiteral                     : 'true' | 'false';
ID                                 : ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
Ext_ID                             : '\'' ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' | ' ' | '\t' )* '\'';

UnparsedActions                    : '@noparse' .*? '@endnoparse';
                         

// comments and white space
Comment                            : '//' ~('\n'|'\r')* '\r'? '\n' -> skip;
SqlComment                         : '--' ~('\n'|'\r')* '\r'? '\n' -> skip;
BlockComment                       : '/*' .*? '*/' -> skip;
Whitespace                         : (' ' | '\t' | '\f' | '\n' | '\r' )+ -> skip;
