package application;
	
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nodeLogic.Edge;
import nodeLogic.Vertex;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ColorPicker;


public class Main extends Application {
	
	private graphController Graph = new graphController();
	private Vertex startingVertex = null;
	private String button = "Vertex";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Label label = new Label("Drawable Surface");
			Pane drawingArea = new Pane(label);
			ToggleButton addVertex = new ToggleButton("Vertex");
			ToggleButton addEdge = new ToggleButton("Edge");
			ToggleButton paint = new ToggleButton("Paint");
			ToggleButton remove = new ToggleButton("Remove");
			ToggleGroup toggleGroup = new ToggleGroup();
			Scene scene;
			VBox buttonsVBox;
			Insets vBoxPadding;
			GridPane gridPane = new GridPane();
			ColumnConstraints column1 = new ColumnConstraints();
			ColumnConstraints column2 = new ColumnConstraints();
			RowConstraints rowConstraint = new RowConstraints();
			Text n = new Text("n = 0");
			Text m = new Text("m = 0");
			Text k = new Text("k = 0");
			Text v = new Text("v = 0");
			
			// making the buttons a group so only one of them can be pressed at any time
			addVertex.setToggleGroup(toggleGroup);
			addEdge.setToggleGroup(toggleGroup);
			paint.setToggleGroup(toggleGroup);
			remove.setToggleGroup(toggleGroup);
			
			// making the add vertex button be the default clicked
			toggleGroup.selectToggle(addVertex);
			
			// adding the buttons and text to the vertical box.
			buttonsVBox = new VBox(10, n, m, k, v, addVertex, addEdge, paint, remove);
			
			// adding padding to the vertical box
			vBoxPadding = new Insets(0, 0, 0, 20); // Insets(top, right, bottom, left)
			buttonsVBox.setPadding(vBoxPadding);
			
			// adding text to drawing area
			// creating container to set up different areas in the scene
			gridPane.setGridLinesVisible(true);
			gridPane.add(buttonsVBox, 0, 0); // Add drawing area to the top-left cell (adjust as needed)
			gridPane.add(drawingArea, 1, 0); // Add buttons to the adjacent cell
			
			// Creating the areas to be based on scene size for columns
			column1.setPercentWidth(10); // based on percentages so 30 = 30% of the scene size
			column2.setPercentWidth(90);
			gridPane.getColumnConstraints().addAll(column1, column2);
			
			// Creating the areas to be based on scene size for rows
			rowConstraint.setPercentHeight(100);
			gridPane.getRowConstraints().addAll(rowConstraint);
			
			// Adding the gridPane which hold all the other panels.
			scene = new Scene(gridPane, 800, 500);
			
