import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StarShipSprite extends Sprite{
    private GalagaGame game;
    private Image image, playerInvincibleImage, fireball0, fireball1, fireball2, fireball3, fireball4, fireball5, fireball6, tmp;
    private Timer timer, resetTimer, invincibilityTimer, imageTimer;

    private boolean isInCollision = false;
    private boolean isInvincible = false; // 무적 상태 여부
    private boolean invincibleState = false;
    private static final int INVINCIBLE_DURATION = 1000; // 무적 지속 시간 (ms)

    private int n = 0;

    public StarShipSprite(GalagaGame game, ArrayList<Image> images, Image image, Image playerInvincibleImage, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.image = image;
        this.playerInvincibleImage = playerInvincibleImage;
        fireball0 = images.get(0);
        fireball1 = images.get(1);
        fireball2 = images.get(2);
        fireball3 = images.get(3);
        fireball4 = images.get(4);
        fireball5 = images.get(5);
        fireball6 = images.get(6);
        tmp = image;
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
        // dy가 양수일 때는 아래쪽으로 이동
        // 500px 이상으로 가면 더 이상 아래쪽으로 이동하지 않도록 합니다.
        if ((dy > 0) && (y > 500)) {
            return;
        }
        // dy가 음수일 때는 위쪽으로 이동
        // 위쪽 끝을 기준으로 10px를 두고, 그 이하로는 이동하지 않도록 합니다.
        if ((dy < 0) && (y < 10)) {
            return;
        }
        super.move();
    }

    // 충돌 모션
    @Override
    public void collisionMotion() {
        if (isInCollision) {
            return; // 이미 충돌 중이라면 실행하지 않음
        }

        imageTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isInCollision) {
                    invincibleState = !invincibleState;
                }
            }
        });
        imageTimer.start();

        isInCollision = true;
        game.setStarshipIsCollision(true);

        setDx(0);
        setDy(0);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n++;

                if (n == 1) {
                    startInvincibilityTimer();
                } else if (n == 8) {
                    startResetTimer(game);
                } else if (n > 30) {
                    n = 0;
                    isInCollision = false;
                    invincibleState = false;
                    timer.stop();
                }
            }
        });
        timer.start();

/*        timer = new Timer(100, new ActionListener() { // 100ms마다 실행
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (num) {
                    case 0:
                        image = fireball0;
                        break;
                    case 1:
                        image = fireball1;
                        break;
                    case 2:
                        image = fireball2;
                        break;
                    case 3:
                        image = fireball3;
                        break;
                    case 4:
                        image = fireball4;
                        break;
                    case 5:
                        image = fireball5;
                        break;
                    case 6:
                        image = fireball6;
                        break;
                    default:
                        startResetTimer(game);
                        startInvincibilityTimer(); // 무적 지속 타이머 시작
                        image = tmp;
                        setImage(image);
                        isInCollision = false;
                        timer.stop(); // 모든 이미지 변경이 끝나면 타이머 중지
                        return;
                }
                setImage(image);
                num++;
            }
        });
        timer.start(); // 타이머 시작*/
    }

    @Override
    public void draw(Graphics g) {
        switch (n) {
            case 0:
                g.drawImage(image, x, y, null);
                break;
            case 1:
                g.drawImage(fireball0, x, y, null);
                break;
            case 2:
                g.drawImage(fireball1, x, y, null);
                break;
            case 3:
                g.drawImage(fireball2, x, y, null);
                break;
            case 4:
                g.drawImage(fireball3, x, y, null);
                break;
            case 5:
                g.drawImage(fireball4, x, y, null);
                break;
            case 6:
                g.drawImage(fireball5, x, y, null);
                break;
            case 7:
                g.drawImage(fireball6, x, y, null);
                break;
            default:
                if (isInCollision && invincibleState) {
                    g.drawImage(playerInvincibleImage, x, y, null);
                } else {
                    g.drawImage(image, x, y, null);
                }
                break;
        }
    }

    public void startResetTimer(GalagaGame game) {
        if (!isInCollision) {
            return; // 충돌 상태가 아니라면 실행하지 않음
        }

        resetTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetStarshipPosition(); // 스타쉽 위치 리셋
                resetTimer.stop();
            }
        });
        resetTimer.start();
    }

    // 무적 지속 타이머
    private void startInvincibilityTimer() {
        isInvincible = true;

        invincibilityTimer = new Timer(100, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num > 31) {

                    isInvincible = false; // 무적 상태 해제
                    invincibilityTimer.stop();
                    imageTimer.stop();
                }
            }
        });
        invincibilityTimer.start();
    }

    @Override
    public void handleCollision(Sprite other) {
        if (!isInCollision && !isInvincible) {
            collisionMotion();
            // 충돌한 객체가 만약 외계인 우주선이면 게임을 종료합니다.
            if (other instanceof Enemy0Pattern1and2 || other instanceof Enemy0Pattern3 ||
                    other instanceof Enemy0Pattern4 || other instanceof Enemy0Pattern5) {
                game.removeSprite(other);
                game.loseLife();
            }

            if (other instanceof BossSprite || other instanceof MidEnemyPattern0) {
                game.loseLife();
            }
        }
    }
}
