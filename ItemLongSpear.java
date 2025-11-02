import greenfoot.*;

public class ItemLongSpear extends ShopItem {
    private static final int BASE_COST = 100;
    private static final int COST_INCREMENT = 75;
    private int level;

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
            int cost = nextCost();
            level++;
            showMessage("Upgrade Long Spear Lv " + level + " dibeli seharga $" + cost + " (dummy)");
            updateLabel();
        }
    }

    private int nextCost() {
        return BASE_COST + (level * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        return "$" + nextCost() + " Long damage (Lv " + (level + 1) + ")";
    }
}
