import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShotSprite extends Sprite{
    private GalagaGame game;

    private Timer removeEnemyTimer;

    public ShotSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        dy = -10;
    }

    @Override
    public void move() {
        super.move();
        // 총알이 화면 밖으로 충분히 나가서 다시는 보이지 않을 위치까지 이동했을 때 제거
        if (y < -100) {
            game.removeSprite(this);
        } else if (y > 700) {
            game.removeSprite(this);
        }
    }

    @Override
    public void handleCollision(Sprite other) {
        if (other instanceof Enemy0Pattern1and2 || other instanceof Enemy0Pattern3 ||
                other instanceof Enemy0Pattern4 || other instanceof Enemy0Pattern5 ||
                other instanceof Enemy0Pattern6 || other instanceof Enemy0Pattern7) {
            if (!other.getIsIncollision()) {
                other.collisionMotion();
                removeEnemyTimer = new Timer(100, new ActionListener() {
                    int removeEnemyNum = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeEnemyNum++;

                        if (removeEnemyNum == 7) {
                            game.removeSprite(other);
                            removeEnemyTimer.stop();
                        }
                    }
                });
                removeEnemyTimer.start();
                game.increaseScore(); // 점수 추가
                game.removeSprite(this);
            }
        }

        if (other instanceof MidEnemyPattern0 || other instanceof LargeEnemy) {
            game.removeSprite(this);
        }

        if (other instanceof StarShipSprite) {
            game.removeSprite(this);
            game.loseLife();
        }
    }
}
