package me.taco.core.web;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {
    
    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private static String contextPath;
    
    private static HtmlUnitDriver browser;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        log.debug("Context path: {}", contextPath);
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void testHomePage() {
        var homePage = "http://localhost:" + port + "/taco";
        browser.get(homePage);

        var homePageTitle = browser.getTitle();
        Assertions.assertThat(homePageTitle).isEqualTo("Taco - Home");

        var h1Text = browser.findElement(By.tagName("h1")).getText();
        Assertions.assertThat(h1Text).isEqualTo("Welcome to Taco");

        var imgSrc = browser.findElement(By.tagName("img")).getAttribute("src");
        Assertions.assertThat(imgSrc).isEqualTo(homePage + "/images/taco.png");
    }

}
