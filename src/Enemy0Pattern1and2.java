import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Enemy0Pattern1and2 extends Sprite{
    private GalagaGame game;
    private Image image, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6;

    private Timer collisionImageTimer, setCollisionTimer;

    private int collisionImageNum = 0;

    public Enemy0Pattern1and2(GalagaGame game, ArrayList<Image> images, Image image, int x, int y) {
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

        dy = 4;
    }

    @Override
    public void move() {
        if (y > 700)
            game.removeSprite(this);

        super.move();
    }

    public void pattern1() {
        dx = 2;
    }

    public void pattern2() {
        dx = -2;
    }

    @Override
    public void collisionMotion() {
        if (getIsIncollision()) return;

/*        setCollisionTimer = new Timer(5, new ActionListener() {
            int n = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                n++;

                if (n == 1) {
                    setIsIncollision(true);
                    setCollisionTimer.stop();
                }
            }
        });
        setCollisionTimer.start();*/
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
        switch (collisionImageNum) {
            case 0:
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
}
