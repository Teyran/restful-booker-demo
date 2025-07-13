# [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html) E2E tests for Restful-booker
<img title="E2E tests for Restful-booker" src="images/mainlogo.png"></div>

##  <a name="contents">📄 Contents</a>
- [Tools and Technologies](#hammer_and_wrench-tools-and-technologies)
- [Test Cases](#white_check_mark-test-cases)
- [How to Run](#arrow_forward-how-to-run)
- [Allure Report](#-test-results-report-in-allure-report)
- [Telegram Notifications](#-telegram-notifications)

## 🛠️ Tools and Technologies
<a href="https://www.jetbrains.com/idea/"><img src="images/icons/intellij_idea.svg" title="IntelliJ IDEA" alt="IntelliJ IDEA" width="50" height="50"/></a>
<a href="https://www.java.com"><img src="images/icons/java.svg" title="Java" alt="Java" width="50" height="50"/></a>
<a href="https://testng.org/"><img src="images/icons/testng.png" title="TestNG" alt="TestNG" width="50" height="50"/></a>
<a href="https://maven.apache.org/"><img src="images/icons/maven.png" title="Gradle" alt="Gradle" width="50" height="50"/></a>
<a href="https://rest-assured.io"><img src="images/icons/rest_assured.svg" title="REST Assured" alt="REST Assured" width="50" height="50"/></a>
<a href="https://www.jenkins.io"><img src="images/icons/jenkins.svg" title="Jenkins" alt="Jenkins" width="50" height="50"/></a>
<a href="https://qameta.io/allure-report"><img src="images/icons/allure_report.svg" title="Allure Report" alt="Allure Report" width="50" height="50"/></a>
<a href="https://web.telegram.org/"><img src="images/icons/telegram.svg" title="Telegram" alt="Telegram" width="50" height="50"/></a>

## ✅ Test Cases
- [x] Successful authorization and token retrieval `POST`
- [x] Successful creation of a new booking `POST`
- [x] Successful update of existing booking `PUT`
- [x] Failed update without authorization token `PUT`
- [x] Retrieval of existing booking `GET`
- [x] Retrieval of all booking IDs returns status 200 `GET`
- [x] Successful booking deletion returns status 201 `DELETE`

## ▶️ How to Run
Tests can be run locally or via [Jenkins](https://jenkins.autotests.cloud/job/MvnBooker/) using the following command:
```bash
$ mvn clean test
```
<img src="images/jenkins-full.png" alt="Jenkins project page">

After the build, you can view the `Allure Report` results

[Back to content ⬆](#contents)

## <img width="3%" title="Allure Report" src="images/icons/allure_report.svg"> Результат тестов [Allure Report](https://jenkins.autotests.cloud/job/MvnBooker/4/allure/)
### Report Page

The page contains the following information:

>- **ALLURE REPORT** provides the number of executed tests and run time info
>- **SUITES** displays logically grouped test batches
>- **FEATURES** groups tests by feature
>- **TREND** shows test count history
>- **CATEGORIES** displays test categories

<img src="images/allure-overview.png" alt="Allure Report Overview page">

### Graph Page
This page shows various statistics based on test results: what passed and what failed.

<img src="images/allure-graph.png" alt="Allure Report graphs">

### Test Execution Page
This page shows which tests were run in different classes, with color indicating test status, and test details.
You can also view test attachments showing request and response data.

<img src="images/allure-test-case.png" alt="Allure Report suites">


[Back to content ⬆](#contents)

## <img width="3%" title="Telegram" src="images/icons/telegram.svg"> Telegram Notifications
 
**Telegram bot** sends results to a channel showing how many tests passed and a small chart.

<p align="center"><img src="images/tg.png" alt="Telegram notifications">
</p>

[Back to content ⬆](#contents)
