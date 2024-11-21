import java.awt.*;
import java.util.Random;

public class Enemy0Sprite extends Sprite{
    private GalagaGame game;

    public Enemy0Sprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        Random random = new Random();
        //dx = random.nextBoolean() ? 5 : -5;
        dx = (x < 400) ? 2 : -2;

        dy = 3;
    }

    @Override
    public void move() {
/*        if (((dx < 0) && (x < 10)) || ((dx > 0) && (x > 750))) {
            dx = -dx;
            y += 10;
            if (y > 500) {
                game.loseLife();
            }
        }*/
        if (y > 700)
            game.removeSprite(this);

        if (y >= 100 && dy > 0) {
            dy = 2; // 속도를 줄임
        }

        super.move();
    }
}
