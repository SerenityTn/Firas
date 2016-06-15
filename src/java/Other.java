package java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author MASTER
 */
public class Other {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String line = null;
		List<String[]> list = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader("C:/test2.txt"));
		while ((line = reader.readLine()) != null) {
			list.add(getArray(line));
		}
		reader.close();
		int i = 0;
		for (String[] stringArr : list) {
			for (String str : stringArr) {
				System.out.print(str + " ");

			}
			System.out.println("");
			i++;
		}
		MST t = new MST(i);
		int[][] graph = new int[i][i];
		int a = 0, b = 0;
		for (String[] stringArr : list) {
			for (String str : stringArr) {
				graph[a][b] = Integer.valueOf(str);
				b++;
			}
			a++;
			b = 0;
		}

		for (int k = 0; k < i; k++) {
			for (int m = 0; m < i; m++) {
				System.out.print(graph[k][m]);
				System.out.print(" ");
			}

			System.out.println("");
		}
		new Other(i, graph);

		t.primMST(graph);
	}

	public Other(int size, int graph[][]) {
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 600;
		final int FRAME_HEIGHT = 650;
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Minimum Spanning Tree Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MSTPanel panel = new MSTPanel(size, graph);
		frame.add(panel);

		frame.setVisible(true);
	}

	class MSTPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int size;
		private Point[] points;
		private Edge[] edges;
		private ArrayList<Edge> mst = new ArrayList<Edge>();
		private ArrayList<Edge> mst2 = new ArrayList<Edge>();
		private boolean showKruskal = false, showPrim = true;

		public MSTPanel(int size, int graph[][]) {
			super();
			this.size = size;
			initialize(graph);
			computeKruskalMST();
			computePrimMST(graph);
		}

		class Point implements Comparable<Point> {
			public double x, y, priority;
			public Point parent = this;
			public int j;

			public Point(double x, double y, int j) {
				this.j = j;
				this.x = x;
				this.y = y;
			}

			public double distanceTo(Point other) {
				double xDiff = x - other.x;
				double yDiff = y - other.y;
				return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			}

			public Point getRoot() {
				if (parent != this)
					parent = parent.getRoot(); // path compression
				return parent;
			}

			public int compareTo(Point other) {
				return (int) Math.signum(priority - other.priority);
			}
		}

		public class Edge implements Comparable<Edge> {
			public Point point1, point2;
			public double weight;

			public Edge(Point point1, Point point2, int graph[][]) {
				this.point1 = point1;
				this.point2 = point2;
				weight = graph[point1.j][point2.j];
			}

			public int compareTo(Edge other) {
				return (int) Math.signum(weight - other.weight);
			}
		}

		public void initialize(int graph[][]) {
			double gridSize = 100; // lower values (e.g. 100) create non-unique
									// solutions and differences between Kruskal
									// and Prim solutions
			Random random = new Random();
			points = new Point[size];
			for (int i = 0; i < size; i++)
				// points[i] = new Point(random.nextDouble(),
				// random.nextDouble());
				// This next line makes it likely that there is more than one
				// minimum spanning tree.
				points[i] = new Point(Math.round(gridSize * random.nextDouble()) / gridSize,
						Math.round(gridSize * random.nextDouble()) / gridSize, i);

			edges = new Edge[size * (size - 1) / 2];
			int e = 0;
			for (int p1 = 0; p1 < size; p1++)
				for (int p2 = p1 + 1; p2 < size; p2++)
					edges[e++] = new Edge(points[p1], points[p2], graph);
		}

		public void computeKruskalMST() {
			// Kruskal's algorithm for computing the minimum spanning tree:
			// Sort the edges in increasing order of edge costs.
			// For each edge in sorted order:
			// If the set root nodes of the edge points are different,
			// add the edge to the MST, and
			// assign the parent of the root of one to be the other root.
		}

		public void computePrimMST(int graph[][]) {
			// Prim's algorithm for computing the minimum spanning tree
			// Simplified (would normally use a priority queue or Fibonacci
			// heap)
			Queue<Point> queue = new LinkedList<Point>();
			for (Point p : points) {
				p.parent = p;
				p.priority = Double.POSITIVE_INFINITY;
				queue.add(p);
			}
			queue.peek().priority = 0;
			Collections.sort((LinkedList<Point>) queue);
			while (!queue.isEmpty()) {
				Point p1 = queue.poll();
				if (p1.parent != p1)
					mst2.add(new Edge(p1, p1.parent, graph));
				for (Point p2 : queue) {
					double distance = graph[p1.j][p2.j];
					if (distance < p2.priority) {
						p2.parent = p1;
						p2.priority = distance;
					}
				}
				Collections.sort((LinkedList<Point>) queue);
			}
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			double panelWidth = getWidth();
			double panelHeight = getHeight();

			g2.setColor(Color.white);
			g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));
			g2.setColor(Color.RED);

			for (Edge e : edges) {
				g2.draw(new Line2D.Double(e.point1.x * panelWidth, e.point1.y * panelHeight, e.point2.x * panelWidth,
						e.point2.y * panelHeight));
				g2.drawString("" + e.point1.j, Math.round(e.point1.x * panelHeight),
						Math.round(e.point1.y * panelWidth));
				g2.drawString("" + e.point2.j, Math.round(e.point2.x * panelHeight),
						Math.round(e.point2.y * panelWidth));

			}
			g2.setColor(Color.BLUE);
			if (showPrim)
				for (Edge e : mst2) {
					g2.draw(new Line2D.Double(e.point1.x * panelWidth, e.point1.y * panelHeight,
							e.point2.x * panelWidth, e.point2.y * panelHeight));
				}
			if (showKruskal)
				for (Edge e : mst)
					g2.draw(new Line2D.Double(e.point1.x * panelWidth, e.point1.y * panelHeight,
							e.point2.x * panelWidth, e.point2.y * panelHeight));
		}
	}

	private static String[] getArray(String s) {
		String[] array = s.split("\\s");
		return array;
	}
}