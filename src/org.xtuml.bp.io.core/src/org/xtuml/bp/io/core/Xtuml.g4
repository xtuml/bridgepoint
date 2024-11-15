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
                                     | class_item+
                                   ; 
                                   
package_declaration                : description? marks?
                                     'package' pkg_name=name ( 'is' ref_name=scoped_name )? ';';
                                   
package_definition                 : description? marks?
                                     'package' pkg_name=name 'is'
                                       ( pkg_items=package_item* 'end' 'package'
                                       | ref_name=scoped_name
                                       ) ';'
                                   ;
                                   
package_item                       : package_declaration
                                     | package_definition
                                     | type_forward_declaration
                                     | type_declaration
                                     | exception_definition
                                     | constant_group_definition
                                     | interface_declaration
                                     | external_entity_definition
                                     | function_definition
                                     | class_declaration
                                     | component_declaration
                                     | component_definition
                                     | relationship_definition
                                     | satisfaction_definition
                                   ;
                                   
component_declaration              : description? marks?
                                     'component' comp_name=name ( 'is' ref_name=scoped_name )? ';';
                                   
component_definition               : description? marks?
                                     'component' comp_name=name 'is'
                                       component_item*
                                     'end' 'component' ';'
                                   ;
                                   
component_item                     : package_declaration
                                     | port_definition
                                   ;
                                   
port_definition                    : description? marks?
                                     (direction='provided' | direction='required') 'port' port_name=name (
                                       ( 'implements' iface_name=scoped_name )? 'is'
                                         message_definition*
                                       'end' 'port'
                                     )? ';'
                                   ;
                                           
interface_declaration              : description? marks?
                                     'interface' iface_name=name ';';

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
                                   
class_declaration                  : description? marks?
                                     'class' class_name=name ( 'is' ref_name=scoped_name )? ';';

class_definition                   : description? marks?
                                     'class' class_name=name 'is'
                                       class_item*
                                     'end' 'class' ';'
                                   ;
                                   
class_item                         : attribute_definition
                                     | identifier_definition
                                     | operation_definition
                                     | state_definition
                                     | event_declaration
                                     | state_machine_definition
                                     | transition_definition
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
                                     ( 'return' type_reference )? (
                                       'is'
                                         action_body
                                       'end' 'operation'
                                     )? ';'
                                   ;
                                   
state_definition                   : description? marks?
                                     class_based='class'?
                                     'state' state_name=name
                                     ( '(' parameter_list ')' )?
                                     (
                                       'is'
                                         action_body
                                       'end' 'state'
                                     )? ';'
                                   ;

event_declaration                  : description? marks?
                                     class_based='class'?
                                     'event' evt_name=name
                                     ( '(' parameter_list ')' )?
                                     ';'
                                   ;
                                   
state_machine_definition           : description? marks?
                                     class_based='class'?
                                     'state' 'model' 'is' (
                                       '|' '|' ( evt_names+=scoped_name '|' )+
                                       '|' ( Divider '|' )+
                                       transition_row+
                                     )? 'end' 'state' 'model' ';'
                                   ;
                                   
transition_row                     : '|' start_state_name=name '|'
                                     ( end_states+=transition_destination '|' )+
                                   ;

transition_destination             : end_state_name=name
                                     | ig='ignore'
                                     | ch='cannot_happen'
                                   ;

transition_definition              : description? marks?
                                     class_based='class'?
                                     'transition' start_state_name=name
                                     '[' evt_name=scoped_name ']' '=>' end_state_name=name
                                     ( '(' parameter_list ')' )?
                                     'is'
                                       action_body
                                     'end' 'transition' ';'
                                   ;
                                   
type_forward_declaration           : description? marks?
                                     'type' type_name=name ';'
                                   ;

type_declaration                   : description? marks?
                                     'type' type_name=name 'is' type_definition ';'
                                   ;
                                   
type_definition                    : structure_type_definition
                                     | enumeration_type_definition
                                     | named_type_definition
                                   ;
 
structure_type_definition          : 'structure'
                                       structure_member*
                                     'end' 'structure'
                                   ;

structure_member                   : description? marks?
                                     member_name=name ':' type_reference ';';

enumeration_type_definition        : 'enum' '('
                                       ( enumerator ( ',' enumerator )* )
                                     ')'
                                   ;

enumerator                         : description? marks?
                                     enum_name=name
                                   ;
                                   
named_type_definition              : type_reference ( 'range' min=const_expression '..' max=const_expression )?;

