package application;


import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
		System.out.println(this.isBipartite());
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
	
	
	
	
	// DO NOT USE THIS FOR NOW!!! DONT!!!
	//attempt to color the graph using DFS
	public Boolean isBipartite()
	{
		if(this.vertices.size() == 0 || this.edges.size() == 0)
		{
			return true;
		}
		
		int colorArr[] = new int[this.vertices.size()];
		for (int i = 0; i < this.vertices.size(); ++i)
		{
			colorArr[i] = -1; //Arrays.fill
		}
		
		for(int i = 0; i < this.vertices.size(); i++)
		{
			if(colorArr[i] == -1)
			{
				if(bipartiteUtil(this.calculateAdjacencyMatrix(), i, colorArr) == false)
				{
					return false;
				}
			}
		}
		return true;

		
	}
	
	public Boolean bipartiteUtil(int adjMat[][], int source, int colorArr[])
	{
		colorArr[source] = 1;
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(source);
		while(!q.isEmpty())
		{
			int u = q.getFirst();
			q.pop();
			
			if(adjMat[u][u] == 1)
			{
				return false;
			}
			for(int v = 0; v < this.vertices.size(); ++v)
			{
				if(adjMat[u][v] == 1 && colorArr[v] == -1)
				{
					colorArr[v] = 1 - colorArr[u];
					q.push(v);
				}
				else if(adjMat[u][v] == 1 && colorArr[v] == colorArr[u]) return false;
			}
		}
		return true;
	}
	
	
	
	
	// returns 2d adjacency matrix
	public int[][] calculateAdjacencyMatrix()
	{
		//each row is a vertex, every column is the vertex it is adjacent to
		int[][] matrix = new int[this.vertices.size()][this.vertices.size()];
		
		for(Edge edge : this.edges)
		{
			matrix[this.vertices.indexOf((edge.getEndpoints()[0]))][this.vertices.indexOf((edge.getEndpoints()[1]))] = 1;
			matrix[this.vertices.indexOf((edge.getEndpoints()[1]))][this.vertices.indexOf((edge.getEndpoints()[0]))] = 1;
		}
		
		return matrix;
	}
	
	public int[][] calculateIncidenceMatrix()
	{
		int[][] matrix = new int[this.vertices.size()][this.edges.size()];
		
		for(int i = 0; i < this.edges.size(); i++)
		{
			Edge curr = this.edges.get(i);
			matrix[this.vertices.indexOf(curr.getEndpoints()[0])][i] = 1;
			matrix[this.vertices.indexOf(curr.getEndpoints()[1])][i] = 1;
		}
		return matrix;
	}
	
	
	
}
