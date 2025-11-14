import greenfoot.*;

public class ItemLongSpear extends ShopItem {
    private static final int BaseCost = 350;
    private static final int CostIncrement = 250;
    private static final int MaxLevel = 2;

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

            if (gw.getLongSpearUpgrades() >= MaxLevel) {
                showMessage("Long Spear sudah MAX");
                updateLabel();
                return;
            }

            int cost = nextCost(gw);
            ShopPurchaseResult result = gw.tryPurchaseLongSpear(cost);

            if (result == ShopPurchaseResult.NotEnoughCoins) {
                showMessage("Koin tidak cukup (butuh $" + cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.MaxedOut) {
                showMessage("Long Spear sudah MAX");
            } else if (result == ShopPurchaseResult.Purchased) {
                int newLevel = gw.getLongSpearUpgrades();
                showMessage("Upgrade Long Spear Lv " + newLevel + " dibeli sebesar $" + cost + " (saldo $" + gw.getCoins() + ")");

            updateLabel();
            gw.applyLongSpearToPlayer();
            }
        }
    }
    
    private int nextCost(GameWorld gw) {
        return BaseCost + (gw.getLongSpearUpgrades() * CostIncrement);
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "";
        }

        if (gw.getLongSpearUpgrades() >= MaxLevel) {
            return "MAX LEVEL";
        }

        int nextLevel = gw.getLongSpearUpgrades() + 1;
        return "$" + nextCost(gw) + " Long damage\nLevel " + nextLevel;
    }
}

