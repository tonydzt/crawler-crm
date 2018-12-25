package com.teamdev.jxbrowser.chromium;

import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TabContent extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(TabContent.class);

    private BrowserView browserView;
    private JComponent browserContainer;
    private final ToolBar toolBar;
    private final JComponent jsConsole;
    private final JComponent container;
    private String type;

    public TabContent(BrowserView browserView, String type) {
        this.type = type;
        this.browserView = browserView;
        this.browserView.getBrowser().addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                try {
                    if (event.isMainFrame()) {
                        firePropertyChange("PageTitleChanged", null, browserView.getBrowser().getTitle());
                    }
                } catch (Throwable ee) {
                    TabContent.logger.error("", ee);
                }

            }
        });
        browserContainer = createBrowserContainer();
        jsConsole = createConsole();
        toolBar = createToolBar(browserView, type);

        container = new JPanel(new BorderLayout());
        container.add(browserContainer, "Center");

        setLayout(new BorderLayout());
        add(toolBar, "North");
        add(container, "Center");
    }

    private void changeBrowser(BrowserView browserView) {
        try {
            container.removeAll();

            this.browserView = browserView;
            this.browserView.getBrowser().addLoadListener(new LoadAdapter() {
                @Override
                public void onFinishLoadingFrame(FinishLoadingEvent event) {
                    if (event.isMainFrame()) {
                        firePropertyChange("PageTitleChanged", null, browserView.getBrowser().getTitle());

                    }


                }


            });
            browserContainer = createBrowserContainer();
            container.add(browserContainer, "Center");
            validate();
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private ToolBar createToolBar(BrowserView browserView, String type) {
        ToolBar toolBar = new ToolBar(browserView, type);
        toolBar.addPropertyChangeListener("TabClosed", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    firePropertyChange("TabClosed", false, true);
                } catch (Throwable ee) {
                    TabContent.logger.error("", ee);
                }
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleDisplayed", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    TabContent.this.showConsole();
                } catch (Throwable ee) {
                    TabContent.logger.error("", ee);
                }
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleClosed", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    TabContent.this.hideConsole();
                } catch (Throwable ee) {
                    TabContent.logger.error("", ee);
                }
            }
        });
        return toolBar;
    }

    private void hideConsole() {
        showComponent(browserContainer);
    }

    private void showComponent(JComponent component) {
        try {
            container.removeAll();
            container.add(component, "Center");
            validate();
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private void showConsole() {
        try {
            JSplitPane splitPane = new JSplitPane(0);
            splitPane.add(browserContainer, "top");
            splitPane.add(jsConsole, "bottom");
            splitPane.setResizeWeight(0.8D);
            splitPane.setBorder(BorderFactory.createEmptyBorder());
            showComponent(splitPane);
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    private JComponent createConsole() {
        try {
            JSConsole result = new JSConsole(browserView.getBrowser());
            result.addPropertyChangeListener("JSConsoleClosed", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    TabContent.this.hideConsole();
                    toolBar.didJSConsoleClose();
                }
            });
            return result;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    private JComponent createBrowserContainer() {
        try {
            JPanel container = new JPanel(new BorderLayout());
            container.add(browserView, "Center");
            return container;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    public void dispose() {
        try {
            browserView.getBrowser().dispose();
        } catch (Throwable ee) {
            logger.error("", ee);
        }
    }

    public BrowserView getBrowserView() {
        return browserView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
