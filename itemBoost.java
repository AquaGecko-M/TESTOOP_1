import greenfoot.*;

public class itemBoost extends ShopItem {
    private static final int BASE_COST = 100;
    private static final int COST_INCREMENT = 60;
    private int level;

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
            int cost = nextCost();
            level++;
            showMessage("Upgrade Boost Lv " + level + " dibeli seharga $" + cost + " (dummy)");
            updateLabel();
        }
    }

    private int nextCost() {
        return BASE_COST + (level * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        return "$" + nextCost() + " +5 sec duration (Lv " + (level + 1) + ")";
    }
}
