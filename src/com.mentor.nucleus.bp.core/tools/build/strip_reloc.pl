#!perl -w
use File::Basename;

# This routine will insulate perl open.
# Use automatic "my" filehandles that politely go out of scope.
sub xtumlmc_open($);
sub xtumlmc_open($)
{
  my $path = shift;
  open( my $fh, $path ) or die "ERROR:  Could not open file $path.\n";
  return $fh;
}


#---------------------------------------------------------------------------
# Parse through the model removing graphics elements and proxy statements,
# handle inline blocks in action language. 
#---------------------------------------------------------------------------
sub xtumlmc_cleanse_model($$);
sub xtumlmc_cleanse_model($$)
{
  @snippets = ();
  $snippet = "";
  $total_snippets = 0;
  my $I = xtumlmc_open( "<" . shift );
  my $O = xtumlmc_open( ">" . shift );

  OUTER: while ( <$I> ) {
    if ( /\/\*\s?\#inline/i ) {
      print $O "$`";
      # We've started an inline block. Insert the placeholder, then
      # read it into a buffer.
      $snippet = "";
      print $O "MC_CODE_INLINE_snippet = $total_snippets;\n";
      $total_snippets++;
      while ( <$I> ) {
        if ( /^\s*\*\// ) {
          print $O "$'";
          @snippets = ( @snippets, $snippet);
          next OUTER;
        } else {
          $snippet .= $_;
        }
      }
    } elsif ( /\/\*(.*)\*\// ) {
      # Handle a line one-line C-style comment
      print $O "$`//$1\n";
      my $post = $'; chop($post);
      if ( $post ne "" ) { print $O "$post\n"; }
    } elsif ( /\/\*/ ) {
      # We've started a multi-line comment block. Change lines to 
      # C++-style comments 
      print $O "$`//$'";
      while ( <$I> ) {
        if ( /\*\// ) {
          print $O "// $`";
          if ( $' ne "" ) { print $O "\n$'"; }
          next OUTER;
        } else {
          print $O "// $_";
        }
      }
    } elsif ( /^INSERT INTO / ) {
      # A new element has been encountered.  Dump out any snippets
      # we found in the previous element.
      my $index = $total_snippets - scalar( @snippets );
      foreach $snip ( @snippets ) {
        chop $snip;
        print $O "INSERT INTO TE_SNIPPET\n\tVALUES ('$index',\n\t'$snip');\n";
        $index++;
      }
      @snippets = ();

      # If this element is for a graphic or proxy, remove it
      if ( ( /^INSERT INTO GD_/ ) ||
           ( /^INSERT INTO ACT_/ ) ||
           ( /^INSERT INTO E_/ ) ||
           ( /^INSERT INTO V_/ ) ||
           ( /^INSERT INTO DIM_/ ) ) {
        while ( <$I> ) { if ( /^.*[;]\s*$/ ) { next OUTER; } }
      }

      # If this element is a proxy, remove it
      if ( /^INSERT INTO .*_PROXY/ ) {
        while ( <$I> ) { if ( /^\s+'.*\.xtuml'\);$/ ) { next OUTER; } }
      }

      print $O "$_";
      next OUTER;
    } else {
      # Just a normal line, send it on through.
      print $O "$_";
    }
  }

  # Dump out any outstanding snippets when we hit EOF
  my $index = $total_snippets - scalar( @snippets );
  foreach $snip ( @snippets ) {
    chop $snip;
    print $O "INSERT INTO TE_SNIPPET\n\tVALUES ($index,\n\t'$snip');\n";
    $index++;
  }
}


#-----------------------------------------------------------------------------
# This routine takes 2 arguments: input file name, and output file name.  It 
# concatenates the data in the input file to the end of the output file.
#-----------------------------------------------------------------------------
sub xtumlmc_concat($$);
sub xtumlmc_concat($$)
{
  my $I = xtumlmc_open( "<" . shift );
  my $O = xtumlmc_open( ">>" . shift );
  while ( <$I> ) {
      print $O "$_";
  }
}

sub strip_reloc($$)
{
    # Substitute out the relocatables so that we can use .bak files.
    my $FILE = xtumlmc_open( "<" . shift );
    my $SQL_FILE = xtumlmc_open( ">" . shift );
    while ( <$FILE> ) {
      s/\${#@\!:[A-Za-z0-9]*:[A-Fa-f0-9,-]*[:]?[A-Fa-f0-9,-]*:([A-Za-z0-9_' ]*)}/$1/g;
      print $SQL_FILE $_;
    }
}

my $inFile = $ARGV[0];
my $outFile = $ARGV[1];
strip_reloc($inFile, $outFile);

