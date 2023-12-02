package nodeLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class Edge extends Path{
	
	// index 0 is the start vertex index 1 is the end vertex - it kind of doesnt matter though not a digraph
	private Vertex[] endpoints = new Vertex[2];
	private MoveTo startPath;
	private QuadCurveTo endPath;
	

	public Edge(Vertex start, Vertex end, Color color)
	{
		this.endpoints[0] = start;
		this.endpoints[1] = end;
		
		this.startPath = new MoveTo(endpoints[0].getCenterX(), endpoints[0].getCenterY());
		this.endPath = new QuadCurveTo();
		endPath.setX(endpoints[1].getCenterX());
		endPath.setY(endpoints[1].getCenterY());
		
		double random = Math.random() * ((150 - 1) + 1) + 1;
		if((int)random > 75)
		{
			random *= -1.0;
		}
		double controlX = (endpoints[1].getCenterX() + endpoints[0].getCenterX()) / 2 + random;
		double controlY = (endpoints[1].getCenterY() + endpoints[0].getCenterY()) / 2 + random;
		this.setControlPoint(controlX, controlY);
		
		
		this.getElements().add(startPath);
		this.getElements().add(endPath);
		
		this.setStrokeWidth(5);
		start.addEdge(this);
		end.addEdge(this);
	}
	
	public Vertex[] getEndpoints()
	{
		return this.endpoints;
	}
	
	public void setControlPoint(double x, double y)
	{
		this.endPath.setControlX(x);
		this.endPath.setControlY(y);
	}

	
	public void handleVertexEvent(Vertex vertex)
	{
		double controlOffsetX = Math.abs(startPath.getX() - this.endPath.getControlX());
		double controlOffsetY = Math.abs(startPath.getY() - this.endPath.getControlY());
		
		System.out.println(controlOffsetX + " " + controlOffsetY);
		if(this.endpoints[0] == vertex)
		{
			this.startPath.setX(vertex.getCenterX());
			this.startPath.setY(vertex.getCenterY());
			//endPath.setControlX(endpoints[0].getCenterX() - controlOffsetX);
			//endPath.setControlY(endpoints[0].getCenterY() - controlOffsetY);
		}
		else
		{
			this.endPath.setX(vertex.getCenterX());
			this.endPath.setY(vertex.getCenterY());
			//endPath.setControlX(endpoints[0].getCenterX() - controlOffsetX);
			//endPath.setControlY(endpoints[0].getCenterY() - controlOffsetY);
		}
	}

}
