package main.java.webcrawler.visualiser;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Visualiser extends Application {
    private double frameTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        ResizableCanvas canvas = new ResizableCanvas(this::draw, pane);
        canvas.setWidth(1600);
        canvas.setHeight(800);
        pane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Webcrawler results");
        primaryStage.show();

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
    }

    public void draw(FXGraphics2D graphics) {

    }

    public void update(double deltaTime) {
        this.frameTime += deltaTime;

        if (this.frameTime > 1d / 60d) {
            updateFrame();
            this.frameTime = 0d;
        }
    }

    private void updateFrame() {

    }
}
