package com.teamdev.jxbrowser.chromium;

import com.kasuo.crawler.config.CrawlerConfig;
import com.teamdev.jxbrowser.chromium.resources.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@Service
public class JxBrowserDemo {

    @Autowired
    private CrawlerConfig crawlerConfig;

    private static AtomicInteger browserNum = new AtomicInteger(0);

    static {
        try {
            Field e = az.class.getDeclaredField("e");
            e.setAccessible(true);
            Field f = az.class.getDeclaredField("f");
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & 0xFFFFFFEF);
            modifersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public JxBrowserDemo() {
    }

    private static void initEnvironment() throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "crawler-or");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    }

    public static void setMailParam() {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/alternative;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public static void start() {
        LoggerProvider.setLevel(Level.OFF);
        try {
            initEnvironment();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMailParam();
        SwingUtilities.invokeLater(() -> {
            initAndDisplayUI();
        });
    }

    private static void initAndDisplayUI() {
        File workPath = new File("./work/" + browserNum.getAndIncrement());
        if (!workPath.exists()) {
            workPath.mkdir();
        }

        TabFactory.browserContext = new BrowserContext(new BrowserContextParams(workPath.getAbsolutePath()));

        TabbedPane tabbedPane = new TabbedPane();
        TabFactory.tabbedPane = tabbedPane;

        Tab tab = TabFactory.createHomeTab();
        insertTab(tabbedPane, tab);


        String title = "天眼查2";
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(2);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    tabbedPane.disposeAllTabs();
                    TabFactory.crawlerOrgService.stopFetch();
                    Thread.sleep(5000L);
                    System.exit(0);
                } catch (Throwable ee) {
                    System.out.println("windowClosing error!");
                }

            }


        });
        frame.add(tabbedPane, "Center");
        frame.setSize(1024, 700);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Resources.getIcon("META-INF/etoip32x32.png").getImage());
        frame.setVisible(true);
        frame.setExtendedState(6);
    }

    private static void insertNewTabButton(TabbedPane tabbedPane) {
        TabButton button = new TabButton(Resources.getIcon("META-INF/new-tab.png"), "New tab");
        button.addActionListener(e -> JxBrowserDemo.insertTab(tabbedPane, TabFactory.createHomeTab()));
        tabbedPane.addTabButton(button);
    }

    private static void insertTab(TabbedPane tabbedPane, Tab tab) {
        tabbedPane.addTab(tab);
        tabbedPane.selectTab(tab);
    }
}
