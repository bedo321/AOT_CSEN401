package game.engine.weapons;
import game.engine.interfaces.*;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public abstract class Weapon implements Attacker {
    private final int baseDamage;

    public Weapon(int baseDamage) {
        super();
        this.baseDamage = baseDamage;
    }

    public int getDamage() {
        return baseDamage;
    }

    public abstract int turnAttack(PriorityQueue<Titan> laneTitans);
}
