//package graph;
//
//import graph.*;
//
//import java.util.*;
//
//public class Suchalgorithmus {
//	Graph origGraph;
//	protected ArrayList<Suchknoten> openList = new ArrayList<>();
//	private Node zielKnoten;
//	private ArrayList<String> path = new ArrayList<>();
//
//	public Suchalgorithmus(Graph g) {
//		origGraph = g;
//	}
//
//	public void suche(Node start, Node ziel) {
//		Suchknoten s = new Suchknoten(start, null, 0);
//		setZielknoten(ziel);
//		addToOpenList(s);
//		while (!openList.isEmpty()) {
//			Suchknoten skn = removeNextFromOpenList();
//			System.out.println("Aktueller Knoten: " + skn.graphKnoten.name);
//			if (istZielknoten(skn)) {
//				clearPath();
//				extrahierePfadAusSuchknoten(skn);
//				return;
//			}
//			expandiere(skn);
//		}
//	}
//
//	public void setZielknoten(Node ziel) {
//		zielKnoten = ziel;
//	}
//
//	/**
//	 * @param skn
//	 * @return true, wenn skn der Zielknoten ist, sonst false
//	 */
//	public boolean istZielknoten(Suchknoten skn) {
//		return skn.getGraphKnoten().equals(zielKnoten);
//	}
//
//	public void expandiere(Suchknoten skn) {
//		for (Edge k : skn.graphKnoten.edges) {
//			Node ziel = k.target;
//			double kantenkosten = k.costs;
//			Suchknoten sknZiel = new Suchknoten(ziel, skn, kantenkosten);
//			addToOpenList(sknZiel);
//		}
//	}
//
//	public void clearPath() {
//		path.clear();
//	}
//
//	public void extrahierePfadAusSuchknoten(Suchknoten skn) {
//		if (skn.elternknoten != null) {
//			extrahierePfadAusSuchknoten(skn.elternknoten);
//		}
//		path.add(skn.graphKnoten.name);
//	}
//
//	public Suchknoten removeNextFromOpenList() {
//		Suchknoten skn = openList.remove(0);
//		return skn;
//	}
//
//	public List<String> getPfad() {
//		return path;
//	}
//
//	public void addToOpenList(Suchknoten skn) {
//		openList.add(skn);
//	}
//
//	public Node getZielknoten() {
//		return zielKnoten;
//	}
//
//	public ArrayList<Suchknoten> getOpenList() {
//		return openList;
//	}
//
//}
//
