package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class WallTrap extends Weapon {
    public final static int WEAPON_CODE = 4;

    public WallTrap(int baseDamage) {
        super(baseDamage);
    }
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        if (!laneTitans.isEmpty()) {
            Titan titan = laneTitans.peek();
            if (titan.getDistance() <= 0) {
                resourcesGained = attack(titan);
                if (resourcesGained == 0)
                    laneTitans.poll();
            }
        }
        return resourcesGained;
    }
}
