package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {
	
	/* instance variables */
	// list of edges out from node
	private HashSet<MapEdge> edges;
	
	// latitude and longitude of node
	private GeographicPoint location;
	
	// predicted distance from start to node
	private double distance;
	
	// actual distance from start to node
	private double actualDistance;
	
	/**
	 * Class constructor.
	 * @param location
	 */
	public MapNode(GeographicPoint location) {
		setEdges(new HashSet<MapEdge>());
		setLocation(location);
		distance = 0.0;
		actualDistance = 0.0;
	}
	
	//TODO rewrite and check
    public double getDistanceTo(MapNode to) {
        if (!getNeighbors().contains(to)) throw new IllegalArgumentException("Cannot find path");
        GeographicPoint toLocation = to.getLocation();
        for (MapEdge me : edges) {
            if (getLocation().equals(toLocation)) {
                return me.getDistance();
            }
        }
        return 0;
    }
	
	/**
	 * Adds edge to MapNode.
	 * @param edge
	 */
	public void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	/**
	 * Returns set of neighbors of MapNode.
	 * @return neighbors
	 */
	public Set<MapNode> getNeighbors() {
		Set<MapNode> neighbors = new HashSet<MapNode>();
		
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		
		return neighbors;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return location.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MapNode) || (obj == null)) {
			return false;
		}
		
		MapNode node = (MapNode) obj;
		
		return node.location.equals(this.location);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapNode [edges=" + edges + ", location=" + location + "]";
	}

	/**
	 * @return the edges
	 */
	public HashSet<MapEdge> getEdges() {
		return edges;
	}

	/**
	 * @param edges the edges to set
	 */
	public void setEdges(HashSet<MapEdge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the location
	 */
	public GeographicPoint getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	@Override
	public int compareTo(MapNode m) {
		return ((Double)(this.getDistance() + this.getActualDistance())).compareTo((Double) (m.getDistance() + m.getActualDistance()));

	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the actualDistance
	 */
	public double getActualDistance() {
		return actualDistance;
	}

	/**
	 * @param actualDistance the actualDistance to set
	 */
	public void setActualDistance(double actualDistance) {
		this.actualDistance = actualDistance;
	}
	
}
