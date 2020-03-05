package main.java.webcrawler.visualiser;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class RunSkin extends ButtonSkin {
    public RunSkin(Button button) {
        super(button);
        ScaleTransition transition = new ScaleTransition(Duration.millis(200));
        
    }
}
