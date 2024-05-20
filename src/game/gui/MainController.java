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
import javafx.scene.image.Image;
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
    @FXML
    public Rectangle box;
    @FXML
    public ImageView titan1;

    @FXML
    public ImageView titan2;

    @FXML
    public ImageView titan3;

    @FXML
    public ImageView titan4;

    @FXML
    public Label titansp1;

    @FXML
    public Label titansp2;

    @FXML
    public Label titansp3;

    @FXML
    public Label titansp4;

    @FXML
    public Label titantxt1;

    @FXML
    public Label titantxt2;

    @FXML
    public Label titantxt3;

    @FXML
    public Label titantxt4;
    @FXML
    public Label titanInfo;
    public static double volume = 0.1;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("Title_Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setTitle("Attack On Titan JavaFX");
        stage.getIcons().add(new Image("file:src/game/gui/assets/AOT ICON.png"));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
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
            slider.setValue(volume);
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
        box.setVisible(true);
        titan1.setVisible(true);
        titan2.setVisible(true);
        titan3.setVisible(true);
        titan4.setVisible(true);
        titantxt1.setVisible(true);
        titantxt2.setVisible(true);
        titantxt3.setVisible(true);
        titantxt4.setVisible(true);
        titansp1.setVisible(true);
        titansp2.setVisible(true);
        titansp3.setVisible(true);
        titansp4.setVisible(true);
        titanInfo.setVisible(true);

    }
    public void Transition4(MouseEvent event) {
        back.setVisible(false);
        slider.setVisible(false);
        slidertxt.setVisible(false);
        settings.setVisible(true);
        start.setVisible(true);
        logo.setVisible(true);
        box.setVisible(false);
        titan1.setVisible(false);
        titan2.setVisible(false);
        titan3.setVisible(false);
        titan4.setVisible(false);
        titantxt1.setVisible(false);
        titantxt2.setVisible(false);
        titantxt3.setVisible(false);
        titantxt4.setVisible(false);
        titansp1.setVisible(false);
        titansp2.setVisible(false);
        titansp3.setVisible(false);
        titansp4.setVisible(false);
        titanInfo.setVisible(false);
    }

    public void switchToEasy(MouseEvent event) throws IOException {
        Stage stage = (Stage)(easy.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("Easy.fxml"));
        Scene scene = new Scene(root);
        this.mediaPlayer.stop();
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public void switchToHard(MouseEvent event) throws IOException {
        Stage stage = (Stage)(easy.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("Hard.fxml"));
        Scene scene = new Scene(root);
        this.mediaPlayer.stop();
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setScene(scene);
        stage.setResizable(false);
    }

}