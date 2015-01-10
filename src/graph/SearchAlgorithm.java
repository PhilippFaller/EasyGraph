package graph;

import graph.*;

import java.util.*;

public class SearchAlgorithm {
	
	Graph origGraph;
	private ArrayList<Suchknoten> openList = new ArrayList<>();
	private Node zielKnoten;
	private ArrayList<String> path = new ArrayList<>();

	public SearchAlgorithm(Graph g) {
		origGraph = g;
	}

	public void search(Node start, Node ziel) {
		Suchknoten s = new Suchknoten(start, null);
		setZielknoten(ziel);
		addToOpenList(s);
		while (!openList.isEmpty()) {
			Suchknoten skn = removeNextFromOpenList();
			if (istZielknoten(skn)) {
				clearPath();
				extractPathFromSearchNodes(skn);
				return;
			}
			expand(skn);
		}
	}

	public void setZielknoten(Node ziel) {
		zielKnoten = ziel;
	}

	/**
	 * @param skn
	 * @return true, wenn skn der Zielknoten ist, sonst false
	 */
	public boolean istZielknoten(Suchknoten skn) {
		return skn.getGraphKnoten().equals(zielKnoten);
	}

	public void expand(Suchknoten skn) {
		for (Edge k : skn.graphKnoten.edges) {
			Node ziel = k.target;
			Suchknoten sknZiel = new Suchknoten(ziel, skn);
			addToOpenList(sknZiel);
		}
	}

	public void clearPath() {
		path.clear();
	}

	public void extractPathFromSearchNodes(Suchknoten skn) {
		if (skn.parent != null) {
			extractPathFromSearchNodes(skn.parent);
		}
		path.add(skn.graphKnoten.name);
	}

	public Suchknoten removeNextFromOpenList() {
		Suchknoten skn = openList.remove(0);
		return skn;
	}

	public List<String> getPath() {
		return path;
	}

	public void addToOpenList(Suchknoten skn) {
		openList.add(skn);
	}

	public Node getTarget() {
		return zielKnoten;
	}

	public ArrayList<Suchknoten> getOpenList() {
		return openList;
	}
}


