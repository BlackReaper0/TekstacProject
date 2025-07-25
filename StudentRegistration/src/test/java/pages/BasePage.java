package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	protected void click(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}
	
	protected void type(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
	}
	
    protected void selectByVisibleText(WebElement dropdownElement, String visibleText) {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOf(dropdownElement)));
        select.selectByVisibleText(visibleText);
    }
	
	protected String getText(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)).getText();
	}
	
    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}