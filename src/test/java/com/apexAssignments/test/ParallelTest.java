package com.apexAssignments.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

    public class ParallelTest {

        WebDriver driver;

        @Test(priority = 1)
        public void testChrome() throws InterruptedException {
            System.out.println("The thread ID for Chrome is "+ Thread.currentThread().getId());
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get("https://www.bstackdemo.com/");
            driver.manage().window().maximize();
            Assert.assertEquals(driver.getTitle(), "StackDemo");
        }

        @Test(priority=2)
        public void testFirefox() throws InterruptedException {
            System.out.println("The thread ID for Firefox is "+ Thread.currentThread().getId());
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.get("https://www.bstackdemo.com/");
            driver.manage().window().maximize();
            Assert.assertEquals(driver.getTitle(), "StackDemo");
        }

        @AfterClass
        public void close() {
            driver.quit();
        }
    }

