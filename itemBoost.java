import greenfoot.*;

public class itemBoost extends ShopItem {
    private static final int BASE_COST = 150;
    private static final int COST_INCREMENT = 100;
    private static final int MAX_LEVEL = 2;

    public itemBoost() {
        setImage(new GreenfootImage("btnItemBoost.png"));
    }

    @Override
    protected void addedToWorld(World world) {
        resizeImage(220, 120);
        updateLabel();
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            GameWorld gw = getGameWorld();
            if (gw == null) {
                return;
            }

            if (gw.getBoostUpgrades() >= MAX_LEVEL) {
                showMessage("Boost sudah MAX");
                updateLabel();
                return;
            }

            int cost = nextCost(gw);
            ShopPurchaseResult result = gw.tryPurchaseBoost(cost);

            if (result == ShopPurchaseResult.NOT_ENOUGH_COINS) {
                showMessage("Koin tidak cukup (butuh $" + cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.MAXED_OUT) {
                showMessage("Boost sudah MAX");
            } else if (result == ShopPurchaseResult.PURCHASED) {
                int newLevel = gw.getBoostUpgrades();
                showMessage("Dash max jadi " + gw.getDashCapacity() + " (upgrade Lv " + newLevel + ", bayar $" + cost + ", saldo $" + gw.getCoins() + ")");
            }

            updateLabel();
        }
    }

    private int nextCost(GameWorld gw) {
        return BASE_COST + (gw.getBoostUpgrades() * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "";
        }

        int level = gw.getBoostUpgrades();
        if (level >= MAX_LEVEL) {
            return "MAX LEVEL";
        }

        int nextLevel = level + 1;
        int nextCapacity = gw.getDashCapacityForLevel(nextLevel);
        return "$" + nextCost(gw) + " Dash cap -> " + nextCapacity;
    }
}