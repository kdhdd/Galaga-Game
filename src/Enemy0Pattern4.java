import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy0Pattern4 extends Sprite {
    private GalagaGame game;

    private double angle; // 각도 (라디안)
    private double speed = 0.035; // 각도 변화 속도
    private boolean isCircularMotion = false; // 원형 움직임 여부
    private int centerX; // 원 중심 X
    private int centerY; // 원 중심 Y
    private int radius = 70; // 원 반지름
    private double rotationAngle; // 이미지 회전 각도

    public Enemy0Pattern4(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;

        dy = 2;
    }

    @Override
    public void move() {
        if (y >= 200 && !isCircularMotion) {
            isCircularMotion = true;
            centerX = x - radius;
            centerY = y;
            angle = -Math.PI * 2;
        }

        if (isCircularMotion) {
            x = (int) (centerX + radius * Math.cos(angle));
            y = (int) (centerY + radius * Math.sin(angle));
            angle += speed;

            rotationAngle = Math.toDegrees(angle) + 360;

            if (angle >= -Math.PI / 2) {
                isCircularMotion = false;
                dx = 2;
                dy = 0;
            }
        } else {
            super.move();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int imageCenterX = x + this.getWidth() / 2;
        int imageCenterY = y + this.getHeight() / 2;

        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(rotationAngle), imageCenterX, imageCenterY);

        g2d.drawImage(this.getImage(), x, y, null);

        g2d.setTransform(oldTransform);
    }
}
