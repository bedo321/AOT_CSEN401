package game.engine.base;

public class Wall {
    private final int baseHealth;
    private int currentHealth;

    Wall(int baseHealth){
        this.baseHealth=baseHealth;
        currentHealth=baseHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
}