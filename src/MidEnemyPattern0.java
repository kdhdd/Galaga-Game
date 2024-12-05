import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MidEnemyPattern0 extends Sprite {
    private GalagaGame game;
    private int health;

    private Image image, warningImage, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6;
    private boolean isWarning = false, isWarningState = false;
    private Timer timer, collisionImageTimer, removeEnemyTimer;

    private int collisionImageNum = 0;

    private double angle; // 각도 (라디안)
    private double speed; // 각도 변화 속도
    private boolean isCircularMotion = false; // 원형 움직임 여부
    private int centerX; // 원 중심 X
    private int centerY; // 원 중심 Y
    private int radius; // 원 반지름
    private double rotationAngle; // 이미지 회전 각도
    private boolean turn1Active = false, turn2Active = false, turn3Active = false;

    public MidEnemyPattern0(GalagaGame game, ArrayList<Image> images, Image image, Image image2, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.health = 40;
        this.image = image;
        warningImage = image2;
        explosion0 = new ImageIcon(images.get(0)).getImage();
        explosion1 = new ImageIcon(images.get(1)).getImage();
        explosion2 = new ImageIcon(images.get(2)).getImage();
        explosion3 = new ImageIcon(images.get(3)).getImage();
        explosion4 = new ImageIcon(images.get(4)).getImage();
        explosion5 = new ImageIcon(images.get(5)).getImage();
        explosion6 = new ImageIcon(images.get(6)).getImage();

        dy = 2;

        // Timer를 초기화
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
        if (y > 700) {
            game.removeSprite(this);
        }

        if (!isCircularMotion) {
            if (y >= 100 && y <= 110 && !turn1Active) {
                isCircularMotion = true;
                turn1Active = true;

                radius = 100;
                speed = 0.025;

                centerX = x + radius;
                centerY = y;
                angle = Math.PI;
            } else if (x >= 530 && x <= 540 && !turn2Active) {
                isCircularMotion = true;
                turn2Active = true;
                radius = 30;
                speed = 0.04;

                centerX = x;
                centerY = y - radius;
                angle = Math.PI / 2;
            } else if (x >= 180 && x <= 190 && !turn3Active) {
                isCircularMotion = true;
                turn3Active = true;
                radius = 100;
                speed = 0.025;

                centerX = x;
                centerY = y + radius;
                angle = -Math.PI / 2;
            }
        }

        if (isCircularMotion && !getIsIncollision()) {
            if (turn1Active) {
                x = (int) (centerX + radius * Math.cos(angle));
                y = (int) (centerY + radius * Math.sin(angle));
                angle -= speed;

                rotationAngle = Math.toDegrees(angle) - 180;

                if (angle <= Math.PI / 2) {
                    isCircularMotion = false;
                    turn1Active = false;

                    angle = 0;
                    dx = 2;
                    dy = 0;
                }
            } else if (turn2Active) {
                x = (int) (centerX + radius * Math.cos(angle));
                y = (int) (centerY + radius * Math.sin(angle));
                angle -= speed;

                rotationAngle = Math.toDegrees(angle) - 180;

                if (angle <= -Math.PI / 2) {
                    isCircularMotion = false;
                    turn2Active = false;

                    angle = 0;
                    dx = -2;
                    dy = 0;
                }
            } else if (turn3Active) {
                x = (int) (centerX + radius * Math.cos(angle));
                y = (int) (centerY + radius * Math.sin(angle));
                angle -= speed;

                rotationAngle = Math.toDegrees(angle) - 180;

                if (angle <= -Math.PI) {
                    isCircularMotion = false;
                    turn3Active = false;

                    angle = 0;
                    dx = 0;
                    dy = 2;
                }
            }
        }
        super.move();
    }

    @Override
    public void collisionMotion() {
        if (getIsIncollision()) return;

        setIsIncollision(true);

        setDx(0);
        setDy(0);

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
        Graphics2D g2d = (Graphics2D) g;

        int imageCenterX = x + this.getWidth() / 2;
        int imageCenterY = y + this.getHeight() / 2;

        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(rotationAngle), imageCenterX, imageCenterY);

        switch (collisionImageNum) {
            case 0:
                if (isWarning && isWarningState)
                    g2d.drawImage(warningImage, x, y, null);
                else
                    g2d.drawImage(image, x, y, null);
                break;
            case 1:
                g2d.drawImage(explosion0, x, y, null);
                break;
            case 2:
                g2d.drawImage(explosion1, x, y, null);
                break;
            case 3:
                g2d.drawImage(explosion2, x, y, null);
                break;
            case 4:
                g2d.drawImage(explosion3, x, y, null);
                break;
            case 5:
                g2d.drawImage(explosion4, x, y, null);
                break;
            case 6:
                g2d.drawImage(explosion5, x, y, null);
                break;
            case 7:
                g2d.drawImage(explosion6, x, y, null);
                break;
        }
        g2d.setTransform(oldTransform);
    }

    @Override
    public void handleCollision(Sprite other) {
        if (other instanceof ShotSprite) {
            health -= 10;
            if (health <= 20) {
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
                                game.removeSprite(MidEnemyPattern0.this);
                                timer.stop();
                                removeEnemyTimer.stop();
                            }
                        }
                    });
                    removeEnemyTimer.start();
                }

            }
        }
    }
}
