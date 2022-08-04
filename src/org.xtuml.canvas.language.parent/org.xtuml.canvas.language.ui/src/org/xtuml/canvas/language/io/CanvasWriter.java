package org.xtuml.canvas.language.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.ContainingShape_c;
import org.xtuml.bp.ui.canvas.Diagram_c;
import org.xtuml.bp.ui.canvas.Elementstyle_c;
import org.xtuml.bp.ui.canvas.Fillcolorstyle_c;
import org.xtuml.bp.ui.canvas.FloatingText_c;
import org.xtuml.bp.ui.canvas.Graphconnector_c;
import org.xtuml.bp.ui.canvas.Graphedge_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.LineSegment_c;
import org.xtuml.bp.ui.canvas.Linecolorstyle_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.Waypoint_c;
import org.xtuml.bp.ui.canvas.persistence.IGraphicalWriter;
import org.xtuml.bp.ui.canvas.references.ReferencePathManagement;
import org.xtuml.canvas.language.canvas.Anchors;
import org.xtuml.canvas.language.canvas.Bounds;
import org.xtuml.canvas.language.canvas.CanvasFactory;
import org.xtuml.canvas.language.canvas.Connector;
import org.xtuml.canvas.language.canvas.ConnectorAnchorElement;
import org.xtuml.canvas.language.canvas.Connectors;
import org.xtuml.canvas.language.canvas.EndAnchor;
import org.xtuml.canvas.language.canvas.EnumEnd;
import org.xtuml.canvas.language.canvas.FloatingText;
import org.xtuml.canvas.language.canvas.FloatingTexts;
import org.xtuml.canvas.language.canvas.GraphicalElement;
import org.xtuml.canvas.language.canvas.Model;
import org.xtuml.canvas.language.canvas.ModelProperties;
import org.xtuml.canvas.language.canvas.ModelPropertiesItem;
import org.xtuml.canvas.language.canvas.ModelRender;
import org.xtuml.canvas.language.canvas.Point;
import org.xtuml.canvas.language.canvas.PointDefinition;
import org.xtuml.canvas.language.canvas.Polyline;
import org.xtuml.canvas.language.canvas.Segment;
import org.xtuml.canvas.language.canvas.Shape;
import org.xtuml.canvas.language.canvas.ShapeAnchorElement;
import org.xtuml.canvas.language.canvas.Shapes;
import org.xtuml.canvas.language.canvas.StartAnchor;
import org.xtuml.canvas.language.canvas.StyleItem;
import org.xtuml.canvas.language.canvas.Styles;
import org.xtuml.canvas.language.io.utils.EnumUtils;
import org.xtuml.canvas.language.ui.CanvasUiModule;
import org.xtuml.canvas.language.ui.internal.LanguageActivator;

import com.google.inject.Inject;

public class CanvasWriter implements IGraphicalWriter {

	@Inject
	IResourceSetProvider resourceSetProvider;

	CanvasFactory factory = CanvasFactory.eINSTANCE;
	Model model = null;

	private NonRootModelElement diagramRepresents;

	@Override
	public void initialize() {
		// nothing to do
	}

	@Override
	public void write(NonRootModelElement model) {
		write(model, false);
	}
	
	@Override
	public void write(NonRootModelElement model, boolean generate) {
		String textualSerialization = CorePlugin.getDefault().getPreferenceStore()
				.getString(BridgePointPreferencesStore.GRAPHICS_TEXTUAL_SERIALIZATION);
		if(MessageDialogWithToggle.NEVER.equals(textualSerialization)) {
			return;
		}
		IFile parentFile = model.getFile();
		IFile xtGraphFile = parentFile.getParent()
				.getFile(new Path(parentFile.getName().replaceAll(".xtuml", ".xtumlg")));
		try {
			write(model, xtGraphFile, generate);
		} catch (IOException | CoreException e) {
			CanvasUiModule.logError("Unable to persist textual graphics", e);
		}
	}

