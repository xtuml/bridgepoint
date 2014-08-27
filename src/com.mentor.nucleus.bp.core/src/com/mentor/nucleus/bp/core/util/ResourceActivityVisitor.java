package com.mentor.nucleus.bp.core.util;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class ResourceActivityVisitor implements IResourceDeltaVisitor {
  String paths = "";

  @Override
  public boolean visit(IResourceDelta delta) throws CoreException {
    IPath path = delta.getFullPath();
    if (path.segmentCount() < 2 || path.segment(1).equals("models")) {
      if (!(path.segmentCount() < 2)) {
        paths = paths + delta.getFullPath() + "\n";
      }
      return true;
    }
    return false;
  }

  public String getResult() {
    return paths;
  }

}
