import java.awt.*;

public class EnemyBullet extends Sprite {
    private GalagaGame game;

    private int playerX;
    private int playerY;

    private double speed = 5.0;

    public EnemyBullet(GalagaGame game, Image image, int x, int y, int playerX, int playerY) {
        super(image, x, y);
        this.game = game;

        this.playerX = playerX;
        this.playerY = playerY;

        moveToPlayer();
    }

    public void moveToPlayer() {
        double deltaX = playerX - x;
        double deltaY = playerY - y;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double unitVectorX = deltaX / distance;
        double unitVectorY = deltaY / distance;

        dx = (int) (speed * unitVectorX);
        dy = (int) (speed * unitVectorY);
    }

    @Override
    public void move() {
        if (y > 600 || y < -100) {
            game.removeSprite(this);
        }

        super.move();
    }
}
