import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class MidEnemyPattern0 extends Sprite {
    private GalagaGame game;
    private int health;

    private Image warningImage;
    private boolean isWarning = false, isWarningState = false;
    private Timer timer;

    private double angle; // 각도 (라디안)
    private double speed; // 각도 변화 속도
    private boolean isCircularMotion = false; // 원형 움직임 여부
    private int centerX; // 원 중심 X
    private int centerY; // 원 중심 Y
    private int radius; // 원 반지름
    private double rotationAngle; // 이미지 회전 각도
    private boolean turn1Active = false, turn2Active = false, turn3Active = false;

    public MidEnemyPattern0(GalagaGame game, Image image, Image image2, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.health = 40;

        warningImage = image2;

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

        if (isCircularMotion) {
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
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int imageCenterX = x + this.getWidth() / 2;
        int imageCenterY = y + this.getHeight() / 2;

        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(rotationAngle), imageCenterX, imageCenterY);

        if (isWarning && isWarningState) {
            g2d.drawImage(warningImage, x, y, null);
        } else {
            g2d.drawImage(this.getImage(), x, y, null);
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
                game.removeSprite(this);
                timer.stop();
            }
        }
    }
}
