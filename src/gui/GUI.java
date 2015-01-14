package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import graph.Node;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import data.GraphIO;

public class GUI extends JFrame {

	public final JFileChooser fileChooser = new JFileChooser();

	private boolean update;
	private boolean currentFileSaved;
	private GraphComponent g;
	private int millisBetweenRepaint;
	private JTextField startField, targetField;
	private NodeComponent selectedNode;
	private JLabel nameLabel;
	private JMenuItem movementItem;
	private JMenuItem removeNode;
	private JMenuItem showValue;

	public GUI(String name){
		super(name);
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		g = new GraphComponent();
		getContentPane().add(g);
		update = true;
		currentFileSaved = true;
		millisBetweenRepaint = 17;
		selectedNode = null;

		nameLabel = new JLabel("");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		g.add(nameLabel, BorderLayout.NORTH);
		JPanel southPanel = new JPanel();
		JButton findButton = new JButton("Finde");
		findButton.setAction(new FindWayAction(this));
		startField = new JTextField(10);
		targetField = new JTextField(10);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(new JLabel("Start:"));
		southPanel.add(startField);
		southPanel.add(new JLabel("Ziel:"));
		southPanel.add(targetField);
		southPanel.add(findButton);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);

		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("Datei");
		JMenu edit = new JMenu("Bearbeiten");
		JMenu help = new JMenu("Hilfe");
		JMenuItem newFile = new JMenuItem("Neue Datei");
		JMenuItem loadFile = new JMenuItem("Lade Datei");
		JMenuItem saveFile = new JMenuItem("Datei speichern");
		JMenuItem addNode = new JMenuItem("Knoten hinzufügen");
		removeNode = new JMenuItem("Knoten entfernen");
		removeNode.setEnabled(false);
		JMenuItem about = new JMenuItem("Über");
		movementItem = new JMenuItem("Sortieren anhalten");
		JMenuItem saveFileAs = new JMenuItem("Speichern unter");
		showValue = new JMenuItem("Kantenwerte anzeigen");
		JMenuItem shuffleNodes = new JMenuItem("Knoten neu anordnen");
		newFile.setAction(new NewFileAction(this));
		loadFile.setAction(new LoadGraphAction(this));
		saveFile.setAction(new SaveFileAction(this));
		saveFileAs.setAction(new SaveFileAsAction(this));
		addNode.setAction(new AddNodeAction(this));
		removeNode.setAction(new RemoveNodeAction(this));
		removeNode.setEnabled(false);
		movementItem.setAction(new AlterMovementAction(this));
		shuffleNodes.setAction(new ShuffleNodesAction(this));
		showValue.setAction(new AlterShowValues(this));
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			ImageIcon img = new ImageIcon(getClass().getResource("../res/doge.png"));
			JOptionPane.showMessageDialog(GUI.this , "","Über" , JOptionPane.PLAIN_MESSAGE, img);
			}
		});
		file.add(newFile);
		file.add(loadFile);
		file.add(saveFile);
		file.add(saveFileAs);
		edit.add(addNode);
		edit.add(removeNode);
		edit.add(movementItem);
		edit.add(shuffleNodes);
		edit.add(showValue);
		menu.add(file);
		menu.add(edit);
		menu.add(help);
		help.add(about);
		setJMenuBar(menu);

		getContentPane().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				NodeComponent n = null;
				if(g != null) n = g.getNodeAt(e.getX(), e.getY());
				if(n != null) n.setDragged(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					NodeComponent n = null;
					if(g != null) n = g.getNodeAt(e.getX(), e.getY());
					if(n != null) n.setDragged(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// You do nuthin', John Snow!
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// You do nuthin', John Snow!

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					NodeComponent n = null;
					if(g != null) n = g.getNodeAt(e.getX(), e.getY());
					if(selectedNode != null){
						if(e.isControlDown() && n != null){
							g.connetct(selectedNode, n);
							return;
						}
						selectedNode.setSelected(false);
					}
					if(n != null){
						n.setSelected(true);
						nameLabel.setText(n.getNode().name);
						removeNode.setEnabled(true);
					}
					else{
						nameLabel.setText("");
						removeNode.setEnabled(false);
					}
					selectedNode = n;
				}

			}
		});

		setIconImage((new ImageIcon(getClass().getResource("../res/icon.png")).getImage()));
		setVisible(true);
	}



	public void startLoop(){
		long t0 = System.currentTimeMillis();
		while(true){
			if(update) g.update();
			long deltaT = System.currentTimeMillis() - t0;
			if(deltaT >= millisBetweenRepaint){
				t0 = System.currentTimeMillis();
				repaint();
			}
		}
	}

	public GraphComponent getGraphComponent() {
		return g;
	}
	public void setGraphComponent(GraphComponent g) {
		this.g = g;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public JLabel getNameLabel() {
		return nameLabel;
	}
	public NodeComponent getSelectedNode() {
		return selectedNode;
	}
	public JMenuItem getMovementItem() {
		return movementItem;
	}
	public boolean isCurrentFileSaved() {
		return currentFileSaved;
	}
	public void setCurrentFileSaved(boolean currentFileSaved) {
		this.currentFileSaved = currentFileSaved;
	}
	public JMenuItem getShowValueItem() {
		return showValue;
	}
	public String getTextFromStartField(){
		return startField.getText();
	}
	public String getTextFromTargetField(){
		return targetField.getText();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {	}
		GUI g = new GUI("Graph");
		g.startLoop();
	}

}
