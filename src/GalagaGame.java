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

    // 불리안 변수
    private boolean running = true;                 // 게임루프를 위한 변수. 게임이 종료되면 false
    private boolean starshipIsCollision = false;    // 플레이어가 폭발하는 동안 움직임을 막기 위한 변수
    private boolean isFiring = false;

    private ArrayList<Sprite> sprites = new ArrayList<>();  // 플레이어, 적, 총알, 보스를 담는 배열리스트
    private Sprite starship;                                // 플레이어 객체

    // 이미지 변수
    private BufferedImage smallEnemy0Image;
    private BufferedImage smallEnemy1Image;
    private BufferedImage smallEnemy2Image;

    private BufferedImage midEnemy0Image;
    private BufferedImage midEnemy0WarningImage;

    private BufferedImage largeEnemyImage;

    private BufferedImage fireball0, fireball1, fireball2, fireball3, fireball4, fireball5, fireball6;
    private BufferedImage[] fireballs = new BufferedImage[7];

    private BufferedImage smallEnemyExplosion0, smallEnemyExplosion1, smallEnemyExplosion2, smallEnemyExplosion3, smallEnemyExplosion4, smallEnemyExplosion5, smallEnemyExplosion6;
    private BufferedImage[] smallEnemyExplosions = new BufferedImage[7];

    private BufferedImage midEnemyExplosion0, midEnemyExplosion1, midEnemyExplosion2, midEnemyExplosion3, midEnemyExplosion4, midEnemyExplosion5, midEnemyExplosion6;
    private BufferedImage[] midEnemyExplosions = new BufferedImage[7];

    private BufferedImage shotImage;
    private BufferedImage enemyBullet;
    private BufferedImage bossShotImage;
    private BufferedImage shipImage;
    private BufferedImage playerInvincibleImage;

    private BufferedImage bossImage;
    private BufferedImage backgroundImage;

    // 플레이어 충돌 모션 이미지 리스트
    private ArrayList<Image> fireballImages = new ArrayList<>();

    // 적 충돌 모션 이미지 리스트
    private ArrayList<Image> smallEnemyExplosionImages = new ArrayList<>();

    private ArrayList<Image> midEnemyExplosionImages = new ArrayList<>();

    // 타이머 변수
    private Timer t;                // 게임루프(Sprite 움직임, 충돌, repaint())을 위한 타이머
    private Timer restartTimer;     // 패턴 재사용을 위한 타이머
    private Timer pattern1, pattern2, pattern3, pattern4, pattern5, pattern6;   // 소형급 적기 패턴을 위한 타이머
    private Timer midPattern1;      // 중형급 적기 패턴을 위한 타이머
    private Timer largePattern1;    // 대형급 적기 패턴을 위한 타이머

    private Timer smallEnemyBulletTimer1, smallEnemyBulletTimer2, smallEnemyBulletTimer3, smallEnemyBulletTimer4, smallEnemyBulletTimer5, smallEnemyBulletTimer6;
    private Timer midEnemyBulletTimer1;

    private int level = 1;
    private int score = 0;
    private int lives = 3;

    private int initialX = 370;     // 플레이어 초기화 위치
    private int initialY = 500;     // 플레이어 초기화 위치

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
            playerInvincibleImage = scaleImage(ImageIO.read(new File("image/playerInvincible.png")), 50, 50);

            smallEnemy0Image = scaleImage(ImageIO.read(new File("image/smallEnemy0.png")), 30, 30);
            smallEnemy1Image = scaleImage(ImageIO.read(new File("image/smallEnemy1.png")), 30, 30);
            smallEnemy2Image = scaleImage(ImageIO.read(new File("image/smallEnemy2.png")), 28, 27);

            fireball0 = scaleImage(ImageIO.read(new File("image/fireball0.png")), 50, 50);
            fireball1 = scaleImage(ImageIO.read(new File("image/fireball1.png")), 53, 53);
            fireball2 = scaleImage(ImageIO.read(new File("image/fireball2.png")), 56, 56);
            fireball3 = scaleImage(ImageIO.read(new File("image/fireball3.png")), 59, 59);
            fireball4 = scaleImage(ImageIO.read(new File("image/fireball4.png")), 56, 56);
            fireball5 = scaleImage(ImageIO.read(new File("image/fireball5.png")), 53, 53);
            fireball6 = scaleImage(ImageIO.read(new File("image/fireball6.png")), 50, 50);

            smallEnemyExplosion0 = scaleImage(ImageIO.read(new File("image/explosion0.png")), 30, 30);
            smallEnemyExplosion1 = scaleImage(ImageIO.read(new File("image/explosion1.png")), 32, 32);
            smallEnemyExplosion2 = scaleImage(ImageIO.read(new File("image/explosion2.png")), 34, 34);
            smallEnemyExplosion3 = scaleImage(ImageIO.read(new File("image/explosion3.png")), 36, 36);
            smallEnemyExplosion4 = scaleImage(ImageIO.read(new File("image/explosion4.png")), 34, 34);
            smallEnemyExplosion5 = scaleImage(ImageIO.read(new File("image/explosion5.png")), 32, 32);
            smallEnemyExplosion6 = scaleImage(ImageIO.read(new File("image/explosion6.png")), 30, 30);

            midEnemyExplosion0 = scaleImage(ImageIO.read(new File("image/explosion0.png")), 60, 60);
            midEnemyExplosion1 = scaleImage(ImageIO.read(new File("image/explosion1.png")), 62, 62);
            midEnemyExplosion2 = scaleImage(ImageIO.read(new File("image/explosion2.png")), 64, 64);
            midEnemyExplosion3 = scaleImage(ImageIO.read(new File("image/explosion3.png")), 66, 66);
            midEnemyExplosion4 = scaleImage(ImageIO.read(new File("image/explosion4.png")), 64, 64);
            midEnemyExplosion5 = scaleImage(ImageIO.read(new File("image/explosion5.png")), 62, 62);
            midEnemyExplosion6 = scaleImage(ImageIO.read(new File("image/explosion6.png")), 60, 60);

            midEnemy0Image = scaleImage(ImageIO.read(new File("image/midEnemy0.png")), 70, 60);
            midEnemy0WarningImage = scaleImage(ImageIO.read(new File("image/midEnemy0Warning.png")), 70, 60);

            largeEnemyImage = scaleImage(ImageIO.read(new File("image/LargeEnemy.png")), 100, 100);

            enemyBullet = scaleImage(ImageIO.read(new File("image/enemyBullet.png")), 10, 10);

            bossImage = scaleImage(ImageIO.read(new File("image/boss.png")), 300, 300);
            backgroundImage = scaleImage(ImageIO.read(new File("image/background.png")), 800, 600);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // fireballs 배열에 넣기
        fireballs[0] = fireball0;
        fireballs[1] = fireball1;
        fireballs[2] = fireball2;
        fireballs[3] = fireball3;
        fireballs[4] = fireball4;
        fireballs[5] = fireball5;
        fireballs[6] = fireball6;

        // explosions 배열에 넣기
        smallEnemyExplosions[0] = smallEnemyExplosion0;
        smallEnemyExplosions[1] = smallEnemyExplosion1;
        smallEnemyExplosions[2] = smallEnemyExplosion2;
        smallEnemyExplosions[3] = smallEnemyExplosion3;
        smallEnemyExplosions[4] = smallEnemyExplosion4;
        smallEnemyExplosions[5] = smallEnemyExplosion5;
        smallEnemyExplosions[6] = smallEnemyExplosion6;

        midEnemyExplosions[0] = midEnemyExplosion0;
        midEnemyExplosions[1] = midEnemyExplosion1;
        midEnemyExplosions[2] = midEnemyExplosion2;
        midEnemyExplosions[3] = midEnemyExplosion3;
        midEnemyExplosions[4] = midEnemyExplosion4;
        midEnemyExplosions[5] = midEnemyExplosion5;
        midEnemyExplosions[6] = midEnemyExplosion6;

        for (BufferedImage fireball : fireballs) {
            fireballImages.add(fireball);
        }

        for (BufferedImage explosion : smallEnemyExplosions) {
            smallEnemyExplosionImages.add(explosion);
        }

        for (BufferedImage explosion : midEnemyExplosions) {
            midEnemyExplosionImages.add(explosion);
        }

        this.requestFocus();
        this.createStarShip(initialX, initialY);
        addKeyListener(this);
    }

    public void setStarshipIsCollision(boolean starshipIsCollision) {
        this.starshipIsCollision = starshipIsCollision;
    }

    public BufferedImage getBossShotImage() {
        return bossShotImage;
    }

    public void resetStarshipPosition() {
        starship.setX(initialX);
        starship.setY(initialY);
        starshipIsCollision = false;
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void startGame() {
        sprites.clear();
        createStarShip(initialX, initialY);
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

    private void createStarShip(int x, int y) {
        starship = new StarShipSprite(this, fireballImages, shipImage, playerInvincibleImage, x, y);
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
        } /*else {
            resetStarshipPosition();
        }*/
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

    // 3번째 방법 MyFrame 클래스가 이벤트를 처리
    @Override
    public void keyPressed(KeyEvent e) {
        if (!starshipIsCollision) {
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
        t = new Timer(5, new ActionListener() {
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
/*
                if (elapsedSeconds % 23 == 0)
                    pattern1.start();

                if (elapsedSeconds % 25 == 0)
                    pattern2.start();

                if (elapsedSeconds % 17 == 0)
                    pattern5.start();

                if (elapsedSeconds % 40 == 0)
                    midPattern1.start();
                    */

/*
                if (elapsedSeconds % 5 == 0)
                    pattern1.start();

 */

                if (elapsedSeconds % 5 == 0)
                    pattern2.start();

/*
                if (elapsedSeconds % 5 == 0)
                    pattern3.start();

                if (elapsedSeconds % 5 == 0)
                    pattern4.start();

                if (elapsedSeconds % 5 == 0)
                    pattern5.start();

                if (elapsedSeconds % 5 == 0)
                    pattern6.start();
*/

/*                if (elapsedSeconds % 5 == 0)
                    midPattern1.start();*/

/*                if (elapsedSeconds % 5 == 0)
                    midPattern1.start();*/


            }
        });
        restartTimer.start();

        pattern1 = new Timer(600, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(GalagaGame.this, smallEnemyExplosionImages, smallEnemy0Image, 50, 10);
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
                num++;

                if (num == 2) {
                    Enemy0Pattern1and2 enemy2 = new Enemy0Pattern1and2(GalagaGame.this, smallEnemyExplosionImages, smallEnemy0Image, 750, 10);
                    enemy2.pattern2();
                    sprites.add(enemy2);

                    smallEnemyBulletTimer2 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (sprites.contains(enemy2)) {

                                EnemyBullet eb = new EnemyBullet(GalagaGame.this, enemyBullet, enemy2.getX() + enemy2.getWidth() / 2, enemy2.getY() + enemy2.getHeight() / 2,
                                        starship.getX(), starship.getY());
                                sprites.add(eb);
                            }
                            smallEnemyBulletTimer2.stop();
                        }
                    });
                    smallEnemyBulletTimer2.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(GalagaGame.this, smallEnemyExplosionImages, smallEnemy0Image, 750, 10);
                    enemy.pattern2();
                    sprites.add(enemy);
                }

                if (num >= 3) {
                    pattern2.stop();
                    num = 0;
                }
            }
        });
        pattern2.start();

        pattern3 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern3 enemy = new Enemy0Pattern3(GalagaGame.this, smallEnemyExplosionImages, smallEnemy1Image, 200, 10);
                sprites.add(enemy);

                pattern3.stop();
            }
        });

        pattern4 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern4 enemy = new Enemy0Pattern4(GalagaGame.this, smallEnemyExplosionImages, smallEnemy1Image, 530, 10);
                sprites.add(enemy);

                pattern4.stop();
            }
        });

        pattern5 = new Timer(200, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern5 enemy = new Enemy0Pattern5(GalagaGame.this, smallEnemyExplosionImages, smallEnemy2Image, 0, 150);
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
                Enemy0Pattern6 enemy = new Enemy0Pattern6(GalagaGame.this, smallEnemyExplosionImages, smallEnemy1Image, 800, 150);
                sprites.add(enemy);

                pattern6.stop();
            }
        });

        midPattern1 = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MidEnemyPattern0 enemy = new MidEnemyPattern0(GalagaGame.this, midEnemyExplosionImages, midEnemy0Image, midEnemy0WarningImage, 200, 10);
                sprites.add(enemy);

                midEnemyBulletTimer1 = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (sprites.contains(enemy)) {

                            EnemyBullet eb = new EnemyBullet(GalagaGame.this, enemyBullet, enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    starship.getX(), starship.getY());
                            sprites.add(eb);
                        }

                        midEnemyBulletTimer1.stop();

                    }
                });
                midEnemyBulletTimer1.start();

                midPattern1.stop();
            }
        });

        largePattern1 = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LargeEnemy enemy = new LargeEnemy(GalagaGame.this, largeEnemyImage, 100, 600);
                sprites.add(enemy);

                largePattern1.stop();
            }
        });
    }

    public static void main(String[] args) {
        GalagaGame g = new GalagaGame();
        g.start();
    }
}
