package tests;

import base.BaseTest;
import components.Header;
import components.InventoryItemComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartTest extends BaseTest {

    String itemName;
    String itemPrice;
    String itemName2;
    String itemPrice2;

    @BeforeMethod
    public void testSetUp() throws InterruptedException {
        driver = new FirefoxDriver();
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        headerPage = new Header(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        loginPage.login(TestData.validUsername, TestData.validPassword);

        List<WebElement> chosenRandomItems = new ArrayList<>();
        InventoryItemComponent item = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        itemName = item.getItemName();
        itemPrice = item.getItemPrice();
        item.clickAddToCartButton();

        InventoryItemComponent item2 = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        itemName2 = item2.getItemName();
        itemPrice2 = item2.getItemPrice();
        item2.clickAddToCartButton();

        headerPage.clickCartIcon();
          Assert.assertTrue(headerPage.getPageTitle().contains("Cart"));
    }

    @Test
    public void canDeleteAllItemsFromCart(){
        cartPage.removeAllItemsFromCart();
          Assert.assertFalse(headerPage.isCartBadgeVisible());
          Assert.assertTrue(cartPage.getItemNames().isEmpty());
    }

    @Test
    public void canFinishOrderFromCartPageWithTwoItemsInCart(){
           Assert.assertTrue(headerPage.isCartBadgeVisible());
        cartPage.clickCheckoutButton();
           Assert.assertTrue(checkoutStepOnePage.isCheckoutInfoContainer());
           Assert.assertTrue(headerPage.getPageTitle().contains("Information"));
        checkoutStepOnePage.inputFirstnameField();
        checkoutStepOnePage.inputLastnameField();
        checkoutStepOnePage.inputPostalCodeField();
        checkoutStepOnePage.clickContinueButton();
           Assert.assertTrue(headerPage.getPageTitle().contains("Overview"));
           Assert.assertTrue(checkoutStepTwoPage.getItemNamesText().contains(itemName) && checkoutStepTwoPage.getItemNamesText().contains(itemName2));
           Assert.assertTrue(checkoutStepTwoPage.getItemPricesText().contains(itemPrice) && checkoutStepTwoPage.getItemPricesText().contains(itemPrice2));
        double expectedTotal = Double.parseDouble(itemPrice.replace("$", ""))
                + Double.parseDouble(itemPrice2.replace("$", ""));
        double actualTotal = Double.parseDouble(
                checkoutStepTwoPage.getItemPriceFromTotalText().replace("Item total: $", ""));
        Assert.assertEquals(actualTotal, expectedTotal, 0.001);
        checkoutStepTwoPage.clickFinishButton();
            Assert.assertTrue(checkoutCompletePage.getCorrectIcon().isDisplayed());
            Assert.assertTrue(headerPage.getPageTitle().contains("Complete"));
        checkoutCompletePage.clickBackHomeButton();
            Assert.assertFalse(inventoryPage.getInventoryItems().isEmpty());
            Assert.assertFalse(headerPage.isCartBadgeVisible());
    }

    @Test
    public void canNotCheckoutWithEmptyCart() {  // Test pada jer mozemo da kliknemo na Checkout i on nas vodi na CheckoutStepOnePage, iako ne bi smelo jer je korpa prazna
        cartPage.removeAllItemsFromCart();
        Assert.assertFalse(headerPage.isCartBadgeVisible());
        cartPage.clickCheckoutButton();
        Assert.assertTrue(headerPage.getPageTitle().contains("Cart"), "User left the Cart page with an empty cart and is on: " + headerPage.getPageTitle());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}