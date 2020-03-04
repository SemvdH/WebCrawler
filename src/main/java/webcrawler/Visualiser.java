package main.java.webcrawler;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Visualiser extends Application {
    private Stage stage;
    private double frameTime = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = stage;
        Canvas canvas = new Canvas(1920, 1080);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);
        stage.setScene(new Scene(new Group(canvas)));
        stage.setTitle("Hello Animation");
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
