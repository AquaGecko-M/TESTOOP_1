import greenfoot.*;

public class itemSpeed extends ShopItem {
    private static final int BASE_COST = 100;
    private static final int COST_INCREMENT = 50;
    private int level;

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
            int cost = nextCost();
            level++;
            showMessage("Upgrade Speed Lv " + level + " dibeli seharga $" + cost + " (dummy)");
            updateLabel();
        }
    }

    private int nextCost() {
        return BASE_COST + (level * COST_INCREMENT);
    }

    @Override
    protected String getLabelText() {
        return "$" + nextCost() + " +10 Speed (Lv " + (level + 1) + ")";
    }
}
