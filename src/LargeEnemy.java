import java.awt.*;

public class LargeEnemy extends Sprite {
    private GalagaGame game;
    private int health;

    public LargeEnemy(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.health = 60;

        dy = -2;


    }

    @Override
    public void move() {
        if (y < -10) {
            game.removeSprite(this);
        }

        super.move();
    }
}
