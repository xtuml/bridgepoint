--

This work is licensed under the Creative Commons CC0 License

---

# Class-based operations in a class are not clearly distinguishable from instance-based operations
### xtUML Project Implementation Note

### 1. Abstract

Class-based operations in a class are not clearly distinguishable from
instance-based operations. They appear in the same way. In BridgePoint 6.1 they
were underlined to distinguish them form instance-based operations. This is the
UML standard notation.

### 2. Document References

<a id="2.1"></a>2.1 [Service Pro SR #4859](https://support.onefact.net/issues/4859) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #10227](https://support.onefact.net/issues/10227)  
<a id="2.3"></a>2.3 [Analysis note](../4859_class_ops/4859_class_ops_ant.md)  

### 3. Background

None.

### 4. Requirements

4.1 Class-based operations shall be clearly distinguishable from instance-based
operations on the canvas.  
4.1.1 Class-based operations shall be underlined.  
4.2 Class-based operations shall be clearly distinguishable from instance-based
operations in model explorer.  

### 5. Analysis

5.1 See [Analysis note for class-based operations](#2.3)  

### 6. Additional Work Needed  
6.1 The [analysis note](2.3) lays out the work required to meet the requirements.  
6.1.1 Initial work was completed as a line graphic drawn under the text, and  
6.1.2 A different color icon used for the class-based operation.  
6.3 A more distinct icon change is desired.  
6.4 A new approach for underline is needed, due to scaling issues with the line graphic.  

### 7. Unit Test  
7.1 Run the manual test suite for this issue [2.3](#2.3).  

### 8. User Documentation  
8.1 Model editor user documentation is to be updated to reflect the meaning of an underlined operation and the new iconography.  

### 9. Code Changes  
9.1  In src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/draw/GraphicsGCDelegate.java  
Added a method to draw text using a TextLayout to allow for underlined text.

    @Override
    public void drawTextLayout(String string, int x, int y, boolean b, TextStyle style) {
		final TextLayout layout = new TextLayout(this.getDevice());
        layout.setText(string);
        layout.setStyle(style, 0, string.length());
        fGraphics.drawTextLayout(layout, x, y);
        layout.dispose();
    }  

9.2 In src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/GCDelegate.java  
Added a method to override.

	public void drawTextLayout(String string, int i, int j, boolean b, TextStyle style) {
		context.drawText(string, i, j, b);
	}

9.3 In src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Gr_c.java  
Changed the Drawtext method to call the new drawTextLayout method for underline style. Added additional commentary indicating that all cases should use the new method.  

    } else {
      if ( Style_c.Underlined == Text_style ) {
        final TextStyle style = new TextStyle();
        style.font = Context.getFont();
        style.underline = true;
        Context.drawTextLayout(Text, (int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), false, style);
      }
      else
      {
        Context.drawText(Text, (int) (X * m_ZoomFactor), (int) (Y * m_ZoomFactor), true);
      }
    }


### End
