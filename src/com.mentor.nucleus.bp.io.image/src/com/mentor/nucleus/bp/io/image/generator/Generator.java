package com.mentor.nucleus.bp.io.image.generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.io.image.Activator;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsEditPartFactory;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsScalableFreeformEditPart;
import com.mentor.nucleus.bp.ui.graphics.print.PrintDiagramOperation;

public class Generator extends Task {
	static IProject project;
	private static String SMPD_NAME = ": System Model Package Diagram"; //$NON-NLS-1$

    public static void genAll(final SystemModel_c sys) {
        genAll(sys, null);
    }
    
	public static void genAll(final SystemModel_c sys, IProject destinationProject) {
		project = getProject(sys);
		PersistableModelComponent pmc = sys.getPersistableComponent();
		String rootPath = pmc.getContainingDirectoryPath().toString();
		sys.getPersistableComponent().loadComponentAndChildren(
				new NullProgressMonitor());
		Ooaofgraphics[] ooags = Ooaofgraphics.getInstances();
		String warningMessage =  "Failed to create image for:\n";
		boolean showWarning = false;
		for (int i = 0; i < ooags.length; i++) {
			Ooaofgraphics ooag = ooags[i];
			InstanceList models = ooag.getInstanceList(Model_c.class);
			for (NonRootModelElement nrModel : models) {
				Model_c model = (Model_c) nrModel;
				if (model.getRepresents() != null) {
					ModelRoot mr = ((NonRootModelElement) model.getRepresents())
							.getModelRoot();
					String canvasName = GraphicsUtil
							.getCanvasEditorTitle((NonRootModelElement) model
									.getRepresents());
					if ((mr.getId().startsWith(rootPath))
							|| canvasName.equals(project.getName() + SMPD_NAME)) {
					    try {
						  createImage(model, Ooaofooa.getInstance(mr.getId()), destinationProject);
					    } catch (Throwable e) {
					        showWarning = true;
					        warningMessage += "\n\t" + canvasName;
					        CorePlugin.logError(warningMessage, e);
			            }
					}
				}
			}			
		}
		
        if ( showWarning ) {
            warningMessage += "\n\nPlease reduce the size of these canvases by grouping the elements on each canvas closer together.";
            UIUtil.openWarning(warningMessage);              
        }
	}

	public static void run(Component_c rootElement) {
		ModelRoot root = rootElement.getModelRoot();
		Ooaofgraphics ooag = Ooaofgraphics.getInstance(root.getId());
		InstanceList models = ooag.getInstanceList(Model_c.class);
		Model_c ptCanvas = null;
		for (NonRootModelElement nrModel : models) {
			Model_c model = (Model_c) nrModel;
			if (model.getRepresents().equals(rootElement)) {
				ptCanvas = model;
				break;
			}
		}
		createImage(ptCanvas, Ooaofooa.getInstance(root.getId()), null);
	}

	private static void createImage(final Model_c ptCanvas,
			final Ooaofooa modelRoot, final IProject destinationProject) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				// make sure that all graphical represents are setup
				CanvasModelListener.setGraphicalRepresents(ptCanvas);
				
				String prefFont = PlatformUI.getWorkbench()
						.getPreferenceStore().getString(
								"com.mentor.nucleus.bp.canvas.font"); //$NON-NLS-1$
				prefFont = prefFont.substring(0, prefFont.indexOf(';'));
				FontData prefFontData = new FontData(prefFont);
				Font displayFont = new Font(Display.getDefault(), prefFontData);
				Shell shell = new Shell();
				FigureCanvas figureCanvas = new FigureCanvas(shell);
				GraphicalViewer viewer = new ScrollingGraphicalViewer();
				viewer.createControl(figureCanvas);
				figureCanvas.setFont(displayFont);
				viewer.setEditPartFactory(new GraphicsEditPartFactory());
				ScalableFreeformRootEditPart rootEditPart = new GraphicsScalableFreeformEditPart();
				viewer.setRootEditPart(rootEditPart);
				viewer.setContents(ptCanvas);
				rootEditPart.getFigure().validate();
				IFigure cLayer = rootEditPart.getLayer(LayerConstants.CONNECTION_LAYER);
				cLayer.validate();
				figureCanvas.getViewport().getUpdateManager().performValidation();
                GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(ptCanvas);
				Rectangle extentRectangle = GraphicalZoomManager
						.getExtentRectangle(GraphicalEditor.getAllSymbols(
								viewer, ptCanvas.Hascontainersymbol()));
				if((elements.length != 0) && (extentRectangle == null)) {
					// return here as there is no way to determine the image
					// size
					displayFont.dispose();
					figureCanvas.dispose();
                    if ( (shell != null) && !shell.isDisposed() ) {
                        shell.dispose();
                    }
					return;
				}
				
