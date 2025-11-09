import greenfoot.*;

public class EnemyPuffer extends Actor implements Damageable {
    private int speed = 1;
    private int direction;
    private int health;
    private int bob = 0;
    
    private int attackRange = 200;
    private SimpleTimer chargeTimer = new SimpleTimer();
    private SimpleTimer attackCooldown = new SimpleTimer();
    private boolean isCharging = false;
    private boolean isKembung = false;
    private boolean canAttack = true;
    
    private GreenfootImage imgKempes;
    private GreenfootImage imgKembung;

    private boolean isMirrored = false; // 🟢 Tambahan: status mirror

    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150;

    public EnemyPuffer(int initialHealth) {
        this.health = initialHealth;
        attackCooldown.mark();

        imgKempes = new GreenfootImage("Kempes.png");
        imgKempes.scale(70, 70);
        imgKembung = new GreenfootImage("Kembung.png");
        imgKembung.scale(70, 70);
        setImage(imgKempes);
    }

    // 🟢 Mirror hanya sekali saat spawn
    protected void addedToWorld(World w) {
        int worldWidth = w.getWidth();

        if (getX() <= 50) {
            direction = 1; // spawn kiri → jalan ke kanan
            mirrorImages(true);
        } else if (getX() >= worldWidth - 50) {
            direction = -1; // spawn kanan → jalan ke kiri
            mirrorImages(false);
        } else {
            direction = Greenfoot.getRandomNumber(2) == 0 ? -1 : 1;
            mirrorImages(direction == 1);
        }
    }

    private void mirrorImages(boolean toRight) {
        // load ulang gambar biar nggak ke-mirror dobel
        imgKempes = new GreenfootImage("Kempes.png");
        imgKempes.scale(70, 70);
        imgKembung = new GreenfootImage("Kembung.png");
        imgKembung.scale(70, 70);

        if (toRight) { // kalau ke kanan → mirror sekali aja
            imgKempes.mirrorHorizontally();
            imgKembung.mirrorHorizontally();
            isMirrored = true;
        } else {
            isMirrored = false;
        }
        setImage(imgKempes);
    }

    public void act() {
        if (getWorld() == null) return;
        move();
        handleAttackLogic();
        checkHitBoat();
    }

    private void handleAttackLogic() {
        if (!canAttack) {
            if (attackCooldown.hasElapsed(3000)) {
                canAttack = true;
                setKembung(false);
            }
            return;
        }

        if (isCharging) {
            if (chargeTimer.hasElapsed(2000)) {
                if (isBoatInRange()) {
                    setKembung(true);
                    canAttack = false;
                    attackCooldown.mark();
                }
                isCharging = false;
            }
            return;
        }

        if (canAttack && !isCharging && isBoatInRange()) {
            isCharging = true;
            chargeTimer.mark();
        }
    }

    private void checkHitBoat() {
        if (isKembung) {
            if (!getObjectsInRange(70, Boat.class).isEmpty()) {
                Boat boat = (Boat) getObjectsInRange(70, Boat.class).get(0);
                boat.takeDamage(1);
            }
        }
    }

    private boolean isBoatInRange() {
        return !getObjectsInRange(attackRange, Boat.class).isEmpty();
    }

    // 🟢 Tidak mirror lagi saat berubah kembung/kempes
    private void setKembung(boolean kembung) {
        this.isKembung = kembung;
        GreenfootImage img = new GreenfootImage(isKembung ? imgKembung : imgKempes);
        setImage(img);
    }

    private void move() {
        World world = getWorld();
        if (world == null) return;
        
        java.util.List<Boat> boats = world.getObjects(Boat.class);
        if (!boats.isEmpty()) {
            Boat boat = boats.get(0);
            int boatX = boat.getX();
            int boatY = boat.getY();
            
            int newX = getX();
            int newY = getY();
            int currentSpeed = isKembung ? speed + 1 : speed;

            if (boatX > getX() + 5) {
                newX += currentSpeed;
                direction = 1;
            } else if (boatX < getX() - 5) {
                newX -= currentSpeed;
                direction = -1;
            }
            
            bob = (bob + 1) % 80;
            int offset = (bob < 40) ? 1 : -1;
            if (boatY > getY()) newY += currentSpeed;
            else if (boatY < getY()) newY -= currentSpeed;
            setLocation(newX, newY + offset);
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (!hurtIFrame.hasElapsed(hurtCooldownMs)) return;
        hurtIFrame.mark();
        health -= amount;
        flash();
        Greenfoot.playSound("Attack.mp3");
        if (health <= 0) {
            addScoreToWorld(5);
            getWorld().removeObject(this);
        }
    }

    private void flash() {
        GreenfootImage img = getImage();
        int old = img.getTransparency();
        img.setTransparency(140);
        Greenfoot.delay(5);
        if (getWorld() != null) img.setTransparency(old);
    }

    private void addScoreToWorld(int score) {
        World world = getWorld();
        if (world instanceof GameWorld)
            ((GameWorld) world).addScore(score);
    }
    
    public int getFacing() { 
        return direction; 
    }
}