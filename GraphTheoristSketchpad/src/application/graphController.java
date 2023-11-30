package application;


import javafx.scene.paint.Color;
import java.util.ArrayList;

import nodeLogic.Edge;
import nodeLogic.Vertex;

public class graphController {
	
	public ArrayList<Edge> edges;
	public ArrayList<Vertex> vertices;
	private Color defaultColor;
	
	public graphController()
	{
		this.edges = new ArrayList<Edge>();
		this.vertices = new ArrayList<Vertex>();
		setDefaultColor(Color.BLACK);
	}
	
	public void addVertex(double x, double y)
	{
		Vertex vertex = new Vertex(x, y, getDefaultColor());
		this.vertices.add(vertex);
	}
	
	public void addEdge(Vertex start, Vertex end)
	{
		Edge edge = new Edge(start, end, getDefaultColor());
		this.edges.add(edge);
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
}
