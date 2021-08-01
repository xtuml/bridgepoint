#!/bin/bash

if [[ $1 == "test" ]]; then
  java -cp target/XMITranslate.jar org.xtuml.bp.io.xmi.translate.XMITranslator \
      -x src/main/resources/messages.xml \
      -mm src/main/resources/metamodel2.xml \
      -t src/main/resources/xmiTrans2_0.xml \
      -t src/main/resources/xtumlExtensionsTrans.xml \
      -verbose
  exit 0;
fi

java -cp target/XMITranslate.jar org.xtuml.bp.io.xmi.translate.XMITranslator $@ 
