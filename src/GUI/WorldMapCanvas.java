package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class WorldMapCanvas extends JPanel {
    public static final Dimension CANVAS_SIZE = new Dimension(500, 500);
    private static final Color CANVAS_DEFAULT_COLOR = new Color(255, 255, 255);


    public WorldMapCanvas() {
        setBackground(CANVAS_DEFAULT_COLOR);
        setMinimumSize(CANVAS_SIZE);
        setPreferredSize(CANVAS_SIZE);
        setMaximumSize(CANVAS_SIZE);
    }

    private static Graphics2D setupCanvas(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        g2D.setBackground(CANVAS_DEFAULT_COLOR);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return g2D;
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = setupCanvas(g);

        Rectangle2D rect = new Rectangle2D.Double(100, 100, 100, 100);

        g2D.setPaint(new Color(255, 0, 0));
        g2D.fill(rect);
        g2D.draw(rect);
    }
}
