package graph;

public class BreadthFirstSearch extends SearchAlgorithm {

	public BreadthFirstSearch(Graph g) {
		super(g);
	}

	@Override
	public void search(Node start, Node target) {
		SearchNode s = new SearchNode(start, null);
		setTargetNode(target);
		addToOpenList(s);
		while (!getOpenList().isEmpty()) {
			SearchNode skn = removeNextFromOpenList();
			if (isTargetNode(skn)) {
				clearPath();
				extractPathFromSearchNodes(skn);
				return;
			}
			expand(skn);
		}
	}
}

