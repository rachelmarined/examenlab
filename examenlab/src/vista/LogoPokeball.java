package vista;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class LogoPokeball extends JComponent {

    private static final Color ROJO = new Color(0xE74C3C);
    private static final Color NEGRO = new Color(0x1F2A37);

    public LogoPokeball() {
        setPreferredSize(new Dimension(64, 64));
        setMinimumSize(new Dimension(48, 48));
        setMaximumSize(new Dimension(64, 64));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diametro = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;

        g2.setColor(ROJO);
        g2.fillArc(x, y, diametro, diametro, 0, 180);
        g2.setColor(Color.WHITE);
        g2.fillArc(x, y, diametro, diametro, 180, 180);

        g2.setColor(NEGRO);
        g2.setStroke(new BasicStroke(3.5f));
        g2.drawOval(x, y, diametro, diametro);
        g2.drawLine(x, y + diametro / 2, x + diametro, y + diametro / 2);

        int radio = diametro / 3;
        int cx = x + diametro / 2 - radio / 2;
        int cy = y + diametro / 2 - radio / 2;
        g2.setColor(Color.WHITE);
        g2.fillOval(cx, cy, radio, radio);
        g2.setColor(NEGRO);
        g2.drawOval(cx, cy, radio, radio);

        g2.dispose();
    }
}
