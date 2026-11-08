# SauceDemo Selenium Automation Project

## Description:
SauceDemo is a testing website. This project is the result of a three-month intensive QA course. 
After completing manual testing and bug reporting, I selected several test cases for automation.
The manual test documentation is included in the project as Excel files.

A later, more advanced version of this project, built with **Cypress** and **JavaScript**
(more tests, sorting and regression coverage, custom commands and fixtures) is available here:
[Cypress/JavaScript version of this project](https://github.com/AleksandraPrlincevic/saucedemo-cypress-js-ui-tests)


## Tech Stack:
 - Java (OpenJDK 26)
 - IntelliJ IDEA 2026.1
 - Selenium WebDriver 4.43.0
 - WebDriverManager (Boni Garcia) – automatic browser driver management
 - TestNG 7.12.0
 - Maven
 - Page Object Model with Selenium PageFactory
 - Firefox browser
 - OS: Windows 11 Pro

## Prerequisites:
 - IntelliJ IDEA (includes bundled Maven — no separate installation needed)
 - Project SDK: OpenJDK 26
 - Firefox browser

## Installation

Clone the repository in IDE terminal:

```bash
git clone https://github.com/AleksandraPrlincevic/saucedemo-selenium-java-ui-tests.git
```
## How to run:
Open the project in IntelliJ IDEA and run tests directly from the IDE.

## Project Structure
* `base` - base classes: `BasePage` (parent for all Page Objects, initializes elements and
  explicit wait) and `BaseTest` (parent for test classes, declares Page Object fields and
  sets up the browser driver)
* `pages` - Page Object Model classes for each page of the site
* `components` — `Header` (reusable, shared across pages) and `InventoryItemComponent`
* `tests` - test classes
* `utils` -  test data (username/password)
* `manual_testing` - manual testing documented in Excel
## What's Covered

* Login — valid/invalid credentials
* Adding items to cart — from Inventory page and from Single Item page
* Cart — removing all items, completing checkout with two items in cart
* Known bug — a test documents that checkout is possible with an empty cart,
  which should not be allowed (test is written to fail while this bug exists)