package game.engine.weapons;

public class WallTrap extends Weapon {
    private final static int WEAPON_CODE = 4;

    public WallTrap(int baseDamage) {
        super(baseDamage);
    }


    // should I implement getters and setters on static variables???
    public static int getWeaponCode() {
        return WEAPON_CODE;
    }

}
