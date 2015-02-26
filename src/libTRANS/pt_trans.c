/*========================================================================
//
// File:      $RCSfile: pt_trans.c,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:35:13 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
*/

#include "pt_trans.h"

#include <stdlib.h>
#include <string.h>

static char *return_buffer = 0;

static int indexOf( const char *p_string, char p_search )
{
	int loop;
    int len = strlen( p_string );
    for ( loop = 0; loop < len; ++ loop )
	{
		if ( p_string[loop] == p_search )
		{
			return loop;
		}
	}
    return -1;
}

static int lastIndexOf( const char *p_string, char p_search, int start_point )
{
	int loop;
    for ( loop = start_point-1; loop >= 0; -- loop )
	{
		if ( p_string[loop] == p_search )
		{
			return loop;
		}
	}
    return -1;
}

static int firstDagger( const char *p_string )
{
	int loop;
    int len = strlen( p_string );
    for ( loop = 0; loop < len-1; ++ loop )
	{
		if ( p_string[loop] == '-' && p_string[loop+1] == '>' )
		{
			return loop;
		}
	}
    return -1;
}

static int lastDagger( const char *p_string )
{
	int loop;
    int len = strlen( p_string );
    for ( loop = len-2; loop >= 0; -- loop )
	{
		if ( p_string[loop] == '-' && p_string[loop+1] == '>' )
		{
			return loop;
		}
	}
    return -1;
}

static void getSubstring( const char *p_string, int first, int last )
{
    int loop, loop2;
    if ( return_buffer )
    {
        free ( return_buffer );
    }
    return_buffer = malloc( last-first+1 );
    loop2 = 0;
    for ( loop = first; loop < last; ++ loop )
    {
        return_buffer[loop2++] = p_string[loop];
    }
    return_buffer[loop2] = 0;
}

static char* findNextParseKeyword( const char* p_string )
{
	int i, j;
	int length = strlen(p_string);
	for ( i = 0; i < length; ++i )
	{
		if ( p_string[i] == '\n' )
		{
			if ( p_string[i+1] != ' ' )
			{
  				for ( j = i+1; j < length; ++j )
				{
				    if ( p_string[j] == ':' )
					{
						// found one
						return (char *) &p_string[i];
					}
					else if ( p_string[j] == '\n' )
					{
						// skip this line
						i = j - 1;
						break;
					}
				}
			}
		}
	}
	// return the last character of the string
	return (char *) &p_string[length];
}


void *pt_TranslateInit()
{
  void *parm = 0;
  return parm;
}

