import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Enemy0Pattern3 extends Sprite {
    private GalagaGame game;

    private Image image, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6;

    private Timer collisionImageTimer;

    private int collisionImageNum = 0;

    private double angle; // 각도 (라디안)
    private double speed = 0.035; // 각도 변화 속도
    private boolean isCircularMotion = false; // 원형 움직임 여부
    private int centerX; // 원 중심 X
    private int centerY; // 원 중심 Y
    private int radius = 70; // 원 반지름
    private double rotationAngle; // 이미지 회전 각도

    public Enemy0Pattern3(GalagaGame game, ArrayList<Image> images, Image image, int x, int y) {
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

        dy = 2; // 초기 세로 이동 속도
    }

    @Override
    public void move() {
        if (x < -10) {
            game.removeSprite(this);
        }

        // y가 200 이상이 되면 원형 움직임 시작
        if (y >= 200 && !isCircularMotion) {
            isCircularMotion = true; // 원형 움직임 활성화
            // 원의 중심을 객체의 오른쪽에 설정
            centerX = x + radius;
            centerY = y;
            angle = Math.PI; // 초기 각도를 180도 (왼쪽 끝)으로 설정
        }

        if (isCircularMotion && !getIsIncollision()) {
            // 원형 궤적 좌표 계산
            x = (int) (centerX + radius * Math.cos(angle));
            y = (int) (centerY + radius * Math.sin(angle));
            angle -= speed; // 각도 감소 (시계 방향)

            // 이미지 회전 각도 계산 (접선 방향)
            rotationAngle = Math.toDegrees(angle) - 180; // 접선 방향 보정


            // 한 바퀴 돌고 원형 움직임 종료
            if (angle <= -Math.PI / 2) {
                isCircularMotion = false; // 원형 움직임 종료
                angle = 0; // 각도 초기화
                dx = -2; // 직선 이동 방향 설정
                dy = 0;
            }
        } else {
            // y가 200 이하일 때는 직선 이동
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

        // 이미지 중심 계산
        int imageCenterX = x + this.getWidth() / 2;
        int imageCenterY = y + this.getHeight() / 2;

        // 현재 Graphics2D의 상태 저장
        AffineTransform oldTransform = g2d.getTransform();

        // 회전 설정 (접선 방향에 따른 rotationAngle)
        g2d.rotate(Math.toRadians(rotationAngle), imageCenterX, imageCenterY);

        // 이미지 그리기
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
        // 이전 Graphics2D 상태로 복구
        g2d.setTransform(oldTransform);
    }
}