exception_definition               : description? marks?
                                     'exception' exception_name=name ';';

constant_group_definition          : description? marks?
                                     'constant' 'group' group_name=name 'is'
                                       constant_definition*
                                     'end' 'constant' 'group' ';'
                                   ;
                                   
constant_definition                : description? marks?
                                     constant_name=name ':' type_reference '=' value=const_expression ';'
                                   ;
                                   
external_entity_definition         : description? marks?
                                     'external' ee_name=name 'is'
                                       bridge_definition*
                                     'end' 'external' ';'
                                   ;

bridge_definition                  : description? marks?
                                     'bridge' brg_name=name
                                     '(' parameter_list? ')'
                                     ( 'return' type_reference )? (
                                       'is'
                                         action_body
                                       'end' 'bridge'
                                     )? ';'
                                   ;

function_definition                : description? marks?
                                     'public'? 'function' func_name=name
                                     '(' parameter_list? ')'
                                     ( 'return' type_reference )? (
                                       'is'
                                         action_body
                                       'end' 'function'
                                     )? ';'
                                   ;
                                   
relationship_definition            : simple_relationship_definition
                                     | linked_relationship_definition
                                     | subsuper_relationship_definition
                                   ;

simple_relationship_definition     : description? marks?
                                     'relationship' rel_name=RelName 'is'
                                     half_rels+=half_relationship_definition ','
                                     half_rels+=half_relationship_definition ';'
                                   ;

linked_relationship_definition     : description? marks?
                                     'relationship' rel_name=RelName 'is'
                                     half_rels+=half_relationship_definition ','
                                     half_rels+=half_relationship_definition
                                     'using' link_mult=('one' | 'many')?
                                     link_class=name ';'
                                   ;
                                   
subsuper_relationship_definition   : description? marks?
                                     'relationship' rel_name=RelName 'is'
                                     super_class=name 'is_a' '('
                                       ( sub_classes+=name ( ',' sub_classes+=name )* )?
                                     ')' ';'
                                   ;

half_relationship_definition       : first_class=name 
                                     cond=('conditionally' | 'unconditionally')
                                     ( phrase=name | 'relates' 'to' )
                                     mult=('one' | 'many')
                                     second_class=name
                                   ;
                                   
satisfaction_definition            : 'satisfaction' 'is' (
                                       ( req_comp_ref=name '::' req_port_ref=name '-(o-' prov_comp_ref=name '::' prov_port_ref=name )
                                       | ( prov_comp_ref=name '::' prov_port_ref=name '-o)-' req_comp_ref=name '::' req_port_ref=name )
                                     ) ';'
                                   ;
                                   
 
parameter_list                     : parameter ( ',' parameter )*;

parameter                          : marks? param_name=name ':' (by_ref='in' | by_ref='out') type_reference;

                                  
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
                                     | arg_name=ID '=' const_expression
                                   ;

scoped_name                        : name ( '::' name )*;

name                               : ID
                                     | Ext_ID
                                   ;

const_expression                   : scoped_name           // TODO need to develop this more
                                     | StringLiteral
                                     | RealLiteral
                                     | IntegerLiteral 
                                     | BooleanLiteral
                                   ;

action_body                        : UnparsedActions
                                     | // empty body
                                   ;

// Terminals
MarkName                           : '@' ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
RelName                            : 'R' ( '1' .. '9' ) ( '0' .. '9' )*;
DescriptionLine                    : '//!' ~('\n'|'\r')* '\r'? '\n';
StringLiteral                      : '"' ( ~('\\'|'"') )* '"';
RealLiteral                        : '-'? ( '0' | ( '1' .. '9' ) ( '0' .. '9' )* ) '.' ( '0' .. '9' )+;
IntegerLiteral                     : '0' | '-'? ( '1' .. '9' ) ( '0' .. '9' )*;
BooleanLiteral                     : 'true' | 'false';
ID                                 : ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) ) ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '_' )*;
Ext_ID                             : '\'' ~( '\'' | '\r' | '\n' )+ '\'';
Divider                            : '---' ( '-' )*;

UnparsedActions                    : '@noparse' .*? '@endnoparse';
                         

// comments and white space
Comment                            : '//' ~('\n'|'\r')* '\r'? '\n' -> skip;
BlockComment                       : '/*' .*? '*/' -> skip;
Whitespace                         : (' ' | '\t' | '\f' | '\n' | '\r' )+ -> skip;
