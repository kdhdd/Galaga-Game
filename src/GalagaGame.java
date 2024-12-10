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
    private boolean isgameOver = false;
    private boolean isFiring = false;
    private boolean paused = false;

    private ArrayList<Sprite> sprites = new ArrayList<>();  // 플레이어, 적, 총알, 보스를 담는 배열리스트
    private Sprite starship;                                // 플레이어 객체

    private Stage1 stage1;
    private Stage2 stage2;

    // 이미지 변수
    private BufferedImage smallEnemy0Image;
    private BufferedImage smallEnemy1Image;
    private BufferedImage smallEnemy2Image;

    private BufferedImage midEnemy0Image;
    private BufferedImage midEnemy0WarningImage;

    private BufferedImage largeEnemyImage;
    private BufferedImage largeEnemyWarningImage;

    private BufferedImage fireball0, fireball1, fireball2, fireball3, fireball4, fireball5, fireball6;
    private BufferedImage[] fireballs = new BufferedImage[7];

    private BufferedImage smallEnemyExplosion0, smallEnemyExplosion1, smallEnemyExplosion2, smallEnemyExplosion3, smallEnemyExplosion4, smallEnemyExplosion5, smallEnemyExplosion6;
    private BufferedImage[] smallEnemyExplosions = new BufferedImage[7];

    private BufferedImage midEnemyExplosion0, midEnemyExplosion1, midEnemyExplosion2, midEnemyExplosion3, midEnemyExplosion4, midEnemyExplosion5, midEnemyExplosion6;
    private BufferedImage[] midEnemyExplosions = new BufferedImage[7];

    private BufferedImage largeEnemyExplosion0, largeEnemyExplosion1, largeEnemyExplosion2, largeEnemyExplosion3, largeEnemyExplosion4, largeEnemyExplosion5, largeEnemyExplosion6;
    private BufferedImage[] largeEnemyExplosions = new BufferedImage[7];

    private BufferedImage shotImage;
    private BufferedImage enemyBullet;
    private BufferedImage shipImage;
    private BufferedImage playerInvincibleImage;

    private BufferedImage backgroundImage;

    // 플레이어 충돌 모션 이미지 리스트
    private ArrayList<Image> fireballImages = new ArrayList<>();

    // 적 충돌 모션 이미지 리스트
    private ArrayList<Image> smallEnemyExplosionImages = new ArrayList<>();

    private ArrayList<Image> midEnemyExplosionImages = new ArrayList<>();

    private ArrayList<Image> largeEnemyExplosionImages = new ArrayList<>();


    // 타이머 변수
    private Timer loopTimer;                // 게임루프(Sprite 움직임, 충돌, repaint())을 위한 타이머

    private int level = 1;
    private int score = 0;
    private int lives = 3;
    private int currentStage = 0;

    private int skill = 100;

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

            largeEnemyExplosion0 = scaleImage(ImageIO.read(new File("image/explosion0.png")), 100, 100);
            largeEnemyExplosion1 = scaleImage(ImageIO.read(new File("image/explosion1.png")), 102, 102);
            largeEnemyExplosion2 = scaleImage(ImageIO.read(new File("image/explosion2.png")), 104, 104);
            largeEnemyExplosion3 = scaleImage(ImageIO.read(new File("image/explosion3.png")), 106, 106);
            largeEnemyExplosion4 = scaleImage(ImageIO.read(new File("image/explosion4.png")), 104, 104);
            largeEnemyExplosion5 = scaleImage(ImageIO.read(new File("image/explosion5.png")), 102, 102);
            largeEnemyExplosion6 = scaleImage(ImageIO.read(new File("image/explosion6.png")), 100, 100);

            midEnemy0Image = scaleImage(ImageIO.read(new File("image/midEnemy0.png")), 70, 60);
            midEnemy0WarningImage = scaleImage(ImageIO.read(new File("image/midEnemy0Warning.png")), 70, 60);

            largeEnemyImage = scaleImage(ImageIO.read(new File("image/LargeEnemy.png")), 100, 100);
            largeEnemyWarningImage = scaleImage(ImageIO.read(new File("image/LargeEnemyWarning.png")), 100, 100);

            enemyBullet = scaleImage(ImageIO.read(new File("image/enemyBullet.png")), 10, 10);

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

        largeEnemyExplosions[0] = largeEnemyExplosion0;
        largeEnemyExplosions[1] = largeEnemyExplosion1;
        largeEnemyExplosions[2] = largeEnemyExplosion2;
        largeEnemyExplosions[3] = largeEnemyExplosion3;
        largeEnemyExplosions[4] = largeEnemyExplosion4;
        largeEnemyExplosions[5] = largeEnemyExplosion5;
        largeEnemyExplosions[6] = largeEnemyExplosion6;

        for (BufferedImage fireball : fireballs) {
            fireballImages.add(fireball);
        }

        for (BufferedImage explosion : smallEnemyExplosions) {
            smallEnemyExplosionImages.add(explosion);
        }

        for (BufferedImage explosion : midEnemyExplosions) {
            midEnemyExplosionImages.add(explosion);
        }

        for (BufferedImage explosion : largeEnemyExplosions) {
            largeEnemyExplosionImages.add(explosion);
        }

        this.requestFocus();
        this.createStarShip(initialX, initialY);
        addKeyListener(this);
    }

    public Image getsmallEnemy0Image() {
        return smallEnemy0Image;
    }

    public Image getsmallEnemy1Image() {
        return smallEnemy1Image;
    }

    public Image getsmallEnemy2Image() {
        return smallEnemy2Image;
    }

    public Image getmidEnemy0Image() {
        return midEnemy0Image;
    }

    public Image getmidEnemy0WarningImage() {
        return midEnemy0WarningImage;
    }

    public Image getlargeEnemyImage() {
        return largeEnemyImage;
    }

    public Image getlargeEnemyWarningImage() {
        return largeEnemyWarningImage;
    }

    public Image getenemyBullet() {
        return enemyBullet;
    }

    public ArrayList<Image> getSmallEnemyExplosionImages() {
        return smallEnemyExplosionImages;
    }

    public ArrayList<Image> getmidEnemyExplosionImages() {
        return midEnemyExplosionImages;
    }

    public ArrayList<Image> getLargeEnemyExplosionImages() {
        return largeEnemyExplosionImages;
    }

    public int getStartShipX() {
        return starship.getX();
    }

    public int getStarShipY() {
        return starship.getY();
    }

    public int getScore() {
        return score;
    }

    public void setStarshipDirection(int dx, int dy) {
        starship.setDx(dx);
        starship.setDy(dy);
    }

    public void levelUp() {
        this.level++;
    }

    public boolean containsEnemy(Sprite sprite) {
        return sprites.contains(sprite);
    }

    public void setStarshipIsCollision(boolean starshipIsCollision) {
        this.starshipIsCollision = starshipIsCollision;
    }

    public void setIsgameOver(boolean isgameOver) {
        this.isgameOver = isgameOver;
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
    }

    public void fire() {
        ShotSprite shot = new ShotSprite(this, shotImage,
                starship.getX() + 10, starship.getY() - 30);
        sprites.add(shot);
    }

    public void pressedX() {
        ArrayList<Sprite> enemysToRemove = new ArrayList<>();
        ArrayList<Sprite> enemysBulletToRemove = new ArrayList<>();

        for (int i = sprites.size() - 1; i >= 0; i--) {
            Sprite sprite = sprites.get(i);
            if (sprite instanceof Enemy0Pattern1and2 || sprite instanceof Enemy0Pattern3
                    || sprite instanceof Enemy0Pattern4 || sprite instanceof Enemy0Pattern5
                    || sprite instanceof Enemy0Pattern6 || sprite instanceof Enemy0Pattern7) {

                sprite.collisionMotion();
                enemysToRemove.add(sprite);  // 삭제할 스프라이트를 임시 리스트에 추가
                increaseScore();
            }

            if (sprite instanceof MidEnemyPattern0) {
                sprite.collisionMotion();
                enemysToRemove.add(sprite);
                increaseMidScore();
            }

            if (sprite instanceof EnemyBullet) {
                enemysBulletToRemove.add(sprite);
            }
        }

        // 타이머를 사용하여 일정 시간 후에 스프라이트 삭제
        new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprites.removeAll(enemysToRemove);
                ((Timer) e.getSource()).stop();  // 타이머 중지
            }
        }).start();  // 타이머 시작

        sprites.removeAll(enemysBulletToRemove);
    }


    public void increaseScore() {
        score += 10;
    }

    public void increaseMidScore() {
        score += 100;
    }

    public void increaseLargeScore() {
        score += 200;
    }

    public void loseLife() {
        lives--;
        if (lives <= 0)
            endGame();
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
        g.drawString("Skill: " + skill, 700, 45);
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

            if (currentStage != level) {
                currentStage = level;

                if (currentStage == 1) {
                    stage1 = new Stage1(this);
                    stage1.startPatterns();
                } else if (currentStage == 2) {
                    stage2 = new Stage2(this);
                    stage2.startPatterns();
                }
            }

            repaint();
        }
    }

    // 3번째 방법 MyFrame 클래스가 이벤트를 처리
    @Override
    public void keyPressed(KeyEvent e) {
        if (!starshipIsCollision && !isgameOver) {
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
            if (e.getKeyCode() == KeyEvent.VK_X) {
                if (skill > 0) {
                    pressedX();
                    skill--;
                }
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
        loopTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
            }
        });
        loopTimer.start();
    }

/*    public static void main(String[] args) {
        GalagaGame g = new GalagaGame();
        g.start();
    }*/
}
