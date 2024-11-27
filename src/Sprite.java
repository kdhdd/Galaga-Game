import java.awt.*;

public class Sprite {
    protected int x;        // 현재 위치의 x좌표
    protected int y;        // 현재 위치의 y좌표
    protected int dx;       // 단위시간에 움직이는 x방향 거리
    protected int dy;       // 단위시간에 움직이는 y방향 거리
    private Image image;    // 스프라이트가 가지고 있는 이미지

    // 생성자
    public Sprite(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    // 스프라이트의 가로 길이를 반환한다.
    public int getWidth() {
        return image.getWidth(null);
    }

    // 스프라이트의 세로 길이를 반환한다.
    public int getHeight() {
        return image.getHeight(null);
    }

    // 스프라이트를 화면에 그린다.
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    // 스프라이트를 움직인다.
    public void move() {
        x += dx;
        y += dy;
    }

    // dx를 설정한다.
    public void setDx(int dx) {
        this.dx = dx;
    }

    // dy를 설정한다.
    public void setDy(int dy) {
        this.dy = dy;
    }

    // dx를 반환한다.
    public int getDx() {
        return dx;
    }

    // dy를 반환한다.
    public int getDy() {
        return dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // x를 반환한다.
    public int getX() {
        return x;
    }

    // y를 반환한다.
    public int getY() {
        return y;
    }

    // 다른 스프라이트와의 충돌 여부를 계산한다. 충돌이면 true를 반환한다.
    public boolean checkCollision(Sprite other) {
        Rectangle myRect = new Rectangle(x, y, getWidth(), getHeight());
        Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());

        // Rectangle 클래스의 intersects 메서드로 겹치는지를 검사하여 반환한다.
        return myRect.intersects(otherRect);
    }

    // 충돌을 처리한다.
    public void handleCollision(Sprite other) {

    }

    // 충돌 모션
    public void collisionMotion() {

    }
}
