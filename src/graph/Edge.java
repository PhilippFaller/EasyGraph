package graph;

public class Edge {

	public Node target;
	public double costs;
	
	public Edge(Node target, double costs) {
		this.target = target;
		this.costs = costs;
	}
}

