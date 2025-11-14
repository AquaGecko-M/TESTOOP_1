import greenfoot.*;

public class itemHeart extends ShopItem {
    private static final int MaxPurchase = 15;
    private static final int Cost = 75;

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

            ShopPurchaseResult result = gw.tryPurchaseHeart(Cost);

            if (result == ShopPurchaseResult.MaxedOut) {
                showMessage("Heart stok habis");
            } else if (result == ShopPurchaseResult.NotEnoughCoins) {
                showMessage("Koin tidak cukup (butuh $" + Cost + ", saldo $" + gw.getCoins() + ")");
            } else if (result == ShopPurchaseResult.Purchased) {
                int count = gw.getHeartPurchases();
                showMessage("+2 Heart dibeli (" + count + "/" + MaxPurchase + ") (saldo $" + gw.getCoins() + ")");
            }
            updateLabel();
        }
    }

    @Override
    protected String getLabelText() {
        GameWorld gw = getGameWorld();
        if (gw == null) {
            return "$" + Cost + " +2 Heart";
        }

        if (gw.getHeartPurchases() >= MaxPurchase) {
            return "STOK HABIS";
        }

        return "$" + Cost + " +2 Heart\nSisa " + (MaxPurchase - gw.getHeartPurchases());
    }
}
