package main.java.webcrawler.visualiser;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Visualiser extends Application {
    private double frameTime = 0;
    private BorderPane pane;
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, pane);
        canvas.setWidth(1600);
        canvas.setHeight(800);
        pane.setCenter(canvas);
        initGUIElements();
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Webcrawler results");
        primaryStage.show();

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
    }

    private void initGUIElements() {
        HBox top = new HBox(200);
        top.getStyleClass().add("content");
        top.setPadding(new Insets(10, 10, 10, 10));
        top.setPrefWidth(canvas.getWidth());
        top.setPrefHeight(200);
        pane.setTop(top);
        pane.getStylesheets().add(getClass().getResource("../../../resources/stylesheets/visualiser.css").toExternalForm());

        // start url, word to search, amount of pages, debug (?)
        TextField urlField = new TextField();
        urlField.setPromptText("Enter the starting url");
        TextField wordField = new TextField();
        wordField.setPromptText("Enter the word to search for");
        TextField amountField = new TextField();
        makeNumeric(amountField);
        amountField.setPromptText("Maximum amount of pages the crawler should visit..");
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMinWidth(400);
        content.setPadding(new Insets(0, 0, 0, 100));
        content.getChildren().addAll(
                new Label("Starting url:"),
                urlField,
                new Label("Word to search for:"),
                wordField,
                new Label("Maximum amount of pages:"),
                amountField);
        top.getChildren().add(content);

        ListView<String> debugWindow = new ListView<>();
        debugWindow.setMinWidth(1100);
        top.setAlignment(Pos.CENTER_LEFT);
        top.getChildren().add(debugWindow);


    }

    private void makeNumeric(TextField textField) {
        // force the field to be numeric only
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(new Color(43, 43, 46));
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setColor(Color.red);
        graphics.draw(new Rectangle2D.Double(10, 10, 500, 500));
    }


    private void updateFrame() {

    }

    public void update(double deltaTime) {
        this.frameTime += deltaTime;

        if (this.frameTime > 1d / 60d) {
            updateFrame();
            this.frameTime = 0d;
        }
    }
}
