package org.xtuml.canvas.language.io;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.ContainingShape_c;
import org.xtuml.bp.ui.canvas.Diagram_c;
import org.xtuml.bp.ui.canvas.ElementSpecification_c;
import org.xtuml.bp.ui.canvas.Elementstyle_c;
import org.xtuml.bp.ui.canvas.Fillcolorstyle_c;
import org.xtuml.bp.ui.canvas.FloatingText_c;
import org.xtuml.bp.ui.canvas.Graphedge_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.LineSegment_c;
import org.xtuml.bp.ui.canvas.ModelSpecification_c;
import org.xtuml.bp.ui.canvas.ModelTool_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Ooatype_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.Waypoint_c;
import org.xtuml.bp.ui.canvas.persistence.IGraphicalLoader;
import org.xtuml.bp.ui.canvas.references.PathUtils;
import org.xtuml.bp.ui.canvas.references.ReferencePathManagement;
import org.xtuml.canvas.language.canvas.Anchor;
import org.xtuml.canvas.language.canvas.Connector;
import org.xtuml.canvas.language.canvas.ConnectorAnchorElement;
import org.xtuml.canvas.language.canvas.Connectors;
import org.xtuml.canvas.language.canvas.FloatingText;
import org.xtuml.canvas.language.canvas.Model;
import org.xtuml.canvas.language.canvas.ModelPropertiesItem;
import org.xtuml.canvas.language.canvas.Segment;
import org.xtuml.canvas.language.canvas.Shape;
import org.xtuml.canvas.language.canvas.ShapeAnchorElement;
import org.xtuml.canvas.language.canvas.Shapes;
import org.xtuml.canvas.language.io.utils.EnumUtils;
import org.xtuml.canvas.language.ui.CanvasUiModule;
import org.xtuml.canvas.language.ui.internal.LanguageActivator;

import com.google.inject.Inject;

public class CanvasGenerator implements IGraphicalLoader {

	@Inject
	IResourceSetProvider resourceSetProvider;

	CanvasWriter writer = new CanvasWriter();
	
	static CanvasGenerator singleton = new CanvasGenerator();

	@Override
	public void initialize() {
	}

	@Override
	public Model_c load(Object container) {
		if (container instanceof NonRootModelElement) {
			NonRootModelElement parentElement = (NonRootModelElement) container;
			IFile parentFile = parentElement.getFile();
			IFile xtGraphFile = parentFile.getParent()
					.getFile(new Path(parentFile.getName().replaceAll(".xtuml", ".xtumlg")));
			try {
				return generate(parentElement, Ooaofgraphics.getInstance(parentElement.getModelRoot().getId()),
						xtGraphFile, false);
			} catch (IOException | CoreException e) {
				CanvasUiModule.logError("Unable to generate xtUML graphics.", e);
			}
		}
		return null;
	}
	
	@Override
	public Model_c reload(Object container) {
		if (container instanceof NonRootModelElement) {
			NonRootModelElement parentElement = (NonRootModelElement) container;
			IFile parentFile = parentElement.getFile();
			IFile xtGraphFile = parentFile.getParent()
					.getFile(new Path(parentFile.getName().replaceAll(".xtuml", ".xtumlg")));
			try {
				return generate(parentElement, Ooaofgraphics.getInstance(parentElement.getModelRoot().getId()),
						xtGraphFile, true);
			} catch (IOException | CoreException e) {
				CanvasUiModule.logError("Unable to generate xtUML graphics.", e);
			}
		}
		return null;
	}

