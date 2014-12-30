package graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Graph {

	private Map<String, Node> graph; 
	private List<Node> graphList;

	public Graph() {
		graph = new HashMap<String, Node>();
	}

	public boolean isEmpty() {
		return graph.isEmpty();
	}

	public int amountOfNodes() {
		return graph.size();
	}

	public Collection<Node> getAllNodes() {
		return graph.values();
	}

	public Node getNode(String name) {
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
		Node v = getNode(source);
		Node w = getNode(dest);
		if(!v.isConnectedWith(w)) v.connectTo(new Edge(w, cost));
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

	public static Graph loadGraph(String fileName) {
		Graph g = new Graph();
		try {
			BufferedReader f = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = f.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				String source = st.nextToken();
				String dest = st.nextToken();
				double cost = Double.parseDouble(st.nextToken());
				g.newEdge(source, dest, cost);
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}
}

