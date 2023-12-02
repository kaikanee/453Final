package application;
	
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nodeLogic.Edge;
import nodeLogic.Vertex;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


public class Main extends Application {
	
	private graphController Graph = new graphController();
	private Vertex startingVertex = null;
	private String button = "Vertex";
	
	@Override
	public void start(Stage primaryStage) {
		try {

			// Test code
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
			
			// making the buttons a group so only one of them can be pressed at any time
			addVertex.setToggleGroup(toggleGroup);
			addEdge.setToggleGroup(toggleGroup);
			paint.setToggleGroup(toggleGroup);
			remove.setToggleGroup(toggleGroup);
			
			// making the add vertex button be the default clicked
			toggleGroup.selectToggle(addVertex);
			
			// adding the buttons and text to the vertical box.
			buttonsVBox = new VBox(10, n, m, k, addVertex, addEdge, paint, remove);
			
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
			    }
			});
			
			// Set the double-click event handler
			drawingArea.setOnMouseClicked(event -> {
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
						if(button.equals("Edge"))
						{
							// this only kind of works idk
							// if we dont have anything selected, select this
							if(startingVertex == null)
							{
								startingVertex = newVertex;
								newVertex.setFill(Color.DARKRED);
							}
							else //if(startEdge != newVertex)
							{
								Edge newEdge = new Edge(startingVertex, newVertex, Color.BLACK);
								
								newEdge.setOnMouseDragged(event3 -> {
									newEdge.setControlPoint(event3.getX(), event3.getY());
								});
								
								drawingArea.getChildren().add(newEdge);
								newVertex.setFill(Color.BLUE);
								Graph.edges.add(newEdge);
								m.setText("m = " + String.valueOf(Graph.edges.size()));
								k.setText("k = " + String.valueOf(Graph.findConnectedComponents()));
								startingVertex = null;
							}
						}
			        });

			        n.setText("n = " + String.valueOf(Graph.vertices.size()));
			        drawingArea.getChildren().add(newVertex);
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
	
}
