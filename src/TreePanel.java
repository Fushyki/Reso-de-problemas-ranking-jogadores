import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TreePanel extends JPanel {

    private BinarySearchTree tree;
    private static final int NODE_RADIUS = 28;
    private static final int VERTICAL_GAP = 70;

    public TreePanel(BinarySearchTree tree) {
        this.tree = tree;
        setBackground(new Color(30, 30, 45));
    }

    public void setTree(BinarySearchTree tree) {
        this.tree = tree;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (tree != null && tree.getRoot() != null) {
            drawTree(g2, tree.getRoot(), getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void drawTree(Graphics2D g, Node node, int x, int y, int offset) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            int childX = x - offset;
            int childY = y + VERTICAL_GAP;
            g.setColor(new Color(120, 180, 255));
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.left, childX, childY, offset / 2);
        }
        if (node.right != null) {
            int childX = x + offset;
            int childY = y + VERTICAL_GAP;
            g.setColor(new Color(120, 180, 255));
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.right, childX, childY, offset / 2);
        }
        g.setColor(new Color(70, 130, 200));
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(new Color(150, 210, 255));
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        String name = node.player.getNickname();
        if (name.length() > 9) {
            name = name.substring(0, 8) + "..";
        }
        int textX = x - fm.stringWidth(name) / 2;
        int textY = y + fm.getAscent() / 2 - 1;
        g.drawString(name, textX, textY);
    }
}