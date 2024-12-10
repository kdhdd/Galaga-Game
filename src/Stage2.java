import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stage2 {
    private GalagaGame game;

    private Timer restartTimer;     // 패턴 재사용을 위한 타이머
    private Timer pattern1, pattern2, pattern3, pattern4, pattern5, pattern6, pattern7, pattern8, pattern1Bullet;   // 소형급 적기 패턴을 위한 타이머
    private Timer midPattern1;      // 중형급 적기 패턴을 위한 타이머
    private Timer largePattern1;    // 대형급 적기 패턴을 위한 타이머
    private Timer smallEnemyBulletTimer1, smallEnemyBulletTimer1_1, smallEnemyBulletTimer2, smallEnemyBulletTimer2_1, smallEnemyBulletTimer3, smallEnemyBulletTimer3_1,
            smallEnemyBulletTimer4, smallEnemyBulletTimer5, smallEnemyBulletTimer6, smallEnemyBulletTimer7, smallEnemyBulletTimer8;
    private Timer midEnemyBulletTimer1;
    private Timer largeEnemyBulletTimer1;

    public Stage2(GalagaGame game) {
        this.game = game;

        pattern1 = new Timer(400, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 1) {
                    Enemy0Pattern1and2 enemy1 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 400 + (num * 40), 0);
                    enemy1.pattern3();
                    game.addSprite(enemy1);

                    smallEnemyBulletTimer1 = new Timer(800, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy1) && !enemy1.getIsIncollision()) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy1.getX() + enemy1.getWidth() / 2, enemy1.getY() + enemy1.getHeight() / 2, 1);
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer1.stop();
                        }
                    });
                    smallEnemyBulletTimer1.start();
                } else if (num == 3) {
                    Enemy0Pattern1and2 enemy3 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 550, 0);
                    enemy3.pattern3();
                    game.addSprite(enemy3);

                    smallEnemyBulletTimer1_1 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy3) && !enemy3.getIsIncollision()) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy3.getX() + enemy3.getWidth() / 2, enemy3.getY() + enemy3.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer1_1.stop();
                        }
                    });
                    smallEnemyBulletTimer1_1.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 400 + (num * 30), 0);
                    enemy.pattern3();
                    game.addSprite(enemy);
                }

                if (num >= 4) {
                    num = 0;
                    pattern1.stop();
                }

            }
        });

        pattern2 = new Timer(400, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 1) {
                    Enemy0Pattern1and2 enemy1 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 700, 0);
                    enemy1.pattern3();
                    game.addSprite(enemy1);

                    smallEnemyBulletTimer2 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy1) && !enemy1.getIsIncollision()) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy1.getX() + enemy1.getWidth() / 2, enemy1.getY() + enemy1.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer2.stop();
                        }
                    });
                    smallEnemyBulletTimer2.start();
                } else if (num == 4) {
                    Enemy0Pattern1and2 enemy4 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 600 - (50 * num), 10);
                    enemy4.pattern3();
                    game.addSprite(enemy4);

                    smallEnemyBulletTimer2_1 = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy4) && !enemy4.getIsIncollision()) {

                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy4.getX() + enemy4.getWidth() / 2, enemy4.getY() + enemy4.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer2_1.stop();
                        }
                    });
                    smallEnemyBulletTimer2_1.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 600 - (50 * num), 10);
                    enemy.pattern3();
                    game.addSprite(enemy);
                }

                if (num >= 4) {
                    num = 0;
                    pattern2.stop();
                }
            }
        });

        pattern3 = new Timer(200, new ActionListener() {
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                if (num == 1) {
                    Enemy0Pattern1and2 enemy1 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 230, 0);
                    game.addSprite(enemy1);

                    smallEnemyBulletTimer3 = new Timer(1400, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy1) && !enemy1.getIsIncollision()) {
                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy1.getX() + enemy1.getWidth() / 2, enemy1.getY() + enemy1.getHeight() / 2,
                                        game.getStartShipX(), game.getStarShipY());
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer3.stop();
                        }
                    });
                    smallEnemyBulletTimer3.start();
                } else if (num == 3) {
                    Enemy0Pattern1and2 enemy3 = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 300, 0);
                    game.addSprite(enemy3);

                    smallEnemyBulletTimer3_1 = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (game.containsEnemy(enemy3) && !enemy3.getIsIncollision()) {
                                EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy3.getX() + enemy3.getWidth() / 2, enemy3.getY() + enemy3.getHeight() / 2, 1);
                                game.addSprite(eb);
                            }
                            smallEnemyBulletTimer3_1.stop();
                        }
                    });
                    smallEnemyBulletTimer3_1.start();
                } else {
                    Enemy0Pattern1and2 enemy = new Enemy0Pattern1and2(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy0Image(), 300 + (num * 30), 0);
                    game.addSprite(enemy);
                }

                if (num >= 4) {
                    num = 0;
                    pattern3.stop();
                }
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
                        if (game.containsEnemy(enemy) && !enemy.getIsIncollision()) {
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
            int num = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                num++;

                Enemy0Pattern6 enemy = new Enemy0Pattern6(game, game.getSmallEnemyExplosionImages(), game.getsmallEnemy1Image(), 800, 150);
                game.addSprite(enemy);

                smallEnemyBulletTimer6 = new Timer(300, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.containsEnemy(enemy) && !enemy.getIsIncollision()) {
                            EnemyBullet eb = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    game.getStartShipX(), game.getStarShipY());
                            game.addSprite(eb);
                        }

                        smallEnemyBulletTimer6.stop();
                    }
                });
                smallEnemyBulletTimer6.start();

                if (num >= 2) {
                    pattern6.stop();
                }
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
                            if (game.containsEnemy(enemy2) && !enemy2.getIsIncollision()) {
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
                            if (game.containsEnemy(enemy2) && !enemy2.getIsIncollision()) {
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
                MidEnemyPattern0 enemy = new MidEnemyPattern0(game, game.getmidEnemyExplosionImages(), game.getmidEnemy0Image(), game.getmidEnemy0WarningImage(), 200, 0);
                game.addSprite(enemy);

                midEnemyBulletTimer1 = new Timer(3000, new ActionListener() {
                    int num = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        num++;

                        if (game.containsEnemy(enemy) && !enemy.getIsIncollision()) {

                            EnemyBullet eb1 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    game.getStartShipX(), game.getStarShipY());
                            game.addSprite(eb1);

                            EnemyBullet eb2 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 0);
                            game.addSprite(eb2);

                            EnemyBullet eb3 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 1);
                            game.addSprite(eb3);
                        }

                        if (num >= 3) {
                            midEnemyBulletTimer1.stop();
                        }
                    }
                });
                midEnemyBulletTimer1.start();

                midPattern1.stop();
            }
        });

        largePattern1 = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LargeEnemy enemy = new LargeEnemy(game, game.getLargeEnemyExplosionImages(), game.getlargeEnemyImage(), game.getlargeEnemyWarningImage(), 350, 600);
                game.addSprite(enemy);

                largeEnemyBulletTimer1 = new Timer(2000, new ActionListener() {
                    int num = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        num++;

                        if (game.containsEnemy(enemy) && !enemy.getIsIncollision()) {
                            EnemyBullet eb1 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2,
                                    game.getStartShipX(), game.getStarShipY());
                            game.addSprite(eb1);

                            EnemyBullet eb2 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 0);
                            game.addSprite(eb2);

                            EnemyBullet eb3 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 1);
                            game.addSprite(eb3);

                            EnemyBullet eb4 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 2);
                            game.addSprite(eb4);

                            EnemyBullet eb5 = new EnemyBullet(game, game.getenemyBullet(), enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, 3);
                            game.addSprite(eb5);
                        }

                        if (num >= 100) {
                            largeEnemyBulletTimer1.stop();
                        }
                    }
                });
                largeEnemyBulletTimer1.start();

                largePattern1.stop();
            }
        });
    }

    public void startPatterns() {
        restartTimer = new Timer(100, new ActionListener() {
            int elapsedSeconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedSeconds++;

                if (elapsedSeconds % 30 == 0 && elapsedSeconds <= 30) {
                    pattern2.start();
                }

                if (elapsedSeconds % 75 == 0 && elapsedSeconds <= 90) {
                    pattern1.start();
                }

                if (elapsedSeconds % 105 == 0 && elapsedSeconds <= 120) {
                    pattern1.start();
                }

                if (elapsedSeconds % 112 == 0 && elapsedSeconds <= 120) {
                    midPattern1.start();
                }

                if (elapsedSeconds % 180 == 0 && elapsedSeconds <= 190) {
                    pattern3.start();
                }

                if (elapsedSeconds % 195 == 0 && elapsedSeconds <= 200) {
                    pattern1.start();
                }

                if (elapsedSeconds % 210 == 0 && elapsedSeconds <= 220) {
                    pattern2.start();
                }

                if (elapsedSeconds % 225 == 0 && elapsedSeconds <= 230) {
                    pattern5.start();
                }

                if (elapsedSeconds % 230 == 0 && elapsedSeconds <= 240) {
                    pattern3.start();
                }

                if (elapsedSeconds % 255 == 0 && elapsedSeconds <= 260) {
                    pattern2.start();
                }

                if (elapsedSeconds % 260 == 0 && elapsedSeconds <= 270) {
                    pattern7.start();
                }

                if (elapsedSeconds % 265 == 0 && elapsedSeconds <= 270) {
                    pattern6.start();
                }

                if (elapsedSeconds % 280 == 0 && elapsedSeconds <= 290) {
                    pattern7.start();
                }

                if (elapsedSeconds % 285 == 0 && elapsedSeconds <= 290) {
                    pattern6.start();
                }

                if (elapsedSeconds % 297 == 0 && elapsedSeconds <= 300) {
                    largePattern1.start();
                }

                if (elapsedSeconds % 310 == 0 && elapsedSeconds <= 320) {
                    pattern6.start();
                }

                if (elapsedSeconds % 315 == 0 && elapsedSeconds <= 320) {
                    pattern8.start();
                }

                if (elapsedSeconds % 330 == 0 && elapsedSeconds <= 340) {
                    pattern7.start();
                }

                if (elapsedSeconds % 335 == 0 && elapsedSeconds <= 340) {
                    pattern6.start();
                }

                if (elapsedSeconds % 350 == 0 && elapsedSeconds <= 360) {
                    pattern1.start();
                }

                if (elapsedSeconds % 355 == 0 && elapsedSeconds <= 360) {
                    pattern3.start();
                }

                if (elapsedSeconds % 365 == 0 && elapsedSeconds <= 375) {
                    pattern8.start();
                }

                if (elapsedSeconds % 370 == 0 && elapsedSeconds <= 375) {
                    pattern1.start();
                }

                if (elapsedSeconds % 390 == 0 && elapsedSeconds <= 400) {
                    pattern6.start();
                    pattern8.start();
                }

                if (elapsedSeconds % 400 == 0 && elapsedSeconds <= 410) {
                    pattern1.start();
                    pattern7.start();
                }

                if (elapsedSeconds % 500 == 0) {
                    restartTimer.stop();
                    game.setIsgameOver(true);
                    game.setStarshipDirection(0, -3);
                    JOptionPane.showMessageDialog(game, "Stage 2 Clear! Your score: " + game.getScore());
                    new Start();
                }
            }
        });
        restartTimer.start();
    }
}
