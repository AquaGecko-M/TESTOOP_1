import greenfoot.*;

public class Kail extends Actor {
    private final Boat owner;
    private int downSpeed = 3;
    private int upSpeed   = 3;

    private int minY;  // dekat boat
    private int maxY;  // kedalaman maksimum
    
    public Kail(Boat owner) {
        this.owner = owner;
        setImage("Kail.png"); 
        
        GreenfootImage image = getImage();
        image.scale(40, 50); 
        setImage(image); 
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
        
        // Cek 1: Apakah kena CommonFish?
        CommonFish common = (CommonFish) getOneIntersectingObject(CommonFish.class);
        if (common != null) {
            // Ya, kena. Ambil nilainya, hapus ikannya.
            ((GameWorld) getWorld()).addScore(common.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(common.getCoinReward());
            getWorld().removeObject(common);
            return; // 'return' agar berhenti di sini & tidak tangkap 2 ikan sekaligus
        }
        
        // Cek 2: Jika tidak kena CommonFish, apakah kena RareFish?
        RareFish rare = (RareFish) getOneIntersectingObject(RareFish.class);
        if (rare != null) {
            // Ya, kena.
            ((GameWorld) getWorld()).addScore(rare.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(rare.getCoinReward());
            getWorld().removeObject(rare);
            return;
        }
        
        // Cek 3: Jika tidak kena Rare/Common, apakah kena EpicFish?
        EpicFish epic = (EpicFish) getOneIntersectingObject(EpicFish.class);
        if (epic != null) {
            // Ya, kena.
            ((GameWorld) getWorld()).addScore(epic.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(epic.getCoinReward());
            getWorld().removeObject(epic);
            return;
        }
        
        GoldFish gold = (GoldFish) getOneIntersectingObject(GoldFish.class);
        if(gold != null) {
            // Ambil nilai dari ikan (bukan hardcoded)
        addScoreToWorld(gold.getValue());
        addCoinsToWorld(gold.getCoinReward());
            
            // Kunci tetap bonus spesial dari Kail
        addKeyItemToWorld();

         getWorld().removeObject(gold);
        return; 
         }
        
        Treasure treasure = (Treasure) getOneIntersectingObject(Treasure.class);

        // NEW CHECK: Is the treasure not null AND is it interactable?
        if (treasure != null && treasure.isInteractable()) {
            GameWorld currentWorld = (GameWorld) getWorld();
            // You must *declare* and *get* the variable before you can use it.
            int difficulty = currentWorld.getCurrentLevel();
            // Pass the treasure object to the QuizWorld
            Greenfoot.setWorld(new QuizWorld(currentWorld, treasure,difficulty));
            
            return; // Stop processing this act cycle
        }
    }

    private void followBoatX() {
        setLocation(owner.getX(), getY()); // selalu sejajar X dengan boat
    }

    private void handleVertical() {
        // Tombol: up/down atau w/s
        if (Greenfoot.isKeyDown("Down") || (Greenfoot.isKeyDown("s"))) {
            setLocation(getX(), getY() + downSpeed);
        } else if (Greenfoot.isKeyDown("Up") || (Greenfoot.isKeyDown("w"))) {
            setLocation(getX(), getY() - downSpeed);
        } else {
            if (getY() > minY) setLocation(getX(), getY() - 1);
        }
    }

    private void clampVertical() {
        int y = Math.max(minY, Math.min(getY(), maxY));
        setLocation(getX(), y);
    }
    
    private void addCoinsToWorld(int amount) {
    ((GameWorld)getWorld()).addCoins(amount);
    }
    
    private void addScoreToWorld(int score) {
        ((GameWorld)getWorld()).addScore(score);
    }
    
    private void addFishCollectedToWorld(int amount) {
        ((GameWorld)getWorld()).addFishCollected(amount);
    }
    private void addKeyItemToWorld() {
        World world = getWorld(); 
        if (world instanceof GameWorld) {
            ((GameWorld)world).addKeyItem();
        }
    
    }
}