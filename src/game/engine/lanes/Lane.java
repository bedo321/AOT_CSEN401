package game.engine.lanes;
import java.util.PriorityQueue;
import java.util.ArrayList;
import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {
    private final Wall laneWall;
    private int dangerLevel;
    private final PriorityQueue<Titan> titans;
    private final ArrayList<Weapon> weapons;

    public Lane(Wall laneWall) {
        titans = new PriorityQueue<>();
        weapons = new ArrayList<>();
        this.laneWall = laneWall;
        dangerLevel = 0;
    }

    public Wall getLaneWall() {
        return laneWall;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = Math.max(0,dangerLevel);
    }

    public PriorityQueue<Titan> getTitans() {
        return titans;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public int compareTo(Lane o) {
        return dangerLevel - o.dangerLevel;
    }
}