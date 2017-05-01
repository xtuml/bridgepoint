/*
 * A custom lexer based on JFlex that is needed because of Antlr's deficiencies 
 * with regards to floating point literals
 */
 
WS = [\ \n\r\t]+
ANY_OTHER = . 
SL_COMMENT = "//"[^\r\n]*(\r?\n)?

REAL = {DIGIT}+ "." {DIGIT}+ (("e"|"E") ("+"|"-")? {DIGIT}+)?
 	 | {DIGIT}* "." {DIGIT}+ (("e"|"E") ("+"|"-")? {DIGIT}+)?
	 | {DIGIT}+              (("e"|"E") ("+"|"-")? {DIGIT}+)  
	 | {DIGIT}+ "#" {DIGIT36}+ "." {DIGIT36}+ ("#" ("+"|"-")? {DIGIT}+)?
	 | {DIGIT}+ "#" {DIGIT36}* "." {DIGIT36}+ ("#" ("+"|"-")? {DIGIT}+)?
	 | {DIGIT}+ "#" {DIGIT36}+                ("#" ("+"|"-")? {DIGIT}+)

DEC_INT = {DIGIT}+
INTEGER = {DEC_INT} ("#" {DIGIT36}+)?

DIGIT8  = [0-7]
DIGIT   = [0-9]
DIGIT16 = [0-9A-Fa-f]
DIGIT36 = [0-9A-Za-z]

DURATION  = @ [Pp] [^@]* @?
TIMESTAMP = @      [^@]* @?
	
ID = [A-Za-z_] ([A-Za-z_0-9])* 

STRING = \" ([^\\\"] | {ESCAPE_SEQUENCE})* \"?
CHAR =  "'" ([^\\\'] | {ESCAPE_SEQUENCE})  "'"
	
ESCAPE_SEQUENCE = "\\" ([^0-9u] | {DIGIT8}{DIGIT8}{DIGIT8} | "u" {DIGIT16}+)

%%
