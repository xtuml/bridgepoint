#! perl
#
#
# Notice:
# (C) Copyright 1998-2012 Mentor Graphics Corporation
#     All rights reserved.
#
# copyright-update.pl -- Update copyright year
# $Id: update-copyright.pl,v 1.5 2012/01/24 17:19:02 kbrown Exp $
#
#   File id
#
#       Copyright (C)   2000-2005 Jari Aalto
#       Created:        2000-01
#       Keywords:       Perl, copyright, update
#
#       This program is free software; you can redistribute it and/or
#       modify it under the terms of the GNU General Public License as
#       published by the Free Software Foundation; either version 2 of
#       the License, or (at your option) any later version.
#
#       This program is distributed in the hope that it will be useful, but
#       WITHOUT ANY WARRANTY; without even the implied warranty of
#       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#       General Public License for more details.
#
#       You should have received a copy of the GNU General Public License along
#       with this program; if not, write to the Free Software Foundation,
#       Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
#
#       Visit http://www.gnu.org/copyleft/gpl.html
#
#   Documentation
#
#       This program will update the year part of the copyright line.
#
#           Copyright (C)       2000-2005
#
#       =>
#
#           Copyright (C)       2000-2005
#
#       The Copyright year can be passed as command line option. If no
#       option is given, current year is used.
#
#   Code Note
#
#       This code has been edited using Emacs editor, where M-x cperl-mode
#       and M-x font-lock-mode was turned on. Due to highlighting problems,
#       a simple Perl regexp marker // confused averything, so an alternative
#       m,, match operator was used.
#
#   End

use autouse 'Pod::Text'     => qw( pod2text );
use autouse 'Pod::Html'     => qw( pod2html );

use 5.004;
use strict;

use English;
use Getopt::Long;
use File::Find;


    my $LIB = "copyright-update.pl";
    my $substitutions;
    my $totalChanges = 0;
    my $replaceResult = "";
    use vars qw ( $VERSION );

    #   This is for use of Makefile.PL and ExtUtils::MakeMaker
    #   So that it puts the tardist number in format YYYY.MMDD
    #   The REAL version number is defined later
    #
    #   The following variable is updated by Emacs setup whenever
    #   this file is saved. See http//tiny-tools.sourceforge.net/

    my $VERSION = '2005.0216';

# ****************************************************************************
#
#   DESCRIPTION
#
#       Help function and embedded POD documentation
#
#   INPUT PARAMETERS
#
#       none
#
#   RETURN VALUES
#
#       none
#
# ****************************************************************************

=pod

=head1 NAME

copyright-update.pl - Update Copyright year information

=head1 README

This program updates the copyright year information for given files. The
year is current year unless passed with B<--year> YEAR option.

   perl -S copyright-update.pl --verbose 1 --test [--year 2002] *

To change all files recursively form current directory, whose author is
"Mr. Foo" use command below. 

   perl -S copyright-update.pl --recursive "Author:.*Mr. Foo" \
        --verbose 1 --test --year 2002 .

For the above command, only files that contain lines like these would
be updated:

   Copyright (C)        2000-2005
   Copyright: (C)       2000-2005

The format must be exatly as show here. Different amount of spaces is
permitted, but the YEAR-YEAR must be kept together in files.

This was first used by BridgePoint in Jan 2007 for bugzilla issue 2673.
Please see that issue for additional information.  

=head1 OPTIONS

=head2 Gneneral options

=over 4

=item B<--help -h>

Print text help

=item B<--Help-html>

Print help in HTML format. You can pipe this to a browser:

    perl -S copyright-update.pl --Help-html | lynx

=item B<--Help-man>

Print help in Unix manual page format. You can pipe this to a comman:

    perl -S copyright-update.pl --Help-man | nroff -man | less

=item B<--recursive>

Recursively search all direcotries given at command line.

=item B<--test>

hangedRun in test mode. Show what would happen. No files are changed.

=item B<--verbose LEVEL>

Print informational messages. Increase numeric LEVEL for more
verbosity.

=item B<--Version>

Print contact and version information

=item B<--year YEAR>

Update files using YEAR. Year value must be four digits.
The default is current calendar year.

=back

=head2 Miscellaneous options

=over 4

=item B<--debug>

Turn on debug.

=back

=head1 DESCRIPTION

<Longer program description>

=head1 TROUBLESHOOTING

None.

=head1 EXAMPLES

None.

=head1 ENVIRONMENT

No environment variables are used.

=head1 FILES

None.

=head1 SEE ALSO

<references to other programs>

=head1 BUGS

No known limitations.

=head1 AVAILABILITY

http://tiny-tools.sourceforge.net/

=head1 SCRIPT CATEGORIES

CPAN/Administrative

=head1 COREQUISITES

Uses tandard Perl modules.

=head1 OSNAMES

C<any>

=head1 VERSION

$Id: update-copyright.pl,v 1.5 2012/01/24 17:19:02 kbrown Exp $

=head1 AUTHOR

