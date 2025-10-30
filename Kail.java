import greenfoot.*;

public class Kail extends Actor {
    private final Boat owner;
    private int downSpeed = 3;
    private int upSpeed   = 3;

    // batas vertikal (atur sesuai layout air-mu)
    private int minY;  // dekat boat
    private int maxY;  // kedalaman maksimum

    public Kail(Boat owner) {
        this.owner = owner;
        // 1. Set gambar dulu
        setImage("Kail.png"); // <--- Pastikan nama file gambarmu BENAR
        
        // 2. Kemudian, ubah ukurannya
        GreenfootImage image = getImage();
        // Ubah angka 30 dan 60 sesuai ukuran yang kamu inginkan
        image.scale(40, 50); // Contoh: Lebar 30 piksel, Tinggi 60 piksel
        setImage(image); // Set gambar yang sudah diskalakan
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
        
        // --- INI BAGIAN YANG DIPERBAIKI ---
        // Kita tidak bisa lagi mencari Fish.class.
        // Kita harus cek CommonFish, RareFish, dan EpicFish satu per satu.
        
        // Cek 1: Apakah kena CommonFish?
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