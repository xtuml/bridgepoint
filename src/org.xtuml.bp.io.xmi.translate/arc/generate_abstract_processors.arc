.// Generate abstract processors for xtuml
.include "utils.inc"
.select any ooaofgraphics from instances of EP_PKG where (selected.Name == "ooaofgraphics")
.select many objs from instances of O_OBJ
.for each obj in objs
package org.xtuml.bp.io.xmi.translate.processors.generated;

import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;

public abstract class Abstract$Cr{obj.Name}Processor extends AbstractXtumlTypeProcessor {
  .select many attrs related by obj->O_ATTR[R102];
  .for each attr in attrs
    .select one prevAttr related by attr->O_ATTR[R103.'succeeds']
    .if (empty prevAttr)
      .while (not_empty attr)
        .invoke persistent = attr_is_persistent(attr)
        .if(persistent.result)
    public abstract String get${attr.Name}();
        .end if
        .select one nextAttr related by attr->O_ATTR[R103.'precedes']
        .assign attr = nextAttr
      .end while
    .end if
  .end for
  .if(not_empty ooaofgraphics)  
    @Override
    public boolean isGraphical() {
        return true;
    }
  .end if
}
  .emit to file "src/main/java/org/xtuml/bp/io/xmi/translate/processors/generated/Abstract$Cr{obj.Name}Processor.java"
.end for