Copyright (C) 2000-2005 Jari Aalto. All rights reserved.
This program is free software; you can redistribute and/or modify program
under the same terms as Perl itself or in terms of Gnu General Public
licence v2 or later.

=cut

sub Help (;$$)
{
    my $id   = "$LIB.Help";
    my $type = shift;  # optional arg, type
    my $msg  = shift;  # optional arg, why are we here...

    if ( $type eq -html )
    {
        pod2html $PROGRAM_NAME;
    }
    elsif ( $type eq -man )
    {
        eval "use Pod::Man";
        $EVAL_ERROR  and  die "$id: Cannot generate Man: $EVAL_ERROR";

        my %options;
        $options{center} = 'cvs status - formatter';

        my $parser = Pod::Man->new(%options);
        $parser->parse_from_file ($PROGRAM_NAME);
    }
    else
    {
        pod2text $PROGRAM_NAME;
    }

    defined $msg  and  print $msg;

    exit 1;
}


# ****************************************************************************
#
#   DESCRIPTION
#
#       Return current year YYYY
#
#   INPUT PARAMETERS
#
#       None
#
#   RETURN VALUES
#
#       number      YYYY
#
# ****************************************************************************

sub Year ()
{
    my $id = "$LIB.Year";
    1900 + (localtime time())[5];
}

# ****************************************************************************
#
#   DESCRIPTION
#
#       Read command line arguments and their parameters.
#
#   INPUT PARAMETERS
#
#       None
#
#   RETURN VALUES
#
#       Globally set options.
#
# ****************************************************************************

sub HandleCommandLineArgs ()
{
    my $id = "$LIB.HandleCommandLineArgs";

    use vars qw
    (
        $test
        $verb
        $debug

        $YEAR
        $OPT_RECURSIVE
    );

    Getopt::Long::config( qw
    (
        require_order
        no_ignore_case
        no_ignore_case_always
    ));

    my ( $help, $helpMan, $helpHtml );          # local variables to function

    GetOptions      # Getopt::Long
    (
          "year=i"     => \$YEAR
        , "help"       => \$help
        , "Help-man"   => \$helpMan
        , "Help-html"  => \$helpHtml
        , "test"       => \$test
        , "debug"      => \$debug
        , "verbose:i"  => \$verb
        , "recursive"  => \$OPT_RECURSIVE
    );

    $help     and  Help();
    $helpMan  and  Help(-man);
    $helpMan  and  Help(-html);

    $YEAR = Year()  unless defined $YEAR;


    unless ( $YEAR =~ m,^\d{4}$, )
    {
        die "$id: Option --year must be given with four digits [$YEAR]";
    }

    if ( defined $verb  and  $verb == 0 )
    {
        $verb = 1;
    }

    $verb = 1  if  $test and $verb == 0;
    $verb = 5  if  $debug;
}


sub replaceCopyright
{
    my $cpyRightPattern = shift(@_);
    my $currentLine = shift(@_);
    my $copyrightText = shift(@_);
    my $firstDate = shift(@_);
    my $secondDate = shift(@_);
    my $mentorCopyright = shift(@_);
    my $file = shift(@_);
    my $id  = "$LIB.replaceCopyright";
    my $y;
    my $wasReplaced = 0;
    $replaceResult = $currentLine;

    if ( ($secondDate and  ($y = $secondDate) eq $YEAR ) || ($firstDate  and  ($y = $firstDate) eq $YEAR ))
    {
        $debug  and  print "$id: Note! Copyright is already $YEAR in $file\n";
    }
    else
    {
        s/$cpyRightPattern/$copyrightText $firstDate-$YEAR $mentorCopyright/i;
        $replaceResult = $_;
    }
    
    $debug and print "$id: $replaceResult";
    return $replaceResult;
}

# ****************************************************************************
#
#   DESCRIPTION
#
#       Handle Single file
#
#   INPUT PARAMETERS
#
#       %hash       -file   => [filename list]
#
#   RETURN VALUES
#
#       none
#
# ****************************************************************************

