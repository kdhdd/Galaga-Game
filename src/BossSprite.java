import java.awt.*;

public class BossSprite extends Sprite{
    private GalagaGame game;
    private int health;

    public BossSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.health = 100;
    }
    @Override
    public void move() {
        if (Math.random() < 0.05) {
            fireAtPlayer();
        }
    }

    @Override
    public void handleCollision(Sprite other) {
        if (other instanceof ShotSprite) {
            System.out.println("Boss hit by shot!");
            health -= 10;
            System.out.println("Boss health: " + health);
            if (health <= 0) {
                game.removeSprite(this);
                game.increaseScore();
                game.endGame();
            }
        }
    }

    public void fireAtPlayer() {
        Sprite bossShot = new ShotSprite(game, game.getBossShotImage(), this.getX() + 140, this.getY() + 300);
        bossShot.setDy(3);
        game.addSprite(bossShot);
    }
}
