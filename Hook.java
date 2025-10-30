import greenfoot.*;

public class Hook extends Actor {
    private final Boat owner;
    private int downSpeed = 3;
    private int upSpeed   = 3;

    // batas vertikal (atur sesuai layout air-mu)
    private int minY;  // dekat boat
    private int maxY;  // kedalaman maksimum

    public Hook(Boat owner) {
        this.owner = owner;
        // setImage("hook.png"); // ganti bila perlu
    }

    protected void addedToWorld(World w) {
        // setel batas saat hook ditambahkan
        minY = owner.getY() + 40;
        maxY = w.getHeight() - 40;
    }

    public void act() {
        followBoatX();
        handleVertical();
        clampVertical();
        
    CommonFish common = (CommonFish) getOneIntersectingObject(CommonFish.class);
        if (common != null) {
            // Ya, kena. Ambil nilainya, hapus ikannya.
            ((GameWorld) getWorld()).addScore(common.getValue());
            getWorld().removeObject(common);
            return; // 'return' agar berhenti di sini & tidak tangkap 2 ikan sekaligus
        }
        
        // Cek 2: Jika tidak kena CommonFish, apakah kena RareFish?
        RareFish rare = (RareFish) getOneIntersectingObject(RareFish.class);
        if (rare != null) {
            // Ya, kena.
            ((GameWorld) getWorld()).addScore(rare.getValue());
            getWorld().removeObject(rare);
            return;
        }
        
        // Cek 3: Jika tidak kena Rare/Common, apakah kena EpicFish?
        EpicFish epic = (EpicFish) getOneIntersectingObject(EpicFish.class);
        if (epic != null) {
            // Ya, kena.
            ((GameWorld) getWorld()).addScore(epic.getValue());
            getWorld().removeObject(epic);
            return;
        }
    }

    private void followBoatX() {
        setLocation(owner.getX(), getY()); // selalu sejajar X dengan boat
    }

    private void handleVertical() {
        // Tombol: up/down atau w/s
        if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + downSpeed);
        } else if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - upSpeed);
        } else {
            // idle: perlahan naik (rasa kail ditarik balik)
            if (getY() > minY) setLocation(getX(), getY() - 1);
        }
    }

    private void clampVertical() {
        int y = Math.max(minY, Math.min(getY(), maxY));
        setLocation(getX(), y);
    }
}
