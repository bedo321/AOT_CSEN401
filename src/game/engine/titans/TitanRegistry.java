package game.engine.titans;

import game.engine.exceptions.GameActionException;

public class TitanRegistry {
    private final int code;
    private int baseHealth;
    private int baseDamage;
    private int heightInMeters;
    private int speed;
    private int resourcesValue;
    private int dangerLevel;

    public TitanRegistry(int code, int baseHealth, int baseDamage, int heightInMeters, int speed,
                         int resourcesValue, int dangerLevel) {
        super();
        this.code = code;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.heightInMeters = heightInMeters;
        this.speed = speed;
        this.resourcesValue = resourcesValue;
        this.dangerLevel = dangerLevel;
    }

    public int getCode() {
        return code;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getHeightInMeters() {
        return heightInMeters;
    }

    public int getSpeed() {
        return speed;
    }

    public int getResourcesValue() {
        return resourcesValue;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public Titan spawnTitan(int distanceFromBase){
        int titanCode = getCode();
        Titan titan;
        if (titanCode == 1)
            titan = new PureTitan(getBaseHealth(),getBaseDamage(),getHeightInMeters(),distanceFromBase,getSpeed(),getResourcesValue(),getDangerLevel());
        else if (titanCode == 2)
            titan = new AbnormalTitan(getBaseHealth(),getBaseDamage(),getHeightInMeters(),distanceFromBase,getSpeed(),getResourcesValue(),getDangerLevel());
        else if (titanCode == 3)
            titan = new ArmoredTitan(getBaseHealth(),getBaseDamage(),getHeightInMeters(),distanceFromBase,getSpeed(),getResourcesValue(),getDangerLevel());
        else if (titanCode == 4)
            titan = new ColossalTitan(getBaseHealth(),getBaseDamage(),getHeightInMeters(),distanceFromBase,getSpeed(),getResourcesValue(),getDangerLevel());
        else
            return null;
        return titan;
    }
}
