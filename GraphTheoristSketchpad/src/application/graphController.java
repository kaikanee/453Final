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
