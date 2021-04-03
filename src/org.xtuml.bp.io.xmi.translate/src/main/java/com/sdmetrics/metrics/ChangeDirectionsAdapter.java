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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Constructs a new graph from an existing one, reversing or removing the
 * directions of the edges.
 * <p>
 * The new graph has the same set of nodes as the original graph, and for every
 * edge (A,B) from node A to node B in the original graph
 * <ul>
 * <li>an edge (B,A) in the reversed graph
 * <li>edges (A,B) and (B,A) in the undirected graph
 * </ul>
 * 
 * @param <T> Type of the nodes on the graph
 */
public class ChangeDirectionsAdapter<T> implements
		StronglyConnectedComponents.Graph<T> {
	/** The set of nodes of the graph. */
	private final Collection<T> nodes;
	/** Map with the nodes (key) and new set of new neighbors (value). */
	private final HashMap<T, Collection<T>> edgeMap;

	/**
	 * Constructor.
	 * @param graph The original graph.
	 * @param makeUndirected Set to <code>true</code> to obtain an undirected
	 *        graph, or <code>false</code> to obtain the graph with reversed
	 *        edges.
	 * @throws SDMetricsException The neighbors for a node could not be
	 *         determined
	 */
	public ChangeDirectionsAdapter(StronglyConnectedComponents.Graph<T> graph,
			boolean makeUndirected) throws SDMetricsException {

		// Create a new set of edges for each node, initially empty.
		nodes = graph.getNodes();
		edgeMap = new HashMap<>(nodes.size());
		for (T node : nodes) {
			edgeMap.put(node, new HashSet<T>());
		}

		// Walk through old graph, add the appropriate edges in the new graph.
		for (T nodeA : nodes) {
			Collection<T> nodeANeighbors = edgeMap.get(nodeA);

			for (T nodeB : graph.getNeighbors(nodeA)) {
				Collection<T> nodeBNeighbors = edgeMap.get(nodeB);
				if (nodeBNeighbors != null) { // nodeB is a node in the graph
					// We have an edge from A to B in the old graph.
					// Add edge from B to A in the new graph, and optionally
					// also retain the edge from A to B in the new graph
					nodeBNeighbors.add(nodeA);
					if (makeUndirected) {
						nodeANeighbors.add(nodeB);
					}
				}
			}
		}
	}

	// Implementation of the Graph interface methods

	@Override
	public Collection<T> getNodes() {
		return nodes;
	}

	@Override
	public Collection<T> getNeighbors(T o) {
		return edgeMap.get(o);
	}
}
