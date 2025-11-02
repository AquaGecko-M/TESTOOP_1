import greenfoot.*;

public class itemHeart extends ShopItem {
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
            showMessage("Membeli +2 Heart (dummy)");
        }
    }

    @Override
    protected String getLabelText() {
        return "$100 +2 Heart";
    }
}
