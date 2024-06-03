package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;

public class AndroidTests {
    @Test
    public void addFirstItemToCart() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("RRCTC00W08W")
                .setApp("D:\\saucelabs.apk").setAppWaitActivity("com.swaglabsmobileapp.MainActivity");
        AndroidDriver driver = new AndroidDriver(
                // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                new URL("http://127.0.0.1:4723"), options
        );
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"standard_user\").instance(0))")
            );
            WebElement el = driver.findElement(xpath("//android.widget.TextView[@text=\"standard_user\"]"));
            el.click();
            WebElement loginButton = driver.findElement(accessibilityId("test-LOGIN"));
            loginButton.click();

            driver.getPageSource();
            String productToBuyName = driver.findElements(accessibilityId("test-Item title")).get(0).getText();

            driver.findElements(accessibilityId("test-ADD TO CART")).get(0).click();
            String cartCount = driver.findElement(accessibilityId("test-Cart"))
                    .findElement(xpath("//android.widget.TextView"))
                    .getText();

            assertEquals(cartCount.trim(), "1");

            driver.findElement(accessibilityId("test-Cart")).click();

            driver.getPageSource();
            String productInCartName = driver.findElement(accessibilityId("test-Description"))
                    .findElement(xpath("(//android.widget.TextView)[1]"))
                    .getText();

            assertEquals(productInCartName, productToBuyName);
        } finally {
            driver.quit();
        }
    }
}
