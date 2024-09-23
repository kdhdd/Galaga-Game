import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GalagaGame extends JPanel implements KeyListener {

    private boolean running = true;

    private ArrayList<Sprite> sprites = new ArrayList<>();
    private Sprite starship;

    private BufferedImage alienImage;
    private BufferedImage shotImage;
    private BufferedImage shipImage;
    private BufferedImage bossImage;
    private BufferedImage backgroundImage;

    private int level = 1;
    private int score = 0;
    private int lives = 3;

    private int initialX = 370;
    private int initialY = 500;

    public void resetStarshipPosition() {
        starship.setX(initialX);
        starship.setY(initialY);
        starship.setDx(0);  // 움직임 초기화
        starship.setDy(0);
    }

    public BufferedImage scaleImage(BufferedImage src, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(src, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }

    public GalagaGame() {
        JFrame frame = new JFrame("Galaga Game");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        frame.setSize(800, 600);
        frame.setLocation((screenSize.width - frame.getWidth() )/ 2,
                (screenSize.height - frame.getHeight() )  / 2);
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            shotImage = scaleImage(ImageIO.read(new File("image/fire.png")), 30, 30);
            shipImage = scaleImage(ImageIO.read(new File("image/starship.png")), 50, 50);
            alienImage = scaleImage(ImageIO.read(new File("image/alien.png")), 50, 50);
            bossImage = scaleImage(ImageIO.read(new File("image/boss.png")), 300, 300);
            backgroundImage = scaleImage(ImageIO.read(new File("image/preview.png")), 800, 600);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.requestFocus();
        this.initSprites(initialX, initialY);
        addKeyListener(this);
    }

    private void initSprites(int x, int y) {
        starship = new StarShipSprite(this, shipImage, x, y);
        sprites.add(starship);
        if (!isBossPresent()) {
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 12; i++) {
                    Sprite alien = new AlienSprite(this, alienImage,
                            100 + (i * 50), (50) + j * 30);
                    sprites.add(alien);
                }
            }
        }
    }

    private void startGame() {
        sprites.clear();
        initSprites(initialX, initialY);
        level = 1;
        lives = 3;
        score = 0;
        running = true;
    }

    public void endGame() {
        running = false;
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        startGame();
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public boolean areAliensRemaining() {
        for (Sprite sprite : sprites) {
            if (sprite instanceof AlienSprite) {
                return true;
            }
        }
        return false;
    }

    public void fire() {
        ShotSprite shot = new ShotSprite(this, shotImage,
                starship.getX() + 10, starship.getY() - 30);
        sprites.add(shot);
    }

    public void increaseScore() {
        score += 10;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0) {
            endGame();
        } else {
            resetStarshipPosition();
        }
    }

    public boolean isBossPresent() {
        for (Sprite sprite : sprites) {
            if (sprite instanceof BossSprite) {
                return true;
            }
        }
        return false;
    }

    public void boss() {
        Sprite boss = new BossSprite(this, bossImage, 235, 20);
        sprites.add(boss);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);

        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = (Sprite) sprites.get(i);

            sprite.draw(g);
        }

        // 점수와 생명 표시
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Level: " + level, 10, 45);
        g.drawString("Lives: " + lives, 700, 20);
    }

    public void gameLoop() {

        while (running) {
            for (int i = 0; i < sprites.size(); i++) {
                Sprite sprite = (Sprite) sprites.get(i);
                sprite.move();
            }

            for (int p = 0; p < sprites.size(); p++) {
                for (int s = p + 1; s < sprites.size(); s++) {
                    Sprite me = (Sprite) sprites.get(p);
                    Sprite other = (Sprite) sprites.get(s);

                    if (me.checkCollision(other)) {
                        me.handleCollision(other);
                        other.handleCollision(me);
                    }
                }
            }

            if (!areAliensRemaining() && !isBossPresent()) {
                level++;
                sprites.clear();
                initSprites(starship.getX(), starship.getY());
            }

            if (score >= 100 && !isBossPresent()) {
                sprites.clear();
                boss();
                initSprites(starship.getX(), starship.getY());
            }

            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            starship.setDx(-3);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            starship.setDx(+3);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            starship.setDy(-3);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            starship.setDy(+3);
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            fire();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            starship.setDx(0);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            starship.setDx(0);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            starship.setDy(0);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            starship.setDy(0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public static void main(String[] args) {
        GalagaGame g = new GalagaGame();
        g.gameLoop();
    }
}
