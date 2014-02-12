#!/bin/perl
#===========================================================================
#
# File:      $RCSfile: UnitTestGenerator.pl,v $
# Version:   $Revision: 1.14 $
# Modified:  $Date: 2013/01/10 23:21:36 $
# 
# (c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
# 
# =====================================================================
# Licensed under the Apache License, Version 2.0 (the "License"); you may not 
# use this file except in compliance with the License.  You may obtain a copy 
# of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software 
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
# License for the specific language governing permissions and limitations under
# the License.
# =====================================================================
#/
my $perlScriptRevision = '$Revision: 1.14 $';
$_ = $perlScriptRevision;
($perlScriptRevision) = (m/\$Revision: ([\d\.]*).*/);

my $perlScriptName = '$RCSfile: UnitTestGenerator.pl,v $';
$_ = $perlScriptName;
($perlScriptName) = (m/\$RCSfile: ([\w\.\w]*)/);

my $packageName="com.mentor.nucleus.bp.core.test";
my $overwriteFiles = 1;  # by default we DO overwrite files
my $matrixFileName;
my $matrixFH;
my $rootClassName;
my @classNameList;
my $className;
my $outPath;
my $suffix;
my $fqOutputFileName;
my $outputFH;
my @DegreesOfFreedom = ();
my %Results = ();
my @MatrixColNames = ();
my @MatrixRowNames = ();
my @MatrixRows = ();
my @dofColumnTypes = ();
my @dofRowTypes = ();
my $currentLine = '';
my $priorLine = '';
my $maxTestsPerClass = 250;
my $createTestSuite =  0;
my $createTestSuitePerClass =  0;

my $usage = <<USAGE;
#===========================================================================
#
# UnitTestGenerator $perlScriptRevision
# (c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
#
# usage:  
# UnitTestGenerator <TestMatrix> <ResultName> [options]
#       TestMatrix : Fully qualified name of the test matrix file.
#       ResultName : Fully qualifed name for the resulting java class file. 
#       -n <xxx>   : The number of tests per file. 
#                    Default: $maxTestsPerClass
#       -p <name>  : The package name for resulting java class.  
#                    Default: $packageName
#       -DNO       : "Do Not Overwrite".  When this flag is specified, the 
#                    hand-maintained java class that was previously generated 
#                    will not be overwritten.
#                    Default: Files WILL BE overwritten by default.  
#       -suite     : If present a test suite will be generated.  A test suite
#                    is always generated if the number of tests generated
#                    if greater then the -n value, but when this option
#                    is present the suite will be generated regardless of the
#                    number of tests generated.      
#       -suitePerClass : This is used in conjunction with -suite.  When 
#                     -suitePerClass is used, a separate test suite for each 
#                     java class file is created.
# 
# Example Usage:
# --------------
#  UnitTestGenerator.pl TestMatrix-01.txt c:/wc/SatisfactionDragDropTests.java 
#
#  This will result in creation of a java class named 
#  SatisfactionDragDropTests and another named SatisfactionDragDropTests_0.  
#  SatisfactionDragDropTests will contain \"TODO\" comments that must be
#  completed.  SatisfactionDragDropTests_0 should not need to be modified.
#  
#===========================================================================
USAGE

my $description = <<DESCRIPTION;
# Description:
# ------------
# This perl script is used to generate unit tests from a provided test
# matrix.  It was created based on issue 3187.
#
# If more then 250 tests will be generated, each group of 250 will be 
# put in a seperate test class (and file) and a "_<x>" will be 
# appended to class and file name.  When this happens, as a convenience, a
# test suite class will be created with the test.  It is named
# <TestClass>TestSuite.java and is placed in the same folder with the other
# generated files.
#
# Example Test Matrix:
# --------------------
# Degrees of Freedom:
#  Source(S)
#  1. Provision_Unformalized
#  2. Provision_formalized_I1
#  
#  Destination(D)
#  1. Provision_Unformalized
#  2. Provision_formalized_I1
#  
#  Results:
#  1. SatisfactionMade "The satisfaction was made successfully"
#  2. SatisfactionNotMade, "Failed to make an expected satisfaction."
#  
#  Matrix:
#      S1  S2 S3
#  D1  R2  R2 R1
#  D2  R2  R2  X
#  D3   X   X  X
#
# Matrix Guidelines:
# ------------------
# 1. No spaces in the names provided. Use '_' or upper-case/lowercase as 
#    needed for readability.
# 2. The matrix rows and columns may be a combination of the Degrees of 
#    Freedom for example, S1D1 would be allowed.  Please see
#    <cvs>Documentation/internal/technical/archive/20070830/notes/i478.xls
#    for an example matrix where this makes sense.
# 3. Internally, the text matrix should be placed alongside the issue's 
#    documentation (Documentation/internal/technical/notes/).
# 4. Name the matrix as follows: i<bugzilla #>-TestMatrix-<xx>.txt
#    <xx> is a number that starts with 01 and increments for each matrix
#    requred by the issue.
# 5. Lines in the matrix that start with '#' are considered comments and 
#    are skipped, please take advantange of comments, and at least provide
#    a comment at the head of the file that describes what the matrix is
#    being used for.
# 6. In the Results section, each possible result is entered as follows:
#    1. <FunctionName>[,| ]+["error description"]
#    This allows a descriptive error to be included in failure cases.
# 7. If a result is not possible and therefore you do not wish to 
#    generate a test for a particular cell(s) in the matrix, an upper-case 
#    "X" can be placed in that cell and no test will be generated for it.
#===========================================================================
DESCRIPTION

