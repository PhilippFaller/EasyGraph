package graph;

import graph.*;

import java.util.*;

public class SearchAlgorithm {
	
	Graph origGraph;
	private ArrayList<SearchNode> openList = new ArrayList<>();
	private ArrayList<SearchNode> closedList = new ArrayList<>();
	private Node targetNode;
	private ArrayList<String> path = new ArrayList<>();

	public SearchAlgorithm(Graph g) {
		origGraph = g;
	}

	public void search(Node start, Node target) {
		SearchNode s = new SearchNode(start, null);
		setTargetNode(target);
		addToOpenList(s);
		while (!openList.isEmpty()) {
			SearchNode skn = removeNextFromOpenList();
			closedList.add(skn);
			if (isTargetNode(skn)) {
				clearPath();
				extractPathFromSearchNodes(skn);
				return;
			}
			expand(skn);
		}
	}

	public void setTargetNode(Node target) {
		targetNode = target;
	}

	/**
	 * @param skn
	 * @return true, wenn skn der Zielknoten ist, sonst false
	 */
	public boolean isTargetNode(SearchNode skn) {
		return skn.getGraphKnoten().equals(targetNode);
	}

	public void expand(SearchNode skn) {
		for (Edge k : skn.graphNode.edges) {
			Node target = k.target;
			SearchNode sknZiel = new SearchNode(target, skn);
//			if(!closedList.contains(sknZiel))
				addToOpenList(sknZiel);
		}
	}

	public void clearPath() {
		path.clear();
	}

	public void extractPathFromSearchNodes(SearchNode skn) {
		if (skn.parent != null) {
			extractPathFromSearchNodes(skn.parent);
		}
		path.add(skn.graphNode.name);
	}

	public SearchNode removeNextFromOpenList() {
		SearchNode skn = openList.remove(0);
		return skn;
	}

	public List<String> getPath() {
		return path;
	}

	public void addToOpenList(SearchNode skn) {
		openList.add(skn);
	}

	public Node getTarget() {
		return targetNode;
	}

	public ArrayList<SearchNode> getOpenList() {
		return openList;
	}
}


