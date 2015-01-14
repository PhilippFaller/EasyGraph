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

	public Edge getEdge(String startNode, String endNode){
		for(Edge e : graph.get(startNode).edges) if( e.target.name.equals(endNode)) return e;
		return null;
	}
	
	public int amountOfNodes() {
		return graph.size();
	}

	public Collection<Node> getAllNodes() {
		return graph.values();
	}

	public Node getNode(String name) {
		Node v = graph.get(name);
		if(v == null){
			newNode(name);
			v = graph.get(name);
		}
		return v;
	}
	
	public Node newNode(String name) {
		Node v = new Node(name);
		graph.put(name, v);
		return v;
	}

	public void newEdge(String source, String dest, double cost) {
		Node v = getNode(source);
		Node w = getNode(dest);
		if(!v.isConnectedWith(w)) v.connectTo(w, cost);
	}
	
	public void removeNode(String name){
		Node removeNode = graph.get(name);
		for(Node n : graph.values()){
			for(int i = 0; i < n.edges.size(); i++){
				if(n.edges.get(i).target == removeNode) n.edges.remove(i);
			}
		}
		graph.remove(name);
	}
	
	@Override
	public String toString(){
		String result = "Adjazenzlisten des Graphen:\n";
		for (Node n : getAllNodes()) {
			result += n.name + ":\n";
			for (Edge e : n.edges) 
				result += "  " + n.name + " -> " + e.target.name + " : " + e.costs + '\n';
		}
		return result + '\n';
	}
	
	public void printGraph(){
		System.out.println(toString());
	}
}

