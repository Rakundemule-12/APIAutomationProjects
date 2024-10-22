Used tools and frameworks
Rest-Assured
Maven repository
TestNG
Jxl to extract test data from Excel files
Main features
Hybrid framework to test automation Webservices. It is keyword-driven and data-driven framework. So, you can separate testing data in Excel sheet with predefined schema and using some keywords inside Excel sheet you can validate the returned response body, headers and cookies.
Supports both REST and SOAP webservices testing.
All classes and methods are implemented in Java with Maven repository to include all dependencies needed. REST-Assured is used to offer a friendly DSL (Domain specific Languages) that describes a connection to an HTTP endpoint and expected results.
Utilizes the capabilities of TestNG such as Data provider annotation to separate test data in external file and flexible test suites configuration and management. Also, TestNG generates 2 types of reports, HTML and XML reports. The HTML reports are very descriptive with good statistics and the JUnit XML reports that can be integrated with Jenkins after test execution to have summary status of each deployment.
To use the framework, NO need to have any coding skills. ZERO line of code needed. Each test cases can be represented as a row or some rows (if the test case consists of some correlated steps) in the relevant Excel sheet. All you need is to know what is your scope in testing and just add the test data in the Excel sheet with the predefined schema then create the TestNG runner xml file that points to test data. For example: The predefined schema in that format: [Description], [URL], [Request method: GET, POST, ...], [Headers keys], [Headers values], [Body(if needed)], [Expected status code], [Assertions]
REST-Assured Java API is to test REST webservices and has no direct support for SOAP webservices. However, REST-Assured can test SOAP webservices by adding xml request in the body and execute POST HTTP request.
The framework validates the returned status code, response body, headers and cookies. It can validate each field data type and value. If the returned response includes object of arraylist, the framework can validate its size using the keyword ".size()"
Can be integrated into DevOps environment to accelerate the delivery process. After each Jenkins deployment, test cases can be executed automatically and the generated XML reports can be passed to Jira to log Defects/Tests automatically. Some configurations needed in Jenkins side.
Solves the complexity of testing correlated APIs as any test step can use data (body value, header or cookie) received in the previous steps. For example and more details you can check Sample_2 sheet in Excel test data. For more details about Keywords used and test data schema, you can have a look at the test data samples.
Source code is available here: https://github.com/ymhmd/buenoAPI-SourceCode
New Features in version 2.0
The returned cookies can be validated using keyword cookie:
The returned cookies can be reused in the next steps using keyword #prev_cookies_res[STEP]:PREV_COOKIE_KEY
NB: For more details how to use cookie keywords, Please refer to TestDataSample.xls > sample_04
New Features in version 3.0
Version 3.0 supports BDD test cases: The first column called "Description" should have simple plain text illustrating what each step does.
alt text

Version 3.0 can integrate with Jira to log bugs automatically. The following Jira configuration json file should be passed to the framework to determine required variables such as your Jira domain, email/password of the reporter, in which project the bug is reported and bug priority. Integration with Jira can be disabled by set false to key enable in Jira json configuration file.
{
	"enbale" : true, 
	"domain" : "your_jira_domain",
	"email" : "your_email",
	"password" : "your_password",
	"projectKey" : "your_project_key",
	"issueType" : "Bug/Task/Subtask ....",
	"priority" : "Low/High ...."
}
The summary of the reported bug in Jira refers to "step number" and the "step description" in the first column in the Excel file.
Demo
