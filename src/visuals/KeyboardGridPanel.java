package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardGridPanel extends JPanel implements KeyListener {

    private int x, y, rows, cols;
    private GridLayout gridLayout;

    public KeyboardGridPanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        setLayout(gridLayout = new GridLayout(rows, cols));

        x = y = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                y = (y == 0) ? rows - 1 : y - 1;
                break;
            case KeyEvent.VK_DOWN:
                y = (y == rows - 1) ? 0 : y + 1;
                break;
            case KeyEvent.VK_LEFT:
                x = (x == 0) ? cols - 1 : x - 1;
                break;
            case KeyEvent.VK_RIGHT:
                x = (x == cols - 1) ? 0 : x + 1;
                break;
        }

        int index = cols * y + x;

        getComponent(index).setBackground(Color.RED);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
