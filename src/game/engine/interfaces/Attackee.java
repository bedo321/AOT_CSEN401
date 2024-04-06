package game.engine.interfaces;

public interface Attackee {
    public int getCurrentHealth();

    public void setCurrentHealth(int health);

    public int getResourcesValue();

    public default boolean isDefeated() {
        return (getCurrentHealth() <= 0);
    }

    public default int takeDamage(int damage) {
        setCurrentHealth(getCurrentHealth() - damage);
        if (isDefeated())
            return getResourcesValue();
        return 0;
    }
}