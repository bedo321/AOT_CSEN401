package game.gui;

import game.engine.Battle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static game.gui.MainController.volume;

public class EasyController implements Initializable {
    AudioClip mediaPlayer;
    Battle battle;
    @FXML
    public Label phase;

    @FXML
    public Label resources;

    @FXML
    public Label score;

    @FXML
    public Label turn;

    @FXML
    public ImageView background;

    @FXML
    public ImageView wall1;

    @FXML
    public ImageView wall2;

    @FXML
    public ImageView wall3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBGMusic("Music_Scene1.mp3");
        try {
            battle = new Battle(1,0,10,3,250);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resources.setText(String.valueOf(battle.getResourcesGathered()));
        score.setText(String.valueOf(battle.getScore()));
        turn.setText(String.valueOf(battle.getNumberOfTurns()));
        phase.setText(String.valueOf(battle.getBattlePhase()));
    }
    private void playBGMusic(String fileName) {
        Media media = new Media(new File("src/game/gui/assets/" + fileName).toURI().toString());
        mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }
}
