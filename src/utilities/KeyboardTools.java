package utilities;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Functions for various Keyboard related actions.
 */
public class KeyboardTools {
    public KeyboardTools() {
    }

    public static void tab() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shiftTab() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void escape() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isArrowKey(KeyEvent e) {
        int k = e.getKeyCode();
        return k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN;
    }
}
