#include <stdio.h>
#include <string.h>

typedef struct { char s[ 80 ]; } xtuml_string;

/* Return type is xtuml_string.
   Parameters are conventional strings.  */
static xtuml_string sfun( char * );
static xtuml_string sfun( char * a )
{
  xtuml_string returnstring;
  char c[ 80 ];
  strcpy( c, a );
  strcpy( returnstring.s, c );
  return returnstring;
}

void sss( char * );
void sss( char * s )
{
  printf( "inside of sss %s\n", s );
}

int main ( void )
{
  char name[ 80 ];
  printf( "xt%s\n", strcpy( name, sfun( "UML" ).s ) );
  sss( sfun( name ).s );
  return 0;
}
