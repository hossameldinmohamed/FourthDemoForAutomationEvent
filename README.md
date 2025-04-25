# Automation Framework
Build framework for company tasks. This framework is designed for both **GUI** test automation, ensuring robust end-to-end testing capabilities. This framework provides an efficient way to automate testing processes, ensuring reliability and performance in various environments.

### The main frameworks included in the project:
* Selenium WebDriver
* TestNG
* Allure Report
* Extent Reports

### Project Design:
* Page Object Model (POM) design pattern
* Data-Driven framework
* Fluent design approach (method chaining)
* Supporting Utilities package in *src/main/java/utils*, which includes various wrapper methods as core utilities for the project

### How to check execution logs and reports from GitHub Actions:
* Log in to GitHub as a prerequisite
* Open the **GitHub Actions** tab
* Open the latest workflow run from the list
* To check execution logs:
    - Click on "Test on Ubuntu" job
    - Open the **"Run Tests - Chrome Headless"** step to view logs
* To open the **Allure report**:
    - Go to the *Artifacts* section
    - Click on **"Allure Report"**, unzip it, and open `index.html`
    - If on Windows and data is empty, run `allow-file-access_open-report_chrome_windows.bat`
* To open the **Extent report**:
    - Click on **"Extent Report"** in *Artifacts*, unzip it, and open `ExtentReports.html`

### How to run the project main test cases locally:
* If running on IntelliJ, add the TestNG listener:
    - Open **Edit Run/Debug Configurations** → **Edit Configuration Templates**
    - Select **TestNG** → **Listeners** → Add `utils.TestngListener`
 
      <img width="964" alt="1" src="https://github.com/user-attachments/assets/a7b08b45-41e0-4cd5-97df-f56a64e9310e" />

* The properties file ***"configuration.properties"*** is located in *src/main/resources*, containing all execution configurations.
* The test cases are located in **src/test/java/**:
    - 📂 `SauceDemoEndToEndTest.java` (UI tests)
* To execute tests:
    - Set `execution.type=Local` in `configuration.properties` for local execution.
    - Right-click on the test suite XML file and select **Run As → TestNG Suite**.
    - To generate the **Allure Report**, run:
      ```sh
      mvn allure:serve
      ```
      (Requires Maven to be installed and configured)
    - After execution, the **Extent Report** (`ExtentReports.html`) will be available in the project root directory.

### How to run the project inside a Docker container:
* Install **Docker**
* The **Docker Compose file** is located in *src/main/resources/docker-compose_selenium4.yml*
* Open a terminal in the `.yml` file directory
* Run the following command to start Selenium Grid with a scalable configuration, ensuring optimal browser test coverage (**1 hub, 4 Chrome nodes, 2 Edge nodes, and 3 Firefox nodes**):
  ```sh
  docker-compose -f docker-compose_selenium4.yml up --scale chrome=4 --scale edge=2 --scale firefox=3 --remove-orphans -d
  ```
* To check running containers:
  ```sh
  docker ps
  ```
* Open a browser and go to [http://localhost:4444/ui](http://localhost:4444/ui) to view the Selenium Grid.
    - Navigate to the **Sessions** tab and click the **video icon** to see test execution live (**password:** `secret`).
* Run the tests with `execution.type=Remote` in `configuration.properties`.

### Notes:
- This framework now fully supports **parallel execution** across different browsers.
- The **GitHub Actions CI/CD pipeline** automatically runs tests on push and PRs to `main`, generating execution logs and reports.
- Test results are stored in **Allure and Extent reports**, which can be accessed via **GitHub Actions artifacts**...

### Steps on how to run on Selenium grid ( You need to install docker on your machine first)
![Selenium Grid](https://github.com/user-attachments/assets/816e72b9-57ab-4fe0-8f5f-98d899aa70cf)


### Selenium Grid Nodes 
<img width="1912" alt="2" src="https://github.com/user-attachments/assets/1f42c5b7-9d9e-4d37-a394-08757dd7fa21" />

### Live Execution View

![Live Execution](https://github.com/user-attachments/assets/8e0f46c6-8fe5-42cd-91c4-ae30bec6c7ef)


### After Execution Report
![Report](https://github.com/user-attachments/assets/58a99c20-91ed-42fa-9b39-d0b714194185)

