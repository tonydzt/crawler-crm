package com.teamdev.jxbrowser.chromium;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;


public class TabButton extends JButton {
    public TabButton(Icon icon, String toolTipText) {
        setIcon(icon);
        setToolTipText(toolTipText);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setContentAreaFilled(false);
        setFocusable(false);
    }
}
