package game.gui;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Stack;

import static game.gui.MainController.volume;

public class EasyController implements Initializable {
    AudioClip mediaPlayer;
    Battle battle;
    Lane lane1;
    Lane lane2;
    Lane lane3;
    int weaponCode;
    PriorityQueue<Lane> lanes;
    @FXML
    public Button piercingCannon;

    @FXML
    public Button sniperCannon;

    @FXML
    public Button volleySpreadCannon;

    @FXML
    public Button wallTrap;
    @FXML
    public Button buy1;

    @FXML
    public Button buy2;

    @FXML
    public Button buy3;
    @FXML
    public Button cancel;
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

    @FXML
    public Button passturn;
    @FXML
    public Label lane1lost;

    @FXML
    public Label lane2lost;

    @FXML
    public Label lane3lost;

    @FXML
    public Label lane1dangerlevel;

    @FXML
    public Label lane2dangerlevel;

    @FXML
    public Label lane3dangerlevel;
    @FXML
    public Rectangle weaponwindow;
    @FXML
    public Button buyweapon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBGMusic("Music_Scene1.mp3");
        try {
            battle = new Battle(1, 0, 10, 3, 250);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resources.setText(String.valueOf(battle.getResourcesGathered()));
        score.setText(String.valueOf(battle.getScore()));
        turn.setText(String.valueOf(battle.getNumberOfTurns()));
        phase.setText(String.valueOf(battle.getBattlePhase()));
        lanes = battle.getLanes();
        lane1 = battle.getOriginalLanes().get(0);
        lane2 = battle.getOriginalLanes().get(1);
        lane3 = battle.getOriginalLanes().get(2);
    }

    private void playBGMusic(String fileName) {
        Media media = new Media(new File("src/game/gui/assets/" + fileName).toURI().toString());
        mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    @FXML
    public void passTurn(ActionEvent event) {
        battle.passTurn();
        viewTurn();
    }
    public void viewTurn() {
        if (lane1.isLaneLost())
            lane1lost.setVisible(true);
        else {
            lane1dangerlevel.setText("Danger Level: " + lane1.getDangerLevel());
            if (lane1.getDangerLevel() >= 15)
                lane1dangerlevel.setStyle("-fx-text-fill: #edffa3");
            if (lane1.getDangerLevel() >= 30)
                lane1dangerlevel.setStyle("-fx-text-fill: #ffa3a3");
        }
        if (lane2.isLaneLost())
            lane2lost.setVisible(true);
        else {
            lane2dangerlevel.setText("Danger Level: " + lane2.getDangerLevel());
            if (lane2.getDangerLevel() >= 15)
                lane2dangerlevel.setStyle("-fx-text-fill: #edffa3");
            if (lane2.getDangerLevel() >= 30)
                lane2dangerlevel.setStyle("-fx-text-fill: #ffa3a3");
        }
        if (lane3.isLaneLost())
            lane3lost.setVisible(true);
        else {
            lane3dangerlevel.setText("Danger Level: " + lane3.getDangerLevel());
            if (lane3.getDangerLevel() >= 15)
                lane3dangerlevel.setStyle("-fx-text-fill: #edffa3");
            if (lane3.getDangerLevel() >= 30)
                lane3dangerlevel.setStyle("-fx-text-fill: #ffa3a3");
        }
        if (battle.isGameOver()) {
            System.out.println("sure");
            return;
        }
        resources.setText(String.valueOf(battle.getResourcesGathered()));
        score.setText(String.valueOf(battle.getScore()));
        turn.setText(String.valueOf(battle.getNumberOfTurns()));
        if (battle.getBattlePhase().equals(BattlePhase.INTENSE) && phase.getText().equals("EARLY")) {
            mediaPlayer.stop();
            playBGMusic("Music_Scene2.mp3");
        }
        if (battle.getBattlePhase().equals(BattlePhase.GRUMBLING) && phase.getText().equals("INTENSE")) {
            mediaPlayer.stop();
            playBGMusic("Music_Scene3.mp3");
        }
        phase.setText(String.valueOf(battle.getBattlePhase()));
    }
    @FXML
    public void wallSpreadCannon(ActionEvent event) {
        wallTrap.setVisible(false);
        volleySpreadCannon.setVisible(false);
        piercingCannon.setVisible(false);
        sniperCannon.setVisible(false);
        buy1.setVisible(true);
        buy2.setVisible(true);
        buy3.setVisible(true);
        weaponCode = 3;
    }
    @FXML
    public void longRangeSpear(ActionEvent event) {
        wallTrap.setVisible(false);
        volleySpreadCannon.setVisible(false);
        piercingCannon.setVisible(false);
        sniperCannon.setVisible(false);
        buy1.setVisible(true);
        buy2.setVisible(true);
        buy3.setVisible(true);
        weaponCode = 2;
    }
    @FXML
    public void cancel(ActionEvent event) {
        wallTrap.setVisible(false);
        volleySpreadCannon.setVisible(false);
        piercingCannon.setVisible(false);
        sniperCannon.setVisible(false);
        buy1.setVisible(false);
        buy2.setVisible(false);
        buy3.setVisible(false);
        weaponwindow.setVisible(false);
        cancel.setVisible(false);
        buyweapon.setDisable(false);
        passturn.setDisable(false);
    }
    @FXML
    public void buyWeapon(ActionEvent event) {
        weaponwindow.setVisible(true);
        wallTrap.setVisible(true);
        volleySpreadCannon.setVisible(true);
        piercingCannon.setVisible(true);
        sniperCannon.setVisible(true);
        cancel.setVisible(true);
        buyweapon.setDisable(true);
        passturn.setDisable(true);
    }
    @FXML
    public void ProximityTrap(ActionEvent event) {
        wallTrap.setVisible(false);
        volleySpreadCannon.setVisible(false);
        piercingCannon.setVisible(false);
        sniperCannon.setVisible(false);
        buy1.setVisible(true);
        buy2.setVisible(true);
        buy3.setVisible(true);
        weaponCode = 4;
    }

    @FXML
    public void antiTitanShell(ActionEvent event) {
        wallTrap.setVisible(false);
        volleySpreadCannon.setVisible(false);
        piercingCannon.setVisible(false);
        sniperCannon.setVisible(false);
        buy1.setVisible(true);
        buy2.setVisible(true);
        buy3.setVisible(true);
        weaponCode = 1;
    }

    @FXML
    public void buyLane1(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        battle.purchaseWeapon(weaponCode,lane1);
        viewTurn();
        buy1.setVisible(false);
        buy2.setVisible(false);
        buy3.setVisible(false);
        cancel.setVisible(false);
        weaponwindow.setVisible(false);
        buyweapon.setDisable(false);
        passturn.setDisable(false);
    }

    @FXML
    public void buyLane2(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        battle.purchaseWeapon(weaponCode,lane2);
        viewTurn();
        buy1.setVisible(false);
        buy2.setVisible(false);
        buy3.setVisible(false);
        cancel.setVisible(false);
        weaponwindow.setVisible(false);
        buyweapon.setDisable(false);
        passturn.setDisable(false);
    }

    @FXML
    public void buyLane3(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        battle.purchaseWeapon(weaponCode,lane3);
        viewTurn();
        buy1.setVisible(false);
        buy2.setVisible(false);
        buy3.setVisible(false);
        cancel.setVisible(false);
        weaponwindow.setVisible(false);
        buyweapon.setDisable(false);
        passturn.setDisable(false);
    }
}
