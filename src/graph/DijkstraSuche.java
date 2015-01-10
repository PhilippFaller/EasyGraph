//package graph;
//
//import graph.Graph;
//
//import java.util.Collections;
//import java.util.Comparator;
//
//public class DijkstraSuche extends Suchalgorithmus {
//	
//	class DijkstraComparator implements Comparator<Suchknoten> {
//
//		@Override
//		public int compare(Suchknoten skn1, Suchknoten skn2) {
//			double k1 = skn1.pfadkosten;
//			double k2 = skn2.pfadkosten;
//			if (k1 < k2)
//				return -1;
//			if (k2 < k1)
//				return 1;
//			return 0;
//		}
//		
//	}
//
//	private Comparator<Suchknoten> comparator = new DijkstraComparator();
//	
//	public DijkstraSuche(Graph g) {
//		super(g);
//	}
//
//	@Override
//	public void addToOpenList(Suchknoten skn) {
//		// in der Oberklasse: openList.add(skn);
//		openList.add(skn);
//		//Collections.sort(openList, comparator);
//	}
//
//	
//}
