package com.teamdev.jxbrowser.chromium;

import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.BorderLayout;
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

    TabContent(BrowserView browserView, String type, TabFactory tabFactory) {
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
                    logger.error("", ee);
                }

            }
        });
        browserContainer = createBrowserContainer();
        jsConsole = createConsole();
        toolBar = createToolBar(browserView, type, tabFactory);

        container = new JPanel(new BorderLayout());
        container.add(browserContainer, "Center");

        setLayout(new BorderLayout());
        add(toolBar, "North");
        add(container, "Center");
    }

    private ToolBar createToolBar(BrowserView browserView, String type, TabFactory tabFactory) {
        ToolBar toolBar = new ToolBar(browserView, type, tabFactory);
        toolBar.addPropertyChangeListener("TabClosed", evt -> {
            try {
                firePropertyChange("TabClosed", false, true);
            } catch (Throwable ee) {
                logger.error("", ee);
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleDisplayed", evt -> {
            try {
                showConsole();
            } catch (Throwable ee) {
                logger.error("", ee);
            }
        });
        toolBar.addPropertyChangeListener("JSConsoleClosed", evt -> {
            try {
                hideConsole();
            } catch (Throwable ee) {
                logger.error("", ee);
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
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
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
            result.addPropertyChangeListener("JSConsoleClosed", evt -> {
                hideConsole();
                toolBar.didJSConsoleClose();
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