	public Model_c generate(NonRootModelElement parentElement, ModelRoot destinationRoot, IFile file, boolean reload)
			throws IOException, CoreException {
		boolean rewrite = false;
		if (!file.exists()) {
			rewrite = true;
			writer.write(parentElement, file, false);
			// if not a new system,
			if (!(parentElement instanceof SystemModel_c)) {
				// this is from a pre-canvas-language model
				// instances will exist already, just need to
				// write the new graphics file
				return null;
			}
		}
		Model_c xtModel = Model_c.ModelInstance(Ooaofgraphics.getInstance(parentElement.getModelRoot().getId()),
				m -> ((Model_c) m).getRepresents() == parentElement);
		if (xtModel != null) {
			if(!reload) {
				return xtModel;
			}
			// for now just recreate, should be fast enough, if it
			// gets to a point were its too slow add updated support
			ModelRoot.disableChangeNotification();
			try {
				xtModel.Dispose();
			} finally {
				ModelRoot.enableChangeNotification();
			}
		}
		LanguageActivator.getInstance().getInjector("org.xtuml.canvas.language.Canvas").injectMembers(this);
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		ResourceSet rs = resourceSetProvider.get(file.getProject());
		Resource r = rs.getResource(uri, true);
		EObject potentialModel = r.getContents().get(0);
		if (potentialModel != null && potentialModel instanceof Model) {
			// only one model supported at this time
			Model model = (Model) potentialModel;
			xtModel = new Model_c(destinationRoot);
			int modelType = EnumUtils.getModelType(parentElement);
			xtModel.setModel_type(modelType);
			xtModel.setOoa_id(parentElement.Get_ooa_id());
			Diagram_c xtDiagram = new Diagram_c(destinationRoot);
			xtModel.relateAcrossR18To(xtDiagram);
			if (model.getProperties() != null) {
				for (ModelPropertiesItem item : model.getProperties().getPropertyItems()) {
					if (item.getColor() != null) {
						Elementstyle_c style = new Elementstyle_c(destinationRoot);
						Fillcolorstyle_c backgroundColor = new Fillcolorstyle_c(destinationRoot);
						backgroundColor.relateAcrossR400To(style);
						backgroundColor.setRed(Integer.parseInt(item.getColor().substring(1, 3), 16));
						backgroundColor.setGreen(Integer.parseInt(item.getColor().substring(3, 5), 16));
						backgroundColor.setBlue(Integer.parseInt(item.getColor().substring(5, 7), 16));
						xtModel.relateAcrossR402To(style);
					} else if (item.getPoint() != null) {
						xtDiagram.setViewportx(item.getPoint().getX());
						xtDiagram.setViewporty(item.getPoint().getY());
					} else {
						xtDiagram.setZoom(item.getZoom());
					}
				}
			}
			xtModel.setRepresents(parentElement);
			ModelSpecification_c[] modelSpecs = ModelSpecification_c
					.ModelSpecificationInstances(Ooaofgraphics.getDefaultInstance());
			for (ModelSpecification_c spec : modelSpecs) {
				if (spec.getModel_type() == modelType) {
					spec.relateAcrossR9To(xtModel);
					break;
				}
			}
			xtModel.Initializetools();
			createGraphicalElements(xtModel, model, parentElement);
		}
		if (rewrite) {
			// on system creation we write the initial file to process
			// but need to write the newly created model
			writer.write(parentElement);
		}
		return xtModel;
	}

	private void createGraphicalElements(Model_c xtModel, Model model, NonRootModelElement parent) {
		model.getElements().forEach(elem -> {
			if (elem instanceof Shapes) {
				Shapes shapes = (Shapes) elem;
				shapes.getShapes().forEach(shape -> {
					createShape(xtModel, shape, parent);
				});
			}
			if (elem instanceof Connectors) {
				Connectors connectors = (Connectors) elem;
				connectors.getConnectors().forEach(connector -> {
					createConnector(xtModel, connector, parent);
				});
			}
		});
	}

	private void updateTextPosition(FloatingText_c txt, FloatingText text) {
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(txt);
		Graphelement_c ge = Graphelement_c.getOneDIM_GEOnR301(node);
		ge.setPositionx(text.getBounds().getX());
		ge.setPositiony(text.getBounds().getY());
		node.setWidth(text.getBounds().getW());
		node.setHeight(text.getBounds().getH());
	}

	private GraphicalElement_c getOrCreateShape(Model_c xtModel, Shape shape, NonRootModelElement parent) {
		GraphicalElement_c existing = GraphicalElement_c.getOneGD_GEOnR1(xtModel,
				ge -> ReferencePathManagement.getPath((NonRootModelElement) ((GraphicalElement_c) ge).getRepresents())
						.equals(shape.getRepresents()));
		if (existing == null) {
			createShape(xtModel, shape, parent);
		}
		existing = GraphicalElement_c.getOneGD_GEOnR1(xtModel,
				ge -> ReferencePathManagement.getPath((NonRootModelElement) ((GraphicalElement_c) ge).getRepresents())
						.equals(shape.getRepresents()));
		return existing;
	}

