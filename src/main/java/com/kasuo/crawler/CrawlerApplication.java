package com.kasuo.crawler;

import com.teamdev.jxbrowser.chromium.JxBrowserDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kasuo.crawler"})
public class CrawlerApplication {

	public static void main(String[] args) {
		JxBrowserDemo.start();
		SpringApplication.run(CrawlerApplication.class, args);
	}
}
