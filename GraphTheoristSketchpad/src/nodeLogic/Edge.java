package nodeLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class Edge extends Path{
	
	// index 0 is the start vertex index 1 is the end vertex
	private Vertex[] endpoints = new Vertex[2];
	private MoveTo startPath;
	private QuadCurveTo endPath;
	


	public Edge(Vertex start, Vertex end, Color color)
	{
		
		this.endpoints[0] = start;
		this.endpoints[1] = end;
		this.setFill(Color.TRANSPARENT);
		this.setStrokeWidth(5);
		
		// Deals with self loops
		
			
			this.startPath = new MoveTo(endpoints[0].getCenterX(), endpoints[0].getCenterY());
			this.endPath = new QuadCurveTo();
			
			endPath.setX(endpoints[1].getCenterX());
			endPath.setY(endpoints[1].getCenterY());
			
			// jank 
			double controlX, controlY;
			if(start.equals(end))
			{
				this.endPath.setX(startPath.getX() - 23);
				this.startPath.setX(startPath.getX() + 23);
				
				controlX = endpoints[1].getCenterX();
				controlY = endpoints[1].getCenterY() -75;
				//System.out.println("Self loop");
			}
			else
			{
				double random = Math.random() * ((25 - 1) + 1) + 1;
				if((int)random > 75)
				{
					random *= -1.0;
				}
				controlX = (endpoints[1].getCenterX() + endpoints[0].getCenterX()) / 2 + random;
				controlY = (endpoints[1].getCenterY() + endpoints[0].getCenterY()) / 2 + random;
			}
			
			this.setControlPoint(controlX, controlY);
			
			
			this.getElements().add(startPath);
			this.getElements().add(endPath);
			
			
			start.addEdge(this);
			end.addEdge(this);
		
	}
	
	public Vertex[] getEndpoints()
	{
		return this.endpoints;
	}
	
	public void setControlPoint(double x, double y)
	{
		double pointx, pointy;
		pointx = 2* x - .5 * (this.startPath.getX() + this.endPath.getX());
		pointy = 2* y - .5 * (this.startPath.getY() + this.endPath.getY());
		this.endPath.setControlX(pointx);
		this.endPath.setControlY(pointy);
	}

	public void handleVertexEvent(Vertex vertex)
	{

		if(this.endpoints[0].equals(this.endpoints[1]))
		{
			this.startPath.setX(vertex.getCenterX() - 22);
			this.startPath.setY(vertex.getCenterY());
			this.endPath.setX(vertex.getCenterX() + 22);
			this.endPath.setY(vertex.getCenterY());
		}
		else if(this.endpoints[0].equals(vertex))
		{
			this.startPath.setX(vertex.getCenterX());
			this.startPath.setY(vertex.getCenterY());
		}
		else
		{
			this.endPath.setX(vertex.getCenterX());
			this.endPath.setY(vertex.getCenterY());
		}
	}
	
}
