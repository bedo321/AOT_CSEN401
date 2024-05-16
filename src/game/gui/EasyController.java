package game.gui;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static game.gui.MainController.volume;

public class EasyController implements Initializable {
    AudioClip mediaPlayer;
    Battle battle;
    Lane lane1;
    Lane lane2;
    Lane lane3;
    int weaponCode;
    double wallBaseHealth;
    int currentNumberOfTitans;
    Lane currentLane;
    PriorityQueue<Lane> lanes;
    ArrayList<Lane> originalLanes;
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

    @FXML
    public ProgressBar bar1;

    @FXML
    public ProgressBar bar2;

    @FXML
    public ProgressBar bar3;

    @FXML
    public Label hp1;

    @FXML
    public Label hp2;

    @FXML
    public Label hp3;

    @FXML
    public Label notEnoughResources;

    @FXML
    public Label invalidLane;

    @FXML
    public GridPane laneGrid;

    public Lane[] allLanes;
    public Label[] allDangerLevels;
    public Label[] allLostLabels;
    public Label[] allHP;
    public ProgressBar[] allHPBars;
    public Button[] weaponShopButtons;
    public Button[] weaponShopLaneButtons;
    public HashMap<Titan, Object[]> titansOnScreen = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBGMusic("Music_Scene1.mp3");
        try {
            battle = new Battle(1, 0, 100, 3, 250);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resources.setText(String.valueOf(battle.getResourcesGathered()));
        score.setText(String.valueOf(battle.getScore()));
        turn.setText(String.valueOf(battle.getNumberOfTurns()));
        phase.setText(String.valueOf(battle.getBattlePhase()));
        originalLanes = battle.getOriginalLanes();
        lanes = battle.getLanes();
        lane1 = originalLanes.get(0);
        lane2 = originalLanes.get(1);
        lane3 = originalLanes.get(2);
        wallBaseHealth = lane1.getLaneWall().getBaseHealth();
        weaponShopButtons = new Button[] {piercingCannon, wallTrap, volleySpreadCannon, sniperCannon};
        setToEasy();
        for (ProgressBar p : allHPBars) {
            p.setStyle("-fx-accent: #a3ffac");
        }
        viewTurn();
    }

    public void setToEasy() {
        allLanes = new Lane[] {lane1, lane2, lane3};
        allDangerLevels = new Label[] {lane1dangerlevel, lane2dangerlevel, lane3dangerlevel};
        allLostLabels = new Label[] {lane1lost, lane2lost, lane3lost};
        allHP = new Label[] {hp1, hp2, hp3};
        allHPBars = new ProgressBar[] {bar1, bar2, bar3};
        wallBaseHealth = lane1.getLaneWall().getBaseHealth();
        weaponShopLaneButtons = new Button[] {buy1, buy2, buy3};
    }

    private void playBGMusic(String fileName) {
        File file = new File("src/game/gui/assets/" + fileName);
        Media media = new Media(file.toURI().toString());
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
        showNewTitans();
        moveTitansOnScreen();
        currentLane = lanes.peek();
        currentNumberOfTitans = battle.getNumberOfTitansPerTurn();
        adjustHealth();
        for (int i = 0; i < allLanes.length; i++) {
            if (allLanes[i].isLaneLost()) {
                allLostLabels[i].setVisible(true);
                continue;
            }
            allDangerLevels[i].setText("Danger Level: " + allLanes[i].getDangerLevel());
            if (allLanes[i].getDangerLevel() >= 15) {
                allDangerLevels[i].setStyle("-fx-text-fill: #edffa3");
            }
            if (allLanes[i].getDangerLevel() >= 30) {
                allDangerLevels[i].setStyle("-fx-text-fill: #ffa3a3");
            }
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
        while (currentNumberOfTitans > battle.getApproachingTitans().size()) {
            battle.refillApproachingTitans();
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        performCancel();
    }

    public void performCancel() {
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(false);
        weaponwindow.setVisible(false);
        cancel.setVisible(false);
        buyweapon.setDisable(false);
        passturn.setDisable(false);
        notEnoughResources.setVisible(false);
        invalidLane.setVisible(false);
        viewTurn();
        weaponCode = 0;
    }

    @FXML
    public void buyWeapon(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        setWeaponButtonVisibility(true);
        buyweapon.setDisable(true);
        passturn.setDisable(true);
        cancel.setVisible(true);
        weaponwindow.setVisible(true);
    }

    @FXML
    public void antiTitanShell(ActionEvent event) {
        weaponCode = 1;
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(true);
    }

    @FXML
    public void longRangeSpear(ActionEvent event) {
        weaponCode = 2;
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(true);
    }

    @FXML
    public void wallSpreadCannon(ActionEvent event) {
        weaponCode = 3;
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(true);
    }

    @FXML
    public void ProximityTrap(ActionEvent event) {
        weaponCode = 4;
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(true);
    }

    @FXML
    public void buyLane1(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane1);
        }
    }

