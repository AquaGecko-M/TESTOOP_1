import greenfoot.*;

public class itemSpeed extends ShopItem {
    private static final int BASE_COST = 65;
    private static final int COST_INCREMENT = 60;
    private static final int MAX_LEVEL = 5;

    public itemSpeed() {
        setImage(new GreenfootImage("btnItemSpeed.png"));
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

            if (gw.getSpeedUpgrades() >= MAX_LEVEL) {
                showMessage("Speed sudah MAX");
                updateLabel();
                return;
            }

            int cost = nextCost(gw);
            ShopPurchaseResult result = gw.tryPurchaseSpeed(cost);

            if (result == ShopPurchaseResult.NOT_ENOUGH_COINS) {
                showMessage("Koin tidak cukup (butuh $" + cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.MAXED_OUT) {
                showMessage("Speed sudah MAX");
            } else if (result == ShopPurchaseResult.PURCHASED) {
                int newLevel = gw.getSpeedUpgrades();
                showMessage("Upgrade Speed Lv " + newLevel + " dibeli seharga $" + cost + " (saldo $" + gw.getCoins() + ")");
            }

            updateLabel();
        }
    }

    private int nextCost(GameWorld gw) {
        return BASE_COST + (gw.getSpeedUpgrades() * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "";
        }

        if (gw.getSpeedUpgrades() >= MAX_LEVEL) {
            return "MAX LEVEL";
        }

        int nextLevel = gw.getSpeedUpgrades() + 1;
        return "$" + nextCost(gw) + " +10 Speed\nLevel " + nextLevel;
    }
}