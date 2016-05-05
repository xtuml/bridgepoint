# 
# UK Crown Copyright (c) 2016. All Rights Reserved.
#
DOCBOOK_ROOT=http://docbook.sourceforge.net/release/xsl-ns/current

MAIN = maslrefman.xml

SRC = \
	preface.xml \
	bibliography.xml \
	grammars.xml \
	lexicalstructure.xml \
	types.xml \
	typeconversion.xml \
	domains.xml \
	objects.xml \
	relationships.xml \
	lifecycles.xml \
	functions.xml \
	services.xml \
	exceptions.xml \
	statements.xml \
	names.xml \
	expressions.xml \
	languagedefinedcharacteristics.xml \
	syntaxsummary.xml


COMMON_STYLE = $(wildcard stylesheets/common/*.xsl)

HTML_STYLE = $(COMMON_STYLE) $(wildcard stylesheets/html/*.xsl)

FO_STYLE = $(COMMON_STYLE) $(wildcard stylesheets/fo/*.xsl)

IMAGES = $(wildcard images/*)

all: html/index.html maslrefman.pdf

generated/stylesheets/html/titlepage.xsl: templates/html/titlepage.xml
	xsltproc --xinclude --output $@ $(DOCBOOK_ROOT)/template/titlepage.xsl templates/html/titlepage.xml

generated/stylesheets/fo/titlepage.xsl: templates/fo/titlepage.xml
	xsltproc --xinclude --output $@ $(DOCBOOK_ROOT)/template/titlepage.xsl templates/fo/titlepage.xml

html/index.html: $(MAIN) $(HTML_STYLE) generated/stylesheets/html/titlepage.xsl $(SRC) $(IMAGES)
	xsltproc --xinclude stylesheets/html/stylesheet.xsl $(MAIN)
	cp -R images html

generated/maslrefman.fo: $(MAIN) $(FO_STYLE) generated/stylesheets/fo/titlepage.xsl $(SRC)
	xsltproc --xinclude stylesheets/fo/stylesheet.xsl $(MAIN) | xsltproc --xinclude add_last_page_ref.xslt - | xmllint --format --nsclean -> $@

%.pdf: generated/%.fo $(IMAGES)
	cp -R images generated
	fop -fo $< $@
	
clean:
	rm -rf generated html maslrefman.pdf
