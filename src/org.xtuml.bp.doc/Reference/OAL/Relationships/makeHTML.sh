#!/bin/bash
asciidoctor -b html5 -a stylesheet=../../../asciidoctor-default.css -a linkcss -a copycss! -a reproducible= Relationships.adoc
