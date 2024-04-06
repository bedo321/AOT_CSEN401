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
        for(int i = 0; i < length; i++) {
            Titan titan = laneTitans.poll();
            if (i < 5) {
                int titanResources = attack(titan);
                resourcesGained += titanResources;
                if (titanResources != 0)
                    laneTitans.add(titan);
            }
            else
                laneTitans.add(titan);
        }
        return resourcesGained;
    }

}
