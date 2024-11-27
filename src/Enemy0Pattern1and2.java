import java.awt.*;

public class Enemy0Pattern1and2 extends Sprite{
    private GalagaGame game;

    public Enemy0Pattern1and2(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        //dx = (x < 400) ? 2 : -2;

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

    public void pattern1() {
        dx = 2;
    }

    public void pattern2() {
        dx = -2;
    }

}