			// Got this outside of the click event so it doesn't need a click to update but rather does it on its own
			toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
			    if (newToggle != null) {
			        // getting what button is selected
			        ToggleButton selectedButton = (ToggleButton) newToggle;
			        button = selectedButton.getText();
			        
			        if(startingVertex != null) {
			        	
			        	vertexDeselected(startingVertex);
			        	startingVertex = null;
			        	
			        }
			        
			    }
			    else {
			    	
			    	button = null;
			    }
			});
			
			// Set the double-click event handler.
			drawingArea.setOnMouseClicked(event -> {
				if(button != null) {
					
					if (event.getClickCount() == 2 && button.equals("Vertex")) {
				        // Double-click detected, draw a new circle at the mouse location
				        double x = event.getX();
				        double y = event.getY();
				        
				        // Creating and adding a vertex and adding to graph controller.
				        Vertex newVertex = new Vertex(x,y, Color.BLACK);
				        
				        Graph.addVertex(newVertex);
				        k.setText("k = " + String.valueOf(Graph.findConnectedComponents()));
				        
				        // drag and drop the vertex only when the vertex button is chosen
				        newVertex.setOnMouseDragged(event2 ->{
				        	if (button.equals("Vertex")) {
				        		
					        	newVertex.setCenterX(event2.getX());
					        	newVertex.setCenterY(event2.getY());
					        	newVertex.vertexMoved();
				        	}
				        	
				        });
				        
				        // adding a new edge
				        newVertex.setOnMouseClicked(edgeEvent -> {
				        	if (button.equals("Vertex") && edgeEvent.getClickCount() != 2) {
				        		// Allows selection of a vertex to show the degree of the current vertex.
				        		vertexSelected(newVertex);
				        		v.setText("v = " + String.valueOf(Graph.getDegree(newVertex)));
				        		if (startingVertex != null && !startingVertex.equals(newVertex)) {
				        			
				        			vertexDeselected(startingVertex);
				        		}
				        		startingVertex = newVertex;
				        	}
				        	else if(button.equals("Edge"))
							{
								// if we don't have anything selected, select this
								if(startingVertex == null)
								{
									startingVertex = newVertex;
									
									vertexSelected(startingVertex);
								}
								else
								{
									Edge newEdge = new Edge(startingVertex, newVertex, Color.BLACK);
									
									newEdge.setOnMouseDragged(event3 -> {
										newEdge.setControlPoint(event3.getX(), event3.getY());
									});
									
									// adding event to edge
									newEdge.setOnMouseClicked(event4 -> {
										
										if(button.equals("Paint")) {
											
											edgeSelected(newEdge);
											
											Stage colorPickerStage = new Stage();
											// Set the default selected color to be the current color of the newVertex
											ColorPicker colorPicker = new ColorPicker((Color) newEdge.getFill());
											
											// Show the color palette selector dialog
											colorPicker.setOnAction(colorEvent -> {
											    Color selectedColor = colorPicker.getValue();
											    
										    	// Set the fill color of the newVertex
										        newEdge.setStroke(selectedColor);
										        edgeDeselected(newEdge);
											});
											
											// When the color picker stage closes
											colorPickerStage.setOnHidden(hiddenEvent -> {
									            
												 edgeDeselected(newEdge);
									        });
											
											    
											// Create a new Stage to show the ColorPicker
											colorPickerStage.setScene(new Scene(colorPicker));
											colorPickerStage.setTitle("Choose Color");
											colorPickerStage.show();
											
											// Close the colorPickerStage automatically when the button changes
							                toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
							                    if (newToggle != null) {
							                        colorPickerStage.hide();
							                    }
							                });
										}
										else if(button.equals("Remove")) {
										
											Graph.removeEdge(newEdge);
											drawingArea.getChildren().remove(newEdge);
											
											// updating the number of edges and components there are
											m.setText("m = " + String.valueOf(Graph.edges.size()));
											k.setText("k = " + String.valueOf(Graph.findConnectedComponents()));
										}
									});
									
									vertexDeselected(startingVertex);
									
									drawingArea.getChildren().add(newEdge);
									
									Graph.edges.add(newEdge);
									
									// Updating the number of edges and the number of components
									m.setText("m = " + String.valueOf(Graph.edges.size()));
									k.setText("k = " + String.valueOf(Graph.findConnectedComponents()));
									startingVertex = null;
								}
							}
							else if(button.equals("Paint")) {
								
								vertexSelected(newVertex);
								
								Stage colorPickerStage = new Stage();
								// Set the default selected color to be the current color of the newVertex
								ColorPicker colorPicker = new ColorPicker((Color) newVertex.getFill());
								
								// Show the color palette selector dialog
								colorPicker.setOnAction(colorEvent -> {
								    Color selectedColor = colorPicker.getValue();
								    
							    	// Set the fill color of the newVertex
							        newVertex.setFill(selectedColor);
							        vertexDeselected(newVertex);
								});
								
								// When the color picker stage closes
								colorPickerStage.setOnHidden(hiddenEvent -> {
						            
									 vertexDeselected(newVertex);
						        });
								
								    
								// Create a new Stage to show the ColorPicker
								colorPickerStage.setScene(new Scene(colorPicker));
								colorPickerStage.setTitle("Choose Color");
								colorPickerStage.show();
								
								// Close the colorPickerStage automatically when the button changes
				                toggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
				                    if (newToggle != null) {
				                        colorPickerStage.hide();
				                    }
				                });
							}
							else if(button.equals("Remove")) {
								ArrayList<Edge> incidentEdges = new ArrayList<>();
								// gets all the incident edges
								incidentEdges = Graph.removeVertex(newVertex);
								
								// Remove the vertex and incident edges from drawing area.
								drawingArea.getChildren().remove(newVertex);
								drawingArea.getChildren().removeAll(incidentEdges);
								
								// Update amount of vertices, edges, and components there are.
								n.setText("n = " + String.valueOf(Graph.vertices.size()));
								m.setText("m = " + String.valueOf(Graph.edges.size()));
								k.setText("k = " + String.valueOf(Graph.findConnectedComponents()));
							}
				        });
				        
				        // Updating the number of vertex in graph
				        n.setText("n = " + String.valueOf(Graph.vertices.size()));
				        drawingArea.getChildren().add(newVertex);
				    }
				}
			});
			
	        // Set up the stage
	        primaryStage.setTitle("Graph Maker");
	        primaryStage.setScene(scene);
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Adds yellow highlight to a vertex when selected
	public void vertexSelected(Vertex currentVertex) {
		
		currentVertex.setStroke(Color.YELLOW);
		currentVertex.setStrokeWidth(5);
		currentVertex.setStrokeType(StrokeType.OUTSIDE);
	}
	
	// Gets rid of highlight when vertex is unselected
	public void vertexDeselected(Vertex currentVertex) {
		
		currentVertex.setStroke(Color.TRANSPARENT);
	}
	
	// same as above for edges
	public void edgeSelected (Edge currentEdge) {
		
		currentEdge.setStroke(Color.YELLOW);
		currentEdge.setStrokeWidth(5);
		currentEdge.setStrokeType(StrokeType.OUTSIDE);
	}
	
	public void edgeDeselected (Edge currentEdge) {
		
		currentEdge.setStroke(currentEdge.getStroke());
	}
}
