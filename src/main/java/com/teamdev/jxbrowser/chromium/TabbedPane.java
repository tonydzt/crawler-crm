package com.teamdev.jxbrowser.chromium;

import java.awt.BorderLayout;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabbedPane
        extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(JPanel.class);
    private final List<Tab> tabs;
    private final TabCaptions captions;
    private final JComponent contentContainer;

    TabbedPane() {
        captions = new TabCaptions();
        tabs = new ArrayList();
        contentContainer = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());
        add(captions, "North");
        add(contentContainer, "Center");
    }

    public void disposeAllTabs() {
        for (Tab tab : getTabs()) {
            disposeTab(tab);
        }
    }

    public synchronized void disposeAllNotHomeTabs() {
        for (Tab tab : getTabs()) {
            String type = tab.getContent().getType();
            if ((type == null) || (!"Home".equals(type))) {
                disposeTab(tab);
            }
        }
    }

    private void disposeTab(Tab tab) {
        try {
            tab.getCaption().setSelected(false);
            tab.getContent().dispose();
            removeTab(tab);

            if (hasTabs()) {
                Tab firstTab = getFirstTab();
                firstTab.getCaption().setSelected(true);
            } else {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.setVisible(false);
                    window.dispose();
                }
            }
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private Tab findTab(TabCaption item) {
        try {
            for (Tab tab : getTabs()) {
                if (tab.getCaption().equals(item)) {
                    return tab;
                }
            }
            return null;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    public void addTab(Tab tab) {
        try {
            TabCaption caption = tab.getCaption();
            caption.addPropertyChangeListener("CloseButtonPressed", new TabCaptionCloseTabListener());
            caption.addPropertyChangeListener("TabSelected", new SelectTabListener());

            TabContent content = tab.getContent();
            content.addPropertyChangeListener("TabClosed", new TabContentCloseTabListener());

            captions.addTab(caption);
            tabs.add(tab);
            validate();
            repaint();
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private boolean hasTabs() {
        return !tabs.isEmpty();
    }

    private Tab getFirstTab() {
        try {
            if ((tabs != null) && (tabs.size() > 0)) {
                return tabs.get(0);
            }
            return null;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    public List<Tab> getTabs() {
        return new ArrayList(tabs);
    }

    private void removeTab(Tab tab) {
        try {
            TabCaption tabCaption = tab.getCaption();
            captions.removeTab(tabCaption);
            tabs.remove(tab);
            validate();
            repaint();
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    public void selectTab(Tab tab) {
        try {
            TabCaption tabCaption = tab.getCaption();
            TabCaption selectedTab = captions.getSelectedTab();
            if ((selectedTab != null) && (!selectedTab.equals(tabCaption))) {
                selectedTab.setSelected(false);
            }
            captions.setSelectedTab(tabCaption);
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    public void selectTab(String type) {
        try {
            for (Tab tab : tabs) {
                if (tab.getContent().getType().equals(type)) {
                    selectTab(tab);
                    break;
                }
            }
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    public void selectTab(Browser browser) {
        try {
            for (Tab tab : tabs) {
                Browser browserTab = tab.getContent().getBrowserView().getBrowser();
                if (browserTab == browser) {
                    selectTab(tab);
                    break;
                }
            }
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private class TabCaptionCloseTabListener implements PropertyChangeListener {
        private TabCaptionCloseTabListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            try {
                TabCaption caption = (TabCaption) evt.getSource();
                Tab tab = TabbedPane.this.findTab(caption);
                TabbedPane.this.disposeTab(tab);
            } catch (Throwable ee) {
                TabbedPane.logger.error("", ee);
            }
        }
    }

    private class SelectTabListener implements PropertyChangeListener {
        private SelectTabListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            try {
                TabCaption caption = (TabCaption) evt.getSource();
                Tab tab = TabbedPane.this.findTab(caption);
                if (caption.isSelected()) {
                    selectTab(tab);
                }
                if (!caption.isSelected()) {
                    TabContent content = tab.getContent();
                    contentContainer.remove(content);
                    contentContainer.validate();
                    contentContainer.repaint();
                } else {
                    TabContent content = tab.getContent();
                    contentContainer.add(content, "Center");
                    contentContainer.validate();
                    contentContainer.repaint();
                }
            } catch (Throwable ee) {
                TabbedPane.logger.error("", ee);
            }
        }
    }

    private class TabContentCloseTabListener implements PropertyChangeListener {
        private TabContentCloseTabListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            try {
                TabContent content = (TabContent) evt.getSource();
                Tab tab = findTab(content);
                TabbedPane.this.disposeTab(tab);
            } catch (Throwable ee) {
                TabbedPane.logger.error("", ee);
            }
        }

        private Tab findTab(TabContent content) {
            try {
                for (Tab tab : getTabs()) {
                    if (tab.getContent().equals(content)) {
                        return tab;
                    }
                }
                return null;
            } catch (Throwable ee) {
                TabbedPane.logger.error("", ee);
            }
            return null;
        }
    }
}