	private void createShape(Model_c xtModel, Shape shape, NonRootModelElement parent) {
		String container = shape.getContainer();
		NonRootModelElement representedElement = PathUtils.getElementByPath(shape.getRepresents(), parent);
		if (representedElement == null) {
			// this is likely a manual addition, we do not support
			// creation at this time. Mostly because there is no
			// good way to set the name, and or figure out the unnamed variant
			return;
		}
		// need to handle creation symbols differently, use the support function
		// on Model_c
		if (container != null) {
			xtModel.Initializeoncreationsymbols();
			// update with user values
			GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR1(xtModel,
					g -> ContainingShape_c.getOneGD_CTROnR28(Shape_c.getOneGD_SHPOnR2((GraphicalElement_c) g)) != null);
			if (ge != null) {
				ge.setRepresents(representedElement);
				ge.setOoa_id(representedElement.Get_ooa_id());
				Graphelement_c graphEle = Graphelement_c.getOneDIM_GEOnR23(ge);
				graphEle.setPositionx(shape.getBounds().getX());
				graphEle.setPositiony(shape.getBounds().getY());
				Graphnode_c node = Graphnode_c.getOneDIM_NDOnR301(graphEle);
				node.setWidth(shape.getBounds().getW());
				node.setHeight(shape.getBounds().getH());
			}
		} else {
			UUID toolId = getToolId(xtModel, representedElement, container != null);
			UUID shapeId = xtModel.Createshape(representedElement == null, toolId);
			Graphelement_c graphElem = Graphelement_c.getOneDIM_GEOnR23(GraphicalElement_c.getManyGD_GEsOnR1(xtModel),
					ge -> ((Graphelement_c) ge).getElementid().equals(shapeId));
			GraphicalElement_c graphicalElem = GraphicalElement_c.getOneGD_GEOnR23(graphElem);
			Shape_c shp = Shape_c.getOneGD_SHPOnR2(graphicalElem);
			graphicalElem.setRepresents(representedElement);
			graphicalElem.setOoa_id(representedElement.Get_ooa_id());
			graphElem.setPositionx(shape.getBounds().getX());
			graphElem.setPositiony(shape.getBounds().getY());
			Graphnode_c node = Graphnode_c.getOneDIM_NDOnR301(graphElem);
			node.setWidth(shape.getBounds().getW());
			node.setHeight(shape.getBounds().getH());
			// Floating text will have been created with the call to API
			FloatingText_c txt = FloatingText_c.getOneGD_CTXTOnR27(shp);
			if (txt != null) {
				updateTextPosition(txt, shape.getText());
			}
		}
	}

	private GraphicalElement_c getOrCreateConnector(Model_c xtModel, Connector connector, NonRootModelElement parent) {
		GraphicalElement_c existing = GraphicalElement_c.getOneGD_GEOnR1(xtModel,
				ge -> ReferencePathManagement.getPath((NonRootModelElement) ((GraphicalElement_c) ge).getRepresents())
						.equals(connector.getRepresents()));
		if (existing == null) {
			createConnector(xtModel, connector, parent);
		}
		existing = GraphicalElement_c.getOneGD_GEOnR1(xtModel,
				ge -> ReferencePathManagement.getPath((NonRootModelElement) ((GraphicalElement_c) ge).getRepresents())
						.equals(connector.getRepresents()));
		return existing;
	}

	private void createConnector(Model_c xtModel, Connector connector, NonRootModelElement parent) {
		NonRootModelElement representedElement = PathUtils.getElementByPath(connector.getRepresents(), parent);
		if (representedElement == null) {
			// this is likely a manual addition, we do not support
			// creation at this time. Mostly because there is no
			// good way to set the name, and or figure out the unnamed variant
			return;
		}
		UUID toolId = getToolId(xtModel, representedElement, false);
		GraphicalElement_c startElem = getStartElement(xtModel, connector, parent);
		UUID startElemId = Gd_c.Null_unique_id();
		if (startElem == null) {
			startElemId = xtModel.getDiagramid();
		} else {
			startElemId = startElem.getElementid();
		}
		GraphicalElement_c endElem = getEndElement(xtModel, connector, parent);
		UUID endElemId = Gd_c.Null_unique_id();
		if (endElem == null) {
			endElemId = xtModel.getDiagramid();
		} else {
			endElemId = endElem.getElementid();
		}
		UUID connId = xtModel.Createconnector(false, endElemId, startElemId, toolId,
				(int) connector.getPolyline().getSegments().get(0).getStartPoint().getX(),
				(int) connector.getPolyline().getSegments().get(0).getEndPoint().getX(),
				(int) connector.getPolyline().getSegments().get(0).getStartPoint().getY(),
				(int) connector.getPolyline().getSegments().get(0).getEndPoint().getY());
		if (!connId.equals(Gd_c.Null_unique_id())) {
			Graphelement_c graphElem = Graphelement_c.GraphelementInstance(xtModel.getModelRoot(),
					ge -> ((Graphelement_c) ge).getElementid().equals(connId));
			GraphicalElement_c graphicalElem = GraphicalElement_c.getOneGD_GEOnR23(graphElem);
			Connector_c con = Connector_c.getOneGD_CONOnR2(graphicalElem);
			graphicalElem.setOoa_id(representedElement.Get_ooa_id());
			createSegments(Connector_c.getOneGD_CONOnR2(graphicalElem), connector);
			graphicalElem.setRepresents(representedElement);
			// API will have created Floating texts
			Stream.of(FloatingText_c.getManyGD_CTXTsOnR8(con)).forEach(txt -> {
				Optional<FloatingText> potentialtext = Stream
						.of(connector.getTexts().getTexts().toArray(new FloatingText[0]))
						.filter(floatingText -> floatingText.getEnd().getWhere().equals(EnumUtils.endFor(txt.getEnd())))
						.findFirst();
				if (potentialtext.isPresent()) {
					updateTextPosition(txt, potentialtext.get());
				}
			});
		}
	}

