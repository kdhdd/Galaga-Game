import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Enemy0Pattern6 extends Sprite {
    private GalagaGame game;

    private Image image, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6;

    private Timer collisionImageTimer;

    private int collisionImageNum = 0;

    private double angle; // 각도 (라디안)
    private double speed = 0.02; // 각도 변화 속도
    private boolean isCircularMotion = false; // 원형 움직임 여부
    private int centerX; // 원 중심 X
    private int centerY; // 원 중심 Y
    private int radius = 230; // 원 반지름
    private double rotationAngle; // 이미지 회전 각도

    public Enemy0Pattern6(GalagaGame game, ArrayList<Image> images, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        this.image = image;
        explosion0 = new ImageIcon(images.get(0)).getImage();
        explosion1 = new ImageIcon(images.get(1)).getImage();
        explosion2 = new ImageIcon(images.get(2)).getImage();
        explosion3 = new ImageIcon(images.get(3)).getImage();
        explosion4 = new ImageIcon(images.get(4)).getImage();
        explosion5 = new ImageIcon(images.get(5)).getImage();
        explosion6 = new ImageIcon(images.get(6)).getImage();
        this.angle = -Math.PI / 2;
        this.rotationAngle = Math.toDegrees(angle) + 180;

        dx = -4;
    }

    @Override
    public void move() {
        if (y < 0) game.removeSprite(this);

        if (x <= 400 && !isCircularMotion) {
            isCircularMotion = true;
            centerX = x;
            centerY = y + radius;
        }

        if (isCircularMotion && !getIsIncollision()) {
            x = (int) (centerX + radius * Math.cos(angle));
            y = (int) (centerY + radius * Math.sin(angle));
            angle -= speed;

            rotationAngle = Math.toDegrees(angle) + 180;

            if (angle <= -Math.PI * 2) {
                isCircularMotion = false;
                dy = -4;
                dx = 0;
            }
        } else {
            super.move();
        }
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
}
