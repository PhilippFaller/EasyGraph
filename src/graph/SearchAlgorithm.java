package graph;

import graph.*;

import java.util.*;

public class SearchAlgorithm {
	Graph origGraph;
	private ArrayList<ResultNode> openList = new ArrayList<>();
	private Node target;
	private ArrayList<String> path = new ArrayList<>();

	public SearchAlgorithm(Graph g) {
		origGraph = g;
	}

	public void search(Node start, Node target) {
		setTargetNode(target);
		// Aus start einen Suchknoten machen und zur Open List hinzufuegen
		openList.add(new ResultNode(start, null));
		
		// Solange Knoten aus der OL entfernen und expandieren, bis das Ziel
		// erreicht ist
		ResultNode n;
		do{
			n = removeNextFromOpenList();
			expand(n);			
		}while(!isTarget(n));
		
		// Aus dem letzten Suchknoten den Pfad extrahieren und in path
		// speichern.
		n.extractPath(path);
		
	}

	public void setTargetNode(Node target) {
		this.target =  target;
	}

	/**
	 * @param n
	 * @return true, wenn skn der Zielknoten ist, sonst false
	 */
	public boolean isTarget(ResultNode n) {
		if(n.graphNode.name.equals(target.name)) return true;
		return false;
	}

	public void expand(ResultNode n) {
		for(Edge e : n.graphNode.edges)
			openList.add(new ResultNode(e.target, n));
		// Alle Kanten des Graphknoten von skn durchlaufen und deren Zielknoten
		// als Suchknoten in die OL einfuegen.
	}

	public void clearPath() {
		path = new ArrayList<>();
	}

	public void extractPathFromResultNodes(ResultNode n) {
		n.extractPath(path);
		// Gerne rekursiv... :-) 
	}

	public ResultNode removeNextFromOpenList() {
		ResultNode k = openList.get(0);
		openList.remove(0);
		return k;
	}

	public List<String> getPath() {
		return path;
	}

	public void addToOpenList(ResultNode skn) {
		openList.add(skn);
	}

	public Node getTarget() {
		return target;
	}

	public ArrayList<ResultNode> getOpenList() {
		return openList;
	}

}


