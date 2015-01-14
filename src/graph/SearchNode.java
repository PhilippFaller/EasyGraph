package graph;

public class SearchNode {
	Node graphNode;
	SearchNode parent;

	public SearchNode(Node kn, SearchNode p) {
		graphNode = kn;
		parent = p;
	}

	public Node getGraphKnoten() {
		return graphNode;
	}

}