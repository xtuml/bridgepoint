# XMI to xtuml translator

## Building

### Dependencies

At this point the tool has some parts generated, this generation depends on the ooaofooa and the ooaofgraphics. This allows the translator to keep in sync with iterations of the xtuml metamodels. You must clone org.xtuml.bp.core and org.xtuml.bp.ui.canvas projects as siblings to this project.

### Packaging

The XMI translator project is managed by maven. Run the following to create the executable:

```sh
mvn package
```

## Running

This project has a utility script to handle the java arguments, XMITranslate. You can run this after packaging passing only "test" as an argument to verify execution. This will output some sql statements along with a completion message.

Available arguments:

```
usage: XMITranslate
 -mm,--metamodel <metamodel path>      The path to a UML 2 based
                                       metamodel.
 -out,--output <output file>           The path to the desired output
                                       xtuml file
 -overwrite                            Overwrite the path to the desired
                                       output xtuml file
 -t,--transformer <transformer path>   The path to a SDMetric transformer.
 -v,--verbose                          Enable all output messages
 -x,--xmi <file path>                  The path to a UML 2 compatible XMI
                                       document.
```
