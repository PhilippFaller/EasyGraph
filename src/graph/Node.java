package graph;
import java.util.ArrayList;
import java.util.List;

public class Node {
	public String name;
	public List<Edge> edges;
	
	public Node(String name) {
		this.name = name;
		edges = new ArrayList<Edge>();
	}
	
	public boolean isConnectedWith(Node n) {
		for(Edge e : edges) {
			if (e.target == n)
				return true;
		}
		return false;
	}

	public void connectTo(Node n, double cost) {
		edges.add(new Edge(n, cost));
	}

}

