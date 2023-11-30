package nodeLogic;

import javafx.scene.paint.Color;

public class Edge {
	
	private Vertex[] endpoints = new Vertex[2];
	private Color color;
	
	public Edge(Vertex start, Vertex end, Color color)
	{
		this.endpoints[0] = start;
		this.endpoints[1] = end;
		this.color = color;
	}
	
	public Vertex[] getEndpoints()
	{
		return this.endpoints;
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
