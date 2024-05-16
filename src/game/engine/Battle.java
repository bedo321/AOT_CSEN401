package game.engine;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;
import game.engine.titans.TitanRegistry;
import game.engine.dataloader.DataLoader;
import game.engine.BattlePhase;
import java.util.*;
import java.io.*;

public class Battle {
    private final static int[][] PHASES_APPROACHING_TITANS = {{1, 1, 1, 2, 1, 3, 4}, {2, 2, 2, 1, 3, 3, 4}, {4, 4, 4, 4, 4, 4, 4}};
    private final static int WALL_BASE_HEALTH = 10000;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase;
    private int numberOfTitansPerTurn;
    private int score;
    private int titanSpawnDistance;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private final PriorityQueue<Lane> lanes;
    private final ArrayList<Lane> originalLanes;

    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes, int initialResourcesPerLane) throws IOException {
        super();
        this.numberOfTurns = numberOfTurns;
        this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
        this.battlePhase = BattlePhase.EARLY;
        this.numberOfTitansPerTurn = 1;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.weaponFactory = new WeaponFactory();
        this.titansArchives = DataLoader.readTitanRegistry();
        this.approachingTitans = new ArrayList<>();
        this.lanes = new PriorityQueue<>();
        this.originalLanes = new ArrayList<>();
        initializeLanes(initialNumOfLanes);
    }

    private void initializeLanes(int numOfLanes) {
        for (int i = 0; i < numOfLanes; i++) {
            Lane lane = new Lane(new Wall(WALL_BASE_HEALTH));
            lanes.add(lane);
            originalLanes.add(lane);
        }
    }
    // I am not sure of the following functions AT ALL
    public void refillApproachingTitans() {
        int index;
        switch (battlePhase) {
            case EARLY: index = 0; break;
            case INTENSE: index = 1; break;
            case GRUMBLING: index = 2; break;
            default: index = -1;
        }
        for (int i : PHASES_APPROACHING_TITANS[index])
            approachingTitans.add(titansArchives.get(i).spawnTitan(titanSpawnDistance));
    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
        if (!lane.isLaneLost() && lanes.contains(lane)) {
            FactoryResponse factoryResponse = getWeaponFactory().buyWeapon(getResourcesGathered(), weaponCode);
            lane.addWeapon(factoryResponse.getWeapon());
            setResourcesGathered(factoryResponse.getRemainingResources());
            performTurn();
        } else
            throw new InvalidLaneException();
    }
    public void passTurn() {
        performTurn();
    }

    private void addTurnTitansToLane() {
        Lane lane = lanes.poll();
        if (lane == null)
            return;
        while (numberOfTitansPerTurn > approachingTitans.size()) {
            refillApproachingTitans();
        }
        for (int i = 0; i < numberOfTitansPerTurn; i++) {
            lane.addTitan(approachingTitans.remove(0));
        }
        lanes.add(lane);
    }
    private void moveTitans() {
        for (Lane lane : lanes) {
            lane.moveLaneTitans();
        }
    }
    private int performWeaponsAttacks() {
        int resourcesGathered = 0;
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                int resources = lane.performLaneWeaponsAttacks();
                resourcesGathered += resources;
            }
        }
        this.score += resourcesGathered;
        this.resourcesGathered += resourcesGathered;
        return resourcesGathered;
    }
    private int performTitansAttacks() {
        int resourcesGathered = 0;
        Stack<Lane> temp = new Stack<>();
        int length = lanes.size();
        for (int i = 0; i < length; i++) {
            Lane lane = lanes.poll();
            int resources = lane.performLaneTitansAttacks();
            resourcesGathered += resources;
            if (!lane.isLaneLost()) {
                temp.push(lane);
            }
        }
        while(!temp.isEmpty())
            lanes.add(temp.pop());
        return resourcesGathered;
    }
    private void updateLanesDangerLevels() {
        Stack<Lane> temp = new Stack<>();
        int length = lanes.size();
        for (int i = 0; i < length; i++) {
            Lane lane = lanes.poll();
            lane.updateLaneDangerLevel();
            temp.push(lane);
        }
        while(!temp.isEmpty()) {
            lanes.add(temp.pop());
        }
    }
    private void finalizeTurns() {
        setNumberOfTurns(getNumberOfTurns()+1);
        if (numberOfTurns < 15)
            setBattlePhase(BattlePhase.EARLY);
        else if (numberOfTurns < 30)
            setBattlePhase(BattlePhase.INTENSE);
        else if (numberOfTurns > 30 && numberOfTurns%5 == 0) {
            setBattlePhase(BattlePhase.GRUMBLING);
            setNumberOfTitansPerTurn(getNumberOfTitansPerTurn()*2);
        }
        else
            setBattlePhase(BattlePhase.GRUMBLING);
    }
    private void performTurn() {
        moveTitans();
        performWeaponsAttacks();
        updateLanesDangerLevels();
        performTitansAttacks();
        addTurnTitansToLane();
        updateLanesDangerLevels();
        finalizeTurns();
    }
    public boolean isGameOver() {
        return lanes.isEmpty();
    }
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
    }

    public int getNumberOfTitansPerTurn() {
        return numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTitanSpawnDistance() {
        return titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
    }

    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return approachingTitans;
    }

    public PriorityQueue<Lane> getLanes() {
        return lanes;
    }

    public ArrayList<Lane> getOriginalLanes() {
        return originalLanes;
    }
}
