package game.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class MainController extends Application implements Initializable {
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer2;
    @FXML
    public ImageView background;
    @FXML
    public ImageView logo;
    @FXML
    public Button settings;
    @FXML
    public Button start;
    @FXML
    public Button understood;
    @FXML
    public Label instructions;
    @FXML
    public Button easy;
    @FXML
    public Button hard;
    @FXML
    public Stage stage;
    @FXML
    public Slider slider;
    @FXML
    public Label slidertxt;
    @FXML
    public Button back;
    @FXML
    public Rectangle rectangle;
    @FXML
    public Rectangle error1;
    @FXML
    public Label error2;
    @FXML
    public Label error3;
    public static double volume = 0.1;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("Title_Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setTitle("Attack On Titan JavaFX");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
    private void playBGMusic(String fileName){
        File file = new File("src/game/gui/assets/" + fileName);
        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        }
        else {
            error1.setVisible(true);
            error2.setVisible(true);
            error3.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            playBGMusic("BGMusic.mp3");
    }
    @FXML
    public void OpacityOff(MouseEvent event) {
        settings.setOpacity(85);
        start.setOpacity(85);
        understood.setOpacity(85);
    }

    @FXML
    public void OpacityOn(MouseEvent event) {
        settings.setOpacity(100);
        start.setOpacity(100);
        understood.setOpacity(100);

    }
    @FXML
    public void changeSound(MouseEvent event) {
        mediaPlayer.setVolume(slider.getValue());
        volume = slider.getValue();
    }
    @FXML
    public void Transition(MouseEvent event) {
        try {
            if (mediaPlayer.getVolume() > 0.1)
                mediaPlayer.setVolume(0.1);
            Media media = new Media(new File("src/game/gui/assets/" + "TTS.mp3").toURI().toString());
            mediaPlayer2 = new MediaPlayer(media);
            mediaPlayer2.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        settings.setVisible(false);
        start.setVisible(false);
        logo.setVisible(false);
        rectangle.setVisible(true);
        instructions.setVisible(true);
        understood.setVisible(true);
    }

    public void Transition2(MouseEvent event) {
        mediaPlayer2.stop();
        instructions.setVisible(false);
        understood.setVisible(false);
        rectangle.setVisible(false);
        easy.setVisible(true);
        hard.setVisible(true);
    }
    public void Transition3(MouseEvent event) {
        settings.setVisible(false);
        start.setVisible(false);
        logo.setVisible(false);
        back.setVisible(true);
        slider.setVisible(true);
        slidertxt.setVisible(true);
    }
    public void Transition4(MouseEvent event) {
        back.setVisible(false);
        slider.setVisible(false);
        slidertxt.setVisible(false);
        settings.setVisible(true);
        start.setVisible(true);
        logo.setVisible(true);
    }

    public void switchToEasy(MouseEvent event) throws IOException {
        Stage stage = (Stage)(easy.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("Easy.fxml"));
        Scene scene = new Scene(root);
        this.mediaPlayer.stop();
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setScene(scene);
    }

}