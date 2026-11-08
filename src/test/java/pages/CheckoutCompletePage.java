package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getCorrectIcon() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("img[alt='Pony Express']")));
    }

    public WebElement getBackHomeButton(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-to-products")));
    }


    //------------------------------metode za testiranje---------------------------

    public void clickBackHomeButton(){
        getBackHomeButton().click();
    }
}
