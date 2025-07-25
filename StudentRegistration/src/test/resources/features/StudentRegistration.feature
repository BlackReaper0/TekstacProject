Feature: Student Registration Form Testing

  Background: 
    Given the student registration form is opened

  @positive @smoke
  Scenario: Successful submission of the form with all valid mandatory fields
    When the user fills all fields with valid data from Excel row "1"
    And submits the form
    Then the registration should be successful with message "Registered Successfully"

  @positive
  Scenario: Resetting the form clears all entered data
    When the user fills all fields with valid data from Excel row "1"
    And resets the form
    Then all form fields should be cleared

  @negative
  Scenario Outline: Form submission fails when required field <field> is missing
    When the user fills all fields except "<field>" with valid data from Excel row "1"
    And submits the form
    Then an alert with error message for "<field>" should be displayed

    Examples: 
      | field           |
      | Name            |
      | FatherName      |
      | PostalAddress   |
      | PersonalAddress |
      | EmailId         |
      | MobileNo        |

  @positive @extended
  Scenario: Successful submission with all fields including optional ones
    When the user fills all fields including optional ones with valid data from Excel row "2"
    And submits the form
    Then the registration should be successful with message "Registered Successfully"
