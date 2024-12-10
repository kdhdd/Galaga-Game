import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LargeEnemy extends Sprite {
    private GalagaGame game;
    private int health;

    private Image image, warningImage, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6;
    private boolean isWarning = false, isWarningState = false;

    private Timer timer, collisionImageTimer, removeEnemyTimer;

    private int collisionImageNum = 0;

    public LargeEnemy(GalagaGame game, ArrayList<Image> images, Image image, Image image2, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.health = 300;
        this.image = image;
        warningImage = image2;
        explosion0 = new ImageIcon(images.get(0)).getImage();
        explosion1 = new ImageIcon(images.get(1)).getImage();
        explosion2 = new ImageIcon(images.get(2)).getImage();
        explosion3 = new ImageIcon(images.get(3)).getImage();
        explosion4 = new ImageIcon(images.get(4)).getImage();
        explosion5 = new ImageIcon(images.get(5)).getImage();
        explosion6 = new ImageIcon(images.get(6)).getImage();

        dy = -1;

        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWarning) {
                    isWarningState = !isWarningState;
                }
            }
        });
        timer.start();
    }

    @Override
    public void move() {
        if (y < 100 && y > 60 && !getIsIncollision()) {
            dx = 1;
        }

        if (y < 50) {
            dy = 0;
            if (x < 200) {
                dx = 1;
            } else if (x > 600) {
                dx = -1;
            }
        }

        super.move();
    }

    @Override
    public void collisionMotion() {
        if (getIsIncollision()) return;

        setIsIncollision(true);

        dx = 0;
        dy = 0;

        collisionImageTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collisionImageNum++;

                if (collisionImageNum == 8) {
                    collisionImageNum = 0;
                    collisionImageTimer.stop();
                }
            }
        });
        collisionImageTimer.start();
    }

    @Override
    public void draw(Graphics g) {
        switch (collisionImageNum) {
            case 0:
                if (isWarning && isWarningState)
                    g.drawImage(warningImage, x, y, null);
                else
                    g.drawImage(image, x, y, null);
                break;
            case 1:
                g.drawImage(explosion0, x, y, null);
                break;
            case 2:
                g.drawImage(explosion1, x, y, null);
                break;
            case 3:
                g.drawImage(explosion2, x, y, null);
                break;
            case 4:
                g.drawImage(explosion3, x, y, null);
                break;
            case 5:
                g.drawImage(explosion4, x, y, null);
                break;
            case 6:
                g.drawImage(explosion5, x, y, null);
                break;
            case 7:
                g.drawImage(explosion6, x, y, null);
                break;
        }
    }

    @Override
    public void handleCollision(Sprite other) {
        if (other instanceof ShotSprite) {
            health -= 10;
            if (health <= 30) {
                isWarning = true;
            }

            if (health <= 0) {
                if (!getIsIncollision()) {
                    collisionMotion();
                    removeEnemyTimer = new Timer(100, new ActionListener() {
                        int removeEnemyNum = 0;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            removeEnemyNum++;

                            if (removeEnemyNum == 7) {
                                game.removeSprite(LargeEnemy.this);
                                timer.stop();
                                removeEnemyTimer.stop();
                            }
                        }
                    });
                    removeEnemyTimer.start();
                }
            }
            game.increaseLargeScore();
        }
    }
}
