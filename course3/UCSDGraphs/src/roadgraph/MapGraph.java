/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	/* instance variables */
	private HashMap<GeographicPoint, MapNode> pointMapNode;
	private HashSet<MapEdge> edges;
	
	/**
	 * Creates a new empty MapGraph.
	 * @param pointMapNode
	 * @param edges
	 */
	public MapGraph() {
		this.pointMapNode = new HashMap<GeographicPoint, MapNode>();
		this.edges = new HashSet<MapEdge>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return pointMapNode.values().size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return pointMapNode.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return edges.size();
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// try to get node from pointMapNode
		MapNode node = pointMapNode.get(location);
		
		// check if node does not exist
		if (node == null) {
			// create node
			node = new MapNode(location);
			
			// put node at location into pointMapNode
			pointMapNode.put(location, node);
			
			// return success
			return true;
		}
		else {
			// print warning that node already exists
			System.out.println("Node at location " + location + " already exists in graph.");
			
			// return failure
			return false;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		// check if any argument reference is null
		if (from == null || to == null || roadName == null || roadType == null) {
			throw new IllegalArgumentException("An argument reference is null.");
		}
		// check if length < 0
		if (length < 0) {
			throw new IllegalArgumentException("Length is less than 0.");
		}
		// try to get MapNodes from pointMapNode on locations
		MapNode fromNode = pointMapNode.get(from);
		MapNode toNode = pointMapNode.get(to);
		
		// check if MapNode points have not already been added as nodes to the graph
		if (fromNode == null) {
			throw new IllegalArgumentException("Node on from-location has not been added to graph.");
		}
		if (toNode == null) {
			throw new IllegalArgumentException("Node on to-location has not been added to graph.");
		}
		
		// create edge
		MapEdge edge = new MapEdge(roadName, roadType, fromNode, toNode, length);
		
		// add edge to to edges
		edges.add(edge);
		
		// add edge to fromNode
		fromNode.addEdge(edge);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @throws IllegalArgumentException if points do not exist or nodes do not exist in graph.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// check if start or goal points do not exists
		if (start == null) {
			throw new IllegalArgumentException("Start point does not exist.");
		}
		if (goal == null) {
			throw new IllegalArgumentException("Goal point does not exist.");
		}
		
		// try to create start and end nodes
		MapNode startNode = pointMapNode.get(start);
		MapNode endNode = pointMapNode.get(goal);
		
		// check if start or goal points do not exist in graph as nodes
		if (startNode == null) {
			throw new IllegalArgumentException("Start node " + start + " does not exist");
		}
		if (endNode == null) {
			throw new IllegalArgumentException("End node " + goal + " does not exist");
		}
	
		// breadth first search standard algorithm
		// create new map
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		// create queue
		Queue<MapNode> leftToVisit = new LinkedList<MapNode>();
		
		// create set of visited nodes
		HashSet<MapNode> visited = new HashSet<MapNode>();
		
		// start bfs
		leftToVisit.add(startNode);
		MapNode next = null;
		
		// bfs loop
		while (!leftToVisit.isEmpty()) {
			next = leftToVisit.remove();
			
			// hook for visualization
			// see class notes for more info
			nodeSearched.accept(next.getLocation());
			
			if (next.equals(endNode))
				break;
	
			for (MapNode neighbor : next.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, next);
					leftToVisit.add(neighbor);
				}
			}
		}
		
		// check if path found
		if (next.equals(endNode)) {
			return buildPath(parentMap, startNode, endNode);
		}
		else {
			System.out.println("No path found from " + start + " to " + goal + " found.");
			return null;
		}
	}
	
	/**
	 * Helps search methods by returning a list of geographic points between two points in a graph.
	 * @param parentMap
	 * @param start
	 * @param goal
	 * @return List<GeographicPoint> between start and goal in parentMap
	 */
	private List<GeographicPoint> buildPath(HashMap<MapNode, MapNode> parentMap, MapNode start, MapNode goal) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goal;
	
		// iterate over map from goal to start, adding nodes to path
		while (!current.equals(start)) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}
		
		// add start node which is not added by iteration above
		path.addFirst(start.getLocation());
		
		return path;
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// create and check start and goal nodes
		MapNode startNode = pointMapNode.get(start);
		MapNode goalNode = pointMapNode.get(goal);
		if (startNode == null) {
			throw new IllegalArgumentException("Start node non existent.");
		}
		if (goalNode == null) {
			throw new IllegalArgumentException("Goal node non existent.");
		}
		
		// initialize: Priority queue (PQ), visited HashSet, parent HashMap,
		// and distances to infinity
		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		for (MapNode mn : pointMapNode.values()) {
			mn.setDistance(Double.MAX_VALUE);
		}
		
		// enqueue {S, O} onto the pq
		startNode.setDistance(0.0);
		pq.add(startNode);
		
		// count number of nodes visited
		int nrNodesVisited = 0;
		
		// while pq is not empty
		while(!pq.isEmpty()) {
			// dequeue node curr from front of queue
			MapNode curr = pq.poll();
			
			// increment number of nodes visited
			nrNodesVisited++;
			
			// if (curr is not visited)
			if (!visited.contains(curr)) {
				// hook for visualization
				// assume variable next is the geographic point corresponding
				// to the element removed from the priority queue
				nodeSearched.accept(curr.getLocation());
				
				// add curr to visited set
				visited.add(curr);
				
				// if (curr == goal) return parent map
				if (curr == goalNode) {
					// print number of nodes visited
					System.out.println(nrNodesVisited);
					
					return buildPath(parentMap, startNode, goalNode);
				}
				
				// for each of curr's neighbors, not in visited set
				for (MapNode n : curr.getNeighbors()) {
					if (!visited.contains(n)) {
						// if path through curr to n is shorter
						// distance from curr to neighbor n node
						double nDistance = 0.0;
						
						// edge weight from curr to neighbor n node
						for (MapEdge edge : edges) {
							if (edge.getStart() == curr && edge.getEnd() == n) {
								nDistance = edge.getDistance();
							}
						}
						
						// update pathToN
						double pathToN = curr.getDistance() + nDistance;
						// print pathToN
						// System.out.println(pathToN);
						if (pathToN < n.getDistance()) {
							// update curr as n's parent in parent map
							n.setDistance(pathToN);
							parentMap.put(n, curr);
							
							// enqueue {n, distance} into the pq
							pq.add(n);
						}
					}
				}
			}
		}
		
		// if we get here then there's no path
		return null;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// create and check start and goal nodes
		MapNode startNode = pointMapNode.get(start);
		MapNode goalNode = pointMapNode.get(goal);
		if (startNode == null) {
			throw new IllegalArgumentException("Start node non existent.");
		}
		if (goalNode == null) {
			throw new IllegalArgumentException("Goal node non existent.");
		}
		
		// initialize: Priority queue (PQ), visited HashSet, parent HashMap,
		// and distances to infinity
		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		for (MapNode mn : pointMapNode.values()) {
			mn.setDistance(Double.MAX_VALUE);
		}
		
		// enqueue {S, O} onto the pq
		startNode.setDistance(0.0);

		pq.add(startNode);
		
		// count number of nodes visited
		int nrNodesVisited = 0;
		
		// while pq is not empty
		while(!pq.isEmpty()) {
			// dequeue node curr from front of queue
			MapNode curr = pq.poll();
			
			// increment number of nodes visited
			nrNodesVisited++;
			
			// if (curr is not visited)
			if (!visited.contains(curr)) {
				// hook for visualization
				// assume variable next is the geographic point corresponding
				// to the element removed from the priority queue
				nodeSearched.accept(curr.getLocation());
				
				// add curr to visited set
				visited.add(curr);
				
				// if (curr == goal) return parent map
				if (curr == goalNode) {
					// print number of nodes visited
					System.out.println(nrNodesVisited);
					
					return buildPath(parentMap, startNode, goalNode);
				}
				
				// for each of curr's neighbors, not in visited set
				for (MapNode n : curr.getNeighbors()) {
					if (!visited.contains(n)) {
						// if path through curr to n is shorter
						// distance from curr to neighbor n node
						double nDistance = 0.0;
						
						// edge weight from curr to neighbor n node
						for (MapEdge edge : edges) {
							if (edge.getStart() == curr && edge.getEnd() == n) {
								nDistance = edge.getDistance();
							}
						}
						
						// update pathToN
						double pathToN = curr.getDistance() + nDistance;
						// print pathToN
						// System.out.println(pathToN);
						if (pathToN < n.getDistance()) {
							// get distances
							GeographicPoint nLocation = n.getLocation();
							double startToN = curr.getActualDistance() + curr.getDistanceTo(n);
							double predictedDistance = startToN + nLocation.distance(goal);
							
							// check if predicted distance is less than next node's distance
							if (predictedDistance < n.getDistance()) {
								// set new distances
								n.setDistance(predictedDistance);
								n.setActualDistance(startToN);
								
								// update parent map and priority queue
								parentMap.put(n, curr);
								pq.add(n);
							}
						}
					}
				}
			}
		}
		
		// if we get here then there's no path
		return null;
	}

	
	
	public static void main(String[] args)
	{	
	    MapGraph simpleTestMap = new MapGraph();
			GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
			
			GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
			GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
			
			System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
			List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
			List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
			
			
			MapGraph testMap = new MapGraph();
			GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
			
			// A very simple test using real data
			testStart = new GeographicPoint(32.869423, -117.220917);
			testEnd = new GeographicPoint(32.869255, -117.216927);
			System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
			testroute = testMap.dijkstra(testStart,testEnd);
			testroute2 = testMap.aStarSearch(testStart,testEnd);
	}
	
}
