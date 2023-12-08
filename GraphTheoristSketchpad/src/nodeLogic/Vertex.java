package nodeLogic;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vertex extends Circle{
	
	public double x, y;
	private final static int SIZE = 25;
	private ArrayList<Edge> incidentEdges = new ArrayList<Edge>();
	
	public Vertex(double x, double y, Color color)
	{
		super(x,y,SIZE, color);
	}
	
	// adds incident edge
	public void addEdge(Edge edge)
	{
		this.incidentEdges.add(edge);
	}
	
	// removes incident edge
	public void removeEdge(Edge removedEdge) {
		
		this.incidentEdges.remove(removedEdge);
	}
	
	// removes all incident edges shared with a vertex.
	public void removeEdge(ArrayList<Edge> remEdges) {
		
		this.incidentEdges.removeAll(remEdges);
	}
	
	//returns an array of all unique adjacent nodes
	public HashSet<Vertex> getAdjacentVertices() {
		
	    HashSet<Vertex> adjacentVertices = new HashSet<>();
	    for (Edge edge : this.incidentEdges) {
	    	
	        for (Vertex node : edge.getEndpoints()) {
	            if (!node.equals(this)) {
	                adjacentVertices.add(node);
	            }
	        }
	    }
	    return adjacentVertices;
	}
	
	// getter for incident edges
	public ArrayList<Edge> getIncidentEdges(){
		
		return this.incidentEdges;
	}
	
	// gets the degree of this vertex
	public int getDegree()
	{
		return this.incidentEdges.size();
	}
	
	// handles the event of a moved vertex.
	public void vertexMoved()
	{
		for(Edge edge : this.incidentEdges)
		{
			edge.handleVertexEvent(this);
		}
	}
	

}
