package graph;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchAlgorithm {
	
	Graph origGraph;
	private List<SearchNode> openList;;
	private Node targetNode;
	private List<String> path;

	public SearchAlgorithm(Graph g) {
		origGraph = g;
		openList = new ArrayList<>();
		path = new ArrayList<>();
	}

	public abstract void search(Node start, Node target);

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

	protected void expand(SearchNode skn) {
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

	protected void extractPathFromSearchNodes(SearchNode skn) {
		if (skn.parent != null) {
			extractPathFromSearchNodes(skn.parent);
		}
		path.add(skn.graphNode.name);
	}

	protected SearchNode removeNextFromOpenList() {
		SearchNode skn = openList.remove(0);
		return skn;
	}

	public List<String> getPath() {
		return path;
	}

	protected void addToOpenList(SearchNode skn) {
		openList.add(skn);
	}

	public Node getTarget() {
		return targetNode;
	}

	public List<SearchNode> getOpenList() {
		return openList;
	}
}


