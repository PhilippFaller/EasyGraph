package graph;


import java.util.ArrayList;
import java.util.List;


public class SearchNode {
	Node graphNode;
	SearchNode parent;

	/**
	 * Erzeuge aus einem Knoten des urspruenglichen Graphen einen Suchknoten
	 * Normalerweise erreicht man einen Suchknoten von einem anderen aus, dem
	 * Elternknoten parent. Dieser wird als Parameter mitgegeben. Falls es sich
	 * um den Startknoten handelt, wird null uebergeben.
	 * 
	 * @param kn
	 *            Der Knoten des Originalgraphen, der zu diesem Suchknoten
	 *            gehoert
	 * @param p
	 *            Der Elternknoten, von dem man zum neuen Suchknoten gelangt
	 *            ist, oder null (falls dieser Suchknoten der Startknoten ist)
	 *
	 */
	public SearchNode(Node kn, SearchNode p) {
		graphNode = kn;
		parent = p;
	}

	public Node getGraphKnoten() {
		return graphNode;
	}

}