import greenfoot.*;  

public class bgShop extends World {
    private static final int DEFAULT_WIDTH = 960;
    private static final int DEFAULT_HEIGHT = 540;

    private final GameWorld pausedWorld;

    public bgShop() {
        this(null);
    }

    public bgShop(GameWorld pausedWorld) {
        super(pausedWorld != null ? pausedWorld.getWidth() : DEFAULT_WIDTH,
              pausedWorld != null ? pausedWorld.getHeight() : DEFAULT_HEIGHT, 1);
        this.pausedWorld = pausedWorld;

        drawBackground();
        layoutItems();
        showMessage("Saldo: $" + (pausedWorld != null ? pausedWorld.getCoins() : 0));
    }

    private void drawBackground() {
        GreenfootImage base = null;
        if (pausedWorld != null) {
            base = new GreenfootImage(pausedWorld.getBackground());
        } else {
            base = new GreenfootImage("24.jpg");
            base.scale(getWidth(), getHeight());
        }

        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new Color(0, 0, 0, 180));
        overlay.fill();
        base.drawImage(overlay, 0, 0);

        GreenfootImage panel = new GreenfootImage("BackgroundShop.png");
        panel.scale((int)(getWidth() * 0.9), (int)(getHeight() * 0.9));
        base.drawImage(panel, (getWidth() - panel.getWidth()) / 2, (getHeight() - panel.getHeight()) / 2);

        setBackground(base);
    }

    private void layoutItems() {
        int centerX = getWidth() / 2;
        int leftOffset = centerX - 100;
        int rightOffset = centerX + 100;
        int centerY = getHeight() / 2;
        int offsetX = 160;
        int offsetY = 100;
        ItemLongSpear longSpear = new ItemLongSpear();
        addItem(longSpear, leftOffset - offsetX, centerY - offsetY,
                leftOffset - offsetX, centerY - offsetY + 70);

        itemSpeed speed = new itemSpeed();
        addItem(speed, rightOffset + offsetX, centerY - offsetY,
                rightOffset + offsetX, centerY - offsetY + 70);

        itemBoost boost = new itemBoost();
        addItem(boost, leftOffset - offsetX, centerY + offsetY,
                leftOffset - offsetX, centerY + offsetY + 70);

        itemHeart heart = new itemHeart();
        addItem(heart, rightOffset + offsetX, centerY + offsetY,
                rightOffset + offsetX, centerY + offsetY + 70);

        itemResume resume = new itemResume();
        addObject(resume, centerX, getHeight() - 70);
    }

    private void addItem(ShopItem item, int x, int y, int labelX, int labelY) {
        addObject(item, x, y);
        item.setLabelPosition(labelX, labelY);
    }

    GameWorld getGameWorld() {
        return pausedWorld;
    }

    void updateItemLabel(ShopItem item, int x, int y, String text) {
        showText("", x, y);
        showText("", x, y + 20);

        if (text == null) {
            return;
        }

        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            showText(lines[i], x, y + (i * 20));
        }
    }

    void showMessage(String message) {
        showText(message, getWidth() / 2, getHeight() - 30);
    }

    void resumeGame() {
        if (pausedWorld != null) {
            Greenfoot.setWorld(pausedWorld);
            pausedWorld.onResumeFromPause();
        } else {
            Greenfoot.setWorld(new GameWorld(1));
        }
    }
}
