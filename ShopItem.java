import greenfoot.*;
import java.awt.Point;


public abstract class ShopItem extends Actor {
    private Point labelPosition;

    protected void resizeImage(int width, int height) {
        GreenfootImage img = getImage();
        if (img != null) {
            img = new GreenfootImage(img); 
            img.scale(width, height);
            setImage(img);
        }
    }

    public void setLabelPosition(int x, int y) {
        labelPosition = new Point(x, y);
        updateLabel();
    }

    protected Point getLabelPosition() {
        return labelPosition;
    }

    protected bgShop getShop() {
        World w = getWorld();
        return (w instanceof bgShop) ? (bgShop) w : null;
    }

    protected GameWorld getGameWorld() {
        bgShop shop = getShop();
        return (shop != null) ? shop.getGameWorld() : null;
    }

    protected void updateLabel() {
        bgShop shop = getShop();
        if (shop != null && labelPosition != null) {
            shop.updateItemLabel(this, labelPosition.x, labelPosition.y, getLabelText());
        }
    }

    protected void showMessage(String message) {
        bgShop shop = getShop();
        if (shop != null) {
            shop.showMessage(message);
        }
    }

    protected abstract String getLabelText();
}