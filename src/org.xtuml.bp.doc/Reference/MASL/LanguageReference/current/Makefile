# 
# UK Crown Copyright (c) 2016. All Rights Reserved.
#
DOCBOOK_ROOT=http://docbook.sourceforge.net/release/xsl-ns/current

MAIN = refman.xml

SRC =   info.xml \
	lexical.xml \
	types.xml \
	domains.xml \
	services.xml \
	relationships.xml \
	exceptions.xml \
	terminators.xml \
	objects.xml \
	statements.xml \
	expressions.xml \
	projects.xml \
	pragmas.xml \
	bibliography.xml  \
	syntax.xml

COMMON_STYLE = $(wildcard stylesheets/common/*.xsl)
	
HTML_STYLE = $(COMMON_STYLE) $(wildcard stylesheets/html/*.xsl)

FO_STYLE = $(COMMON_STYLE) $(wildcard stylesheets/fo/*.xsl)

all: html/index.html maslrefman.pdf

generated/stylesheets/html/titlepage.xsl: templates/html/titlepage.xml
	xsltproc --xinclude --output $@ $(DOCBOOK_ROOT)/template/titlepage.xsl templates/html/titlepage.xml

generated/stylesheets/fo/titlepage.xsl: templates/fo/titlepage.xml
	xsltproc --xinclude --output $@ $(DOCBOOK_ROOT)/template/titlepage.xsl templates/fo/titlepage.xml

generated/alphasyntax.xml: syntax.xml alphasyntax.xslt
	xsltproc --xinclude --output $@ alphasyntax.xslt syntax.xml 

html/index.html: $(MAIN) $(HTML_STYLE) generated/stylesheets/html/titlepage.xsl generated/alphasyntax.xml $(SRC)
	xsltproc --xinclude stylesheets/html/stylesheet.xsl $(MAIN)

generated/maslrefman.fo: $(MAIN) $(FO_STYLE) generated/stylesheets/fo/titlepage.xsl generated/alphasyntax.xml $(SRC)
	xsltproc --xinclude stylesheets/fo/stylesheet.xsl $(MAIN) | xsltproc --xinclude add_last_page_ref.xslt - | xmllint --format --nsclean -> $@

%.pdf: generated/%.fo
	fop -fo $< $@
	
clean:
	rm -rf generated html maslrefman.pdf
