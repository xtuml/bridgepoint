/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
    
 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Calculates the strongly connected components of a directed graph. The
 * algorithm used to calculate the strongly connected components is not
 * self-explanatory, but is described in most "algorithms and data structures
 * 101" text books. This implementation is based on T. Ottmann, P. Widmayer,
 * "Algorithmen und Datenstukturen", BI Wissenschaftsverlag, 1991, pp. 566-570.
 * The complexity of the algorithm is O(|N|+|E|) (N=set of nodes, E=set of
 * edges).
 * 
 * @param <T> Type of the nodes on the graph
 */

public class StronglyConnectedComponents<T> {

	/**
	 * Provides the strongly connected components algorithm with the information
	 * it requires about the directed graph.
	 * 
	 * @param <T> Type of the nodes on the graph
	 */
	public interface Graph<T> {
		/**
		 * Retrieves the set of nodes of the graph.
		 * 
		 * @return A collection of objects that constitute the nodes of the
		 *         graph.
		 */
		Collection<T> getNodes();

		/**
		 * Obtains, for a node, the set of nodes to which it has an outgoing
		 * edge.
		 * 
		 * @param node The node for which to obtain the neighbor nodes.
		 * @return A collection of the neighbor nodes to which <code>node</code>
		 *         has an outgoing edge.
		 * @throws SDMetricsException if the neighbors for a node could not be
		 *         determined.
		 */
		Collection<T> getNeighbors(T node) throws SDMetricsException;
	}

	/** The graph to operate on. */
	private Graph<T> graph;

	/** Information the CC algorithm tracks for each node of the graph. */
	static class NodeInfo {
		/** Indicates if the node is currently stacked. */
		boolean stacked = false;
		/**
		 * Depth-first index (DFI) of each graph node; -1 means not yet visited.
		 */
		int dfi = -1;
		/** minimum DFI of all nodes in a cycle. */
		int cmd;

		/**
		 * Index of this node's connected component. Used only for reporting of
		 * results.
		 */
		int ccIndex = -1;
	}

	/** Stores one node info for each node. */
	private HashMap<T, NodeInfo> infoMap;
	/** Stores the connected components. */
	private ArrayList<Collection<T>> connectedComponents;

	/** Stack of nodes not yet assigned to any SCC. */
	private LinkedList<T> stack = new LinkedList<>();

	/** Current depth-first-index. */
	private int cdfi;
	/** Minimum size of a connected component to be reported. */
	private int minCCSize = 0;
	/**
	 * Indicates if isolated nodes are to be reported only if they have an edge
	 * to themselves.
	 */
	private boolean countIsolatedIfSelfref;

	/**
	 * Gets the number of strongly connected components found.
	 * 
	 * @return The number N of strongly connected components. The components are
	 *         addressed by an index, valid indices run from 0 to N-1.
	 */
	public int getConnectedComponentCount() {
		return connectedComponents.size();
	}

	/**
	 * Gets the node set for a strongly connected component.
	 * 
	 * @param index Index of the strongly connected component.
	 * @return The nodes of the strongly connected component.
	 */
	public Collection<T> getConnectedComponent(int index) {
		return connectedComponents.get(index);
	}

	/**
	 * Gets the index of the strongly connected component of a node.
	 * 
	 * @param node The node of interest.
	 * @return Index of the strongly connected component that contains the node,
	 *         or -1 if the node is not part of a strongly connected component.
	 */
	public int getIndexFor(T node) {
		NodeInfo ni = infoMap.get(node);
		return ni == null ? -1 : infoMap.get(node).ccIndex;
	}

	/**
	 * Calculates the strongly connected components for a graph.
	 * 
	 * @param inputGraph The graph to operate on.
	 * @param minSize The minimum size a strongly connected component must have
	 *        to be reported by the algorithm.
	 * @param isoIfSelfref When this is set to <code>true</code>, isolated nodes
	 *        ("connected components of size one") are reported only if the node
	 *        has an edge to itself. When set to <code>false</code>, such
	 *        connected components are always reported. If minSize is greater
	 *        than one, this parameter is ignored.
	 * @throws SDMetricsException The set of neighbors of a node could not be
	 *         calculated.
	 */
	public void calculateConnectedComponents(Graph<T> inputGraph, int minSize,
			boolean isoIfSelfref) throws SDMetricsException {
		this.graph = inputGraph;
		this.minCCSize = minSize;
		this.countIsolatedIfSelfref = isoIfSelfref;

		Collection<T> nodes = graph.getNodes();
		infoMap = new HashMap<>(nodes.size());
		for (T node : nodes) {
			infoMap.put(node, new NodeInfo());
		}
		connectedComponents = new ArrayList<>();

		cdfi = 0;
		stack.clear();

		for (T node : nodes) {
			searchSCC(node);
		}

		// Map each node to its connected component
		for (int i = 0; i < connectedComponents.size(); i++) {
			Collection<T> cc = connectedComponents.get(i);
			for (T node : cc) {
				infoMap.get(node).ccIndex = i;
			}
		}
	}

	/**
	 * Searches the SCC of a node.
	 * 
	 * @param node The node whose SCC to search.
	 * @throws SDMetricsException The neighbors of the node could not be
	 *         calculated.
	 */
	private void searchSCC(T node) throws SDMetricsException {
		NodeInfo ni = infoMap.get(node);
		if (ni.dfi >= 0) {
			return; // already visited => do nothing
		}

		cdfi++;
		ni.dfi = cdfi;
		ni.cmd = cdfi;
		stack.add(node);
		ni.stacked = true;
		boolean selfReferential = false;

		// iterate over connected nodes, depth-first search
		for (T neighbor : graph.getNeighbors(node)) {
			if (neighbor == node) {
				selfReferential = true;
			}

			NodeInfo neighborInfo = infoMap.get(neighbor);

			if (neighborInfo != null) { // ignore links leading outside the node set
				if (neighborInfo.dfi < 0) { // node not yet visited
					searchSCC(neighbor);
					ni.cmd = Math.min(ni.cmd, neighborInfo.cmd);
				} else if (neighborInfo.dfi < ni.dfi && neighborInfo.stacked) {
					ni.cmd = Math.min(ni.cmd, neighborInfo.dfi);
				}
			}
		}

		if (ni.cmd == ni.dfi) {
			// have the root of an SCC, its nodes are on the stack
			ArrayList<T> newSCC = new ArrayList<>();
			T sccMember;
			do {
				sccMember = stack.removeLast();
				newSCC.add(sccMember);
				infoMap.get(sccMember).stacked = false;
			} while (sccMember != node);

			// if the SCC is large enough, report it
			boolean countCC = newSCC.size() >= minCCSize;
			if (minCCSize == 1 && newSCC.size() == 1 && countIsolatedIfSelfref) {
				countCC = selfReferential;
			}
			if (countCC) {
				connectedComponents.add(newSCC);
			}
		}
	}
}
