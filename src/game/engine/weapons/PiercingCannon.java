package game.engine.weapons;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.Titan;
import java.util.*;

public class PiercingCannon extends Weapon {
    public final static int WEAPON_CODE = 1;
    public PiercingCannon(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGained = 0;
        int length = laneTitans.size();
        Stack<Titan> temp = new Stack<>();
        for(int i = 0; i < length && i < 5; i++) {
            Titan titan = laneTitans.poll();
            int titanResources = attack(titan);
            resourcesGained += titanResources;
            if (titanResources == 0)
                temp.push(titan);
        }
        while(!temp.isEmpty())
            laneTitans.add(temp.pop());
        return resourcesGained;
    }
}
