package graph;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {

	private Map<String, Node> graph; 

	public Graph() {
		graph = new HashMap<String, Node>();
	}

	public boolean isEmpty() {
		return graph.isEmpty();
	}

	public int amountOfNodes() {
		return graph.size();
	}

	public Collection<Node> knoten() {
		return graph.values();
	}

	public Node getKnoten(String name) {
		Node v = graph.get(name);
		if (v == null) {
			v = newNode(name);
		}
		return v;
	}

	public Node newNode(String name) {
		Node v = new Node(name);
		graph.put(name, v);
		return v;
	}

	public void newEdge(String source, String dest, double cost) {
		Node v = getKnoten(source);
		Node w = getKnoten(dest);
		if(!v.isConnectedWith(w)) v.connectTo(new Edge(w, cost));
	}
}
