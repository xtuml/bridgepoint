package org.xtuml.bp.ui.canvas.persistence;

public class PersistenceExtension {
	private String identity;
	private String resourceExtension;
	private IGraphicalLoader loader;
	private IGraphicalWriter writer;
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getResourceExtension() {
		return resourceExtension;
	}
	public void setResourceExtension(String resourceExtension) {
		this.resourceExtension = resourceExtension;
	}
	public IGraphicalLoader getLoader() {
		return loader;
	}
	public void setLoader(IGraphicalLoader loader) {
		this.loader = loader;
	}
	public IGraphicalWriter getWriter() {
		return writer;
	}
	public void setWriter(IGraphicalWriter writer) {
		this.writer = writer;
	}

}
