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

public class Start extends JPanel implements KeyListener {
    private BufferedImage startImage, startCoinImage;

    private Timer drawTimer;

    private int coin = 0;

    public BufferedImage scaleImage(BufferedImage src, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(src, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }

    public Start() {
        JFrame frame = new JFrame("Galaga Game");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        frame.setSize(800,600);
        frame.setLocation((screenSize.width - frame.getWidth()) / 2,
                (screenSize.height - frame.getHeight()) / 2);
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            startImage = scaleImage(ImageIO.read(new File("image/homescreen.png")), 800, 600);
            startCoinImage = scaleImage(ImageIO.read(new File("image/homescreenCoin.png")), 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        drawTimer.start();

        this.requestFocus();
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        switch (coin) {
            case 0:
                g.drawImage(startImage, 0, 0, this.getWidth(), this.getHeight(), null);
                break;
            case 1:
                g.drawImage(startCoinImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            coin = 1;
        if (e.getKeyCode() == KeyEvent.VK_ENTER && coin == 1) {
            drawTimer.stop();
            GalagaGame g = new GalagaGame();
            g.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    public static void main(String[] args) {
        new Start();
    }
}
