package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Micha
 *
 */
public class ResultNode {
	Node graphNode;
	ResultNode parent;

	/**
	 * Erzeuge aus einem Knoten des urspruenglichen Graphen einen Suchknoten
	 * Normalerweise erreicht man einen Suchknoten von einem anderen aus, dem
	 * Elternknoten parent. Dieser wird als Parameter mitgegeben. Falls es sich
	 * um den Startknoten handelt, wird null uebergeben.
	 * 
	 * @param n
	 *            Der Knoten des Originalgraphen, der zu diesem Suchknoten
	 *            gehoert
	 * @param parent
	 *            Der Elternknoten, von dem man zum neuen Suchknoten gelangt
	 *            ist, oder null (falls dieser Suchknoten der Startknoten ist)
	 *
	 */
	public ResultNode(Node n, ResultNode parent) {
		graphNode = n;
		this.parent = parent;
	}

	public Node getGraphKnoten() {
		return graphNode;
	}
	
	public void extractPath(List<String> path){
		if(parent != null) parent.extractPath(path);
		path.add(graphNode.name);
	}
}
