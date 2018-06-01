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

> * The HTML is generated using version 1.19.2.1 of `pandoc`. This specific release can be downloaded here: https://github.com/jgm/pandoc/releases/tag/1.19.2.1
> * There is a [Markdown cheatsheet here](https://guides.github.com/pdfs/markdown-cheatsheet-online.pdf)
> * The original [github-like CSS is here](https://gist.github.com/dashed/6714393)
> * There are browser extensions that will render markdown to html for local files, which is handy when writing docs.  The one I use for chromium is called "Markdown Viewer"

To generate PDFs for release emails: 
Open the html file in google chrome and use the built-in `Print > Save as PDF` feature.  This leaves 
off unwanted header and footer text like the file URL.

To update the release notes closed issues list:
- Run the query on support.onefact.net, modify the query to only show the Issue number and Subject columns
- Use the mouse to highlight the issues and copy
- Paste into ReleaseNotes.md which will leave lines like:

```
    8256    MASL Project Conversion Guide (System level Modeling flow)
    8628    Update MASL Conversion Guide
    8257    OAL to MASL
```

- Save the ReleaseNotes.md
- Choose from the menu Search > File
- In containing text type the following without quotes but with spaces: "    (\d+)   "
- Check the "Regular expression" checkbox
- In Filename enter ReleaseNotes.md
- Click Replace...
- In the With field enter: [$1](https://support.onefact.net/issues/$1) | 
- Click OK
 
To duplicate a column in vi:
- In VIM, press Ctrl + V to go in Visual Block mode.
- Select the required columns with your arrow keys and press x to cut them in the buffer.
- Move cursor to row 1 column 9 and press P (thats capital P) in command mode.

To add square brackets around the first number:
```
:%s/ \([0-9][0-9]*\) /\[\1\] /
```

To add links to the issue numbers in the first column, use vi and...
```
:%s/ \([0-9][0-9]*\) /(https:\/\/support.onefact.net\/issues\/\1) /
```
