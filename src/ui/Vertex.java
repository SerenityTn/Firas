package ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vertex extends Circle{
	public static Vertex srcVertex = null;
	public static Vertex destVertex = null;

	public Vertex(double x, double y, double radius){
		super(x, y, radius);
		clickListener();
	}

	public void clickListener(){
		this.setOnMousePressed(event -> {
			if (event.isSecondaryButtonDown()) {
				Group group = (Group)this.getParent();
				group.getChildren().remove(this);
				event.consume();
			}else{
				if(srcVertex == null){
					System.out.println("source is selected");
					srcVertex = this;
					this.setFill(Color.RED);
				}else if(destVertex == null){
					System.out.println("destination is selected");
					destVertex = this;
					this.setFill(Color.RED);
				}else{
					clearSelection();
					srcVertex = this;
					this.setFill(Color.RED);
				}
				event.consume();
			}
		});
	}

	public static void clearSelection(){
		if(srcVertex != null){
			srcVertex.setFill(Color.BLACK);
			srcVertex= null;
		}
		if(destVertex!= null){
			destVertex.setFill(Color.BLACK);
			destVertex = null;
		}
	}
}