use File::Basename;
use File::Spec::Functions;


sub processCommandLine;
sub processCommandLine
{
    # ARG[0] should be the input file and ARGV[1] should be the output file, the
    # rest is optional
	if ($#ARGV <= 0) {
		print($usage);
		exit;
	}
	
	for ( my $i = 0; $i < @ARGV; $i++ ) {
	  if ($i==0) {
    	$matrixFileName = $ARGV[$i];	      
	  } elsif ($i==1) {
    	$fqOutputFileName = $ARGV[$i];
	  } else {
    	  my $k = $ARGV[$i] if ( $ARGV[$i] =~ s/^-// );
    	  if ( $k =~ /^(p)$/ ) { $i++; $packageName = $ARGV[$i]; }
    	  elsif ( $k =~ /^(DNO)$/ ) { $overwriteFiles = 0; }
    	  elsif ( $k =~ /^(n)$/ ) { $i++; $maxTestsPerClass = $ARGV[$i]; }
    	  elsif ( $k =~ /^(suite)$/ ) { $createTestSuite = 1; }
    	  elsif ( $k =~ /^(suitePerClass)$/ ) { $createTestSuitePerClass = 1; }
    	  elsif ( $k =~ /^(h)$/ ) { print("$usage$description"); exit; }
    	  elsif ( $k =~ /^(\?)$/ ) { print("$usage$description"); exit; }
    	  else { die "Unrecognized argument ($k) to ExtractMetrics.pl\n"; }
	  }
	}

    print "\n";
    print "TestMatrix: $matrixFileName\n";
    print "ResultFile: $fqOutputFileName\n";
    print "Package: $packageName\n";
    print "Overwrite Files: " . ($overwriteFiles ? "true" : "false"). "\n";
    print "Tests Per Class: $maxTestsPerClass\n";
    print "\n";
    
	if ($packageName eq "") {
		die "The package name must be specified.\n";
	}	
};

sub readEnumeratedItem();
sub readEnumeratedItem() {
	$_= $currentLine;
	# example: 1. Provision_Unformalized
	my ($number, $remainingText) = (m/(\d+)\.?\s*(.+)/x);
	return $number,$remainingText;
}

sub readDOFHeader();
sub readDOFHeader() {
	$_= $currentLine;
	#example: Source(S)
	$word   = '\w+';    # match a whole word.	
	$brace  = '\(';    # match the left brace
	
	my ($name, $abbrev) = (/^($word)$brace($word)/x);

	return $name,$abbrev;
}

sub isDOFHeader();
sub isDOFHeader() {
	my $printWhenFound = shift(@_);
	my $isHeader = 0;
	my ($name, $abbrev) = readDOFHeader();
	if ($name ne '' and $abbrev ne '') {
		if ($printWhenFound) {
			print "\tDOF: $name\n";
		}
		$isHeader = 1;
	}
	return $isHeader;
}

sub isNewSection();
sub isNewSection() {
	my $isNew = 0;
	my $len = length( $currentLine );
	if ($len > 0 and substr($currentLine, $len-1, 1) eq ":") {
		$isNew = 1;
	}
	return $isNew;
}

sub readDegreesOfFreedom();
sub readDegreesOfFreedom() {
	my $DOFExpectedText = "Degrees of Freedom:";
	
	getNextLine();
	if ($currentLine ne $DOFExpectedText) {
		die "ERROR: Invalid file format.  Expected \"$DOFExpectedText\" found: \"$currentLine\".\n";
	}
	
	getNextLine();
	if ( &isDOFHeader(0) ) {
		my $firstDOFHeader = 1;
		my $dofInstanceCount = 0;
		
		while ($currentLine ne '') {
		    if (isNewSection()) {
		    	# Before move on to the another section make sure at least
		    	# one DOF entry was specified for the prior DOF header
		    	if ((not $firstDOFHeader) and (not $dofInstanceCount)) {
		    		die "ERROR: At least one Degree Of Freedom should be specified for \"$priorLine\"";
		    	}
		    	
		    	# exit when we hit the next section
		    	last;
		    }	
		    if ( &isDOFHeader(1)) {
		    	# Before move on to the another header make sure at least
		    	# one DOF entry was specified for the prior DOF header
		    	if ((not $firstDOFHeader) and (not $dofInstanceCount)) {
		    		die "ERROR: At least one Degree Of Freedom should be specified for \"$priorLine\"";
		    	}
		    	my ($name, $abbrev) = readDOFHeader();
		    	
		    	# we create a type of degree of freedom here (add the type to the list)
		    	my $dofCount = $#DegressOfFreedom+1;
		    	$DegressOfFreedom[$dofCount][0] =  [$name,$abbrev];
		    	$firstDOFHeader = 0;
		    	$dofInstanceCount = 0;
		    } else {
		    	$firstDOFElementFound = 1;
		    	my ($number, $name) = readEnumeratedItem();
		    	my $dofCount = $#DegressOfFreedom;
		    	$DegressOfFreedom[$dofCount][1][$dofInstanceCount++] = [$number,$name];
		    }
			getNextLine();
		}
	} else {
		die "ERROR: At least 1 Degree of freedom is required.\n";
	}
}


sub readResultDescription();
sub readResultDescription() {
    my $resultLine = shift(@_);
    
   	$_ = $resultLine;
   	# Example descriptions:
   	#  isValidDestination    "Pasting of source element was not allowed"
   	#  SatisfactionMade,"Satisfaction should have been made and was not"
   	#  SatisfiedAndFormalized, Satisfaction was made and it is formalized.
   	my ($resultFunction, $resultDescription) = (m/[\"| ]*([a-zA-Z1-9_]+)[\"|,| ]*([a-zA-Z1-9_, \.]*)\"?/x);
   	
   	if ($resultFunction eq "") {
   	    die "Error: Missing result function name in the test matrix.";
   	}
   	
   	# if the user didn't specify a description just use the function name.
   	if ($resultDescription eq "") {
   	    $resultDescription = $resultFunction;
   	}
    return $resultFunction, $resultDescription;
}

sub readResults();
sub readResults() {
	my $ResultsExpectedText = "Results:";
	
	if ($currentLine ne $ResultsExpectedText) {
		die "ERROR: Invalid file format.  Expected \"$ResultsExpectedText\" found: \"$currentLine\".\n";
	}
	
	getNextLine();
	while ($currentLine ne '') {
	    if (isNewSection()) {
	    	# exit when we hit the next section
	    	last;
	    }	
    	
    	my ($number, $remainderOfLine) = readEnumeratedItem();
    	$Results{$number} = $remainderOfLine;
		getNextLine();
	}
}

sub getDOFTypeFromDOFInstance();
sub getDOFTypeFromDOFInstance() {
	$dofInstance = shift(@_);
	$dofType = "";
	my @dofTypes = split(/\d+/, $dofInstance);
	if ($#dofTypes >= 0) {
		$dofType = join('', @dofTypes);
	}

	return $dofType;	
}

sub getDOFInstanceIDFromDOFInstance();
sub getDOFInstanceIDFromDOFInstance() {
	$dofType = shift(@_);
	my @dofInstances = getDOFInstanceIDListFromDOFInstance($dofType);
	if ($#dofInstances >= 0) {
		$dofInstance = join('', @dofInstances);
	}
	return $dofInstance;	
}

sub getDOFInstanceIDListFromDOFInstance();
sub getDOFInstanceIDListFromDOFInstance() {
	$dofType = shift(@_);
	$dofInstance = "";

	my @dofInstances = ($dofType =~ m/(\d+)/g);
	return @dofInstances;	
}

sub readColumnNames();
sub readColumnNames() {
	@MatrixColNames = split(/\s+/, $currentLine);

	while ($MatrixColNames[0] eq "") {
		shift(@MatrixColNames);
	}
	
	if ($#MatrixColNames >= 0) {
		@dofColumnTypes = ();
		for (my $i = 0; $i <= $#MatrixColNames; $i++) {			
			my $dofType = &getDOFTypeFromDOFInstance( $MatrixColNames[$i] );
			if ($dofType ne "") {
				push(@dofColumnTypes, $dofType);
			}
		}
	} else {
		die "ERROR: At least 1 matrix column name is expected.\n"
	}
}


sub readMatrix();
sub readMatrix() {
	my $MatrixExpectedText = "Matrix:";
	
	if ($currentLine ne $MatrixExpectedText) {
		die "ERROR: Invalid file format.  Expected \"$MatrixExpectedText\" found: \"$currentLine\".\n";
	}
	
	getNextLine();
	readColumnNames();
	
	getNextLine();
	while ($currentLine ne '') {
		my @currentRow = split(/\s+/, $currentLine);	
		while ($currentRow[0] eq "") {
			shift(@currentRow);
		}
		
		my $thisRowName = $currentRow[0];		
		push(@MatrixRowNames, $thisRowName);
		
		shift(@currentRow); # drop the row name 
		push(@MatrixRows, join(' ', @currentRow ));

		my $dofType = &getDOFTypeFromDOFInstance( $thisRowName );
		if ($dofType ne "") {
			push(@dofRowTypes, $dofType);
		}

		getNextLine();
	}
}


sub getNextLine();
sub getNextLine()
{
	$priorLine = $currentLine;
	$currentLine = '';
	while ( <$matrixFH> ) {
		# Skip comments
	    if (substr($_, 0, 1) eq "#") {
	    	next;
	    }
	    #Skip blank lines
		if ( /^\s*$/ ) {
	    	next;
	  	}
	  	
	  	# remove all whitespace aat the end of lines
	  	s/\s+$//;
	  	$currentLine = $_;

	  	last;
	}
}


sub createTestSuiteClass();
sub createTestSuiteClass() {
	my $rootClass = shift ( @_ );
	my $className = $rootClass . "TestSuite";
    $fqOutputFileName = catfile($outPath, $className);
   	$fqOutputFileName .= $suffix;    		
	($className, $outPath, $suffix) = fileparse( $fqOutputFileName, qr/\.[^.]*/ );
	
	if (-e $fqOutputFileName) {
        print("\tOverwriting the TestSuite class: ($className).\n");
	} else {
        print("\tCreating the TestSuite class: ($className).\n");
	}
	    
	open( $outputFH, ">" . $fqOutputFileName ) or die "ERROR:  Could not open file $fqOutputFileName.\n";
    
    &createCopyrightAndPackageSpec( 1 );
	print $outputFH "import java.io.File;\n";
	print $outputFH "\n";
	print $outputFH "import junit.framework.Test;\n";
	print $outputFH "import junit.framework.TestSuite;\n";
	print $outputFH "import $packageName.*;\n";
	print $outputFH "\n";
	print $outputFH "public class $className extends TestSuite {\n";
	print $outputFH "\n";
	print $outputFH "    /**\n";
	print $outputFH "     * Returns the suite.  This is required to\n";
	print $outputFH "     * use the JUnit Launcher.\n";
	print $outputFH "     */\n";
	print $outputFH "    public static Test suite() {\n";
	print $outputFH "        return new $className();\n";
	print $outputFH "    }\n";
	print $outputFH "\n"; 
	print $outputFH "    /**\n";
	print $outputFH "     * Construct the test suite.\n";
	print $outputFH "     */\n";
	print $outputFH "    public $className()\n";
	print $outputFH "    {\n";
	if ($createTestSuitePerClass) {
		print $outputFH "        addTest(new TestSuite($rootClass.class));\n";
	} else {
		for (my $i = 0; $i <= $#classNameList; $i++) {
			print $outputFH "        addTest(new TestSuite($classNameList[$i].class));\n";
		}
	}
	print $outputFH "    }\n";
	print $outputFH "}\n";
	print $outputFH "\n";
	
    close($outputFH);
}

sub createTestSuiteResultClass();
sub createTestSuiteResultClass() {
    $className = $rootClassName . "TestResultSuite";
    $fqOutputFileName = catfile($outPath, $className);
   	$fqOutputFileName .= $suffix;    		
	($className, $outPath, $suffix) = fileparse( $fqOutputFileName, qr/\.[^.]*/ );
	
	if (-e $fqOutputFileName) {
        print("\tOverwriting the TestResultSuite class: ($className).\n");
	} else {
        print("\tCreating the TestResultSuite class: ($className).\n");
	}
	    
	open( $outputFH, ">" . $fqOutputFileName ) or die "ERROR:  Could not open file $fqOutputFileName.\n";
    
    &createCopyrightAndPackageSpec( 1 );
    	
	print $outputFH "import junit.framework.Test;\n";
	print $outputFH "import junit.framework.TestSuite;\n";
	print $outputFH "\n";
	print $outputFH "import org.eclipse.core.runtime.CoreException;\n";
	print $outputFH "\n";
	print $outputFH "public class $className extends TestSuite {\n";
	print $outputFH "\n";
	print $outputFH "    /**\n";
	print $outputFH "     * Returns the suite.  This is required to\n";
	print $outputFH "     * use the JUnit Launcher.\n";
	print $outputFH "     */\n";
	print $outputFH "    public static Test suite() throws CoreException {\n";
	print $outputFH "        return new $className();\n";
	print $outputFH "    }\n";
	print $outputFH "\n";
	print $outputFH "    /**\n";
	print $outputFH "     * Construct the test suite.\n";
	print $outputFH "     */\n";
	print $outputFH "    public $className() throws CoreException\n";
	print $outputFH "    {\n";
	print $outputFH "    	$rootClassName.generateResults = true;\n";
	if ($createTestSuitePerClass) {
		for (my $i = 0; $i <= $#classNameList; $i++) {
			my $testSuiteName = $classNameList[$i] . "TestSuite";

			print $outputFH "    	addTest(new $testSuiteName());\n";
		}
	} else {
		my $testSuiteName = $rootClassName . "TestSuite";
		print $outputFH "    	addTest(new $testSuiteName());\n";
	}
	print $outputFH "    }\n";
	print $outputFH "}\n";
	print $outputFH "\n";

    close($outputFH);
}


sub createCopyrightAndPackageSpec();
sub createCopyrightAndPackageSpec() {
    my $willBeModified = shift(@_);
    
	print $outputFH "//=====================================================================\n";
	print $outputFH "//\n";
	print $outputFH '//File:      $RCSfile: UnitTestGenerator.pl,v $' . "\n";
	print $outputFH '//Version:   $Revision: 1.14 $' . "\n";
	print $outputFH '//Modified:  $Date: 2013/01/10 23:21:36 $' . "\n";
	print $outputFH "//\n";
	if ($willBeModified) {
    	print $outputFH "// WARNING:      Do not edit this generated file\n";
	} else {
    	print $outputFH "// NOTE: This file was generated, but is maintained by hand.\n";
	}
	print $outputFH "// Generated by: $perlScriptName\n";
	print $outputFH "// Version:      $perlScriptRevision\n";
	my ($myMatrix, $myPath, $mySuffix) = fileparse( $matrixFileName, qr/\.[^.]*/ );
	$myMatrix .= $mySuffix;
	print $outputFH "// Matrix:       $myMatrix\n";
	print $outputFH "//\n";
	print $outputFH "//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.\n";
	print $outputFH "//\n";
	print $outputFH "//=====================================================================\n";
    print $outputFH "// Licensed under the Apache License, Version 2.0 (the \"License\"); you may not\n"; 
    print $outputFH "// use this file except in compliance with the License.  You may obtain a copy \n";
    print $outputFH "// of the License at\n";
    print $outputFH "// \n";
    print $outputFH "//       http://www.apache.org/licenses/LICENSE-2.0\n";
    print $outputFH "// \n";
    print $outputFH "// Unless required by applicable law or agreed to in writing, software\n"; 
    print $outputFH "// distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n"; 
    print $outputFH "// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the \n";
    print $outputFH "// License for the specific language governing permissions and limitations under\n";
    print $outputFH "// the License.\n";
	print $outputFH "//=====================================================================\n";
	print $outputFH "\n";
	print $outputFH "package $packageName;\n";
	print $outputFH "\n";
}

sub createImports();
sub createImports() {
	print $outputFH "import org.eclipse.ui.IEditorPart;\n";
	print $outputFH "\n";
	print $outputFH "import com.mentor.nucleus.bp.core.*;\n";
	print $outputFH "import com.mentor.nucleus.bp.core.common.NonRootModelElement;\n";
	print $outputFH "import com.mentor.nucleus.bp.test.common.*;\n";
	print $outputFH "import com.mentor.nucleus.bp.ui.canvas.*;\n";
	print $outputFH "import com.mentor.nucleus.bp.ui.graphics.editor.*;\n";
	print $outputFH "import com.mentor.nucleus.bp.ui.canvas.test.*;\n";
	print $outputFH "\n";
}

sub createGenericClassDefintion();
sub createGenericClassDefintion() {
    my $isSubClass = shift(@_);
    
	if ( $isSubClass ) {
    	print $outputFH "public class $className extends $rootClassName {\n";
		print $outputFH "\n";	
		print $outputFH "    protected String getResultName() {\n";
		print $outputFH "        return super.getResultName();\n";
		print $outputFH "    }\n";
		print $outputFH "\n";	
		print $outputFH "    public $className(String arg0) {\n";
		print $outputFH "        super(\"$className\", arg0);\n";
		print $outputFH "    }\n";
		print $outputFH "\n";
	} else {
    	print $outputFH "public class $className extends CanvasTest {\n";
		print $outputFH "    public static boolean generateResults = false;\n";
		print $outputFH "    public static boolean useDrawResults = true;\n";
		print $outputFH "\n";	
		print $outputFH "    String test_id = \"\";\n";
		print $outputFH "\n";	
		print $outputFH "    protected String getResultName() {\n";
		print $outputFH "        return getClass().getSimpleName() + \"_\" + test_id;\n";
		print $outputFH "    }\n";
		print $outputFH "\n";	
		print $outputFH "    protected GraphicalEditor fActiveEditor;\n";	
		print $outputFH "\n";	
		print $outputFH "    protected GraphicalEditor getActiveEditor() {\n";
		print $outputFH "        return fActiveEditor;\n";
		print $outputFH "    }\n";
		print $outputFH "\n";	
		print $outputFH "    public $className(String subTypeClassName, String subTypeArg0) {\n";
		print $outputFH "        super(subTypeClassName, subTypeArg0);\n";
		print $outputFH "    }\n";
		print $outputFH "\n";
	  print $outputFH "    protected String getTestId(String src, String dest, String count) {\n";
	  print $outputFH "        return \"test_\" + count;\n";
	  print $outputFH "    }\n";
	  print $outputFH "\n";	
	}
	print $outputFH "    protected void setUp() throws Exception {\n";
	print $outputFH "        super.setUp();\n";
	print $outputFH "    }\n";
	print $outputFH "\n";	
	print $outputFH "    protected void tearDown() throws Exception {\n";
	print $outputFH "        super.tearDown();\n";
	print $outputFH "    }\n";
	print $outputFH "\n";	
}

sub createInstanceAccessors();
sub createInstanceAccessors() {	
	my $type = shift(@_);
	
	my %uniqueTypes = ();
	my %uniqueNames = ();
	my @rowCol = ("row", "column");
	for (my $outerCount = 0; $outerCount <= $#rowCol; $outerCount++) {
    	if ($rowCol[$outerCount] eq "row"){
    		for (my $i = 0; $i <= $#dofRowTypes; $i++) {		
    			$uniqueTypes{"$dofRowTypes[$i]"} = " ";
    		}
    		
    		for (my $j = 0; $j <= $#MatrixRowNames; $j++) {
    			$uniqueNames{"$MatrixRowNames[$j]"} = " ";
    		}
    	} elsif ($rowCol[$outerCount] eq "column") {
    		for (my $i = 0; $i <= $#dofColumnTypes; $i++) {		
    			$uniqueTypes{"$dofColumnTypes[$i]"} = " ";
    		}
    		
    		for (my $j = 0; $j <= $#MatrixColNames; $j++) {
    			$uniqueNames{"$MatrixColNames[$j]"} = " ";
    		}
    	} else {
    		die "ERROR: Unexpected type encountered in createInstanceAccessor(): \"$rowCol[$i]\"\n"
    	}
	}

	while (($dofName, $theVal) = each(%uniqueTypes)) {
		print $outputFH "    /**\n";
		print $outputFH "     * \"$dofName\" is one of the degrees of freedom as specified in this issues\n";
		print $outputFH "     * test matrix.\n";
		print $outputFH "     * This routine gets the \"$dofName\" instance from the given name.\n";
		print $outputFH "     * \n";
		print $outputFH "     * \@param element The degree of freedom instance to retrieve\n";
		print $outputFH "     * \@return A model element used in the test as specified by the test matrix\n";
		print $outputFH "     */\n";
		print $outputFH "    NonRootModelElement select$dofName(String element) {\n";
		print $outputFH "        NonRootModelElement nrme = null;\n";

		@keys = keys(%uniqueNames);
		@sortedKeys = sort(@keys);
		$isFirst = 1; 
		for (my $i = 0; $i <= $#sortedKeys; $i++) {
		    
		    my $typeOfThisInstance = &getDOFTypeFromDOFInstance( $sortedKeys[$i] );
		    
			if ( $typeOfThisInstance eq $dofName ) {
				# if this is the first
				if ( $isFirst ) {
					print $outputFH "        ";
					$isFirst = 0;
				} else {
					print $outputFH " ";
				}
				print $outputFH "if (element.equalsIgnoreCase(\"$sortedKeys[$i]\")) {\n";
				print $outputFH "        	//TODO: Implement\n";
				print $outputFH "        } ";
				
				# if this the last element
				if ($i == $#sortedKeys) {
					print $outputFH "\n";
				} else {
					print $outputFH "else";
				}
			}
		}
		
		print $outputFH "        assertTrue(\"An instance with degree of freedom type \\\"$dofName\\\" was not found.  Instance Name: \" + element + \".\", nrme!=null);\n";
		print $outputFH "        return nrme;\n";
		print $outputFH "    }\n";
		print $outputFH "\n";		
	}
}

sub createCellAction();
sub createCellAction() {			
	# Use a hash so we only get unique names
	my %operationNames = ();
	for (my $i = 0; $i <= $#dofColumnTypes; $i++) {
		for (my $j = 0; $j <= $#dofRowTypes; $j++) {
			$operationNames{"$dofColumnTypes[$i]_$dofRowTypes[$j]_Action"} = " ";
		}
	}

	my @keys = keys(%operationNames);
	for (my $i = 0; $i <= $#keys; $i++) {
    	my $opName = $keys[$i];
		print $outputFH "    /**\n";
		print $outputFH "     * This routine performs the action associated with a matrix cell.\n";
		print $outputFH "     * The parameters represent model instances aquired based on the specifed\n";
		print $outputFH "     * column instance and row instance.\n";
		print $outputFH "     * \n";
		print $outputFH "     * \@param columnInstance Model instance from the column\n"; 
		print $outputFH "     * \@param rowInstance Model instance from the row\n";
		print $outputFH "     */\n";
		print $outputFH "    void $opName(NonRootModelElement columnInstance, NonRootModelElement rowInstance) {\n";
		print $outputFH "        //TODO: Implement\n";
		print $outputFH "    }\n";	
		print $outputFH "\n";	
	}	
}

sub createResultActions();
sub createResultActions() {
	my @keys = keys(%Results);
	for (my $i = 0; $i <= $#keys; $i++) {
    	my $resultLineValue = $Results{$keys[$i]};
        my ($resultFunction, $resultDescription) = &readResultDescription($resultLineValue);

		print $outputFH "    /**\n";
 		print $outputFH "    * This function verifies an expected result.\n";
 		print $outputFH "    *\n";
 		print $outputFH "    * \@param source A model element instance aquired through a action taken\n";
 		print $outputFH "    *               on a column of the matrix.\n";
 		print $outputFH "    * \@param destination A model element instance aquired through a action taken\n";
 		print $outputFH "    *                    taken on a row of the matrix.\n";
 		print $outputFH "    * \@return true if the test succeeds, false if it fails\n";
 		print $outputFH "    */\n";
		print $outputFH "    boolean checkResult_$resultFunction(NonRootModelElement source, NonRootModelElement destination) {\n";
		print $outputFH "        boolean $resultFunction = false;\n";
		print $outputFH "        //TODO: Implement\n";
		print $outputFH "        return $resultFunction;\n";
		print $outputFH "    }\n";
		print $outputFH "\n";
		print $outputFH "\n";
    }
}

#
# If a test result is specified as "X" in the matrix then it means, do not 
# generate a test for it.  This rountine looks to see how many tests will 
# actually be generated exclusing those with an "X".
#
sub getNumTests();
sub getNumTests() {
	my $numTests = 0;
	for (my $col = 0; $col <= $#MatrixColNames; $col++) {
		for (my $row = 0; $row <= $#MatrixRowNames; $row++) {
			
			my $colType = &getDOFTypeFromDOFInstance($MatrixColNames[$col]);
			my $rowType = &getDOFTypeFromDOFInstance($MatrixRowNames[$row]);
			my $myCol = &getDOFTypeFromDOFInstance( $MatrixColNames[$col] );
			my $myRow = &getDOFTypeFromDOFInstance( $MatrixRowNames[$row] );			
			my @currentRow = split(/\s+/, $MatrixRows[$row]);
			my $expectedResult = $currentRow[$col];

            # If the table has an "X" for the expected result it means don't generate it.
            if ($expectedResult ne "X") {
            	$numTests = $numTests + 1;
            }
		}
	}
	return $numTests;
}

sub createTests();
sub createTests() {
	my $startCount = shift(@_);
	my $testCnt = 0;
	my $cellsCounted = 0;
	for (my $col = 0; $col <= $#MatrixColNames; $col++) {
		for (my $row = 0; $row <= $#MatrixRowNames; $row++) {
			my $colType = &getDOFTypeFromDOFInstance($MatrixColNames[$col]);
			my $rowType = &getDOFTypeFromDOFInstance($MatrixRowNames[$row]);
			my $myCol = &getDOFTypeFromDOFInstance( $MatrixColNames[$col] );
			my $myRow = &getDOFTypeFromDOFInstance( $MatrixRowNames[$row] );			
			my @currentRow = split(/\s+/, $MatrixRows[$row]);
			my $expectedResult = $currentRow[$col];
			
			# If the table has an "X" for the expected result it means don't 
			# generate a test for it.
			if ($expectedResult ne "X") {
				# startCnt and maxTests are used for the case where the suite 
				# improve JDK performance.
				if ($startCount > $cellsCounted++) {
					next;
				}
				# note that testCnt is 1-based (a requirement of the test harness)
				# each seperate file created starts with test id 1.
				if ($testCnt++ >= $maxTestsPerClass) {
					last;
				}
				
				print $outputFH "    /**\n";
				print $outputFH "     * Perform the test for the given matrix column ($MatrixColNames[$col]) and row ($MatrixRowNames[$row]).\n";
				print $outputFH "     * \n";
				print $outputFH "     */\n";
				print $outputFH "    public void test$MatrixColNames[$col]_$MatrixRowNames[$row]() {\n";
				print $outputFH "        test_id = getTestId(\"$MatrixColNames[$col]\", \"$MatrixRowNames[$row]\", \"$testCnt\");\n";
				print $outputFH "\n";
				print $outputFH "        NonRootModelElement src = select$colType(\"$MatrixColNames[$col]\");\n";
				print $outputFH "\n";
				print $outputFH "        NonRootModelElement dest = select$rowType(\"$MatrixRowNames[$row]\");\n";
				print $outputFH "\n";
				print $outputFH "        $myCol" . "_" . $myRow . "_" . "Action(src, dest);\n";				
                # There may be multiple expected results				
				my @resultInstanceNums = &getDOFInstanceIDListFromDOFInstance( $expectedResult );
				foreach my $resultID (@resultInstanceNums) {
					my $resultLineValue = $Results{ $resultID };
					my ($resultFunction, $resultDescription) = &readResultDescription($resultLineValue);
					print $outputFH "        assertTrue(\"$resultDescription\", checkResult_$resultFunction(src,dest));\n";
				}
				print $outputFH "        \n";
				print $outputFH "        GraphicalEditor editor = getActiveEditor();\n";
				print $outputFH "        if(editor != null && useDrawResults) {\n";
				print $outputFH "           validateOrGenerateResults(editor, generateResults);\n";
				print $outputFH "        }\n";
				print $outputFH "    }\n";
				print $outputFH "\n";
			}
		}
	}
}
	

sub createSupertypeClassWithTODOs();
sub createSupertypeClassWithTODOs() {
 	$className = $rootClassName;
	$fqOutputFileName = catfile($outPath, $className);
	$fqOutputFileName .= $suffix;    		

    if (-e $fqOutputFileName) {
        if ($overwriteFiles) {
        	print("\tOverwriting the existing test superclass: $className\n");
        } else {
        	print("\tSuperclass: $className exists.  Skipping generation of this file.\n");
        	return;
        }
    } else {
        	print("\tCreating the test superclass: $className\n");
    }
	
   	open( $outputFH, ">" . $fqOutputFileName ) or die "ERROR:  Could not open file $fqOutputFileName.\n";
   	&createCopyrightAndPackageSpec( 0 );
   	createImports();
   	&createGenericClassDefintion( 0 );
   	&createInstanceAccessors();
   	createCellAction();
   	createResultActions();
   	print $outputFH "}\n";
   	close($outputFH);
}

sub createTestClass();
sub createTestClass() {
    my $startCount = shift(@_);
    
    if (-e $fqOutputFileName) {
    	print("\tOverwriting test class: $className\n");
    } else {
    	print("\tCreating test class: $className\n");
    }
	open( $outputFH, ">" . $fqOutputFileName ) or die "ERROR:  Could not open file $fqOutputFileName.\n";
	&createCopyrightAndPackageSpec( 1 );
	createImports();
	&createGenericClassDefintion( 1 );
	&createTests($startCount);	
	print $outputFH "}\n";
	close($outputFH);
}

sub generateAllTestFiles();
sub generateAllTestFiles() {
	my $numTests = getNumTests();
	print "\tTotal number of tests to generate: $numTests\n";
	my $numSeperateFiles = sprintf("%d", $numTests / $maxTestsPerClass);
	my $remainder = $numTests % $maxTestsPerClass;
	if ($numSeperateFiles > 0 or $remainder > 0) {
	    $numSeperateFiles++;
	}
	
	($className, $outPath, $suffix) = fileparse( $fqOutputFileName, qr/\.[^.]*/ );
 	$rootClassName = $className;
    
    # create each Junit test class
	my $startCount = 0;
    for (my $i = 0; $i < $numSeperateFiles; $i++) {
   		$className = $rootClassName . "_" . $i;
   		$fqOutputFileName = catfile($outPath, $className);
   		$fqOutputFileName .= $suffix;    		
		($className, $outPath, $suffix) = fileparse( $fqOutputFileName, qr/\.[^.]*/ );
        
        &createTestClass($startCount);
   		push(@classNameList, $className); # keep track of each test class created    		
   		$startCount += $maxTestsPerClass;		
    }
    
    createSupertypeClassWithTODOs();    
    
    # If multiple test files had to be created create a new test suite as 
    # a convience for the caller (they may or may not use it).
    if ($numSeperateFiles > 1 || $createTestSuite) {    
    	if ($createTestSuitePerClass) {
			for (my $i = 0; $i <= $#classNameList; $i++) {
				&createTestSuiteClass( $classNameList[$i] );
	    		&createTestSuiteResultClass();
			}
    	} else {
	    	&createTestSuiteClass( $rootClassName );
    		&createTestSuiteResultClass();
    	}
    }
}

&processCommandLine();

open( $matrixFH, $matrixFileName ) or die "ERROR:  Could not open file $matrixFileName.\n";
print "Reading the Degrees Of Freedom section...\n";
readDegreesOfFreedom();

print "Reading the Results section...\n";
readResults();

print "Reading the Matrix rows...\n";
readMatrix();


print "Generating files...\n";
generateAllTestFiles();

print "Success!\n";
