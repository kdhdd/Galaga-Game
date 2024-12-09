import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chapter1 {
    private GalagaGame game;

    private Timer restartTimer;     // 패턴 재사용을 위한 타이머
    private Timer pattern1, pattern2, pattern3, pattern4, pattern5, pattern6, pattern7, pattern8, pattern1Bullet;   // 소형급 적기 패턴을 위한 타이머
    private Timer midPattern1;      // 중형급 적기 패턴을 위한 타이머
    private Timer largePattern1;    // 대형급 적기 패턴을 위한 타이머
    private Timer smallEnemyBulletTimer1, smallEnemyBulletTimer2, smallEnemyBulletTimer3, smallEnemyBulletTimer4,
            smallEnemyBulletTimer5, smallEnemyBulletTimer6, smallEnemyBulletTimer7, smallEnemyBulletTimer8;
    private Timer midEnemyBulletTimer1;

    public Chapter1(GalagaGame game) {
        this.game = game;

        pattern1 = new Timer(300, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 50 + (num * 50), 10);
                enemy.pattern1();
                game.addSprite(enemy);

                num++;

                if (num >= 3) {
                    pattern1.stop();
                    num = 0;
                }

            }
        });

        pattern2 = new Timer(300, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 2) {
                    Enemy0Pattern1and2 enemy2 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 750, 10);
                    enemy2.pattern2();
                    game.addSprite(enemy2);

                    smallEnemyBulletTimer2 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy2)) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy2.getX() + enemy2.getWidth() / 2, enemy2.getY() + enemy2.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer2.stop();
                        }
                    });
                    smallEnemyBulletTimer2.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 750 - (50 * num), 10);
                    enemy.pattern2();
                    game.addSprite(enemy);
                }

                if (num >= 3) {
                    pattern2.stop();
                    num = 0;
                }
            }
        });

        pattern3 = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern3 enemy = new Enemy0Pattern3(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy1Image(), 200, 10);
                game.addSprite(enemy);

                pattern3.stop();
            }
        });

        pattern4 = new Timer(400, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern4 enemy = new Enemy0Pattern4(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy1Image(), 530, 10);
                game.addSprite(enemy);

                smallEnemyBulletTimer4 = new Timer(1000, new ActionListener() {
                    int num = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.containsEnemy(enemy)) {
                            EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    game.getStartShipX(), game.getStarShipY());
                            game.addSprite(eb);
                        }

                        if (num >= 3) {
                            num = 0;
                            smallEnemyBulletTimer4.stop();
                        }
                    }
                });
                smallEnemyBulletTimer4.start();

                pattern4.stop();
            }
        });

        pattern5 = new Timer(200, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern5 enemy = new Enemy0Pattern5(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy2Image(), 0, 150);
                game.addSprite(enemy);

                num++;

                if (num >= 5) {
                    pattern5.stop();
                    num = 0;
                }
            }
        });

        pattern6 = new Timer(900, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Enemy0Pattern6 enemy = new Enemy0Pattern6(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy1Image(), 800, 150);
                game.addSprite(enemy);

                pattern6.stop();
            }
        });

        pattern7 = new Timer(1400, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 2) {
                    Enemy0Pattern7 enemy2 = new Enemy0Pattern7(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 0, 150, 0.013);
                    game.addSprite(enemy2);

                    smallEnemyBulletTimer7 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy2)) {
                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy2.getX() + enemy2.getWidth() / 2, enemy2.getY() + enemy2.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer7.stop();
                        }
                    });
                    smallEnemyBulletTimer7.start();
                } else {
                    Enemy0Pattern7 enemy = new Enemy0Pattern7(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 0, 150, 0.013);
                    game.addSprite(enemy);
                }

                if (num >= 3) {
                    pattern7.stop();
                    num = 0;
                }
            }
        });

        pattern8 = new Timer(400, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 2) {
                    Enemy0Pattern7 enemy2 = new Enemy0Pattern7(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 0, 150, 0.013 + (num * 0.003));
                    game.addSprite(enemy2);

                    smallEnemyBulletTimer8 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy2)) {
                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy2.getX() + enemy2.getWidth() / 2, enemy2.getY() + enemy2.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer8.stop();
                        }
                    });
                    smallEnemyBulletTimer8.start();
                } else {
                    Enemy0Pattern7 enemy = new Enemy0Pattern7(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 0, 150 + (num * 30), 0.013);
                    game.addSprite(enemy);
                }

                if (num >= 3) {
                    pattern8.stop();
                    num = 0;
                }
            }
        });

        midPattern1 = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MidEnemyPattern0 enemy = new MidEnemyPattern0(game, game.getmidEnemyExplosionImages(), game.getmidEnemy0Image(), game.getmidEnemy0WarningImage(), 200, 10);
                game.addSprite(enemy);

                midEnemyBulletTimer1 = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.containsEnemy(enemy)) {

                            EnemyBullet eb1 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    game.getStartShipX(), game.getStarShipY());
                            game.addSprite(eb1);

                            EnemyBullet eb2 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 0);
                            game.addSprite(eb2);

                            EnemyBullet eb3 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 1);
                            game.addSprite(eb3);
                        }

                        midEnemyBulletTimer1.stop();

                    }
                });
                midEnemyBulletTimer1.start();

                midPattern1.stop();
            }
        });

        pattern1Bullet = new Timer(300, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 2) {
                    Enemy0Pattern1and2 enemy2 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 50 + (num * 50), 10);
                    enemy2.pattern1();
                    game.addSprite(enemy2);

                    smallEnemyBulletTimer1 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy2)) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy2.getX() + enemy2.getWidth() / 2, enemy2.getY() + enemy2.getHeight() / 2, 0);
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer1.stop();
                        }
                    });
                    smallEnemyBulletTimer1.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 50 + (num * 50), 10);
                    enemy.pattern1();
                    game.addSprite(enemy);
                }

                if (num >= 3) {
                    pattern1Bullet.stop();
                    num = 0;
                }
            }
        });
    }

    public void startPatterns() {
        restartTimer = new Timer(100, new ActionListener() {
            int elapsedSeconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedSeconds++;

                if (elapsedSeconds % 30 == 0 && elapsedSeconds <= 100) {
                    pattern1.start();
                }

                if (elapsedSeconds % 45 == 0 && elapsedSeconds <= 100) {
                    pattern2.start();
                }

                if (elapsedSeconds % 110 == 0 && elapsedSeconds <= 120) {
                    pattern4.start();
                    pattern5.start();
                }

                if (elapsedSeconds % 130 == 0 && elapsedSeconds <= 140) {
                    pattern7.start();
                    midPattern1.start();
                }

                if (elapsedSeconds % 170 == 0 && elapsedSeconds <= 180) {
                    pattern4.start();
                    pattern5.start();
                    pattern2.start();
                }

                if (elapsedSeconds % 200 == 0 && elapsedSeconds <= 210) {
                    pattern8.start();
                }

                if (elapsedSeconds >= 220 && elapsedSeconds % 20 == 0 && elapsedSeconds <= 270) {
                    pattern6.start();
                }

                if (elapsedSeconds >= 240 && elapsedSeconds % 30 == 0 && elapsedSeconds <= 330) {
                    pattern1Bullet.start();
                }

                if (elapsedSeconds >= 250 && elapsedSeconds % 30 == 0 && elapsedSeconds <= 340) {
                    pattern2.start();
                }

                if (elapsedSeconds % 400 == 0) {
                    restartTimer.stop();
                    game.setIsgameOver(true);
                    game.setStarshipDirection(0, -3);
                    JOptionPane.showMessageDialog(game, "Stage 1 Clear! Your score: " + game.getScore());
                    game.levelUp();
                    game.setIsgameOver(false);
                    game.setStarshipDirection(0, 0);
                    game.resetStarshipPosition();
                }
            }
        });
        restartTimer.start();
    }
}
