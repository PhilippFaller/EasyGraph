package data;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public abstract class GraphIO {

	public static Graph loadGraph(String fileName) throws IOException, NoSuchElementException{
		Graph g = new Graph();
		BufferedReader f = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line = f.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			String source = st.nextToken();
			String dest = st.nextToken();
			double cost = Double.parseDouble(st.nextToken());
			g.newEdge(source, dest, cost);
		}
		f.close();
		return g;
	}
	
	public static void saveGraph(Graph g, String fileName) throws IOException{
		BufferedWriter f = new BufferedWriter(new FileWriter(fileName));
		for(Node n : g.getAllNodes()){
			for(Edge e : n.edges){
				f.write(n.name);
				f.write("\t");
				f.write(e.target.name);
				f.write("\t");
				f.write(String.valueOf(e.costs));
				f.write("\n");
			}
		}
		f.close();
	}

}
