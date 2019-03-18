package sample;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {

    // x und y Werte unserer Main Scene
    double mainSceneX, mainSceneY;
    // Die Liste unserer Nodes (hier Vierecke)
    private ArrayList<Shape> nodes;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300);

        // Viereck das Bewegt wird
        Rectangle rectToMove = new Rectangle(50, 50, 50, 50);
        // Viereck das unser Ziel ist
        Rectangle rectTarget = new Rectangle(150, 150, 100, 100);

        // hinzufügen unserer Vierecke (Nodes)
        nodes = new ArrayList<Shape>();
        nodes.add(rectToMove);
        nodes.add(rectTarget);

        // hier setzen wir die Maus, sodass sie
        // eine Hand wird, wenn wir drüber fahren
        rectToMove.setCursor(Cursor.HAND);

        // das wird aufgerufen, wenn unsere Maus auf
        // das Viereck drückt
        rectToMove.setOnMousePressed((t) -> {
            mainSceneX = t.getSceneX();
            mainSceneY = t.getSceneY();

            Rectangle r = (Rectangle) (t.getSource());
            r.toFront();
        });

        // Wird aufgerufen, wenn unser Viereck sich bewegt
        // also sehr häufig
        rectToMove.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - mainSceneX;
            double offsetY = t.getSceneY() - mainSceneY;

            Rectangle r = (Rectangle) (t.getSource());

            r.setX(r.getX() + offsetX);
            r.setY(r.getY() + offsetY);

            mainSceneX = t.getSceneX();
            mainSceneY = t.getSceneY();
            checkBounds(rectTarget);
        });

        // wird aufgerufen, wenn man die Maus loslässt
        rectToMove.setOnMouseReleased((t) -> {
            rectToMove.setFill(Color.BLACK);
        });

        root.getChildren().addAll(rectToMove,rectTarget);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Diese Methode sucht nach einer Kollision. Danke an Jewelsea von StackOverflow
    // Diese Methode wird jedes mal aufgerufen wenn man unser kleines Viereck
    // gedraggt wird.
    private void checkBounds(Shape block) {
        boolean collisionDetected = false;
        for (Shape static_bloc : nodes) {
            if (static_bloc != block) {
                static_bloc.setFill(Color.YELLOW);

                if (block.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
                    collisionDetected = true;
                }
            }
        }

        if (collisionDetected) {
            block.setFill(Color.GREEN);
        } else {
            block.setFill(Color.BLUE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}