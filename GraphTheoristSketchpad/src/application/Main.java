package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nodeLogic.Edge;
import nodeLogic.Vertex;


public class Main extends Application {
	
	private graphController Graph = new graphController();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {

			// Test code
			
			Graph.addVertex(400, 200);
			Graph.setDefaultColor(Color.RED);
			Graph.addVertex(200, 400);
			Graph.addEdge(Graph.vertices.get(0), Graph.vertices.get(1));
			// --0--0239120-938-921930-9128
			Canvas canvas = new Canvas(800, 500); 
			GraphicsContext graphics = canvas.getGraphicsContext2D();
			Button button = new Button("dfhuiashdouashjdoiahsioudha");
			
			
			GridPane test2 = new GridPane();
			StackPane test = new StackPane();
			test2.getChildren().addAll(generateGraphCanvas(this.Graph), button);

			
			Scene scene = new Scene(test2,800,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			
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
