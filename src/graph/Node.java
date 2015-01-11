package graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


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

