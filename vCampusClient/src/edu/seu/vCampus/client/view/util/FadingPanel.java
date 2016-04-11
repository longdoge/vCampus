package edu.seu.vCampus.client.view.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FadingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage buffer;
    private boolean isFading = false;
    private long start;
    private float alpha = 1.0f;

    @Override
    public void paint(Graphics g) {
        if (isFading) {// During fading, we prevent child components from being
                       // painted
            g.clearRect(0, 0, getWidth(), getHeight());
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(buffer, 0, 0, this);// We only draw an image of them
                                            // with an alpha
        } else {
            super.paint(g);
        }
    }

    public void fade(float time, final boolean fadeOut) {
        start = System.currentTimeMillis();
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.print(buffer.getGraphics()); // Draw the current components on the buffer
        isFading = true;
        final int timeInMillis = (int) (time * 1000);
        final Timer t = new Timer(10, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - start;
                if (elapsed > timeInMillis) {
                    start = 0;
                    isFading = false;
                    buffer = null;
                    repaint();
                    t.stop();
                } else {
                    if (fadeOut)
                        alpha = 1 - (float)Math.sin((double) elapsed / timeInMillis * Math.PI / 2);
                    else
                        alpha = (float)Math.sin((double) elapsed / timeInMillis * Math.PI / 2);
                    repaint();
                }
            }
        });
        t.start();
    }
}