const char *pt_TranslateString( void *p_parm,
                                const char *p_switch, 
                                const char *p_string)
{
    int loop, loop2, len, newLength, num_ampltgt, num_quot, num_tick;
    int rel_num_start = -1;
    int rel_num_end   = -1;
    int dagger_start   = -1;
    int tick_start = -1;
    int tick_end = -1;
	char *parse_kw_descrip_start;
	char *parse_kw_descrip_end;
	char * underscorePtr;

	if ( strcmp( p_switch, "nosplat" ) == 0 )
    {
        len = strlen( p_string );
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] != '*' )
            {
                return_buffer[loop2++] = p_string[loop];
            }
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    } else if (strcmp(p_switch, "strsep_") == 0) {
    	// Return all characters up to, but not including the first "_".  If
    	// no "_" is present the string passed-in is returned.
        len = strlen( p_string );
        if ( return_buffer ) {
            free ( return_buffer );
        }

        underscorePtr = strchr ( p_string, '_' );
        if (underscorePtr != 0) {
        	len = (underscorePtr-p_string);
        }
        return_buffer = malloc( len+1 );  // Add 1 for the null terminator

        memcpy(return_buffer, p_string, len);
        return_buffer[len] = 0;

        return return_buffer;
    } else if (strcmp(p_switch, "_strsep") == 0) {
    	// Return all characters that follow the first "_" in the string.  If
    	// no "_" is present an empty string is returned.
        if ( return_buffer ) {
            free ( return_buffer );
            return_buffer = 0;
        }

        underscorePtr = strchr ( p_string, '_' );
        if (underscorePtr != 0) {
            len = strlen( p_string );
            newLength = &(p_string[len-1]) - underscorePtr;

            return_buffer = malloc((newLength) + 1 );  // Add 1 for the null terminator

            memcpy(return_buffer, underscorePtr+1, newLength);
            return_buffer[newLength] = 0;
        } else {
            return_buffer = malloc(1);  // Add 1 for the null terminator
            return_buffer[0] = 0;
        }

        return return_buffer;
    }
    else if ( strcmp( p_switch, "2tick" ) == 0 )
    {
        len = strlen( p_string );
        num_tick = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
          if ( p_string[loop] == '\'' )
            num_tick += 1;
        }
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+num_tick+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] == '\'' )
            {
                return_buffer[loop2++] = '\'';
            }
            return_buffer[loop2++] = p_string[loop];
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    }
    else if ( strcmp( p_switch, "nonl" ) == 0 )
    {
        len = strlen( p_string );
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] == '\n' )
            {
                return_buffer[loop2++] = ' ';
            }
            else
            {
                return_buffer[loop2++] = p_string[loop];
            }
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    }
    else if ( strcmp( p_switch, "xmlclean" ) == 0 )
    {
        num_ampltgt = 0;
        len = strlen( p_string );
        for ( loop = 0; loop < len; ++ loop )
        {
          if ( p_string[loop] == '&' )
            num_ampltgt += 4;
          else if ( p_string[loop] == '<' )
            num_ampltgt += 3;
          else if ( p_string[loop] == '>' )
            num_ampltgt += 3;
        }
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+num_ampltgt+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] == '&' )
            {
                return_buffer[loop2++] = '&';
                return_buffer[loop2++] = 'a';
                return_buffer[loop2++] = 'm';
                return_buffer[loop2++] = 'p';
                return_buffer[loop2++] = ';';
            }
            else if ( p_string[loop] == '<' )
            {
                return_buffer[loop2++] = '&';
                return_buffer[loop2++] = 'l';
                return_buffer[loop2++] = 't';
                return_buffer[loop2++] = ';';
            }
            else if ( p_string[loop] == '>' )
            {
                return_buffer[loop2++] = '&';
                return_buffer[loop2++] = 'g';
                return_buffer[loop2++] = 't';
                return_buffer[loop2++] = ';';
            }
            else
            {
                return_buffer[loop2++] = p_string[loop];
            }
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    }
    else if ( strcmp( p_switch, "xmlquot" ) == 0 )
    {
        num_quot = 0;
        len = strlen( p_string );
        for ( loop = 0; loop < len; ++ loop )
        {
          if ( p_string[loop] == '\'' )
          {
            num_quot += 1;
            break;
          }
        }
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+3 );
        loop2 = 0;
        if ( num_quot > 0 )
        {
            return_buffer[loop2++] = '"';
        }
        else
        {
            return_buffer[loop2++] = '\'';
        }
        strcpy( &return_buffer[loop2], p_string );
        loop2 += len;

        if ( num_quot > 0 )
        {
            return_buffer[loop2++] = '"';
        }
        else
        {
            return_buffer[loop2++] = '\'';
        }

        return_buffer[loop2] = 0;
        return return_buffer;
    }
	else if ( strcmp( p_switch, "xmlname" ) == 0 )
	{
		len = strlen( p_string );
		if ( return_buffer )
		{
			free (return_buffer );
		}
		return_buffer = malloc( len+1 );
		loop2 = 0;
		for( loop = 0; loop < len; ++loop )
		{
			// if p_string[loop] is a good character (A-Za-z0-9_) for an xml name then keep it
			if ( ( ( p_string[loop] >= 'A' ) && ( p_string[loop] <= 'Z' ) ) ||
				 ( ( p_string[loop] >= 'a' ) && ( p_string[loop] <= 'z' ) ) ||
				 ( ( p_string[loop] >= '0' ) && ( p_string[loop] <= '9' ) ) ||
				 (   p_string[loop] == '_' ) ||
				 ( ( p_string[loop] == '-' ) && ( loop != 0 ) ) ||
				 ( ( p_string[loop] == '.' ) && ( loop != 0 ) ) )
			{
				return_buffer[loop2++] = p_string[loop];
			}
			else
				// otherwise replace it with an underscore
			{
				return_buffer[loop2++] = '_';
			}
		}
		return_buffer[loop2] = 0;
		return return_buffer;
	}
    else if ( strcmp( p_switch, "u2d" ) == 0 )
    {
        len = strlen( p_string );
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] == '_' )
            {
                return_buffer[loop2++] = '-';
            }
            else
            {
                return_buffer[loop2++] = p_string[loop];
            }
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    }
    else if ( strcmp( p_switch, "d2u" ) == 0 )
    {
        len = strlen( p_string );
        if ( return_buffer )
        {
            free ( return_buffer );
        }
        return_buffer = malloc( len+1 );
        loop2 = 0;
        for ( loop = 0; loop < len; ++ loop )
        {
            if ( p_string[loop] == '-' )
            {
                return_buffer[loop2++] = '_';
            }
            else
            {
                return_buffer[loop2++] = p_string[loop];
            }
        }
        return_buffer[loop2] = 0;
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cf_kl" ) == 0 )
    {
		rel_num_start = indexOf( p_string, '[' );
		dagger_start = firstDagger( p_string );
	    if (rel_num_start != -1 && dagger_start != -1) {
		    getSubstring( p_string, dagger_start + 2, rel_num_start);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cf_rel" ) == 0 )
    {
		rel_num_start = indexOf( p_string, '[' );
		rel_num_end = indexOf( p_string, ']' );
	    if (rel_num_start != -1 && rel_num_end != -1) {
			tick_start = indexOf( p_string, '.' );  // actually the location of the dot
			if ( tick_start != -1 && tick_start < rel_num_end ) {
				getSubstring( p_string, rel_num_start + 2, tick_start);
			}
			else {
				getSubstring( p_string, rel_num_start + 2, rel_num_end);
			}
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cf_phrase" ) == 0 )
    {
		rel_num_start = indexOf( p_string, '[' );
		rel_num_end = indexOf( p_string, ']' );
		tick_start = indexOf( p_string, '\'' );
	    if (rel_num_start != -1 && rel_num_end != -1 &&
			rel_num_start < tick_start && tick_start < rel_num_end ) {
			tick_end = indexOf( p_string + tick_start + 1, '\'' );
		    getSubstring( p_string, tick_start + 1, tick_start + tick_end + 1);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cf_rest" ) == 0 )
    {
		rel_num_end = indexOf( p_string, ']' );
	    if (rel_num_end != -1) {
			len = strlen(p_string);
		    getSubstring( p_string, rel_num_end+1, len);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cb_kl" ) == 0 )
    {
		rel_num_start = lastIndexOf( p_string, '[', strlen(p_string) );
		dagger_start = lastDagger( p_string );
	    if (rel_num_start != -1 && dagger_start != -1) {
		    getSubstring( p_string, dagger_start + 2, rel_num_start);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cb_rel" ) == 0 )
    {
		rel_num_start = lastIndexOf( p_string, '[', strlen(p_string) );
		rel_num_end = lastIndexOf( p_string, ']', strlen(p_string) );
	    if (rel_num_start != -1 && rel_num_end != -1) {
			tick_start = lastIndexOf( p_string, '.', strlen(p_string) );  // actually the location of the dot
			if ( tick_start != -1 && tick_start > rel_num_start ) {
				getSubstring( p_string, rel_num_start + 2, tick_start);
			}
			else {
			    getSubstring( p_string, rel_num_start + 2, rel_num_end);
			}
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cb_phrase" ) == 0 )
    {
		rel_num_start = lastIndexOf( p_string, '[', strlen(p_string) );
		rel_num_end = lastIndexOf( p_string, ']', strlen(p_string) );
		tick_end = lastIndexOf( p_string, '\'', strlen(p_string) );
	    if (rel_num_start != -1 && rel_num_end != -1 &&
			rel_num_start < tick_end && tick_end < rel_num_end ) {
			tick_start = lastIndexOf( p_string, '\'', tick_end-1 );
		    getSubstring( p_string, tick_start + 1, tick_end );
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "cb_rest" ) == 0 )
    {
		dagger_start = lastDagger( p_string );
	    if (dagger_start != -1) {
		    getSubstring( p_string, 0, dagger_start);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else if ( strcmp( p_switch, "full_descrip" ) == 0 )
    {
		parse_kw_descrip_start = strstr( p_string, "Description:");
	    if (parse_kw_descrip_start != NULL) {
			parse_kw_descrip_start += strlen("Description:");
		    parse_kw_descrip_end = findNextParseKeyword( parse_kw_descrip_start );
		    getSubstring( parse_kw_descrip_start, 0, parse_kw_descrip_end - parse_kw_descrip_start);
		}
		else
		{
	        if ( return_buffer )
		    {
			    free ( return_buffer );
	        }
	        return_buffer = malloc( 1 );
		    return_buffer[0] = 0;
		}
        return return_buffer;
    }
    else
    {
        return p_string;
    }
}

void pt_TranslateCleanup( void *p_parm )
{
    if ( return_buffer )
    {
        free ( return_buffer );
        return_buffer = 0;
    }
}

