package com.kasuo.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kasuo.crawler", "com.teamdev.jxbrowser.chromium"})
public class CrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}
}
