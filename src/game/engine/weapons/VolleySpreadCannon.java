package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;
import java.util.Stack;

public class VolleySpreadCannon extends Weapon {
    public final static int WEAPON_CODE = 3;
    private final int minRange;
    private final int maxRange;

    public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
        super(baseDamage);
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        int length = laneTitans.size();
        Stack<Titan> temp = new Stack<>();
        for(int i = 0; i < length; i++) {
            Titan titan = laneTitans.poll();
            if (titan.getDistance() >= getMinRange() && titan.getDistance() <= getMaxRange()) {
                int titanResources = attack(titan);
                resourcesGained += titanResources;
                if (titanResources == 0) {
                    temp.push(titan);
                }
            }
            else
                temp.push(titan);
        }
        while(!temp.isEmpty())
            laneTitans.add(temp.pop());
        return resourcesGained;
    }
}