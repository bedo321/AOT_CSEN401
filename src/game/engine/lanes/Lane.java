package game.engine.lanes;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Stack;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {
    private final Wall laneWall;
    private int dangerLevel;
    private final PriorityQueue<Titan> titans;
    private final ArrayList<Weapon> weapons;

    public Lane(Wall laneWall) {
        super();
        this.titans = new PriorityQueue<>();
        this.weapons = new ArrayList<>();
        this.laneWall = laneWall;
        this.dangerLevel = 0;
    }

    public void addTitan(Titan titan) {
        titans.add(titan);
    }
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }
    public void moveLaneTitans() {
        int length = getTitans().size();
        Stack<Titan> temp = new Stack<>();
        for (int i = 0; i < length; i++) {
            Titan titan = titans.poll();
            titan.move();
            temp.push(titan);
        }
        while(!temp.isEmpty())
            addTitan(temp.pop());
    }
    public int performLaneTitansAttacks() {
        int resourcesGathered = 0;
        int length = getTitans().size();
        Stack<Titan> temp = new Stack<>();
        for (int i = 0; i < length; i++) {
            Titan titan = titans.poll();
            if (titan.hasReachedTarget())
                resourcesGathered += titan.attack(laneWall);
            temp.push(titan);
        }
        while(!temp.isEmpty())
            addTitan(temp.pop());
        return resourcesGathered;
    }
    public int performLaneWeaponsAttacks() {
        int resourcesGathered = 0;
        for (int i = 0; i < weapons.size(); i++)
           resourcesGathered += weapons.get(i).turnAttack(titans);
        return resourcesGathered;
    }
    public boolean isLaneLost() {
        return getLaneWall().isDefeated();
    }

    public void updateLaneDangerLevel() {
        int newDangerLevel = 0;
        int length = getTitans().size();
        Stack<Titan> temp = new Stack<>();
        for (int i = 0; i < length; i++) {
            Titan titan = titans.poll();
            newDangerLevel += titan.getDangerLevel();
            temp.push(titan);
        }
        while(!temp.isEmpty())
            addTitan(temp.pop());
        setDangerLevel(newDangerLevel);
    }

    public Wall getLaneWall() {
        return laneWall;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
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