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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nodeLogic.Edge;
import nodeLogic.Vertex;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	
	private graphController Graph = new graphController();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {

			// Test code
			Label label = new Label("Drawable Surface");
			Pane drawingArea = new Pane(label);
			Button addRemoveVertice = new Button("Vertice");
			Button addRemoveEdge = new Button("Edge");
			Button paint = new Button ("Paint");
			Scene scene;
			VBox buttonsVBox;
			Insets vBoxPadding;
			GridPane gridPane = new GridPane();
			ColumnConstraints column1 = new ColumnConstraints();
			ColumnConstraints column2 = new ColumnConstraints();
			RowConstraints rowConstraint = new RowConstraints();
			Text n = new Text("n = 0");
			Text m = new Text("m = 0");
			
			// adding the buttons and text to the vertical box.
			buttonsVBox = new VBox(10, n, m, addRemoveVertice, addRemoveEdge, paint);
			
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
			    if (event.getClickCount() == 2) {
			        // Double-click detected, draw a new circle at the mouse location
			        double x = event.getX();
			        double y = event.getY();
			        
			        // Add to graph controller
			        Graph.addVertex(x, y);
			        
			        // Using circles to create a new circle
			        Circle newCircle = new Circle(20, Color.BLACK);
			        newCircle.setCenterX(x);
			        newCircle.setCenterY(y);

			        n.setText("n= " + String.valueOf(Graph.vertices.size()));
			        drawingArea.getChildren().add(newCircle);
			    }
			});

		        // Set up the stage
		        primaryStage.setTitle("Graph Maker");
		        primaryStage.setScene(scene);
		        primaryStage.show();
			
			
			// -------------------------------------------------------------------
			/*Graph.addVertex(400, 200);
			Graph.setDefaultColor(Color.RED);
			Graph.addVertex(200, 400);
			Graph.addEdge(Graph.vertices.get(0), Graph.vertices.get(1));
			
			GridPane test2 = new GridPane();
			test2.getChildren().addAll(generateGraphCanvas(this.Graph), button);
			
			Canvas canvas = new Canvas(800, 500); 
			canvas.getGraphicsContext2D();
			Button button = new Button("dfhuiashdouashjdoiahsioudha");
			
			Scene scene = new Scene(test2,800,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			*/
			// --0--0239120-938-921930-9128
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	private Canvas generateGraphCanvas(graphController graph)
	{
		Canvas canvas = new Canvas(800, 500); 
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.setStroke(Color.RED);
		graphics.setLineWidth(2);
		int index = 0;
		for(Vertex vertex : graph.vertices)
		{
			graphics.setFill(vertex.getColor());
			graphics.fillOval(vertex.x - 15, vertex.y - 15, 30, 30);
			graphics.fillText("v" + index, vertex.x + 10, vertex.y + 20);
			index++;
			
		}
		
		for(Edge edge : graph.edges)
		{
			graphics.setStroke(edge.getColor());
			graphics.strokeLine(edge.getEndpoints()[0].x, edge.getEndpoints()[0].y, edge.getEndpoints()[1].x, edge.getEndpoints()[1].y);
			
		}
		return canvas;
	}
}
