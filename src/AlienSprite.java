import java.awt.*;

public class AlienSprite extends Sprite{
    private GalagaGame game;

    public AlienSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        dx = -3;    // 초기에는 왼쪽으로 이동한다.
    }

    @Override
    public void move() {
        if (((dx < 0) && (x < 10)) || ((dx > 0) && (x > 800))) {
            dx = -dx;
            y += 10;
            if (y > 600) {
                game.loseLife();
            }
        }
        super.move();
    }
    // 외계인 우주선이 왼쪽으로 움직이면서 내려오게 되는데 게임판의 경계선에 닿으면 방향을 반대로 변경한다.
    // 만약 y의 값이 600을 넘으면 외계인 우주선이 우리 우주선을 잡은 것이므로 게임을 종료한다.
}
