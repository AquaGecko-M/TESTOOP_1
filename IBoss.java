public interface IBoss extends Damageable {
    /**
     * @return The boss's current health as a percentage (0.0 to 1.0).
     */
    double getHealthPercentage();

    /**
     * @return true if the boss is still alive and in the world.
     */
    boolean isAlive();
}

/**
 * An interface that represents something that can take damage.
 */
interface Damageable {
    void takeDamage(int amount);
}
