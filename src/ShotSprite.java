import java.awt.*;

public class ShotSprite extends Sprite{
    private GalagaGame game;

    public ShotSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        dy = -3;
    }

    @Override
    public void move() {
        super.move();
        // 총알이 화면 밖으로 충분히 나가서 다시는 보이지 않을 위치까지 이동했을 때 제거
        if (y < -100) {
            game.removeSprite(this);
        }
    }

    @Override
    public void handleCollision(Sprite other) {
        if (other instanceof AlienSprite) {
            game.removeSprite(this);
            game.removeSprite(other);
            game.increaseScore(); // 점수 추가
        }

        if (other instanceof BossSprite) {
            game.removeSprite(this);
        }
    }
}