	public void write(NonRootModelElement diagramRepresents, IFile file, boolean generate) throws IOException, CoreException {
		// create an empty file
		if (!file.exists()) {
			file.create(new ByteArrayInputStream(new byte[0]), true, new NullProgressMonitor());
		}
		Model_c xtModel = Model_c.ModelInstance(Ooaofgraphics.getInstance(diagramRepresents.getModelRoot().getId()),
				m -> ((Model_c) m).getRepresents() == diagramRepresents);
		if (xtModel == null) {
			// if told to generate do that then write
			if(generate) {
				CanvasGenerator.getSingleton().load(diagramRepresents);
			}
			return;
		}
		CanvasPlugin.setGraphicalRepresents(xtModel);
		LanguageActivator.getInstance().getInjector("org.xtuml.canvas.language.Canvas").injectMembers(this);
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		ResourceSet rs = resourceSetProvider.get(file.getProject());
		Resource r = rs.getResource(uri, true);
		r.getContents().clear();
		model = factory.createModel();
		ModelRender modelRender = factory.createModelRender();
		modelRender.setImportURI(ReferencePathManagement.getPath(diagramRepresents));
		model.setRender(modelRender);
		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(xtModel);
		Fillcolorstyle_c backgroundColor = Fillcolorstyle_c.getOneSTY_FCSOnR400(Elementstyle_c.getManySTY_SsOnR402(xtModel));
		if ((diagram != null && diagram.getViewportx() != 0 && diagram.getViewporty() != 0) ||
				(backgroundColor != null)) {
			ModelProperties properties = factory.createModelProperties();
			if (diagram != null && diagram.getViewportx() != 0 && diagram.getViewporty() != 0) {
				ModelPropertiesItem pointItem = factory.createModelPropertiesItem();
				Point viewport = factory.createPoint();
				viewport.setX((int) diagram.getViewportx());
				viewport.setY((int) diagram.getViewporty());
				pointItem.setPoint(viewport);
				properties.getPropertyItems().add(pointItem);
				ModelPropertiesItem zoomItem = factory.createModelPropertiesItem();
				zoomItem.setZoom((int) diagram.getZoom());
				properties.getPropertyItems().add(zoomItem);
			}
			if (backgroundColor != null) {
				ModelPropertiesItem colorItem = factory.createModelPropertiesItem();
				colorItem.setColor(String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
				properties.getPropertyItems().add(colorItem);
			}
			model.setProperties(properties);
		}
		this.diagramRepresents = diagramRepresents;
		populateModel();
		r.getContents().add(model);
		SaveOptions.Builder options = SaveOptions.newBuilder();
		options.format();
		r.save(options.getOptions().toOptionsMap());
	}

	private void populateModel() {
		Model_c diagram = Model_c.ModelInstance(Ooaofgraphics.getInstance(diagramRepresents.getModelRoot().getId()),
				m -> ((Model_c) m).getRepresents() == diagramRepresents);
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(diagram);
		Shapes shapes = factory.createShapes();
		Connectors connectors = factory.createConnectors();
		model.getElements().add(shapes);
		model.getElements().add(connectors);
		Stream.of(elements).forEach(ele -> {
			Shape_c shp = Shape_c.getOneGD_SHPOnR2(ele);
			if (shp != null) {
				Shape shape = getOrCreateShape(shp);
				if (!shapes.getShapes().contains(shape)) {
					shapes.getShapes().add(shape);
				}
			}
			Connector_c con = Connector_c.getOneGD_CONOnR2(ele);
			if (con != null) {
				Connector connector = getOrCreateConnector(con);
				if (!connectors.getConnectors().contains(connector)) {
					connectors.getConnectors().add(connector);
				}
			}
		});
	}

	private Connector createConnector(Connector_c con) {
		GraphicalElement_c conEle = GraphicalElement_c.getOneGD_GEOnR2(con);
		Connector connector = factory.createConnector();
		connector.setType(EnumUtils.typeFor(conEle.getOoa_type()));
		StartAnchor startAnchor = createStartAnchor(con);
		EndAnchor endAnchor = createEndAnchor(con);
		Anchors anchors = factory.createAnchors();
		if (startAnchor != null) {
			anchors.setStartAnchor(startAnchor);
		}
		if (endAnchor != null) {
			anchors.setEndAnchor(endAnchor);
		}
		connector.setRepresents(getPath(GraphicalElement_c.getOneGD_GEOnR2(con)));
		connector.setName(getNameFromPath(connector.getRepresents()));
		connector.setPolyline(createPolyline(con));
		connector.setAnchors(anchors);
		createText(con, connector);
		createStyles(conEle, connector::setStyles);
		return connector;
	}

	private void createText(Connector_c con, Connector connector) {
		FloatingTexts floatingTexts = factory.createFloatingTexts();
		Stream.of(FloatingText_c.getManyGD_CTXTsOnR8(con)).forEach(text -> {
			Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(text);
			Graphelement_c ge = Graphelement_c.getOneDIM_GEOnR301(node);
			if (node.getWidth() != 0 || node.getHeight() != 0 || ge.getPositionx() != 0 || ge.getPositiony() != 0) {
				FloatingText floatingText = factory.createFloatingText();
				Bounds rect = factory.createBounds();
				rect.setX((int) ge.getPositionx());
				rect.setY((int) ge.getPositiony());
				rect.setW((int) node.getWidth());
				rect.setH((int) node.getHeight());
				floatingText.setBounds(rect);
				EnumEnd end = factory.createEnumEnd();
				end.setWhere(EnumUtils.endFor(text.getEnd()));
				floatingText.setEnd(end);
				floatingTexts.getTexts().add(floatingText);
			}
		});
		connector.setTexts(floatingTexts);
	}
	
	private void createText(Shape_c shp, Shape shape) {
		FloatingText_c text = FloatingText_c.getOneGD_CTXTOnR27(shp);
		if (text != null) {
			Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(text);
			Graphelement_c ge = Graphelement_c.getOneDIM_GEOnR301(node);
			FloatingText floatingText = factory.createFloatingText();
			Bounds rect = factory.createBounds();
			rect.setX((int) ge.getPositionx());
			rect.setY((int) ge.getPositiony());
			rect.setW((int) node.getWidth());
			rect.setH((int) node.getHeight());
			floatingText.setBounds(rect);
			EnumEnd end = factory.createEnumEnd();
			end.setWhere(EnumUtils.endFor(text.getEnd()));
			floatingText.setEnd(end);
			shape.setText(floatingText);
		}
	}
	
	private void createStyles(GraphicalElement_c ge, Consumer<Styles> setter) {
		Fillcolorstyle_c fillColor = Fillcolorstyle_c.getOneSTY_FCSOnR400(Elementstyle_c.getManySTY_SsOnR401(ge));
		Linecolorstyle_c lineColor = Linecolorstyle_c.getOneSTY_LCSOnR400(Elementstyle_c.getManySTY_SsOnR401(ge));
		if (fillColor != null || lineColor != null) {
			Styles styles = factory.createStyles();
			if (fillColor != null) {
				StyleItem fillItem = factory.createStyleItem();
				fillItem.setFill_color(String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
				styles.getStyle_items().add(fillItem);
			}
			if (lineColor != null) {
				StyleItem lineItem = factory.createStyleItem();
				lineItem.setLine_color(String.format("#%02x%02x%02x", lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue()));
				styles.getStyle_items().add(lineItem);
			}
			setter.accept(styles);
		}
	}

	private String getNameFromPath(String represents) {
		String containerPath = diagramRepresents.getPath();
		String elementName = represents.replaceFirst(containerPath + "::", "");
		return elementName.replaceAll(" ", "").replaceAll(":", "__").replaceAll("\\.", "_").replaceAll("-",
				"").replaceAll(">", "").replaceAll("\\(", "_").replaceAll("\\)", "_");
	}

	private Polyline createPolyline(Connector_c con) {
		Polyline polyline = factory.createPolyline();
		LineSegment_c nextSeg = LineSegment_c.getOneGD_LSOnR6(con,
				s -> ((LineSegment_c) s).getPrevious_elementid().equals(Gd_c.Null_unique_id()));
		while (nextSeg != null) {
			Waypoint_c startWay = Waypoint_c.getOneDIM_WAYOnR21(nextSeg);
			Waypoint_c endWay = Waypoint_c.getOneDIM_WAYOnR22(nextSeg);
			Segment segment = factory.createSegment();
			Point start = factory.createPoint();
			start.setX((int) startWay.getPositionx());
			start.setY((int) startWay.getPositiony());
			Point end = factory.createPoint();
			end.setX((int) endWay.getPositionx());
			end.setY((int) endWay.getPositiony());
			segment.setStartPoint(start);
			segment.setEndPoint(end);
			polyline.getSegments().add(segment);
			nextSeg = LineSegment_c.getOneGD_LSOnR7Precedes(nextSeg);
		}
		return polyline;
	}

	private Shape createShape(Shape_c shp) {
		GraphicalElement_c ele = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Graphelement_c ge = Graphelement_c.getOneDIM_GEOnR23(ele);
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(shp);
		Shape shape = factory.createShape();
		shape.setType(EnumUtils.typeFor(ele.getOoa_type()));
		Bounds rect = factory.createBounds();
		rect.setX((int) ge.getPositionx());
		rect.setY((int) ge.getPositiony());
		rect.setW((int) node.getWidth());
		rect.setH((int) node.getHeight());
		shape.setBounds(rect);
		shape.setRepresents(getPath(ele));
		shape.setName(getNameFromPath(shape.getRepresents()));
		ContainingShape_c containerShp = ContainingShape_c.getOneGD_CTROnR28(shp);
		if (containerShp != null) {
			shape.setContainer("container");
		}
		createText(shp, shape);
		createStyles(ele, shape::setStyles);
		return shape;
	}

	private String getPath(GraphicalElement_c ele) {
		return ReferencePathManagement.getPath((NonRootModelElement) ele.getRepresents());
	}

	private StartAnchor createStartAnchor(Connector_c con) {
		StartAnchor startAnchor = null;
		Graphconnector_c graphConnector = Graphconnector_c.getOneDIM_CONOnR320(Graphedge_c.getOneDIM_EDOnR20(con));
		GraphicalElement_c startElem = GraphicalElement_c
				.getOneGD_GEOnR23(Graphelement_c.getOneDIM_GEOnR311(graphConnector));
		startAnchor = factory.createStartAnchor();
		if (startElem != null) {
			Shape_c shpAnchor = Shape_c.getOneGD_SHPOnR2(startElem);
			if (shpAnchor != null) {
				ShapeAnchorElement shapeAnchor = factory.createShapeAnchorElement();
				startAnchor.setAnchor(shapeAnchor);
				Shape shape = getOrCreateShape(shpAnchor);
				shapeAnchor.setElement(shape);
			} else {
				Connector_c conAnchor = Connector_c.getOneGD_CONOnR2(startElem);
				if (conAnchor != null) {
					ConnectorAnchorElement connectorAnchor = factory.createConnectorAnchorElement();
					startAnchor.setAnchor(connectorAnchor);
					Connector connector = getOrCreateConnector(conAnchor);
					connectorAnchor.setElement(connector);
				}
			}
		}
		// some connectors start on whitespace
		PointDefinition anchorPoint = factory.createPointDefinition();
		if (startElem != null) {
			anchorPoint.setX((int) graphConnector.getPositionx());
			anchorPoint.setY((int) graphConnector.getPositiony());
		} else {
			// get the values from the first segment waypoint
			LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(con);
			LineSegment_c firstSegment = segments[0];
			Waypoint_c startWayPoint = Waypoint_c.getOneDIM_WAYOnR21(firstSegment);
			anchorPoint.setX((int) startWayPoint.getPositionx());
			anchorPoint.setY((int) startWayPoint.getPositiony());
		}
		startAnchor.setPoint(anchorPoint);
		return startAnchor;
	}

	private EndAnchor createEndAnchor(Connector_c con) {
		EndAnchor endAnchor = null;
		Graphconnector_c graphConnector = Graphconnector_c.getOneDIM_CONOnR321(Graphedge_c.getOneDIM_EDOnR20(con));
		GraphicalElement_c endElem = GraphicalElement_c
				.getOneGD_GEOnR23(Graphelement_c.getOneDIM_GEOnR311(graphConnector));
		endAnchor = factory.createEndAnchor();
		if (endElem != null) {
			Shape_c shpAnchor = Shape_c.getOneGD_SHPOnR2(endElem);
			if (shpAnchor != null) {
				ShapeAnchorElement shapeAnchor = factory.createShapeAnchorElement();
				endAnchor.setAnchor(shapeAnchor);
				Shape shape = getOrCreateShape(shpAnchor);
				shapeAnchor.setElement(shape);
			} else {
				Connector_c conAnchor = Connector_c.getOneGD_CONOnR2(endElem);
				if (conAnchor != null) {
					ConnectorAnchorElement connectorAnchor = factory.createConnectorAnchorElement();
					endAnchor.setAnchor(connectorAnchor);
					Connector connector = getOrCreateConnector(conAnchor);
					connectorAnchor.setElement(connector);
				}
			}
		}
		// some connectors end on whitespace
		PointDefinition anchorPoint = factory.createPointDefinition();
		if (endElem != null) {
			anchorPoint.setX((int) graphConnector.getPositionx());
			anchorPoint.setY((int) graphConnector.getPositiony());
		} else {
			// get the values from the last segment waypoint
			LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(con);
			LineSegment_c lastSegment = segments[segments.length - 1];
			Waypoint_c endWayPoint = Waypoint_c.getOneDIM_WAYOnR22(lastSegment);
			anchorPoint.setX((int) endWayPoint.getPositionx());
			anchorPoint.setY((int) endWayPoint.getPositiony());
		}
		endAnchor.setPoint(anchorPoint);
		return endAnchor;
	}

	private Connector getOrCreateConnector(Connector_c anchor) {
		Connector connector = null;
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(anchor);
		for (GraphicalElement elem : model.getElements()) {
			if (elem instanceof Connectors) {
				for (Connector potential : ((Connectors) elem).getConnectors()) {
					if (potential.getRepresents().equals(getPath(ge))) {
						connector = potential;
						break;
					}
				}
			}
		}
		if (connector == null) {
			connector = createConnector(anchor);
			for (GraphicalElement ele : model.getElements()) {
				if (ele instanceof Connectors) {
					((Connectors) ele).getConnectors().add(connector);
					break;
				}
			}
			;
		}
		return connector;
	}

	private Shape getOrCreateShape(Shape_c anchor) {
		Shape shape = null;
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(anchor);
		for (GraphicalElement elem : model.getElements()) {
			if (elem instanceof Shapes) {
				for (Shape potential : ((Shapes) elem).getShapes()) {
					if (potential.getRepresents().equals(getPath(ge))) {
						shape = potential;
						break;
					}
				}
			}
			if (shape != null) {
				break;
			}
		}
		if (shape == null) {
			shape = createShape(anchor);
			for (GraphicalElement ele : model.getElements()) {
				if (ele instanceof Shapes) {
					((Shapes) ele).getShapes().add(shape);
					break;
				}
			}
			;
		}
		return shape;
	}

	@Override
	public void nameChange(NonRootModelElement modelElement, Object oldValue) {
		IFile parentFile = modelElement.getFile();
		// remove old file
		IFile oldFile = parentFile.getParent().getFile(new Path(((String) oldValue + ".xtumlg")));
		if (oldFile.exists()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
				Stream.of(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()).forEach(p -> {
					Stream.of(p.getEditorReferences()).forEach(ref -> {
						try {
							IFile editorInputFile = ref.getEditorInput().getAdapter(IFile.class);
							if (editorInputFile.equals(oldFile)) {
								p.closeEditor(ref.getEditor(false), false);
							}
						} catch (PartInitException e) {
							CanvasUiModule.logError("Failed to close existing editor after file deletion.", e);
						}
					});
				});
			});
			try {
				oldFile.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				CanvasUiModule.logError("Could not delete old textual graphics file on rename.", e);
			}
		}
	}

}