sub HandleFile ( % )
{
    my $id  = "$LIB.HandleFile";
    my %arg = @ARG;

    my @files   = @{ $arg{-file} };


    unless ( @files )
    {
        warn "$id: -file argument is empty: ",  $arg{-file};
        return;
    }

    $debug  and  print "$id: -file [@files]\n";

    local ( *FILE, $ARG );

    for my $file ( @files )
    {

        $debug  and  print "$id: Opening file: $file\n";

        if (-B $file) {
            $debug  and  print "$id: Cannot open $file it is binary.\n";
            next;
        } else {
            if ( $file =~ /svn-base$/ ) {
                print "$id: Skipping SVN admin file $file\n";
                next;
            }

            unless ( open FILE, "<$file" )
            {
                $verb  and  print "$id: Cannot open $file\n";
                next;
            }
        }
        
        my $mentor = "([[:print:]]*Mentor Graphics)";
        my $yearExp = "[ \\t]*(\\d{4})[ \\t]*-?[ ]*(\\d{4})?[ \\t]*";
        my $cpyRghtFront = "((\\([Cc]\\)|©)?[ \\t]*Copyright:?)" . $yearExp . $mentor;
        my $cpyRghtBack = "(Copyright:?[ \\t]*(\\([Cc]\\)|©)?)" . $yearExp . $mentor;
        my $curLine = 1;
        my $numChangesInFile = 0;
        my $resultFile = "";
        $debug  and  print "Pattern 1: $cpyRghtFront\n";
        $debug  and  print "Pattern 2: $cpyRghtBack\n";
        while (<FILE>) 
        {
        
            my $before = $_;
            my $thisResult = $before;
            if (/$cpyRghtFront/i) 
            {
                $thisResult = &replaceCopyright($cpyRghtFront, $before, $1, $3, $4, $5, $file);
            } elsif (/$cpyRghtBack/i)
            {
                $thisResult = &replaceCopyright($cpyRghtBack, $before, $1, $3, $4, $5, $file);
            }
            
            $resultFile .= $thisResult;
            
            if ($thisResult ne $before) 
            {
                if ($numChangesInFile == 0) 
                {
                    $substitutions = $substitutions . $file . "\n";
                }
                my $msg = "";
                $msg = $msg . "    Line $curLine\n";
                $msg = $msg . "    Before: $before";
                $msg = $msg . "    After : $thisResult";
                $msg = $msg . "\n";
                $totalChanges += 1;
                $numChangesInFile += 1;
                
                $substitutions = $substitutions . $msg;
            }
            
            $curLine += 1;          
        }
        
        close FILE;
        if (not $test and $numChangesInFile) 
        {
            open (FILE, ">$file" ) or die "Cannot open $file for writting.\n";
            print FILE $resultFile;
            close FILE;
        }        
    } # end of loop
    
}

# ****************************************************************************
#
#   DESCRIPTION
#
#       Recursively find out all files and chnege their content.
#
#   INPUT PARAMETERS
#
#       None. This function is called from File::FInd.pm library
#
#   RETURN VALUES
#
#       None.
#
# ****************************************************************************


sub wanted ()
{
    my $id = "$LIB.wanted";

    my $dir  = $File::Find::dir;
    my $file = $File::Find::name;

    if ( $dir =~ m,(CVS|RCS)$,i )
    {
        $File::Find::prune = 1;
        $debug  and  print "$id: Ignored directory: $dir\n";
        return;
    }

    #   Emacs backup files this.txt~  and   #this.text#

    my $ignore = '[#~]$|\.(log|tmp|bak|bin|s?o|com|exe)$'
                 . '|\.(ppt|xls|jpg|png|gif|tiff|bmp)$'
                 . '|update-copyright\.pl$'
                 ;


    if ( $file =~ m,$ignore,oi )
    {
        $debug  and  print "$id: Ignored temporary file: $file\n";
        return;
    }


    if ( -f )
    {
        if ( $verb > 3 )
        {
            print "$id: $file\n";
        }

        HandleFile -file => [$file];
    }
}

# ****************************************************************************
#
#   DESCRIPTION
#
#       Expand files in list. Win32 support
#
#   INPUT PARAMETERS
#
#       @       list of file glob patterns.
#
#   RETURN VALUES
#
#       @       list of filenames
#
# ****************************************************************************

sub FileGlobs ( @ )
{
    my $id   = "$LIB.FileGlobs";
    my @list = @ARG;
    not @list  and  die "$id: No files to expand. Argument list is empty.";

    my @files;

    for my $glob ( @list )
    {
        #       Win32 can't expand "*". We must do it here.
        #       Grep only FILES, not directories.

        push @files, grep { -f } glob $glob;
    }

    $debug  and  print "$id: RETURN [@files]\n";
    @files;
}

# ****************************************************************************
#
#   DESCRIPTION
#
#       Main function
#
#   INPUT PARAMETERS
#
#       None
#
#   RETURN VALUES
#
#       None
#
# ****************************************************************************


sub Main ()
{
    my $id = "$LIB.Main";

    HandleCommandLineArgs();

    unless ( @ARGV )
    {
        die "What files to change? See --help.";
    }

    $debug  and  print "$id: ARGV [@ARGV]\n";

    # .......................................... expand command line ...


    if ( $OPT_RECURSIVE )
    {
        find( {wanted => \&wanted, no_chdir => 1},  @ARGV );
    }
    else
    {
        my @files = FileGlobs @ARGV;
        HandleFile -file => [@files];        
    }

    my $resultFileName = "update-copyright-results.txt";
    if ($totalChanges) {
        open (FILE, ">$resultFileName") or die "Unable to open $resultFileName for writting.\n";
        print FILE $substitutions;
        close FILE; 
        
        if ($test) {
            print "NOTE! No files were modified.  A report was created.\n";
        } else {
            print "NOTE! Files were modified.  A report was created.\n";
        }
        print "NOTE! A report can be found in $resultFileName\n";
    } else {
        print "NOTE! No matches were found.\n";
    }
}

Main();

0;
__END__
