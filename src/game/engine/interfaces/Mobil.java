package game.engine.interfaces;

public interface Mobil {
    public int getDistance();
    public void setDistance(int distance);
    public int getSpeed();
    public void setSpeed(int speed);

    public default boolean hasReachedTarget() {
        return (getDistance() <= 0);
    }
    public default boolean move() {
        int distance = getDistance() - getSpeed();
        setDistance(distance);
        return hasReachedTarget();
    }
}
