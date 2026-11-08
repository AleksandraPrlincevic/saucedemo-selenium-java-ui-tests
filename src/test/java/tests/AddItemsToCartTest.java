package tests;

import base.BaseTest;
import components.Header;
import components.InventoryItemComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.*;
import utils.TestData;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.testng.ITestResult;

public class AddItemsToCartTest extends BaseTest {

    private List<WebElement> chosenRandomItems;

    @BeforeMethod
    public void testSetUp() throws InterruptedException {
        driver = new FirefoxDriver();
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        chosenRandomItems = new ArrayList<>();

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        singleItemPage = new SingleItemPage(driver);
        headerPage = new Header(driver);
        cartPage = new CartPage(driver);

        loginPage.login(TestData.validUsername, TestData.validPassword);
 }
    @Test(priority = 1)
    public void canAddOneItemFromInventoryPage() throws InterruptedException {
           Assert.assertFalse(inventoryPage.getInventoryItems().isEmpty());
           Assert.assertFalse(headerPage.isCartBadgeVisible());
        InventoryItemComponent item = inventoryPage.getInventoryItemComponent(chosenRandomItems);
           String itemName = item.getItemName();
           String itemPrice =item.getItemPrice();
        item.clickAddToCartButton();
           Assert.assertTrue(headerPage.isCartBadgeVisible());
           Assert.assertEquals(headerPage.getNumberInBadge(), 1);
        headerPage.clickCartIcon();
           Assert.assertTrue(cartPage.isCheckoutButtonDisplayed());
           System.out.println("EXPECTED: " + itemName);
           System.out.println("ACTUAL: " + cartPage.getItemInCartName());
           Assert.assertEquals(cartPage.getItemInCartName(), itemName);
           Assert.assertEquals(cartPage.getItemInCartPrice(), itemPrice);
    }

    @Test(priority = 2)
    public void canAddThreeItemsFromInventoryPage() throws InterruptedException {
           Assert.assertFalse(inventoryPage.getInventoryItems().isEmpty());
           Assert.assertFalse(headerPage.isCartBadgeVisible());

        InventoryItemComponent item = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        String itemName = item.getItemName();
        String itemPrice =item.getItemPrice();
        item.clickAddToCartButton();

        InventoryItemComponent item2 = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        String itemName2 = item2.getItemName();
        String itemPrice2 =item2.getItemPrice();
        item2.clickAddToCartButton();

        InventoryItemComponent item3 = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        String itemName3 = item3.getItemName();
        String itemPrice3 =item3.getItemPrice();
        item3.clickAddToCartButton();

           Assert.assertTrue(headerPage.isCartBadgeVisible());
           Assert.assertEquals(headerPage.getNumberInBadge(), 3);
        headerPage.clickCartIcon();
           Assert.assertTrue(cartPage.isCheckoutButtonDisplayed());
           System.out.println("EXPECTED: " + itemName + ", " +itemName2+", "+ itemName3);
           System.out.println("ACTUAL: " + cartPage.getItemInCartName());

           Assert.assertTrue(cartPage.getItemNamesText().contains(itemName) &&
        cartPage.getItemNamesText().contains(itemName2) && cartPage.getItemNamesText().contains(itemName3));
           Assert.assertTrue(cartPage.getItemPricesText().contains(itemPrice)&& cartPage.getItemPricesText().contains(itemPrice2)&& cartPage.getItemPricesText().contains(itemPrice3));
    }

      @Test(priority = 5)
       public void canAddItemFromSingleItemPage(){
           Assert.assertFalse(inventoryPage.getInventoryItems().isEmpty());
        InventoryItemComponent item = inventoryPage.getInventoryItemComponent(chosenRandomItems);
        String itemName = item.getItemName();
        String itemPrice =item.getItemPrice();
        item.clickItemNameLink();
        shortWait.until(ExpectedConditions.elementToBeClickable(singleItemPage.addToCartButton));
           Assert .assertEquals(singleItemPage.getSingleItemName(), itemName);
           Assert .assertEquals(singleItemPage.getSingleItemPrice(), itemPrice);
        singleItemPage.clickAddToCartButton();
           Assert.assertEquals(headerPage.getNumberInBadge(), 1);
        headerPage.clickCartIcon();
           Assert.assertTrue(cartPage.isCheckoutButtonDisplayed());
           Assert.assertEquals(cartPage.getItemInCartName(), itemName);
           Assert.assertEquals(cartPage.getItemInCartPrice(), itemPrice);
     }

    @AfterMethod
    public void tearDown(ITestResult result) {
        driver.quit();
    }
}
