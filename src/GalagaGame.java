import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private BufferedImage smallEnemy0Image;
    private BufferedImage smallEnemy1Image;
    private BufferedImage smallEnemy2Image;

    private BufferedImage midEnemy0Image;
    private BufferedImage midEnemy0WarningImage;

    private BufferedImage shotImage;
    private BufferedImage bossShotImage;
    private BufferedImage shipImage;
    private BufferedImage bossImage;
    private BufferedImage backgroundImage;

    private int level = 1;
    private int score = 0;
    private int lives = 3;

    private Timer t;
    private Timer restartTimer;
    private Timer pattern1, pattern2, pattern3, pattern4, pattern5, pattern6;
    private Timer midPattern1;

    private int initialX = 370;
    private int initialY = 500;

    public void resetStarshipPosition() {
        starship.setX(initialX);
        starship.setY(initialY);
        starship.setDx(0);  // 움직임 초기화
        starship.setDy(0);
    }

    public BufferedImage getBossShotImage() {
        return bossShotImage;
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
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
            bossShotImage = scaleImage(ImageIO.read(new File("image/bossShot.png")), 50, 50);
            shipImage = scaleImage(ImageIO.read(new File("image/player.png")), 50, 50);

            smallEnemy0Image = scaleImage(ImageIO.read(new File("image/smallEnemy0.png")), 30, 30);
            smallEnemy1Image = scaleImage(ImageIO.read(new File("image/smallEnemy1.png")), 30, 30);
            smallEnemy2Image = scaleImage(ImageIO.read(new File("image/smallEnemy2.png")), 28, 27);

            midEnemy0Image = scaleImage(ImageIO.read(new File("image/midEnemy0.png")), 70, 60);
            midEnemy0WarningImage = scaleImage(ImageIO.read(new File("image/midEnemy0Warning.png")), 70, 60);

            bossImage = scaleImage(ImageIO.read(new File("image/boss.png")), 300, 300);
            backgroundImage = scaleImage(ImageIO.read(new File("image/background.png")), 800, 600);

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
/*        if (!isBossPresent()) {
            for (int j = 0; j < 1; j++) {
                for (int i = 0; i < 2; i++) {
                    Sprite alien = new Enemy0Pattern1and2(this, enemy0Image,
                            100 + (i * 550), 10);
                    sprites.add(alien);
                }
            }
        }*/
    }

    private long spawnTick = 0;

    // 랜덤하게 x 매판 똑같은 패턴으로 고칠 것
    /*
    0 200
    3 170
    9 440
    10 50
     */
/*    private void generateEnemy() {
        if (spawnTick < System.currentTimeMillis()) {
            spawnTick = System.currentTimeMillis() + new Random().nextInt(2000) + 500;
            sprites.add(new Enemy0Pattern1and2(this, enemy0Image, new Random().nextInt(750) + 50, 10));
        }
    }*/

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

    public boolean areEnemy0Remaining() {
        for (Sprite sprite : sprites) {
            if (sprite instanceof Enemy0Pattern1and2) {
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

        if (running) {
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

/*            if (!areEnemy0Remaining() && !isBossPresent()) {
                level++;
                sprites.clear();
                initSprites(starship.getX(), starship.getY());
            }*/
/*            if (!isBossPresent())
                generateEnemy();

            if (score >= 100 && !isBossPresent()) {
                sprites.clear();
                boss();
                initSprites(starship.getX(), starship.getY());
            }*/

            repaint();
        }
    }

    private boolean isFiring = false;

    // 3번째 방법 MyFrame 클래스가 이벤트를 처리
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isFiring) {
            fire();
            isFiring = true;
        }
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isFiring = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public void start() {
        t = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
            }
        });
        t.start();

        restartTimer = new Timer(1000, new ActionListener() {
            int elapsedSeconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedSeconds++;

                if (elapsedSeconds % 23 == 0)
                    pattern1.start();

                if (elapsedSeconds % 25 == 0)
                    pattern2.start();

                if (elapsedSeconds % 17 == 0)
                    pattern5.start();

                if (elapsedSeconds % 40 == 0)
                    midPattern1.start();

/*
                if (elapsedSeconds % 5 == 0)
                    pattern1.start();

                if (elapsedSeconds % 5 == 0)
                    pattern2.start();

                if (elapsedSeconds % 7 == 0)
                    pattern5.start();

                if (elapsedSeconds % 5 == 0)
                    midPattern1.start();
*/

            }
        });
        restartTimer.start();

        pattern1 = new Timer(600, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(GalagaGame.this, smallEnemy0Image, 50, 10);
                enemy.pattern1();
                sprites.add(enemy);

                num++;

                if (num >= 3) {
                    pattern1.stop();
                    num = 0;
                }

            }
        });

        pattern2 = new Timer(600, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(GalagaGame.this, smallEnemy0Image, 750, 10);
                enemy.pattern2();
                sprites.add(enemy);

                num++;

                if (num >= 3) {
                    pattern2.stop();
                    num = 0;
                }
            }
        });

        pattern3 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern3 enemy = new Enemy0Pattern3(GalagaGame.this, smallEnemy1Image, 200, 10);
                sprites.add(enemy);

                pattern3.stop();
            }
        });

        pattern4 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern4 enemy = new Enemy0Pattern4(GalagaGame.this, smallEnemy1Image, 530, 10);
                sprites.add(enemy);

                pattern4.stop();
            }
        });
        pattern4.start();

        pattern5 = new Timer(200, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern5 enemy = new Enemy0Pattern5(GalagaGame.this, smallEnemy2Image, 0, 150);
                sprites.add(enemy);

                num++;

                if (num >= 5) {
                    pattern5.stop();
                    num = 0;
                }
            }
        });

        pattern6 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern6 enemy = new Enemy0Pattern6(GalagaGame.this, smallEnemy1Image, 800, 150);
                sprites.add(enemy);

                pattern6.stop();
            }
        });

        midPattern1 = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MidEnemyPattern0 enemy = new MidEnemyPattern0(GalagaGame.this, midEnemy0Image, midEnemy0WarningImage, 200, 10);
                sprites.add(enemy);

                midPattern1.stop();
            }
        });
    }

    public static void main(String[] args) {
        GalagaGame g = new GalagaGame();
        g.start();
    }
}
