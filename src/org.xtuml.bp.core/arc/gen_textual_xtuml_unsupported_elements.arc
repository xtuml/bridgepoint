.// Output a class containing the list of unsupported textual xtUML Elements
package org.xtuml.bp.core.common;

public class TextualXtumlUnsupportedElements {
	
	public static final Class<?>[] UNSUPPORTED_ELEMENTS = {\
.select many unsupported_elements from instances of UnsupportedElement
.assign sep = "\n"
.for each el in unsupported_elements
  .select any o_obj from instances of O_OBJ where (selected.Key_Lett == el.KeyLetters)
  .if (not_empty o_obj)
${sep}			org.xtuml.bp.core.$Cr{o_obj.Name}_c.class\
    .assign sep = ",\n"
  .else
    .print "WARNING: Could not find key letters: ${el.KeyLetters}"
  .end if
.end for

	};

	public static final String[] UNSUPPORTED_ELEMENT_NAMES = {\
.assign sep = "\n"
.for each el in unsupported_elements
  .select any o_obj from instances of O_OBJ where (selected.Key_Lett == el.KeyLetters)
  .if (not_empty o_obj)
${sep}			"$C{o_obj.Name}"\
    .assign sep = ",\n"
  .else
    .print "WARNING: Could not find key letters: ${el.KeyLetters}"
  .end if
.end for

	};

}
.//
.emit to file "src/org/xtuml/bp/core/common/TextualXtumlUnsupportedElements.java"
.//
.select many o_objs from instances of O_OBJ ordered_by (Key_Lett)
.for each o_obj in o_objs
  .select one pkg related by o_obj->PE_PE[R8001]->EP_PKG[R8000]
  .if (("$l{o_obj.Descrip:Persistent}" != "false") and ("$l{pkg.Descrip:Persistent}" != "false"))
INSERT INTO UnsupportedElement VALUES ('${o_obj.Key_Lett}');  -- '${o_obj.Name}'
  .end if
.end for
.//.emit to file "out.txt"