package nodeLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line{
	
	private Vertex[] endpoints = new Vertex[2];

	public Edge(Vertex start, Vertex end, Color color)
	{
		this.endpoints[0] = start;
		this.endpoints[1] = end;
		this.setFill(color);
		this.setStartX(endpoints[0].getCenterX());
		this.setStartY(endpoints[0].getCenterY());
		this.setEndX(endpoints[1].getCenterX());
		this.setEndY(endpoints[1].getCenterY());
		start.addEdge(this);
		end.addEdge(this);
	}
	
	public Vertex[] getEndpoints()
	{
		return this.endpoints;
	}
	
	

	
	public void handleVertexEvent(Vertex vertex)
	{
		System.out.println("Handled event...");
		if(this.endpoints[0] == vertex)
		{
			this.setStartX(vertex.getCenterX());
			this.setStartY(vertex.getCenterY());
		}
		else
		{
			this.setEndX(vertex.getCenterX());
			this.setEndY(vertex.getCenterY());
		}
	}

}
