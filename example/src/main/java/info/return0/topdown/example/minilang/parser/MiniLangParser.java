package info.return0.topdown.example.minilang.parser;

import java.util.Collection;

import info.return0.topdown.AtLeastOne;
import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Result;
import info.return0.topdown.example.minilang.syntax.Syntax;

/*
<program> ::= {<function_definition> | <variable_definition> | <struct_definition> }+

<function_definition> ::= <identifier> '(' [<parameter_list>] ')' ':' <type>  '{' <statement>* '}'

<type> ::= <identifier>

<struct_type> ::= 'struct' <identifier>

<parameter_list> ::= <parameter> {',' <parameter>}*

<parameter> ::= <identifier> ':' <type>

<variable_definition> ::= <identifier>: <type>　'=' <expression> ';'

<statement> ::= <variable_definition> ';'
              | <assignment> ';'
              | <expression> ';'
              | <if_statement>
              | <for_statement>
              | <return_statement> ';'
              | '{' <statement>* '}'

<assignment> ::= <identifier> {<array_access> | <structure_access>}* '=' <expression>

<array_access> :: = '[' <expression> ']'

<structure_access> :: = '.' <identifier>

<function_call> ::= <identifier> '(' [<argument_list>] ')'

<argument_list> ::= <expression> {',' <expression>}*

<if_statement> ::= 'if' '(' <condition> ')' <statement> ['else' <statement>]

<for_statement> ::= 'for' '(' [<assignment>] ';' [<condition>] ';' [<assignment>] ')' <statement>

<return_statement> ::= 'return' [<expression>]

<expression> ::= { <number>
               | <string>
               | <identifier>
               | <function_call>
               | <binary_expression>
               | '(' <expression> ')'
               } {<array_access> | <structure_access>}*

<binary_expression> ::= <term> (<binary_operator1> <term>)*
<term> ::= <factor> (<binary_operator2> <factor>)*
<factor> ::= <expression> | '(' <binary_expression> ')'

<binary_operator1> ::= '+' | '-' | '==' | '!=' | '<' | '>' | '<=' | '>=' | '&&' | '||'
<binary_operator2> ::= ('*'|'/')

<condition> ::= <expression>

<identifier> ::= [a-zA-Z_] [a-zA-Z_0-9]*

<integer> ::= [0-9]+

<float> ::= [0-9]+ '.' [0-9]+

<string> ::= '"' {<any_character>}* '"'

<struct_definition> ::= 'struct' <identifier> '{' <member_list> '}'

<member_list> ::= {<member_declaration>}+

<member_declaration> ::= <type> <identifier> {'[' <integer> ']'}* ';'

 */


public class MiniLangParser {
	public Result<Collection<Syntax>> parse(String code) {
		var seq = CharacterSequence.builder().source(code).autoRemoveWS(true).build();
		return new AtLeastOne<Syntax>(Choice.ofTry(new FunctionDefinition(), new StructureDefinition(), new VariableDefinition())).parse(seq);
	}
}