	private void createSegments(Connector_c xtCon, Connector connector) {
		// delete existing segments and replace with stored polyline segments
		Stream.of(LineSegment_c.getManyGD_LSsOnR6(xtCon)).forEach(seg -> {
			xtCon.unrelateAcrossR6From(seg);
			Waypoint_c startWay = Waypoint_c.getOneDIM_WAYOnR21(seg);
			Waypoint_c endWay = Waypoint_c.getOneDIM_WAYOnR22(seg);
			seg.Dispose();
			startWay.Dispose();
			endWay.Dispose();
		});
		LineSegment_c lastSeg = null;
		Waypoint_c lastEndWay = null;
		Graphedge_c edge = Graphedge_c.getOneDIM_EDOnR20(xtCon);
		for (Segment segment : connector.getPolyline().getSegments()) {
			LineSegment_c xtSeg = new LineSegment_c(xtCon.getModelRoot());
			xtSeg.relateAcrossR6To(xtCon);
			xtSeg.relateAcrossR7ToFollows(lastSeg);
			Waypoint_c startWay = lastEndWay;
			if (startWay == null) {
				startWay = new Waypoint_c(xtCon.getModelRoot());
				startWay.setPositionx(segment.getStartPoint().getX());
				startWay.setPositiony(segment.getStartPoint().getY());
			}
			startWay.relateAcrossR21To(xtSeg);
			Waypoint_c endWay = new Waypoint_c(xtCon.getModelRoot());
			endWay.relateAcrossR22To(xtSeg);
			endWay.setPositionx(segment.getEndPoint().getX());
			endWay.setPositiony(segment.getEndPoint().getY());
			startWay.relateAcrossR324ToFollows(endWay);
			edge.relateAcrossR319To(startWay);
			edge.relateAcrossR319To(endWay);
			lastSeg = xtSeg;
			lastEndWay = endWay;
		}
	}

	private GraphicalElement_c getEndElement(Model_c xtModel, Connector connector, NonRootModelElement parent) {
		GraphicalElement_c endElem = null;
		Anchor anchor = connector.getAnchors().getEndAnchor().getAnchor();
		if (anchor != null) {
			if (anchor instanceof ShapeAnchorElement) {
				endElem = getOrCreateShape(xtModel, ((ShapeAnchorElement) anchor).getElement(), parent);
			} else {
				endElem = getOrCreateConnector(xtModel, ((ConnectorAnchorElement) anchor).getElement(), parent);
			}
		}
		return endElem;
	}

	private GraphicalElement_c getStartElement(Model_c xtModel, Connector connector, NonRootModelElement parent) {
		GraphicalElement_c startElem = null;
		Anchor anchor = connector.getAnchors().getStartAnchor().getAnchor();
		if (anchor != null) {
			if (anchor instanceof ShapeAnchorElement) {
				startElem = getOrCreateShape(xtModel, ((ShapeAnchorElement) anchor).getElement(), parent);
			} else {
				startElem = getOrCreateConnector(xtModel, ((ConnectorAnchorElement) anchor).getElement(), parent);
			}
		}
		return startElem;
	}


	private UUID getToolId(Model_c xtModel, NonRootModelElement represents, boolean container) {
		int typeFromReference = EnumUtils.getTypeFromReference(represents, container);
		// auto creation elements do not have a tool, use the non-imported variant
		if(represents instanceof ImportedRequirement_c) {
			typeFromReference = Ooatype_c.RequiredInterface;
		}
		if(represents instanceof ImportedProvision_c) {
			typeFromReference = Ooatype_c.ProvidedInterface;
		}
		ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(xtModel);
		for (int i = 0; i < tools.length; i++) {
			ModelTool_c tool = tools[i];
			if (tool.getOoa_type() == typeFromReference) {
				ElementSpecification_c spec = ElementSpecification_c.getOneGD_ESOnR103(tool);
				if (!container) {
					return tool.getTool_id();
				}
				if (container && spec.getSymboltype().equals("container")) {
					return tool.getTool_id();
				}
			}
		}
		return Gd_c.Null_unique_id();
	}

	public static CanvasGenerator getSingleton() {
		return singleton;
	}

}
