package com.teamdev.jxbrowser.chromium;

import com.kasuo.crawler.service.AbstractCrawlerService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TabCaption extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCrawlerService.class);
    private boolean selected;
    private TabCaptionComponent component;

    public TabCaption() {
        setLayout(new BorderLayout());
        setOpaque(false);
        add(createComponent(), "Center");
        add(Box.createHorizontalStrut(1), "East");
    }

    private JComponent createComponent() {
        try {
            component = new TabCaptionComponent();
            component.addPropertyChangeListener("CloseButtonPressed", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        firePropertyChange("CloseButtonPressed", evt.getOldValue(), evt.getNewValue());
                    } catch (Throwable ee) {
                        TabCaption.logger.error("", ee);
                    }
                }
            });
            component.addPropertyChangeListener("TabClicked", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        setSelected(true);
                    } catch (Throwable ee) {
                        TabCaption.logger.error("", ee);
                    }
                }
            });
            return component;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(155, 26);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 26);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void setTitle(String title) {
        component.setTitle(title);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        try {
            boolean oldValue = this.selected;
            this.selected = selected;
            component.setSelected(selected);
            firePropertyChange("TabSelected", oldValue, selected);
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private static class TabCaptionComponent extends JPanel {
        private JLabel label;
        private final Color defaultBackground;

        private TabCaptionComponent() {
            defaultBackground = getBackground();
            setLayout(new BorderLayout());
            setOpaque(false);
            add(createLabel(), "Center");
        }


        private JComponent createLabel() {
            label = new JLabel();
            label.setOpaque(false);
            label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        if (e.getButton() == 1) {
                            firePropertyChange("TabClicked", false, true);
                        }
                        if (e.getButton() == 2) {
                            firePropertyChange("CloseButtonPressed", false, true);
                        }
                    } catch (Throwable ee) {
                        TabCaption.logger.error("", ee);
                    }
                }
            });
            return label;
        }

        void setTitle(final String title) {
            SwingUtilities.invokeLater(() -> {
                try {
                    label.setText(title);
                    label.setToolTipText(title);
                } catch (Throwable ee) {
                    TabCaption.logger.error("", ee);
                }
            });
        }

        void setSelected(boolean selected) {
            try {
                setBackground(selected ? defaultBackground : new Color(150, 150, 150));
                repaint();
            } catch (Throwable ee) {
                TabCaption.logger.error("", ee);
            }
        }

        @Override
        public void paint(Graphics g) {
            try {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(new GradientPaint(0.0F, 0.0F, Color.LIGHT_GRAY, 0.0F, getHeight(), getBackground()));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paint(g);
            } catch (Throwable ee) {
                TabCaption.logger.error("", ee);
            }
        }
    }
}
