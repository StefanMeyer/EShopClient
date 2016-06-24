package ui.client.panel;	
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

import ui.client.GUI_2;
import valueobjects.Stats;

	
	
	
@SuppressWarnings("serial")
public class statsPanel extends JPanel {
	   private int max_bestand = 100;
	   private static final int BORDER_GAP = 100;
	   private static final Color GRAPH_COLOR = Color.green;
	   private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
	   private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	   private static final int GRAPH_POINT_WIDTH = 5;
	   private static final int Y_HATCH_CNT = 10;
	   private List<Integer> scores = new ArrayList<Integer>();
	   

	   
	   public statsPanel(List <Stats> Statlist) {
			if (Statlist.isEmpty()) {
				System.out.println("Keine Statistik verfügbar");
			} else {
				Iterator<Stats> iter = Statlist.iterator();
				int max_bes = 0;
				while (iter.hasNext()) {
					Stats statslist2 = iter.next();
					scores.add(statslist2.getBestand());
					//maximalhöhe bestimmen
					if (max_bes < statslist2.getBestand()) {
						this.max_bestand = statslist2.getBestand();
						System.out.println("Bestand: " + statslist2.getBestand());
						max_bes = this.max_bestand;
					}		
				}
			}
	   }
	 @Override
	//Code von http://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java
     public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      Graphics2D g2 = (Graphics2D)g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	      double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
	      double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (max_bestand - 1);
	      System.out.println("THIS: " + this.max_bestand);	 
	      List<Point> graphPoints = new ArrayList<Point>();
	      for (int i = 0; i < scores.size(); i++) {
	         int x1 = (int) (i * xScale + BORDER_GAP);
	         int y1 = (int) ((max_bestand - scores.get(i)) * yScale + BORDER_GAP);
	         graphPoints.add(new Point(x1, y1));
	      }

	      // create x and y axes 
	      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
	      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

	      // create hatch marks for y axis. 
	      for (int i = 0; i < Y_HATCH_CNT; i++) {
	         int x0 = BORDER_GAP;
	         int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
	         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
	         int y1 = y0;
	         g2.drawLine(x0, y0, x1, y1);
	      }

	      // and for x axis
	      for (int i = 0; i < scores.size() - 1; i++) {
	         int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
	         int x1 = x0;
	         int y0 = getHeight() - BORDER_GAP;
	         int y1 = y0 - GRAPH_POINT_WIDTH;
	         g2.drawLine(x0, y0, x1, y1);
	      }

	      Stroke oldStroke = g2.getStroke();
	      g2.setColor(GRAPH_COLOR);
	      g2.setStroke(GRAPH_STROKE);
	      for (int i = 0; i < graphPoints.size() - 1; i++) {
	         int x1 = graphPoints.get(i).x;
	         int y1 = graphPoints.get(i).y;
	         int x2 = graphPoints.get(i + 1).x;
	         int y2 = graphPoints.get(i + 1).y;
	         g2.drawLine(x1, y1, x2, y2);         
	      }

	      g2.setStroke(oldStroke);      
	      g2.setColor(GRAPH_POINT_COLOR);
	      for (int i = 0; i < graphPoints.size(); i++) {
	         int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
	         int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
	         int ovalW = GRAPH_POINT_WIDTH;
	         int ovalH = GRAPH_POINT_WIDTH;
	         g2.fillOval(x, y, ovalW, ovalH);
	      }
	   }

	   @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(800, 600);
	   }

	   private static void createAndShowGui() {		   
	      GUI_2 gui = new GUI_2("Shop");
	      JFrame frame = new JFrame("");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      	   
	      JPanel layout = new JPanel();
	      JPanel nav = new JPanel();
	      layout.setLayout(new BorderLayout());
	      nav.setLayout(new GridLayout(5,1));
		   List <Stats> alleStats = gui.getShop().gibAlleStats();
			if (alleStats.isEmpty()) {
				System.out.println("Keine Statistiken verfügbar");
			} else {
				Iterator<Stats> iter = alleStats.iterator();
				int lastartikelnummer = 0;
				int max_bes = 0;
				while (iter.hasNext()) {
					Stats statslist2 = iter.next();
					//Für jeden artikel ein Listenemelemt erstellen
					if (statslist2.getArklnummer() != lastartikelnummer) {

						//neues Listenfeld erzeugen
						JButton button = new JButton("Statistik für: " + statslist2.getAtklname());
						button.addActionListener(new ActionListener() { 
							public void actionPerformed(ActionEvent arg0) {
								System.out.println("layout " + statslist2.getAtklname());
								layout.add(new statsPanel(gui.getShop().statsSuchen(statslist2.getArklnummer())), BorderLayout.CENTER);
								frame.pack();
							}
						});
						nav.add(button);
						System.out.println("add: " + statslist2.getAtklname());
					}
					lastartikelnummer = statslist2.getArklnummer();
				}
			}	      
	      layout.add(nav, BorderLayout.WEST);
	      frame.getContentPane().add(layout);	
	      frame.pack();
	      frame.setLocationByPlatform(true);
	      frame.setVisible(true);
	   }

	   public static void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            createAndShowGui();
	         }
	      });
	   }
}