                ImageLoader imgLoader = new ImageLoader();
				if ( elements.length == 0 ) {
                    Image img = getImageFor("resources/images/blank_canvas.png");
                    imgLoader.data = new ImageData[] { img.getImageData() };
                    img.dispose();
				} else {
	                Image img = new Image(Display.getDefault(),
	                        extentRectangle.width, extentRectangle.height);
	                PrintDiagramOperation.printImage(img, viewer, extentRectangle,
	                        ptCanvas.Hascontainersymbol(), PrintDiagramOperation.FIT_PAGE);
	                imgLoader.data = new ImageData[] { img.getImageData() };
                    img.dispose();
				}
				
				String fullPath = "";
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					imgLoader.save(out, SWT.IMAGE_PNG);
					byte[] buffer = out.toByteArray();
					out.close();
					IProject destProject = destinationProject;
					if(destProject == null) {
					    // use the current element to find the project
					    NonRootModelElement element = (NonRootModelElement) ptCanvas.getRepresents();
					    destProject = element.getFile().getProject();
					}
					IPath path = destProject.getLocation();
					path = path.append("doc"); //$NON-NLS-1$
					File file = path.toFile();
					if (!file.exists()) {
						file.mkdir();
					}
					path = path.append("images"); //$NON-NLS-1$
					file = path.toFile();
					if (!file.exists()) {
						file.mkdir();
					}
					String containerName = GraphicsUtil.getContainerList(
							ptCanvas, modelRoot);
					String fileNameBody = GraphicsUtil
							.getCanvasEditorTitle((NonRootModelElement) ptCanvas
									.getRepresents());
					fileNameBody = fileNameBody.replace(": ", "-"); //$NON-NLS-1$ //$NON-NLS-2$
					if (!containerName.isEmpty()) {
						fileNameBody = containerName + fileNameBody;
					}
					fullPath = path
							.append(fileNameBody + ".png").toString(); //$NON-NLS-1$
					FileOutputStream fos = new FileOutputStream(fullPath);
					fos.write(buffer);
					fos.close();
				} catch (IOException e) {
					CorePlugin.logError("Failed writing image: "  + fullPath, e);
				} finally {
					// need to deactivate the DiagramEditPart to handle
					// layer removal
					viewer.getContents().deactivate();
					displayFont.dispose();
					figureCanvas.dispose();
					if ( (shell != null) && !shell.isDisposed() ) {
					    shell.dispose();
					}
				}
			}
		});
	}

	public static IProject getProject(SystemModel_c sys) {

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject proj : projects) {
			if (proj.getName().equals(sys.getName())) {
				return proj;
			}
		}
		return null;
	}
	
    public static ImageDescriptor getImageDescriptor(String name) {
        try {
            Activator plugin = Activator.getDefault();
            if (plugin == null) {
                RuntimeException rtException = new RuntimeException("Unable to acquire the bp.io.image.Activator, therefore the image " + name + " can not be loaded.");
                throw rtException;
            }
            URL installURL = plugin.getBundle().getEntry("/");
            URL url = new URL(installURL, name);

            InputStream check = null;
            try {
                check = url.openStream();
            } catch (IOException e1) {
                CorePlugin.logError("Can not load the image: " + name, e1);
            } finally{
                if (check != null){
                    try {
                        check.close();
                    } catch (IOException e2) {
                    }
                }
            }

            ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
            return imageDescriptor;
        } catch (RuntimeException e) {
            return ImageDescriptor.getMissingImageDescriptor();
        } catch (MalformedURLException e) {
            return ImageDescriptor.getMissingImageDescriptor();
        }
    }

    public static Image getImageFor(String imagePath) {
        ImageDescriptor descriptor = getImageDescriptor(imagePath);
        Image image = descriptor.createImage();
        return image;
    }

}