    @FXML
    public void buyLane2(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane2);
        }
    }

    @FXML
    public void buyLane3(ActionEvent event) throws InvalidLaneException, InsufficientResourcesException {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane3);
        }
    }

    public void completePurchase(int code, Lane lane) throws InvalidLaneException, InsufficientResourcesException {
        try {
            battle.purchaseWeapon(code, lane);
            performCancel();
            weaponCode = 0;
        } catch (InsufficientResourcesException e) {
            for (Button b : weaponShopLaneButtons) {
                b.setVisible(false);
            }
            notEnoughResources.setVisible(true);
            invalidLane.setVisible(false);
            setWeaponButtonVisibility(true);
            setLaneButtonVisibility(false);
        } catch (InvalidLaneException e) {
            invalidLane.setVisible(true);
        }
    }

    public void setWeaponButtonVisibility(boolean visibility) {
        for (Button b : weaponShopButtons) {
            b.setVisible(visibility);
        }
    }

    public void setLaneButtonVisibility(boolean visibility) {
        for (Button b : weaponShopLaneButtons) {
            b.setVisible(visibility);
        }
    }

    public void adjustHealth() {
        for (int i = 0; i < allLanes.length; i++) {
            allHP[i].setText(String.valueOf(allLanes[i].getLaneWall().getCurrentHealth()));
            allHPBars[i].setProgress((double) Integer.parseInt(allHP[i].getText()) / wallBaseHealth);
            if (Integer.parseInt(allHP[i].getText()) < 3000) {
                allHPBars[i].setStyle("-fx-accent: #f66363");
            } else if (Integer.parseInt(allHP[i].getText()) < 7000) {
                allHPBars[i].setStyle("-fx-accent: #edffa3");
            }
        }
    }

    public void showNewTitans() {
        for (int i = 0; i < allLanes.length; i++) {
            Lane l = allLanes[i];
            if (l.isLaneLost()) {
                continue;
            }
            for (Titan t : l.getTitans()) {
                if (titansOnScreen.containsKey(t)) {
                    continue;
                }
                double width = 70;
                Image titanImage = new Image("Armored.png");
                ImageView newTitan = new ImageView();
                newTitan.setFitHeight(width * 1.367);
                newTitan.setFitWidth(width);
                newTitan.setImage(titanImage);

                ProgressBar titanHealth = new ProgressBar(1);
                titanHealth.setStyle("-fx-accent: #f66363");
                titansOnScreen.put(t, new Object[] {newTitan, titanHealth});
                laneGrid.add(titanHealth, t.getDistance() / 10, i);
                laneGrid.add(newTitan, t.getDistance() / 10, i);
            }
        }
    }

    public void moveTitansOnScreen() {
        Stack<Titan> titansToRemove = new Stack<>();
        for (Titan t : titansOnScreen.keySet()) {
            ImageView titanEntity = (ImageView) titansOnScreen.get(t)[0];
            ProgressBar healthBar = (ProgressBar) titansOnScreen.get(t)[1];

            healthBar.setProgress((double) t.getCurrentHealth() / t.getBaseHealth());
            if (t.isDefeated()) {
                titanEntity.setVisible(false);
                healthBar.setVisible(false);
                titansToRemove.push(t);
                continue;
            }

            GridPane.setValignment(healthBar, VPos.TOP);
            GridPane.setColumnIndex(healthBar, t.getDistance() / 10);
            GridPane.setColumnIndex(titanEntity, t.getDistance() / 10);
        }
        while (!titansToRemove.isEmpty()) {
            titansOnScreen.remove(titansToRemove.pop());
        }
    }
}
