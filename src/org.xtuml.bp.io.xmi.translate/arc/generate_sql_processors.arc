.// Generate sql processor for xtuml elements
.include "utils.inc"
.select many objs from instances of O_OBJ
.for each obj in objs
package org.xtuml.bp.io.xmi.translate.processors.generated.impl;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.Abstract$Cr{obj.Name}Processor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class $Cr{obj.Name}ProcessorSQL extends Abstract$Cr{obj.Name}Processor {
  .select many attrs related by obj->O_ATTR[R102];
  .for each attr in attrs
    .select one prevAttr related by attr->O_ATTR[R103.'succeeds']
    .if (empty prevAttr)
      .while (not_empty attr)
        .invoke persistent = attr_is_persistent(attr)
        .if(persistent.result)
    @Override
    public String get${attr.Name}() {
          .select one type related by attr->S_DT[R114]
          .select one ref_attr related by attr->O_RATTR[R106]
          .if(not_empty ref_attr)
            .select one type related by ref_attr->O_BATTR[R113]->O_ATTR[R106]->S_DT[R114]
          .end if
          .if(type.Name == "string")
        // determine where to get string value, use SQLUtils.stringValue(<string value from EA Element>)
        // If a naming attribute use:
        //   return SQLUtils.stringValue(getModelElement().getName());
        return null;
          .elif(type.Name == "unique_id")
            .invoke isui = is_single_unique_id(attr)
            .if(isui.result)
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
            .else
        // determine where to get unique id, use SQLUtils.idValue(<string value of EA id>)
        return null;
            .end if;
          .elif(type.Name == "real" or type.Name == "integer" or type.Name == "long")
        // determine where to get number value, use SQLUtils.numberValue(<number value from EA Element>)
        return null;
          .else
        // Replace with appropriate data from XMI model
        return null;
          .end if
    }
        .end if
        .select one nextAttr related by attr->O_ATTR[R103.'precedes']
        .assign attr = nextAttr
      .end while
      .break for
    .end if
  .end for

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(
  .assign count = 0;
  .for each attr in attrs
    .select one prevAttr related by attr->O_ATTR[R103.'succeeds']
    .if (empty prevAttr)
      .while (not_empty attr)
        .invoke persistent = attr_is_persistent(attr)
        .if(persistent.result)
          .if(count == 0)
          get${attr.Name}()
          .else
          , get${attr.Name}()
          .end if
        .end if
        .assign count = count + 1
        .select one nextAttr related by attr->O_ATTR[R103.'precedes']
        .assign attr = nextAttr
      .end while
    .end if
  .end for
        );
    }
}
  .emit to file "src/main/java/org/xtuml/bp/io/xmi/translate/processors/generated/impl/$Cr{obj.Name}ProcessorSQL.java"
.end for