README
==========

Much of the documentation here was written with MS Word as a WYSIWYG HTML editor.  

This has obvious drawbacks for an open-source project.  If those files must be
edited as is, you can try to use LibreOffice.  

However, the xtUML team will move to more robust and open means of documentation
creation.  For example, the ```Reference > UserInterface > PackageReferences```
documentation source form is github-flavored markdown (found in ```PackageReferences.md```).  

The HTML version of this document is used inside the eclipse help system and it
is generated with the tool ```pandoc```.

```
$ pandoc PackageReferences.md -f markdown_github -t html5 -o PackageReferences.html -c ../../../github-pandoc.css 
```
