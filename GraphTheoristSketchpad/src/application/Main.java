package application;
	
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nodeLogic.Edge;
import nodeLogic.Vertex;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


public class Main extends Application {
	
	private graphController Graph = new graphController();
	// to store the coordinates of the first click for edge drawing and checking if its the first time I clicked
	private double startX, startY;
	private boolean isFirstClick = true;
	
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
			Text currentButton = new Text("Adding \n Vertex");
			
			// making the buttons a group so only one of them can be pressed at any time
			addVertex.setToggleGroup(toggleGroup);
			addEdge.setToggleGroup(toggleGroup);
			paint.setToggleGroup(toggleGroup);
			remove.setToggleGroup(toggleGroup);
			
			// making the add vertex button be the default clicked
			toggleGroup.selectToggle(addVertex);
			
			// adding the buttons and text to the vertical box.
			buttonsVBox = new VBox(10, currentButton,n, m, addVertex, addEdge, paint, remove);
			
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
			
			// Set the double-click event handler
			drawingArea.setOnMouseClicked(event -> {
				String button = null;
				
				if (toggleGroup.getSelectedToggle() != null) {
					// getting what button is selected
		            ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
					button = selectedButton.getText();
				}
				if (event.getClickCount() == 2 && button.equals("Vertex")) {
			        // Double-click detected, draw a new circle at the mouse location
			        double x = event.getX();
			        double y = event.getY();
			        
			        // This is to reset in case you clicked once in the edge drawing and then left it.
			        isFirstClick = true;
			        
			        // Creating and adding a vertex and adding to graph controller.
			        Vertex newVertex = new Vertex(x,y, Color.BLACK);
			        
			        newVertex.setOnMouseDragged(event2 ->{
			        	
			        	newVertex.setCenterX(event2.getX());
			        	newVertex.setCenterY(event2.getY());
			        });
			        
			        Graph.addVertex(newVertex);

			        n.setText("n= " + String.valueOf(Graph.vertices.size()));
			        drawingArea.getChildren().add(newVertex);
			    }
				else if(button.equals("Edge")) {
					if (isFirstClick) {
		                // First click
		                startX = event.getX();
		                startY = event.getY();
		                isFirstClick = false;
		            } else {
		                // Second click
		                double endX = event.getX();
		                double endY = event.getY();

		                /* Create a line need to add end point to the edge some way
		                 * we have to figure out how to select a vertex and then 
		                 * adding another. Figuring out the center of each and drawing the line
		                 * to those coordinates. We need to change the line to be and edge instead.
		                 */
		                Line line = new Line(startX, startY, endX, endY);
		                line.setStroke(Color.BLACK);

		                // Add the line to the pane
		                drawingArea.getChildren().add(line);
		                
		                // reset for the next line drawing
		                isFirstClick = true;
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
	
}
