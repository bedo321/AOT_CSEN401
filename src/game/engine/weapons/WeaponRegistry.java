package game.engine.weapons;

public class WeaponRegistry {
    private final int code;
    private int price;
    private int damage;
    private String name;
    private int minRange;
    private int maxRange;


    public WeaponRegistry(int code, int price) {
        super();
        this.code = code;
        this.price = price;
    }
    public WeaponRegistry(int code, int price, int damage, String name) {
        this(code,price);
        this.damage = damage;
        this.name = name;
    }
    public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange) {
        this(code, price, damage, name);
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public Weapon buildWeapon() {
        int weaponCode = getCode();
        Weapon weapon;
        if (weaponCode == 1)
            weapon = new PiercingCannon(getDamage());
        else if (weaponCode == 2)
            weapon = new SniperCannon(getDamage());
        else if (weaponCode == 3)
            weapon = new VolleySpreadCannon(getDamage(),getMinRange(),getMaxRange());
        else
            weapon = new WallTrap(getDamage());
        return weapon;
    }
}
