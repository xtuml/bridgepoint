package org.xtuml.bp.mc.utilities;

import java.io.PrintStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;
import org.xtuml.bp.core.CorePlugin;

public class ModelCompilerConsoleManager {

    public PrintStream out;
    public PrintStream err;
    
    public ModelCompilerConsoleManager() {
        out = System.out;
        err = System.err;
    }

    public void configureConsole(String consoleName) {
        // prepare the console
        if (PlatformUI.isWorkbenchRunning()) {
            out = new PrintStream(findConsole(consoleName).newOutputStream());
            IOConsoleOutputStream errStream = findConsole(consoleName).newOutputStream();
            err = new PrintStream(errStream);
            Display.getDefault().asyncExec(() -> {
                errStream.setColor(new Color(Display.getDefault(), 255, 0, 0));
                try {
                    IConsoleView view = (IConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
                    view.display(findConsole(consoleName));
                } catch (PartInitException e) {
                    CorePlugin.logError("Error. Could not allocate console for build: " + e.getMessage(), e);
                }
            });
        } else {
            out = System.out;
            err = System.err;
        }
    }

    private MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++)
            if (name.equals(existing[i].getName()))
                return (MessageConsole) existing[i];
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, CorePlugin.getImageDescriptor("green-bp.gif"));
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }

}
