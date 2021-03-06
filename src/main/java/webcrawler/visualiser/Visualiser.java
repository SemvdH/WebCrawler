package main.java.webcrawler.visualiser;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.webcrawler.crawler.CrawlThread;
import main.java.webcrawler.crawler.WebCrawler;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Visualiser extends Application {
    private double frameTime = 0;
    private BorderPane pane;
    private ResizableCanvas canvas;
    private ListView<String> log;
    private CrawlThread thread;
    private WebCrawler crawler;

    private int lastLogSize = 0;

    //TODO make listview always scroll to bottom
    //TODO implement visualisation
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Visualiser();
        pane = new BorderPane();
        canvas = new ResizableCanvas(this::draw, pane);
        canvas.setWidth(1600);
        canvas.setHeight(800);
        pane.setCenter(canvas);
        initGUIElements();
        log("debug");
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
        HBox top = new HBox(100);
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
        VBox content = new VBox(5);
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
        Button button = new Button("Run");
        button.setOnAction(e -> {
//            log.getItems().clear();
            thread = new CrawlThread(Integer.parseInt(amountField.getText()), true, parseUrl(urlField.getText()), wordField.getText(), this);
            thread.start();
            this.crawler = thread.getCrawler();
            System.out.println(crawler);
            ObservableList<String> crawlerMessages = FXCollections.observableList(crawler.messages);
            this.log.setItems(crawlerMessages);
        });

        top.getChildren().add(button);
        log = new ListView<>();
        log.setMinWidth(1100);
        top.setAlignment(Pos.CENTER_LEFT);
        top.getChildren().add(log);


    }

    private String parseUrl(String text) {
        if (!text.startsWith("http://")) {
            text = "http://" + text;
        }
        if (text.startsWith("https")) {
            text = text.replace("https", "http");
        }
        System.out.println("parsed to " + text);
        return text;
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

//        graphics.setColor(Color.red);
//        graphics.draw(new Rectangle2D.Double(10, 10, 500, 500));
    }


    private void updateFrame() {

    }

    public void update(double deltaTime) {
        this.frameTime += deltaTime;

        if (this.frameTime > 1d / 60d) {
            updateFrame();
            this.frameTime = 0d;
        }
        if (this.log.getItems().isEmpty()) {
            this.log.getItems().add("test");
        }
        this.log.refresh();

//        if (thread != null && thread.isAlive()) {
//            if (crawler == null) {
//                crawler = thread.getCrawler();
//            }
//            if (crawler != null) {
//                if (!this.crawler.isDone()) {
//
//                    List<String> msgs = new ArrayList<>(crawler.getMessages());
//                    System.out.println(msgs);
////                if (!msgs.isEmpty()) {
////                    System.out.println("adding messages:\n" + msgs);
//                    log.getItems().addAll(msgs);
//                    thread.getCrawler().clearMessages();
        if (!log.getItems().isEmpty())
            log.scrollTo(log.getItems().size() - 1);
////                    lastLogSize = log.getItems().size();
//
////                }
//                }
//            }
//
//        }
    }

    public void log(String item) {
        try {
            this.log.getItems().add(item);
        } catch (Exception e) {
            System.out.println("exception caught");
        }
    }
}
