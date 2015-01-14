package gui;

import graph.Node;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import data.GraphIO;


public abstract class MyAction extends AbstractAction{
	protected GUI g;
	public MyAction(GUI g, String text, String shortDescription, int acceleratorKey){
		super(text);
		this.g = g;
		putValue(SHORT_DESCRIPTION, shortDescription);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, KeyEvent.CTRL_DOWN_MASK));
	}
}

class FindWayAction extends MyAction{
	public FindWayAction(GUI g){
		super(g, "Finde", "Findet den kürzesten Weg auf dem Graph", KeyEvent.VK_ENTER);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		g.getGraphComponent().findWay(g.getTextFromStartField(), g.getTextFromTargetField());
	}
}

class AddNodeAction extends MyAction{
	public AddNodeAction(GUI g){
		super(g, "Knoten hinzufügen", "Fügt dem Graph einen neuen Knoten hinzu", KeyEvent.VK_Q);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(g == null) return;
		String name = JOptionPane.showInputDialog(g, "Geben sie den Name des neuen Knoten ein.", "Neuer Knoten");
		if(name == null) return;
		if(name.matches(".*\\s+.*")){
			JOptionPane.showMessageDialog(g, "Der Name darf keine Whitespacezeichen enthalten.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		g.setUpdate(false);
		g.getGraphComponent().addNewNode(new PhysikNode(new Node(name), Math.random() * g.getWidth(), Math.random() * g.getHeight()));
		g.setUpdate(true);
	}
}

class RemoveNodeAction extends MyAction{
	public RemoveNodeAction(GUI g){
		super(g, "Knoten entfernen", "Entfernt einen Knoten von dem Graph", KeyEvent.VK_R);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		g.getNameLabel().setText("");
		if(g.getSelectedNode() != null) g.getGraphComponent().removeNode(g.getSelectedNode());
	}
}

class AlterMovementAction extends MyAction{
	public static final String s = "Sortieren anhalten";
	public AlterMovementAction(GUI g){
		super(g, s, "Stellt ein ob der Graph sich sortiert oder nicht", KeyEvent.VK_SPACE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(PhysikNode.isForceActive()){
			PhysikNode.setForceActive(false);
			g.getMovementItem().setText("Starte sortieren");
		}
		else{
			PhysikNode.setForceActive(true);
			g.getMovementItem().setText(s);
		}
	}
}

class LoadGraphAction extends MyAction{
	public LoadGraphAction(GUI g){
		super(g, "Lade Graph", "Läd eine Datei die einen Graph enthält", KeyEvent.VK_L);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int result = g.fileChooser.showDialog(g, "Lade");
		if(result == JFileChooser.APPROVE_OPTION){
			try{
				g.setUpdate(false);
				if(g != null) g.getContentPane().remove(g.getGraphComponent());
				g.setGraphComponent(new GraphComponent(GraphIO.loadGraph(g.fileChooser.getSelectedFile().getAbsolutePath())));
				g.getContentPane().add(g.getGraphComponent());
				g.revalidate();
				g.getGraphComponent().shuffleNodes();
				g.setUpdate(true);
				g.setCurrentFileSaved(true);
			}

			catch(Exception ex){
				JOptionPane.showMessageDialog(g, "Fehler beim lesen der Datei \""
						+ g.fileChooser.getSelectedFile().getAbsolutePath() + "\".",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
}

class NewFileAction extends MyAction{
	public NewFileAction(GUI g){
		super(g, "Neue Datei", "Öffnet eine leere Datei", KeyEvent.VK_N);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(g != null) g.getContentPane().remove(g.getGraphComponent());
		g.setGraphComponent(new GraphComponent());
		g.getContentPane().add(g.getGraphComponent());
		g.revalidate();
		g.setUpdate(true);
		g.setCurrentFileSaved(true);
	}
}

class SaveFileAction extends MyAction{
	public SaveFileAction(GUI g){
		super(g, "Datei speichern", "Speichert die aktuelle Datei", KeyEvent.VK_S);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(g.getGraphComponent().getGraph().amountOfNodes() <= 1 ){
				JOptionPane.showMessageDialog(g, "Es werden mindestens 2 verbundene Knoten zu speichern benötigt.",
						"Speichern", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int result = 0;
			if(g.isCurrentFileSaved())g.fileChooser.showSaveDialog(g);
			if(result == JFileChooser.APPROVE_OPTION || !g.isCurrentFileSaved()) 
				GraphIO.saveGraph(g.getGraphComponent().getGraph(), g.fileChooser.getSelectedFile().getAbsolutePath());
			g.setCurrentFileSaved(false);
		}catch (Exception ex){
			JOptionPane.showMessageDialog(g, "Fehler beim speichern der Datei.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}		
	}
}

class SaveFileAsAction extends MyAction{
	public SaveFileAsAction(GUI g){
		super(g, "Speichern unter", "Auswahl wo die aktuelle Datei gespeichert werden soll", 0);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(g.getGraphComponent().getGraph().amountOfNodes() <= 1 ){
				JOptionPane.showMessageDialog(g, "Es werden mindestens 2 verbundene Knoten zu speichern benötigt.",
						"Speichern", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int result = 0;
			g.fileChooser.showSaveDialog(g);
			if(result == JFileChooser.APPROVE_OPTION) 
				GraphIO.saveGraph(g.getGraphComponent().getGraph(), g.fileChooser.getSelectedFile().getAbsolutePath());
			g.setCurrentFileSaved(false);
		}catch (Exception ex){
			JOptionPane.showMessageDialog(g, "Fehler beim speichern der Datei.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}	
	}	
}

class AlterShowValues extends MyAction{
	public static final String s = "Kantenwerte anzeigen";
	public AlterShowValues(GUI g){
		super(g, s, "Wählt aus ob die Kantenwerte angezeigt werden oder nicht", KeyEvent.VK_V);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(EdgeComponent.showValues()){
			EdgeComponent.setShowValues(false);
			g.getShowValueItem().setText(s);
		}else{
			EdgeComponent.setShowValues(true);
			g.getShowValueItem().setText("Kantenwerte verstecken");
		}
	}
}

class ShuffleNodesAction extends MyAction{
	public ShuffleNodesAction(GUI g){
		super(g, "Knoten neu anordnen", "Wählt aus ob die Kantenwerte angezeigt werden oder nicht", KeyEvent.VK_K);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		g.getGraphComponent().shuffleNodes();
	}
}