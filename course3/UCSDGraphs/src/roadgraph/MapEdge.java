package roadgraph;

public class MapEdge {
	/* instance variables */
	private String roadName;
	private String roadType;
	private MapNode start;
	private MapNode end;
	
	// distance of edge between start and end MapNode
	private double distance;
	
	

	/**
	 * Class constructor.
	 * @param roadName
	 * @param roadType
	 * @param start
	 * @param end
	 * @param distance
	 */
	public MapEdge(String roadName, String roadType, MapNode start, MapNode end, double distance) {
		this.roadName = roadName;
		this.roadType = roadType;
		this.start = start;
		this.end = end;
		this.distance = distance;
	}
	
	// given one node in an edge, return the other node
	/**
	 * Returns MapNode that node is connected to.
	 * @param node
	 * @return connected node
	 */
	public MapNode getOtherNode(MapNode node)
	{
		if (node.equals(start)) {
			return end;
		}
		else if (node.equals(end)) {
			return start;
		}
		
		throw new IllegalArgumentException("Node is not on edge");
	}

	/**
	 * @return the roadName
	 */
	public String getRoadName() {
		return roadName;
	}

	/**
	 * @param roadName the roadName to set
	 */
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	/**
	 * @return the roadType
	 */
	public String getRoadType() {
		return roadType;
	}

	/**
	 * @param roadType the roadType to set
	 */
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	/**
	 * @return the start
	 */
	public MapNode getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(MapNode start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public MapNode getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(MapNode end) {
		this.end = end;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapEdge [roadName=" + roadName + ", roadType=" + roadType + ", start=" + start.getLocation() + ", end=" + end.getLocation()
				+ ", distance=" + distance + "]";
	}
	
}
