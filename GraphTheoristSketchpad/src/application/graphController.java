package application;


import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

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
	
	public void addVertex(Vertex vertex)
	{
		this.vertices.add(vertex);
	}
	
	public Edge addEdge(Vertex start, Vertex end)
	{
		Edge edge = new Edge(start, end, getDefaultColor());
		this.edges.add(edge);
		return edge;
	}
	
	// Remove a vertex and all incident vertices.
	public ArrayList<Edge> removeVertex(Vertex vertex) {
		HashSet<Vertex> adjacentVertices = new HashSet<>();
		ArrayList<Edge> incidentEdges = new ArrayList<>();
		
		adjacentVertices = vertex.getAdjacentVertices();
		incidentEdges = vertex.getIncidentEdges();
		
		
		for (Vertex ver: adjacentVertices) {
			
			ver.removeEdge(incidentEdges);
		}
		
		this.edges.removeAll(incidentEdges);
		this.vertices.remove(vertex);
		
		return incidentEdges;
	}
	
	// removes a single edge
	public void removeEdge(Edge currentEdge) {
		Vertex[] endPoints;
		
		endPoints = currentEdge.getEndpoints();
		
		// if its a loop edge
		if (endPoints[0].equals(endPoints[1])) {
			
			endPoints[0].removeEdge(currentEdge);
		}
		else {
			// remove from adjacent vertices.
			for (Vertex vertex : endPoints) {
				
				vertex.removeEdge(currentEdge);
			}
		}
		
		this.edges.remove(currentEdge);
	}
	
	public int getDegree(Vertex vertex) {;
		
		return vertex.getDegree();
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	// Finding k components
	public int findConnectedComponents() {
		 
		HashSet<Vertex> visited = new HashSet<>();
		int components = 0;
		
		for (Vertex vertex : vertices) {
			
			if (!visited.contains(vertex)) {
				dfs(vertex, visited);
				components++;
			}
		}
	
		return components;
	}
	
	// Depth First Search
	private void dfs(Vertex start, HashSet<Vertex> visited) {
        Stack<Vertex> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);

                for (Vertex neighbor : current.getAdjacentVertices()) {
                	
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
	}
	
}
