package graph;
import java.util.LinkedList;
import java.util.List;


public class Node {
	public String name;
	public List<Edge> edges;
	
	public Node(String name) {
		this.name = name;
		edges = new LinkedList<Edge>();
	}
	
	public boolean isConnectedWith(Node n) {
		for(Edge e : edges) {
			if (e.target == n)
				return true;
		}
		return false;
	}

	public void connectTo(Edge edge) {
		edges.add(edge);
	}

}
