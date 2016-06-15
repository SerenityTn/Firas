package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.Vertex;

public class Main extends Application {
	Group root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Graphics in JavaFX");
		root = new Group();
		Canvas canvas = new Canvas(650, 600);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				double x = event.getSceneX();
				double y = event.getSceneY();
				System.out.println(x + "  " + y);
				Vertex v = new Vertex(x, y, 20.0f);
				addVertex(v);
				event.consume();
			}else{
				Vertex.clearSelection();
			}
		});

	}

	public void addVertex(Vertex v) {
		System.out.println(v + " | " + root);
		root.getChildren().add(v);
	}
}
