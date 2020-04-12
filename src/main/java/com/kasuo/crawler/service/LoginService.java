package com.kasuo.crawler.service;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSAccessible;
import org.apache.log4j.Logger;

public class LoginService {
   protected static Logger logger = Logger.getLogger(LoginService.class);
   public AbstractCrawlerService crawler;
   public Browser browser;

   @JSAccessible
   public void login() {
      logger.debug("js call login");
      this.crawler.handleLoginPage(this.browser, true);
   }
}
