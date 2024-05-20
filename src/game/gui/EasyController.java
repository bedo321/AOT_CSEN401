package game.gui;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    @FXML
    public GridPane weaponSpace1;

    @FXML
    public GridPane weaponSpace2;

    @FXML
    public GridPane weaponSpace3;

    @FXML
    public Rectangle laneLostBorder1;

    @FXML
    public Rectangle laneLostBorder2;

    @FXML
    public Rectangle laneLostBorder3;

    @FXML
    public Label defeated1;

    @FXML
    public Label defeated2;

    @FXML
    public Button defeated3;
    @FXML
    public ImageView weapon1;
    @FXML
    public ImageView weapon2;
    @FXML
    public ImageView weapon3;
    @FXML
    public ImageView weapon4;
    @FXML
    public Label weapontxt1;
    @FXML
    public Label weapontxt2;
    @FXML
    public Label weapontxt3;
    @FXML
    public Label weapontxt4;
    @FXML
    public Button aiTurn;
    @FXML
    public Button action;
    public Image weaponImg1 = new Image(getClass().getResource("/game/gui/assets/Pure.png").toString());
    public Image weaponImg2 = new Image(getClass().getResource("/game/gui/assets/Abnormal.png").toString());
    public Image weaponImg3 = new Image(getClass().getResource("/game/gui/assets/Armored.png").toString());
    public Image weaponImg4 = new Image(getClass().getResource("/game/gui/assets/Colossal.png").toString());




    public Lane[] allLanes;
    public Label[] allDangerLevels;
    public Label[] allLostLabels;
    public Label[] allHP;
    public ProgressBar[] allHPBars;
    public Button[] weaponShopButtons;
    public Button[] weaponShopLaneButtons;
    public GridPane weaponSpaces[];
    public HashMap<Titan, VBox> titansOnScreen = new HashMap<>();
    public int[][] weaponCounts;
    public Rectangle[] laneLostBorders;
    public Label[][] weaponCountLabels;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBGMusic("Music_Scene1.mp3");
        try {
            battle = new Battle(1, 0, 100, 3, 250);
        } catch (IOException e) {
            File file1 = new File("titans.csv");
            if (!file1.exists()) {
                try {
                    file1.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                rewriteTitansCSV();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            File file2 = new File("weapons.csv");
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                rewriteWeaponsCSV();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                battle = new Battle(1, 0, 100, 3, 250);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
    public void rewriteTitansCSV() throws FileNotFoundException {
        PrintWriter csvWriter = new PrintWriter("titans.csv");
        csvWriter.println("1,100,15,15,10,10,1");
        csvWriter.println("2,100,20,10,15,15,2");
        csvWriter.println("3,200,85,15,10,30,3");
        csvWriter.println("4,1000,100,60,5,60,4");

        csvWriter.flush();
        csvWriter.close();

    }

    public void rewriteWeaponsCSV() throws FileNotFoundException {
        PrintWriter csvWriter = new PrintWriter("weapons.csv");
        csvWriter.println("1,25,10,Anti Titan Shell");
        csvWriter.println("2,25,35,Long Range Spear");
        csvWriter.println("3,100,5,Wall Spread Cannon,20,50");
        csvWriter.println("4,75,100,Proximity Trap");

        csvWriter.flush();
        csvWriter.close();
    }

    public void setToEasy() {
        allLanes = new Lane[] {lane1, lane2, lane3};
        allDangerLevels = new Label[] {lane1dangerlevel, lane2dangerlevel, lane3dangerlevel};
        allLostLabels = new Label[] {lane1lost, lane2lost, lane3lost};
        allHP = new Label[] {hp1, hp2, hp3};
        allHPBars = new ProgressBar[] {bar1, bar2, bar3};
        wallBaseHealth = lane1.getLaneWall().getBaseHealth();
        weaponShopLaneButtons = new Button[] {buy1, buy2, buy3};
        weaponSpaces = new GridPane[] {weaponSpace1, weaponSpace2, weaponSpace3};
        weaponCounts = new int[3][4];
        laneLostBorders = new Rectangle[] {laneLostBorder1, laneLostBorder2, laneLostBorder3};
        weaponCountLabels = new Label[3][4];
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
    public void passTurn(MouseEvent event) {
        battle.passTurn();
        performCancel();
        viewTurn();
    }

    public void viewTurn() {
        showNewTitans();
        moveTitansOnScreen();
        adjustHealth();
        for (int i = 0; i < allLanes.length; i++) {
            if (allLanes[i].isLaneLost()) {
                allLostLabels[i].setVisible(true);
                laneLostBorders[i].setVisible(true);
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
            action.setDisable(true);
            weaponwindow.setVisible(true);
            defeated1.setVisible(true);
            defeated2.setText("Your Score: " + score.getText());
            defeated2.setVisible(true);
            defeated3.setVisible(true);
            return;
        }
        resources.setText(String.valueOf(battle.getResourcesGathered()));
        score.setText(String.valueOf(battle.getScore()));
        turn.setText(String.valueOf(battle.getNumberOfTurns()));
        if (battle.getBattlePhase().equals(BattlePhase.INTENSE) && phase.getText().equals("EARLY")) {
            phase.setText(String.valueOf(battle.getBattlePhase()));
            phase.setStyle("-fx-text-fill: #edffa3");
            mediaPlayer.stop();
            playBGMusic("Music_Scene2.mp3");
        }
        if (battle.getBattlePhase().equals(BattlePhase.GRUMBLING) && phase.getText().equals("INTENSE")) {
            phase.setText(String.valueOf(battle.getBattlePhase()));
            phase.setStyle("-fx-text-fill: #ffa3a3");
            mediaPlayer.stop();
            playBGMusic("Music_Scene3.mp3");
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        performCancel();
    }
    @FXML
    public void actionEvent(ActionEvent event) {
        weaponwindow.setVisible(true);
        buyweapon.setVisible(true);
        passturn.setVisible(true);
        aiTurn.setVisible(true);
        action.setDisable(true);
        cancel.setVisible(true);
    }

    public void performCancel() {
        setWeaponButtonVisibility(false);
        setLaneButtonVisibility(false);
        weaponwindow.setVisible(false);
        cancel.setVisible(false);
        buyweapon.setVisible(false);
        passturn.setVisible(false);
        notEnoughResources.setVisible(false);
        invalidLane.setVisible(false);
        aiTurn.setVisible(false);
        action.setDisable(false);
        viewTurn();
        weaponCode = 0;
    }

    @FXML
    public void buyWeapon(ActionEvent event) {
        setWeaponButtonVisibility(true);
        buyweapon.setVisible(false);
        passturn.setVisible(false);
        aiTurn.setVisible(false);
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
    public void buyLane1(ActionEvent event) {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane1);
        }
    }

    @FXML
    public void buyLane2(ActionEvent event) {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane2);
        }
    }

    @FXML
    public void buyLane3(ActionEvent event) {
        if (weaponCode != 0) {
            completePurchase(weaponCode, lane3);
        }
    }

    public void completePurchase(int code, Lane lane) {
        try {
            battle.purchaseWeapon(code, lane);
            performCancel();
            int laneIndex = getLaneIndex(lane);
            showNewWeapon(code, laneIndex);
            weaponCounts[laneIndex][code - 1]++;
            Label weaponLabel = weaponCountLabels[laneIndex][code - 1];
            int weaponCount = weaponCounts[laneIndex][code - 1];
            weaponLabel.setText(Integer.toString(weaponCount));
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
        weapon1.setVisible(visibility);
        weapon2.setVisible(visibility);
        weapon3.setVisible(visibility);
        weapon4.setVisible(visibility);
        weapontxt1.setVisible(visibility);
        weapontxt2.setVisible(visibility);
        weapontxt3.setVisible(visibility);
        weapontxt4.setVisible(visibility);
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
                for (Titan t : l.getTitans()) {
                    if (!titansOnScreen.containsKey(t)) {
                        continue;
                    }
                    VBox titanUI = titansOnScreen.get(t);
                    laneGrid.getChildren().remove(titanUI);
                    titansOnScreen.remove(t);
                }
                continue;
            }
            for (Titan t : l.getTitans()) {
                if (titansOnScreen.containsKey(t)) {
                    continue;
                }
                int code = getTitanCode(t);
                Image titanImage = getTitanPath(code);
                ImageView newTitan = new ImageView();
                setTitanSize(code, newTitan);
                newTitan.setImage(titanImage);
                ProgressBar titanHealth = new ProgressBar(1);
                titanHealth.setMaxHeight(10);
                titanHealth.setStyle("-fx-accent: #f66363");
                VBox titanUI = new VBox();
                titanUI.getChildren().add(titanHealth);
                titanUI.getChildren().add(newTitan);
                titansOnScreen.put(t, titanUI);
                laneGrid.add(titanUI, t.getDistance() / 5, i);
            }
        }
    }

    private void setTitanSize(int code, ImageView newTitan) {
//        newTitan.setFitHeight(20); newTitan.setFitWidth(20); return;
        switch (code) {
            case 1: newTitan.setFitHeight(60); newTitan.setFitWidth(50); return;
            case 2: newTitan.setFitHeight(70); newTitan.setFitWidth(60); return;
            case 3: newTitan.setFitHeight(90); newTitan.setFitWidth(70); return;
            case 4: newTitan.setFitHeight(110); newTitan.setFitWidth(100); return;
        }
    }

    public void moveTitansOnScreen() {
        Stack<Titan> titansToRemove = new Stack<>();
        for (Titan t : titansOnScreen.keySet()) {
            VBox titanUI = titansOnScreen.get(t);
            ProgressBar healthBar = (ProgressBar) titanUI.getChildren().get(0);
            healthBar.setProgress((double) t.getCurrentHealth() / t.getBaseHealth());
            if (t.isDefeated()) {
                laneGrid.getChildren().remove(titansOnScreen.get(t));
                titansToRemove.push(t);
                continue;
            }
            titanUI.setAlignment(Pos.BOTTOM_CENTER);
            GridPane.setValignment(titanUI, VPos.BOTTOM);
            GridPane.setHalignment(titanUI, HPos.CENTER);
            GridPane.setColumnIndex(titanUI, t.getDistance() / 5);
        }
        while (!titansToRemove.isEmpty()) {
            titansOnScreen.remove(titansToRemove.pop());
        }
    }

    public void showNewWeapon(int code, int laneIndex) {
        if (weaponCounts[laneIndex][code - 1] > 0) {
            return;
        }
        String path = getWeaponPath(code);
        Image weaponImage = new Image(getClass().getResource(path).toString());
        ImageView weaponEntity = new ImageView();
        weaponEntity.setImage(weaponImage);
        weaponCountLabels[laneIndex][code - 1] = new Label();
        Label weaponCountLabel = weaponCountLabels[laneIndex][code - 1];
//        Rectangle weaponCountBorder = new Rectangle();
        weaponCountLabel.setText("1");
        weaponCountLabel.setPrefHeight(80);
        weaponCountLabel.setStyle("-fx-font-family: \"Press Start 2P\"; -fx-font-size: 32px; -fx-text-fill: #d34760;");
        weaponEntity.setFitHeight(100);
        weaponEntity.setFitWidth(100);
        GridPane weaponSpace = weaponSpaces[laneIndex];

        if (code == 4) {
            laneGrid.add(weaponEntity, 0, laneIndex);
            GridPane.setHalignment(weaponEntity, HPos.LEFT);
            GridPane.setValignment(weaponEntity, VPos.BOTTOM);
            weaponSpace.add(weaponCountLabel, 0, 0);
            GridPane.setHalignment(weaponCountLabel, HPos.LEFT);
//            weaponSpaces[laneIndex].add(weaponCountLabel, 0, laneIndex);
            return;
        }
//        weaponCountBorder.setHeight(70);
//        weaponCountBorder.setWidth(70);
//        weaponCountBorder.setOpacity(0.7);
        int[] positions = {0, 2, 1};
//        weaponSpace.add(weaponCountBorder, positions[code - 1], 0);
//        GridPane.setHalignment(weaponCountBorder, HPos.RIGHT);
        weaponSpace.add(weaponCountLabel, positions[code - 1], 0);
        GridPane.setHalignment(weaponCountLabel, HPos.RIGHT);
        GridPane.setValignment(weaponCountLabel, VPos.CENTER);
        weaponSpace.add(weaponEntity, positions[code - 1], 0);
        GridPane.setHalignment(weaponEntity, HPos.CENTER);
    }

    public String getWeaponPath(int code) {
        switch (code) {
            case 1: return "/game/gui/assets/cannon.png";
            case 2: return "/game/gui/assets/Long Range Spear 3.png";
            case 3: return "/game/gui/assets/Wall Spread Cannon.png";
            case 4: return "/game/gui/assets/Proximity Trap 2.png";
            default: return null;
        }
    }

    public Image getTitanPath(int code) {
        switch (code) {
            case 1: return weaponImg1;
            case 2: return weaponImg2;
            case 3: return weaponImg3;
            case 4: return weaponImg4;
        }
        return null;
    }

    public int getTitanCode(Titan t) {
        if (t instanceof PureTitan) {
            return 1;
        } else if (t instanceof AbnormalTitan) {
            return 2;
        } else if (t instanceof ArmoredTitan) {
            return 3;
        } else if (t instanceof ColossalTitan) {
            return 4;
        } else {
            return 0;
        }
    }

    public int getLaneIndex(Lane l) {
        for (int i = 0; i < allLanes.length; i++) {
            if (allLanes[i] == l) {
                return i;
            }
        }
        return -1;
    }
    public void switchToTitle(MouseEvent event) throws IOException {
        Stage stage = (Stage)(defeated3.getScene().getWindow());
        Parent root = FXMLLoader.load(getClass().getResource("Title_Screen.fxml"));
        Scene scene = new Scene(root);
        this.mediaPlayer.stop();
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap");
        stage.setScene(scene);
        stage.setResizable(false);
    }
    @FXML
    public void aiTurnAction (MouseEvent event) {
        int danger = 0;
        int laneNum = 0;
        for (int i = 0; i < allLanes.length; i++) {
            if (!allLanes[i].isLaneLost()) {
                if (allLanes[i].getDangerLevel() >= danger) {
                    danger = allLanes[i].getDangerLevel();
                    laneNum = i;
                }
            }
        }
        Label cannon = weaponCountLabels[laneNum][0];
        Label spear = weaponCountLabels[laneNum][1];
        Label wallTrap = weaponCountLabels[laneNum][3];
        if (phase.getText().equals("EARLY")) {
            if (spear == null || Integer.parseInt(spear.getText()) < 5) {
                if (Integer.parseInt(resources.getText()) >= 25)
                    completePurchase(2,allLanes[laneNum]);
                else
                    battle.passTurn();
            }
            else {
                if (Integer.parseInt(resources.getText()) >= 25)
                    completePurchase(1, allLanes[laneNum]);
                else
                    battle.passTurn();
            }
        }
        else if (cannon == null || phase.getText().equals("INTENSE") || Integer.parseInt(turn.getText()) < 40) {
            if (Integer.parseInt(resources.getText()) >= 25)
                completePurchase(1,allLanes[laneNum]);
            else
                battle.passTurn();
        }
        else {
            if (wallTrap == null || Integer.parseInt(wallTrap.getText()) < 20) {
                if (Integer.parseInt(resources.getText()) >= 75)
                    completePurchase(4,allLanes[laneNum]);
                else if (Integer.parseInt(resources.getText()) >= 25)
                    completePurchase(1,allLanes[laneNum]);
                else
                    battle.passTurn();
            }
            else {
                if (Integer.parseInt(resources.getText()) >= 25)
                    completePurchase(1, allLanes[laneNum]);
                else
                    battle.passTurn();
            }
        }
        performCancel();
        viewTurn();
    }
}
