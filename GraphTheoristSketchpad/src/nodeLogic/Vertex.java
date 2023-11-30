package nodeLogic;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Vertex {
	
	public double x, y;
	private Color color;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public Vertex(double x, double y, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void addEdge(Edge edge)
	{
		this.edges.add(edge);
	}
	
	//returns an array of all unique adjacent nodes
	public Vertex[] getAdjacentVertices()
	{
		ArrayList<Vertex> adjacentVertices = new ArrayList<Vertex>(); // maybe just use hashset to avoid collision (parallel edges)
		for(Edge edge : edges)
		{
			for(Vertex node : edge.getEndpoints())
			{
				if(!(node == this) && !adjacentVertices.contains(node))
				{
					adjacentVertices.add(node);
				}
			}
		}
		return adjacentVertices.toArray(new Vertex[0]);
	}
	
	public int getDegree()
	{
		// need to account for loops, just go through edges and check if both endpoints are the same as this,
		return this.edges.size();
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

}
