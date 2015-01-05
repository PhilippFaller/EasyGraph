package data;

import graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
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

}
