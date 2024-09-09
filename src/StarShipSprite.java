import java.awt.*;

public class StarShipSprite extends Sprite{
    private GalagaGame game;

    public StarShipSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        dx = 0;
        dy = 0;
    }

    @Override
    public void move() {
        // dx가 음수일 때는 왼쪽으로 이동
        // 좌측 끝을 기준으로 10px를 두고, 그 이하로는 이동하지 않도록 합니다.
        if ((dx < 0) && (x < 10)) {
            return;
        }
        // dx가 양수일 때는 오른쪽으로 이동
        // 800px 이상으로 가면 더 이상 오른쪽으로 이동하지 않도록 합니다.
        if ((dx > 0) && ( x > 800)) {
            return;
        }
        super.move();
    }

    @Override
    public void handleCollision(Sprite other) {
        // 충돌한 객체가 만약 외계인 우주선이면 게임을 종료합니다.
        if (other instanceof AlienSprite) {
            game.loseLife();
        }
    }
}
