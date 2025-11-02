import greenfoot.*;

public class itemHeart extends ShopItem {
    private static final int MAX_PURCHASE = 20;
    private static final int COST = 100;

    public itemHeart() {
        setImage(new GreenfootImage("btnItemHeart.png"));
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

            ShopPurchaseResult result = gw.tryPurchaseHeart(COST);

            if (result == ShopPurchaseResult.MAXED_OUT) {
                showMessage("Heart stok habis");
            } else if (result == ShopPurchaseResult.NOT_ENOUGH_COINS) {
                showMessage("Koin tidak cukup (butuh $" + COST + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.PURCHASED) {
                int count = gw.getHeartPurchases();
                showMessage("+2 Heart dibeli (" + count + "/" + MAX_PURCHASE + ") (saldo $" + gw.getCoins() + ")");
            }
            updateLabel();
        }
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "$" + COST + " +2 Heart";
        }

        if (gw.getHeartPurchases() >= MAX_PURCHASE) {
            return "STOK HABIS";
        }

        return "$" + COST + " +2 Heart\nSisa " + (MAX_PURCHASE - gw.getHeartPurchases());
    }
}