/**
 * An interface (a "contract") that all bosses must follow.
 * This allows a single health bar to track ANY boss.
 * * It extends Damageable, so all bosses MUST also have a
 * takeDamage(int amount) method.
 */
public interface IBoss extends Damageable
{
    /**
     * @return The boss's current health as a percentage (0.0 to 1.0).
     */
    public double getHealthPercentage();
    
    /**
     * @return true if the boss is still alive and in the world.
     */
    public boolean isAlive();
}