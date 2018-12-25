package com.teamdev.jxbrowser.chromium;

import com.kasuo.crawler.service.AbstractCrawlerService;
import com.kasuo.crawler.service.CrawlerOrgTYCService;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.RenderAdapter;
import com.teamdev.jxbrowser.chromium.events.RenderEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultDownloadHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultPopupHandler;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TabFactory {
    private static final Logger logger = LoggerFactory.getLogger(TabFactory.class);

    public static TabbedPane tabbedPane = null;
    public static AbstractCrawlerService crawlerOrgService = null;
    public static BrowserContext browserContext = null;
    public static Browser browserHome = null;
    public static Browser browserSearch = null;

    public TabFactory() {
    }

    public static Tab createHomeTab() {
        logger.info("ChromiumVariables...");
        Map<String, String> chromiumVariables = BrowserPreferences.getChromiumVariables();
        for (Entry<String, String> entry : chromiumVariables.entrySet()) {
            logger.info("key=" + entry.getKey() + ",value=" + entry.getValue());
        }

        logger.info("ChromiumSwitches before set...");
        for (String s : BrowserPreferences.getChromiumSwitches()) {
            logger.info(s);
        }

        BrowserPreferences.setChromiumSwitches("--renderer-process-limit=5", "--enable-logging", "-–log-level=0", "--disable-webgl-image-chromium", "--disable-2d-canvas-image-chromium", "--disable-gpu-memory-buffer-compositor-resources", "--disable-gpu-memory-buffer-video-frames",
                "--disable-native-gpu-memory-buffers", "--mem-pressure-system-reserved-kb=2000000", "--memory-pressure-thresholds-mb=1000,1000", "--gpu-program-cache-size-kb=100000", "--media-cache-size=100000");

        logger.info("ChromiumSwitches after set...");
        for (String s : BrowserPreferences.getChromiumSwitches()) {
            logger.info(s);
        }

        browserHome = new Browser(browserContext);
        logger.info("createTab: Home renderProcess pid=" + browserHome.getRenderProcessInfo().getPID());

        BrowserPreferences preferences = browserHome.getPreferences();
        logger.info("BrowserPreferences...");
        logger.info("isAllowRunningInsecureContent=" + preferences.isAllowRunningInsecureContent());
        logger.info("isApplicationCacheEnabled=" + preferences.isApplicationCacheEnabled());
        logger.info("isDatabasesEnabled=" + preferences.isDatabasesEnabled());
        logger.info("isImagesEnabled=" + preferences.isImagesEnabled());
        logger.info("isJavaScriptCanAccessClipboard=" + preferences.isJavaScriptCanAccessClipboard());
        logger.info("isJavaScriptCanOpenWindowsAutomatically=" + preferences.isJavaScriptCanOpenWindowsAutomatically());
        logger.info("isJavaScriptEnabled=" + preferences.isJavaScriptEnabled());
        logger.info("isLoadsImagesAutomatically=" + preferences.isLoadsImagesAutomatically());
        logger.info("isLocalStorageEnabled=" + preferences.isLocalStorageEnabled());
        logger.info("isPluginsEnabled=" + preferences.isPluginsEnabled());
        logger.info("isTransparentBackground=" + preferences.isTransparentBackground());

        preferences.setPluginsEnabled(false);
        logger.debug("PluginsInfo...");
        for (PluginInfo info : browserHome.getPluginManager().getPluginsInfo()) {
            logger.debug("PluginsInfo: name=" + info.getName() + "," + info.getDescription());
        }

        logger.debug("jxBrowser cacheDir: " + browserHome.getContext().getCacheDir());
        logger.debug("jxBrowser memoryDir: " + browserHome.getContext().getMemoryDir());
        logger.debug("jxBrowser dataDir: " + browserHome.getContext().getDataDir());
        logger.debug("jxBrowser storageType: " + (browserHome.getContext().getStorageType().compareTo(StorageType.DISK) == 0 ? "DISK" : "MEMORY"));

        BrowserView browserView = new BrowserView(browserHome);
        TabContent tabContent = new TabContent(browserView, "Home");
        TabCaption tabCaption = new TabCaption();
        tabCaption.setTitle("about:blank");
        tabContent.addPropertyChangeListener("PageTitleChanged", evt -> tabCaption.setTitle((String) evt.getNewValue()));
        browserHome.setDownloadHandler(new DefaultDownloadHandler(browserView));
        browserHome.setDialogHandler(new DefaultDialogHandler(browserView));
        browserHome.setPopupHandler(new DefaultPopupHandler());

        crawlerOrgService = CrawlerOrgTYCService.getInstance();

        browserHome.addRenderListener(new RenderAdapter() {
            @Override
            public void onRenderCreated(RenderEvent event) {
                TabFactory.logger.info("Chromium Home render process is created.");
            }

            @Override
            public void onRenderGone(RenderEvent event) {
                try {
                    TabFactory.logger.warn("Chromium Home render process is gone: terminationStatus=" + event.getTerminationStatus());

                    TabFactory.crawlerOrgService.sendNotice("9");

                    TabFactory.logger.info("Chromium Home render process is gone: 关闭tab");
                    TabFactory.tabbedPane.disposeAllTabs();

                    Thread.sleep(5000L);
                    TabFactory.crawlerOrgService.resetFetchStatus(event.getBrowser(), "Home浏览器停止运行");

                    Thread.sleep(5000L);
                    System.exit(-1);
                } catch (Throwable e) {
                    TabFactory.logger.error("Home onRenderGone error!", e);
                }

            }

        });
        browserHome.addDisposeListener((DisposeListener) event -> {
            TabFactory.logger.warn("Home browser disposed! ");
            TabFactory.browserHome = null;
            TabFactory.crawlerOrgService.resetFetchStatus(null, "浏览器停止运行");
        });
        browserHome.addLoadListener(new LoadAdapter() {
            @Override
            public void onStartLoadingFrame(StartLoadingEvent event) {
                TabFactory.startLoadingFrameHandler(event, "Home");
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent event) {
                TabFactory.failLoadingFrameHandler(event, "Home");
            }

            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                TabFactory.finishLoadingFrameHandler(event, "Home");

            }
        });
        String url = crawlerOrgService.getLoginUrl();
        browserHome.loadURL(url);
        return new Tab(tabCaption, tabContent);
    }

    public static synchronized void createSearchTab() {
        browserSearch = new Browser(browserContext);
        logger.info("createTab: Search renderProcess pid=" + browserSearch.getRenderProcessInfo().getPID());

        BrowserView browserView = new BrowserView(browserSearch);
        TabContent tabContent = new TabContent(browserView, "Search");
        TabCaption tabCaption = new TabCaption();
        tabCaption.setTitle("about:blank");
        tabContent.addPropertyChangeListener("PageTitleChanged", evt -> tabCaption.setTitle((String) evt.getNewValue()));
        browserSearch.setDownloadHandler(new DefaultDownloadHandler(browserView));
        browserSearch.setDialogHandler(new DefaultDialogHandler(browserView));
        browserSearch.setPopupHandler(new DefaultPopupHandler());


        browserSearch.addRenderListener(new RenderAdapter() {
            @Override
            public void onRenderCreated(RenderEvent event) {
                TabFactory.logger.info("Chromium Search render process is created.");
            }

            @Override
            public void onRenderGone(RenderEvent event) {
                try {
                    TabFactory.logger.warn("Chromium Search render process is gone: terminationStatus=" + event.getTerminationStatus());

                    TabFactory.crawlerOrgService.sendNotice("9");
                    Thread.sleep(5000L);

                    TabFactory.rebootFetchData(event.getBrowser());
                } catch (Throwable e) {
                    TabFactory.logger.error("Search onRenderGone error!", e);
                }

            }

        });
        browserSearch.addDisposeListener((DisposeListener) event -> {
            TabFactory.logger.warn("Search browser disposed!");
            TabFactory.rebootFetchData((Browser) event.getSource());
        });
        browserSearch.addLoadListener(new LoadAdapter() {
            @Override
            public void onStartLoadingFrame(StartLoadingEvent event) {
                TabFactory.startLoadingFrameHandler(event, "Search");
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent event) {
                TabFactory.failLoadingFrameHandler(event, "Search");
            }

            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                TabFactory.finishLoadingFrameHandler(event, "Search");

            }
        });
        Tab tab = new Tab(tabCaption, tabContent);
        tabbedPane.addTab(tab);
        tabbedPane.selectTab("Home");
    }

    private static void startLoadingFrameHandler(StartLoadingEvent event, String browserType) {
        if (event.isMainFrame()) {
            Browser browser = event.getBrowser();
            crawlerOrgService.recordBeginLoadingTimes(browser);
            logger.debug("************* tab " + browserType + " " + crawlerOrgService.pageType(browser) + " onStartLoadingFrame ... ");
            logger.debug("loadingURL=" + browser.getURL());
            logger.debug("************************************************************************************");
        }
    }

    private static void failLoadingFrameHandler(FailLoadingEvent event, String browserType) {
        Browser browser = event.getBrowser();
        crawlerOrgService.recordEndLoadingTimes(browser);
        logger.error("************* tab " + browserType + " " + crawlerOrgService.pageType(browser) + " onFailLoadingFrame! errorCode=" + event.getErrorCode() + "," + event.getErrorDescription() + ",uses time: " + crawlerOrgService.calcLoadingTimes(browser) + " ms");
        logger.debug("loadingURL=" + browser.getURL());
        logger.debug("************************************************************************************");
        crawlerOrgService.handleFailLoadingFrame(event);
    }

    private static void finishLoadingFrameHandler(FinishLoadingEvent event, String browserType) {
        try {
            if (event.isMainFrame()) {
                Browser browser = event.getBrowser();
                crawlerOrgService.recordEndLoadingTimes(browser);
                logger.debug("************* tab " + browserType + " " + crawlerOrgService.pageType(browser) + " onFinishLoadingFrame,uses time: " + crawlerOrgService.calcLoadingTimes(browser) + " ms");
                logger.debug("loadingURL=" + browser.getURL());


                synchronized (crawlerOrgService) {
                    crawlerOrgService.showDebugInfo();
                    AbstractCrawlerService.sleep(200L);
                    crawlerOrgService.handleOrgInfo(browser, event);
                }

                logger.debug("************************************************************************************");
            }
        } catch (Throwable e) {
            logger.error("handle onFinishLoadingFrame error ", e);
        }
    }

    private static void rebootFetchData(Browser browser) {
        try {
            logger.info("关闭tab");
            browserSearch = null;
            tabbedPane.disposeAllNotHomeTabs();


            Thread.sleep(5000L);
            crawlerOrgService.rebootFetchStatus(browser, "Search浏览器正在重启");


            Thread.sleep(2000L);

            createSearchTab();


            logger.info("重新启动抓取...");
            crawlerOrgService.rebootOverFetchStatus(browser);
            crawlerOrgService.setCrawlRebootNums(crawlerOrgService.getCrawlRebootNums() + 1);
            crawlerOrgService.setFetchDataStatus(true);
            crawlerOrgService.startFetch(browserSearch);
        } catch (Throwable e) {
            logger.error("rebootFetchData error ", e);
        }
    }

    public static void selectTab(Browser browser) {
        tabbedPane.selectTab(browser);
    }

    public static void selectTab(String type) {
        tabbedPane.selectTab(type);
    }

    public static synchronized boolean hasTab(String browserType) {
        for (Tab tab : tabbedPane.getTabs()) {
            if (tab.getContent().getType().equals(browserType)) {
                return true;
            }
        }
        return false;
    }
}
