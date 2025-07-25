package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.StudentRegistrationPage;
import utilities.ExcelUtility;
import utilities.DriverManager;
import utilities.LoggerUtility;
import java.util.Map;
import static org.testng.Assert.*;

public class StudentRegistrationSteps {

	private final WebDriver driver = DriverManager.getDriver();
	private final StudentRegistrationPage form = new StudentRegistrationPage(driver);
	private Map<String, String> testData;

	@Given("the student registration form is opened")
	public void openRegistrationForm() {
		LoggerUtility.info("Student registration form is opened");
	}
	
	/*
	@When("the user fills all mandatory fields with valid data from Excel row {string}")
	public void fillMandatoryFieldsFromExcel(String rowNum) {
		int row = Integer.parseInt(rowNum);
		testData = ExcelUtility.getData("src/test/resources/testData/StudentRegistrationData.xlsx", "Sheet1", row);

		LoggerUtility.info("Filling mandatory fields with data from Excel row: " + row);

		form.enterStudentName(testData.get("Name"));
		form.enterFatherName(testData.get("FatherName"));
		form.enterPermanentAddress(testData.get("PostalAddress"));
		form.enterPersonalAddress(testData.get("PersonalAddress"));
		form.enterEmail(testData.get("EmailId"));
		form.enterMobileNumber(testData.get("MobileNo"));
		form.selectGender(testData.get("Sex"));
	}
	*/
	
	@When("the user fills all fields with valid data from Excel row {string}")
	public void fillAllFieldsFromExcel(String rowNum) {
		int row = Integer.parseInt(rowNum);
		testData = ExcelUtility.getData("src/test/resources/testData/StudentRegistrationData.xlsx", "Sheet1", row);

		LoggerUtility.info("Filling all fields with data from Excel row: " + row);

		// fillMandatoryFieldsFromExcel(rowNum);
		form.enterStudentName(testData.get("Name"));
		form.enterFatherName(testData.get("FatherName"));
		form.enterPermanentAddress(testData.get("PostalAddress"));
		form.enterPersonalAddress(testData.get("PersonalAddress"));
		form.selectGender(testData.get("Sex"));
		form.selectCity(testData.get("City"));
		form.selectCourse(testData.get("Course"));
		form.selectState(testData.get("State"));
		form.enterPincode(testData.get("PinCode"));
		form.enterEmail(testData.get("EmailId"));
		form.enterDOB(testData.get("DOB"));
		form.enterMobileNumber(testData.get("MobileNo"));
	}

	@When("the user fills all fields including optional ones with valid data from Excel row {string}")
	public void fillAllFieldsIncludingOptional(String rowNum) {
		fillAllFieldsFromExcel(rowNum);
	}

	@When("the user fills all fields except {string} with valid data from Excel row {string}")
	public void fillFormWithMissingField(String missingField, String rowNum) {
		int row = Integer.parseInt(rowNum);
		testData = ExcelUtility.getData("src/test/resources/testData/StudentRegistrationData.xlsx", "Sheet1", row);

		LoggerUtility.info("Filling form while intentionally omitting field: " + missingField);

		if (!"Name".equals(missingField))
			form.enterStudentName(testData.get("Name"));
		if (!"FatherName".equals(missingField))
			form.enterFatherName(testData.get("FatherName"));
		if (!"PostalAddress".equals(missingField))
			form.enterPermanentAddress(testData.get("PostalAddress"));
		if (!"PersonalAddress".equals(missingField))
			form.enterPersonalAddress(testData.get("PersonalAddress"));
		if (!"Sex".equals(missingField))
			form.selectGender(testData.get("Sex"));
		if (!"City".equals(missingField))
			form.selectCity(testData.get("City"));
		if (!"Course".equals(missingField))
			form.selectCourse(testData.get("Course"));
		if (!"State".equals(missingField))
			form.selectState(testData.get("State"));
		if (!"PinCode".equals(missingField))
			form.enterPincode(testData.get("PinCode"));
		if (!"EmailId".equals(missingField))
			form.enterEmail(testData.get("EmailId"));
		if (!"DOB".equals(missingField))
			form.enterDOB(testData.get("DOB"));
		if (!"MobileNo".equals(missingField))
			form.enterMobileNumber(testData.get("MobileNo"));
	}

	@When("submits the form")
	public void submitForm() {
		LoggerUtility.info("Submitting the registration form");
		form.clickSubmit();
	}

	@When("resets the form")
	public void resetForm() {
		LoggerUtility.info("Resetting the registration form");
		form.clickReset();
	}

	@Then("the registration should be successful with message {string}")
	public void verifySuccessMessage(String expectedMessage) {
		String actualMessage = form.getSuccessMessage();
		LoggerUtility.info("Verifying success message. Expected: " + expectedMessage + ", Actual: " + actualMessage);
		assertEquals(actualMessage, expectedMessage, "Registration success message mismatch");
	}

	@Then("all form fields should be cleared")
	public void verifyFieldsAreCleared() {
		LoggerUtility.info("Verifying all form fields are cleared after reset");

		assertTrue(form.getStudentName().isEmpty(), "Student name field is not empty");
		assertTrue(form.getFatherName().isEmpty(), "Father name field is not empty");
		assertTrue(form.getPermanentAddress().isEmpty(), "Permanent address field is not empty");
		assertTrue(form.getPersonalAddress().isEmpty(), "Personal address field is not empty");
		assertTrue(form.getPincode().isEmpty(), "Pincode field is not empty");
		assertTrue(form.getEmail().isEmpty(), "Email field is not empty");
		assertTrue(form.getDOB().isEmpty(), "DOB field is not empty");
		assertTrue(form.getMobileNumber().isEmpty(), "Mobile number field is not empty");
	}

	@Then("the form submission should fail")
	public void verifySubmissionFailed() {
		LoggerUtility.info("Verifying form submission failed as expected");
		assertFalse(form.isSuccessMessagePresent(), "Form submission should not be successful");
	}

	// Update the verifyFieldErrorMessage method
	@Then("an alert with error message for {string} should be displayed")
	public void verifyAlertErrorMessage(String field) {
		String expectedMessage = "";

		switch (field.toLowerCase()) {
		case "name":
			expectedMessage = "Please provide your Name!";
			break;
		case "fathername":
			expectedMessage = "Please provide your Father Name!";
			break;
		case "postaladdress":
			expectedMessage = "Please provide your Postal Address!";
			break;
		case "personaladdress":
			expectedMessage = "Please provide your Personal Address!";
			break;
		case "emailid":
			expectedMessage = "Please enter correct email ID";
			break;
		case "mobileno":
			expectedMessage = "Please provide a Mobile No in the format 123";
			break;
		default:
			fail("Unexpected field: " + field);
		}

		assertTrue(form.isAlertPresent(), "Expected alert not present for field: " + field);
		String actualMessage = form.getAlertText();
		form.acceptAlert();

		LoggerUtility.info("Verifying alert message for field: " + field);
		LoggerUtility.info("Expected: " + expectedMessage + ", Actual: " + actualMessage);

		assertTrue(actualMessage.contains(expectedMessage), "Alert message mismatch for field: " + field
				+ "\nExpected: " + expectedMessage + "\nActual: " + actualMessage);
	}
}