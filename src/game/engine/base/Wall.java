package game.engine.base;
import game.engine.interfaces.*;

public class Wall implements Attackee {
    private static final int resourcesValue = -1;
    private final int baseHealth;
    private int currentHealth;

    public Wall(int baseHealth) {
        super();
        this.baseHealth = baseHealth;
        currentHealth = baseHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, currentHealth);
    }

    @Override
    public int getResourcesValue() {
        return resourcesValue;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
}