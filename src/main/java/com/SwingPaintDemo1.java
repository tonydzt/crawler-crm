package com;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
/**
 * @author douzhitong
 * @date 2018/11/24
 */


public class SwingPaintDemo1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,250);
        f.setVisible(true);
        while(true) {

        }
    }
}