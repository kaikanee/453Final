package nodeLogic;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vertex extends Circle{
	
	public double x, y;
	private final static int SIZE = 25;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public Vertex(double x, double y, Color color)
	{
		super(x,y,SIZE, color);
	}
	
	public void addEdge(Edge edge)
	{
		this.edges.add(edge);
	}
	
	//returns an array of all unique adjacent nodes
	public HashSet<Vertex> getAdjacentVertices() {
		
	    HashSet<Vertex> adjacentVertices = new HashSet<>();
	    for (Edge edge : edges) {
	    	
	        for (Vertex node : edge.getEndpoints()) {
	            if (!node.equals(this)) {
	                adjacentVertices.add(node);
	            }
	        }
	    }
	    return adjacentVertices;
	}
	
	public int getDegree()
	{
		// need to account for loops, just go through edges and check if both endpoints are the same as this,
		return this.edges.size();
	}
	
	public void vertexMoved()
	{
		for(Edge edge : this.edges)
		{
			edge.handleVertexEvent(this);
		}
	}
	

}
