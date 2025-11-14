import greenfoot.*;

public class itemBoost extends ShopItem {
    private static final int BaseCost = 100;
    private static final int CostIncrement = 100;
    private static final int MaxLevel = 2;

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

            if (gw.getBoostUpgrades() >= MaxLevel) {
                showMessage("Boost sudah MAX");
                updateLabel();
                return;
            }

            int cost = nextCost(gw);
            ShopPurchaseResult result = gw.tryPurchaseBoost(cost);

            if (result == ShopPurchaseResult.NotEnoughCoins) {
                showMessage("Koin tidak cukup (butuh $" + cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.MaxedOut) {
                showMessage("Boost sudah MAX");
            } else if (result == ShopPurchaseResult.Purchased) {
                int newLevel = gw.getBoostUpgrades();
                showMessage("Dash max jadi " + gw.getDashCapacity() + " (upgrade Lv " + newLevel + ", bayar $" + cost + ", saldo $" + gw.getCoins() + ")");
            }

            updateLabel();
        }
    }

    private int nextCost(GameWorld gw) {
        return BaseCost + (gw.getBoostUpgrades() * CostIncrement);
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "";
        }

        int level = gw.getBoostUpgrades();
        if (level >= MaxLevel) {
            return "MAX LEVEL";
        }

        int nextLevel = level + 1;
        int nextCapacity = gw.getDashCapacityForLevel(nextLevel);
        return "$" + nextCost(gw) + " Dash cap -> " + nextCapacity;
    }
}
