import greenfoot.*;

public class ItemLongSpear extends ShopItem {
    private static final int BASE_COST = 500;
    private static final int COST_INCREMENT = 250;
    private static final int MAX_LEVEL = 2;

    public ItemLongSpear() {
        setImage(new GreenfootImage("btnItemLongSpear.png"));
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

            if (gw.getLongSpearUpgrades() >= MAX_LEVEL) {
                showMessage("Long Spear sudah MAX");
                updateLabel();
                return;
            }

            int cost = nextCost(gw);
            ShopPurchaseResult result = gw.tryPurchaseLongSpear(cost);

            if (result == ShopPurchaseResult.NOT_ENOUGH_COINS) {
                showMessage("Koin tidak cukup (butuh $" + cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.MAXED_OUT) {
                showMessage("Long Spear sudah MAX");
            } else if (result == ShopPurchaseResult.PURCHASED) {
                int newLevel = gw.getLongSpearUpgrades();
                showMessage("Upgrade Long Spear Lv " + newLevel + " dibeli sebesar $" + cost + " (saldo $" + gw.getCoins() + ")");

            updateLabel();
            gw.applyLongSpearToPlayer();
            }
        }
    }
    
    private int nextCost(GameWorld gw) {
        return BASE_COST + (gw.getLongSpearUpgrades() * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "";
        }

        if (gw.getLongSpearUpgrades() >= MAX_LEVEL) {
            return "MAX LEVEL";
        }

        int nextLevel = gw.getLongSpearUpgrades() + 1;
        return "$" + nextCost(gw) + " Long damage\nLevel " + nextLevel;
    }
}