package com.teamdev.jxbrowser.chromium;

import com.teamdev.jxbrowser.chromium.resources.Resources;
import com.kasuo.crawler.service.AbstractCrawlerService;
import com.kasuo.crawler.util.CrmProperties;
import com.teamdev.jxbrowser.chromium.events.Callback;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jdk.nashorn.internal.objects.NativeFunction.call;


public class ToolBar
        extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCrawlerService.class);

    private JButton refreshButton;
    private JButton stopButton;
    private JMenuItem consoleMenuItem;
    private final JTextField addressBar;
    private final BrowserView browserView;

    public ToolBar(BrowserView browserView, String type) {
        this.browserView = browserView;
        addressBar = createAddressBar();
        setLayout(new GridBagLayout());
        add(createActionsPane(), new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
        add(addressBar, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 17, 1, new Insets(4, 0, 4, 5), 0, 0));
        add(createMenuButton(type), new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 22, 2, new Insets(0, 0, 0, 5), 0, 0));
    }

    public void didJSConsoleClose() {
        consoleMenuItem.setText("Run JavaScript...");
    }

    private JPanel createActionsPane() {
        try {
            refreshButton = createRefreshButton(browserView.getBrowser());
            stopButton = createStopButton(browserView.getBrowser());

            JPanel actionsPanel = new JPanel();

            actionsPanel.add(refreshButton);
            actionsPanel.add(stopButton);
            return actionsPanel;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    private JTextField createAddressBar() {
        try {
            final JTextField result = new JTextField("about:blank");
            result.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        browserView.getBrowser().loadURL(result.getText());
                    } catch (Throwable ee) {
                        ToolBar.logger.error("", ee);
                    }

                }
            });
            browserView.getBrowser().addLoadListener(new LoadAdapter() {
                @Override
                public void onStartLoadingFrame(StartLoadingEvent event) {
                    try {
                        if (event.isMainFrame()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    refreshButton.setEnabled(false);
                                    stopButton.setEnabled(true);
                                }
                            });
                        }
                    } catch (Throwable ee) {
                        ToolBar.logger.error("", ee);
                    }
                }

                @Override
                public void onProvisionalLoadingFrame(final ProvisionalLoadingEvent event) {
                    try {
                        if (event.isMainFrame()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    result.setText(event.getURL());
                                    result.setCaretPosition(result.getText().length());
                                }
                            });
                        }
                    } catch (Throwable ee) {
                        ToolBar.logger.error("", ee);
                    }
                }

                @Override
                public void onFinishLoadingFrame(FinishLoadingEvent event) {
                    try {
                        if (event.isMainFrame()) {
                            SwingUtilities.invokeLater(() -> {
                                refreshButton.setEnabled(true);
                                stopButton.setEnabled(false);

                            });

                        }


                    } catch (Throwable ee) {

                        ToolBar.logger.error("", ee);
                    }
                }
            });
            return result;
        } catch (Throwable ee) {
            logger.error("", ee);
        }
        return null;
    }

    private static JButton createRefreshButton(Browser browser) {
        return createButton("Refresh", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    browser.reload();
                } catch (Throwable ee) {
                    logger.error("", ee);
                }
            }
        });
    }

    private static JButton createStopButton(Browser browser) {
        return createButton("Stop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    browser.stop();
                } catch (Throwable ee) {
                   logger.error("", ee);
                }
            }
        });
    }

    private static JButton createButton(String caption, Action action) {
        ActionButton button = new ActionButton(caption, action);
        String imageName = caption.toLowerCase();
        button.setIcon(Resources.getIcon("META-INF/" + imageName + ".png"));
        button.setRolloverIcon(Resources.getIcon("META-INF/" + imageName + "-selected.png"));
        return button;
    }

    private JComponent createMenuButton(String type) {
        final JPopupMenu popupMenu = new JPopupMenu();

        if (type.equals("Home")) {
            JMenuItem menuItem = createFetchDataMenuItem(type);
            if (menuItem != null) {
                popupMenu.add(menuItem);
            }
            popupMenu.addSeparator();
            popupMenu.add(createRefreshPropertiesMenuItem());

            String production = CrmProperties.getProperty("production");
            if (production.equals("false")) {
                menuItem = createDebugFetchDataMenuItem(type);
                if (menuItem != null) {
                    popupMenu.add(menuItem);
                }
            }
            popupMenu.addSeparator();
        }

        popupMenu.add(createGetHTMLMenuItem());
        popupMenu.add(createSaveWebPageMenuItem());
        popupMenu.add(createClearCacheMenuItem());
        popupMenu.add(createPreferencesSubMenu());
        popupMenu.add(createConsoleMenuItem());
        popupMenu.add(createJavaScriptDialogsMenuItem());
        popupMenu.add(createExecuteCommandSubMenu());

        final ActionButton button = new ActionButton("Preferences", null);
        button.setIcon(Resources.getIcon("META-INF/gear.png"));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiers() & 0x10) != 0) {
                    popupMenu.show(e.getComponent(), 0, button.getHeight());
                } else {
                    popupMenu.setVisible(false);
                }
            }
        });
        return button;
    }

    private Component createRefreshPropertiesMenuItem() {
        JMenuItem menuItem = new JMenuItem("刷新配置参数");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrmProperties.refresh();
            }

        });
        return menuItem;
    }

    private Component createClearCountMenuItem(String type) {
        JMenuItem menuItem = new JMenuItem("清除统计数据");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }

        });
        return menuItem;
    }

    private Component createPreferencesSubMenu() {
        JMenu menu = new JMenu("Preferences");
        BrowserPreferences preferences = browserView.getBrowser().getPreferences();
        menu.add(createCheckBoxMenuItem("JavaScript Enabled", preferences.isJavaScriptEnabled(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setJavaScriptEnabled(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        menu.add(createCheckBoxMenuItem("Images Enabled", preferences.isImagesEnabled(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setImagesEnabled(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        menu.add(createCheckBoxMenuItem("Plugins Enabled", preferences.isPluginsEnabled(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setPluginsEnabled(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        menu.add(createCheckBoxMenuItem("JavaScript Can Access Clipboard", preferences.isJavaScriptCanAccessClipboard(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setJavaScriptCanAccessClipboard(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        menu.add(createCheckBoxMenuItem("JavaScript Can Open Windows", preferences.isJavaScriptCanOpenWindowsAutomatically(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setJavaScriptCanOpenWindowsAutomatically(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        menu.add(createCheckBoxMenuItem("Web Audio Enabled", preferences.isWebAudioEnabled(), new CheckBoxMenuItemCallback() {
            @Override
            public void call(boolean selected) {
                BrowserPreferences preferences = browserView.getBrowser().getPreferences();
                preferences.setWebAudioEnabled(selected);
                browserView.getBrowser().setPreferences(preferences);
                browserView.getBrowser().reloadIgnoringCache();
            }
        }));
        return menu;
    }

    private static JCheckBoxMenuItem createCheckBoxMenuItem(String title, boolean selected, CheckBoxMenuItemCallback action) {
        final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(title, selected);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                call(menuItem.isSelected());
            }
        });
        return menuItem;
    }

    private Component createClearCacheMenuItem() {
        JMenuItem menuItem = new JMenuItem("Clear Cache");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browserView.getBrowser().getCacheStorage().clearCache(new Callback() {
                    public void invoke() {
                        JOptionPane.showMessageDialog(browserView, "Cache is cleared successfully.", "Clear Cache", 1);
                    }
                });
            }
        });
        return menuItem;
    }

    private Component createExecuteCommandSubMenu() {
        final JMenu menu = new JMenu("Execute Command");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                Component[] menuItems = menu.getMenuComponents();
                for (Component menuItem : menuItems) {
                    menuItem.setEnabled(browserView.getBrowser().isCommandEnabled(((CommandMenuItem) menuItem).getCommand()));
                }
            }


            @Override
            public void menuDeselected(MenuEvent e) {
            }


            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        menu.add(createExecuteCommandSubMenuItem("Cut", EditorCommand.CUT));
        menu.add(createExecuteCommandSubMenuItem("Copy", EditorCommand.COPY));
        menu.add(createExecuteCommandSubMenuItem("Paste", EditorCommand.PASTE));
        menu.add(createExecuteCommandSubMenuItem("Select All", EditorCommand.SELECT_ALL));
        menu.add(createExecuteCommandSubMenuItem("Unselect", EditorCommand.UNSELECT));
        menu.add(createExecuteCommandSubMenuItem("Undo", EditorCommand.UNDO));
        menu.add(createExecuteCommandSubMenuItem("Redo", EditorCommand.REDO));
        menu.add(createExecuteCommandSubMenuItem("Insert Text...", "Insert Text", EditorCommand.INSERT_TEXT));
        menu.add(createExecuteCommandSubMenuItem("Find Text...", "Find Text", EditorCommand.FIND_STRING));
        return menu;
    }

    private Component createExecuteCommandSubMenuItem(String commandName, final EditorCommand command) {
        CommandMenuItem menuItem = new CommandMenuItem(commandName, command);
        menuItem.addActionListener(e -> browserView.getBrowser().executeCommand(command));
        return menuItem;
    }

    private Component createExecuteCommandSubMenuItem(String commandName, final String dialogTitle, final EditorCommand command) {
        CommandMenuItem menuItem = new CommandMenuItem(commandName, command);
        menuItem.addActionListener(e -> {
            String value = JOptionPane.showInputDialog(browserView, "Command value:", dialogTitle, -1);
            browserView.getBrowser().executeCommand(command, value);
        });
        return menuItem;
    }

    private Component createSaveWebPageMenuItem() {
        JMenuItem menuItem = new JMenuItem("Save Web Page...");
        menuItem.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("my-web-page.html"));
                int result = fileChooser.showSaveDialog(browserView);
                if (result == 0) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String dirPath = new File(selectedFile.getParent(), "resources").getAbsolutePath();
                    browserView.getBrowser().saveWebPage(selectedFile.getAbsolutePath(), dirPath, SavePageType.COMPLETE_HTML);
                }
            } catch (Throwable ee) {
                ToolBar.logger.error("", ee);
            }
        });
        return menuItem;
    }

    private Component createJavaScriptDialogsMenuItem() {
        JMenuItem menuItem = new JMenuItem("JavaScript Dialogs");
        menuItem.addActionListener(e -> browserView.getBrowser().loadURL("http://www.javascripter.net/faq/alert.htm"));
        return menuItem;
    }

    private Component createDownloadFileMenuItem() {
        JMenuItem menuItem = new JMenuItem("Download File");
        menuItem.addActionListener(e -> browserView.getBrowser().loadURL("http://www.etoip.com"));
        return menuItem;
    }

    private Component createGetHTMLMenuItem() {
        JMenuItem menuItem = new JMenuItem("Get HTML");
        menuItem.addActionListener(e -> {
            try {
                String html = browserView.getBrowser().getHTML();
                Window window = SwingUtilities.getWindowAncestor(browserView);
                JDialog dialog = new JDialog(window);
                dialog.setModal(true);
                dialog.setContentPane(new JScrollPane(new JTextArea(html)));
                dialog.setSize(700, 500);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } catch (Throwable ee) {
                ToolBar.logger.error("", ee);
            }
        });
        return menuItem;
    }

    private JMenuItem createFetchDataMenuItem(String type) {
        JMenuItem menuItem = new JMenuItem("启动抓取...");

        AbstractCrawlerService.menuItem = menuItem;

        menuItem.addActionListener(e -> {
            try {
                if ((TabFactory.crawlerOrgService == null) || (TabFactory.browserHome == null)) {
                    ToolBar.logger.error("crawler NOT FOUND crawlerOrgService browserHome instance!");
                    JOptionPane.showMessageDialog(null, "出现异常，请关闭应用，重新启动！", " 错误提示", 0);
                    return;
                }
                if (!TabFactory.hasTab("Search")) {
                    TabFactory.createSearchTab();
                }

                JMenuItem menuItem1 = (JMenuItem) e.getSource();
                if (TabFactory.crawlerOrgService.getFetchDataStatus()) {
                    menuItem1.setText("启动抓取...");
                    ToolBar.logger.info("手工停止抓取...");
                    TabFactory.crawlerOrgService.setFetchDataStatus();
                } else {
                    menuItem1.setText("停止抓取...");
                    ToolBar.logger.info("手工启动抓取...");
                    TabFactory.crawlerOrgService.setFetchDataStatus();
                    TabFactory.crawlerOrgService.startFetch(TabFactory.browserSearch);
                }
                TabFactory.crawlerOrgService.showDebugInfo();
            } catch (Throwable ee) {
                ToolBar.logger.error("", ee);
            }
        });
        return menuItem;
    }


    private JMenuItem createDebugFetchDataMenuItem(String type) {
        JMenuItem menuItem = new JMenuItem("启动抓取DEBUG");

        AbstractCrawlerService.menuItem2 = menuItem;

        menuItem.addActionListener(e -> {
            try {
                Browser browser = browserView.getBrowser();
                JMenuItem menuItem1 = (JMenuItem) e.getSource();
                if (TabFactory.crawlerOrgService.getFetchDataStatus()) {
                    menuItem1.setText("启动抓取DEBUG");
                    ToolBar.logger.info("手工停止抓取DEBUG...");
                    TabFactory.crawlerOrgService.setFetchDataStatus();
                    TabFactory.crawlerOrgService.setFetchDataTestStatus();
                } else {
                    menuItem1.setText("停止抓取DEBUG");
                    ToolBar.logger.info("手工启动抓取DEBUG...");
                    TabFactory.crawlerOrgService.setFetchDataStatus();
                    TabFactory.crawlerOrgService.setFetchDataTestStatus();
                    TabFactory.crawlerOrgService.startFetch(browser);
                }
                TabFactory.crawlerOrgService.showDebugInfo();
            } catch (Throwable ee) {
                ToolBar.logger.error("", ee);
            }
        });
        return menuItem;
    }

    private JMenuItem createConsoleMenuItem() {
        consoleMenuItem = new JMenuItem("Run JavaScript...");
        consoleMenuItem.addActionListener(e -> {
            if ("Run JavaScript...".equals(consoleMenuItem.getText())) {
                consoleMenuItem.setText("Close JavaScript Console");
                firePropertyChange("JSConsoleDisplayed", false, true);
            } else {
                consoleMenuItem.setText("Run JavaScript...");
                firePropertyChange("JSConsoleClosed", false, true);
            }
        });
        return consoleMenuItem;
    }

    private boolean isFocusRequired() {
        String url = addressBar.getText();
        return (url.isEmpty()) || (url.equals("about:blank"));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(() -> {
            if (ToolBar.this.isFocusRequired()) {
                addressBar.requestFocus();
                addressBar.selectAll();
            }
        });
    }

    private static class ActionButton extends JButton {
        private ActionButton(String hint, Action action) {
            super();
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            setBorderPainted(false);
            setRolloverEnabled(true);
            setToolTipText(hint);
            setText(null);
            setFocusable(false);
            setDefaultCapable(false);
        }
    }

    private static abstract interface CheckBoxMenuItemCallback {
        public abstract void call(boolean paramBoolean);
    }
}
