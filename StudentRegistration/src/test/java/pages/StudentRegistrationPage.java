package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.LoggerUtility;

public class StudentRegistrationPage extends BasePage {

	public StudentRegistrationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		LoggerUtility.info("Initialized Student Registration Page elements");
	}

	// Locators
	@FindBy(id = "textname")
	private WebElement studentNameInput;

	@FindBy(id = "fathername")
	private WebElement fatherNameInput;

	@FindBy(id = "paddress")
	private WebElement permanentAddressInput;

	@FindBy(id = "personaladdress")
	private WebElement personalAddressInput;

	@FindBy(id = "pincode")
	private WebElement pincodeInput;

	@FindBy(id = "emailid")
	private WebElement emailInput;

	@FindBy(id = "dob")
	private WebElement dobInput;

	@FindBy(id = "mobileno")
	private WebElement mobileInput;

	@FindBy(xpath = "//input[@name='sex' and @value='male']")
	private WebElement maleRadio;

	@FindBy(xpath = "//input[@name='sex' and @value='Female']")
	private WebElement femaleRadio;

	@FindBy(name = "City")
	private WebElement cityDropdown;

	@FindBy(name = "Course")
	private WebElement courseDropdown;

	@FindBy(name = "State")
	private WebElement stateDropdown;

	@FindBy(xpath = "//input[@value='Submit Form']")
	private WebElement submitButton;

	@FindBy(xpath = "//input[@type='reset']")
	private WebElement resetButton;

	@FindBy(tagName = "h2")
	private WebElement successMessage;

	@FindBy(className = "error-message")
	private List<WebElement> errorMessages;

	// Field error message locators would be added based on actual implementation
	// For example:
	@FindBy(id = "name-error")
	private WebElement nameError;

	// Action Methods
	public void enterStudentName(String name) {
		LoggerUtility.debug("Entering student name: " + name);
		type(studentNameInput, name);
	}

	public void enterFatherName(String fatherName) {
		LoggerUtility.debug("Entering father's name: " + fatherName);
		type(fatherNameInput, fatherName);
	}

	public void enterPermanentAddress(String address) {
		LoggerUtility.debug("Entering permanent address: " + address);
		type(permanentAddressInput, address);
	}

	public void enterPersonalAddress(String address) {
		LoggerUtility.debug("Entering personal address: " + address);
		type(personalAddressInput, address);
	}

	public void enterPincode(String pincode) {
		LoggerUtility.debug("Entering pincode: " + pincode);
		type(pincodeInput, pincode);
	}

	public void enterEmail(String email) {
		LoggerUtility.debug("Entering email: " + email);
		type(emailInput, email);
	}

	public void enterDOB(String dob) {
		LoggerUtility.debug("Entering date of birth: " + dob);
		type(dobInput, dob);
	}

	public void enterMobileNumber(String mobile) {
		LoggerUtility.debug("Entering mobile number: " + mobile);
		type(mobileInput, mobile);
	}

	public void selectGender(String gender) {
		LoggerUtility.debug("Selecting gender: " + gender);
		if (gender.equalsIgnoreCase("male")) {
			click(maleRadio);
		} else if (gender.equalsIgnoreCase("female")) {
			click(femaleRadio);
		}
	}

	public void selectCity(String city) {
		LoggerUtility.debug("Selecting city: " + city);
		selectByVisibleText(cityDropdown, city);
	}

	public void selectCourse(String course) {
		LoggerUtility.debug("Selecting course: " + course);
		selectByVisibleText(courseDropdown, course);
	}

	public void selectState(String state) {
		LoggerUtility.debug("Selecting state: " + state);
		selectByVisibleText(stateDropdown, state);
	}

	public void clickSubmit() {
		LoggerUtility.debug("Clicking submit button");
		click(submitButton);
	}

	public void clickReset() {
		LoggerUtility.debug("Clicking reset button");
		click(resetButton);
	}

	public String getSuccessMessage() {
		String message = getText(successMessage);
		LoggerUtility.debug("Success message: " + message);
		return message;
	}

	public boolean isSuccessMessagePresent() {
		boolean isPresent = isDisplayed(successMessage);
		LoggerUtility.debug("Is success message present: " + isPresent);
		return isPresent;
	}

	// Add these methods to your StudentRegistrationPage class

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getAlertText() {
		try {
			return driver.switchTo().alert().getText();
		} catch (Exception e) {
			LoggerUtility.error("No alert present: " + e.getMessage());
			return "";
		}
	}

	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			LoggerUtility.error("Failed to accept alert: " + e.getMessage());
		}
	}

	public String getFieldErrorMessage(String fieldName) {
		// First check if there's an alert
		if (isAlertPresent()) {
			String alertText = getAlertText();
			acceptAlert();
			return alertText;
		}

		// If no alert, check for inline validation messages
		switch (fieldName.toLowerCase()) {
		case "name":
			return studentNameInput.getAttribute("validationMessage");
		case "fathername":
			return fatherNameInput.getAttribute("validationMessage");
		case "postaladdress":
			return permanentAddressInput.getAttribute("validationMessage");
		case "emailid":
			return emailInput.getAttribute("validationMessage");
		case "mobileno":
			return mobileInput.getAttribute("validationMessage");
		default:
			return "";
		}
	}

	// Field value getters for verification
	public String getStudentName() {
		return studentNameInput.getAttribute("value");
	}

	public String getFatherName() {
		return fatherNameInput.getAttribute("value");
	}

	public String getPermanentAddress() {
		return permanentAddressInput.getAttribute("value");
	}

	public String getPersonalAddress() {
		return personalAddressInput.getAttribute("value");
	}

	public String getPincode() {
		return pincodeInput.getAttribute("value");
	}

	public String getEmail() {
		return emailInput.getAttribute("value");
	}

	public String getDOB() {
		return dobInput.getAttribute("value");
	}

	public String getMobileNumber() {
		return mobileInput.getAttribute("value");
	}
}