/*
 * Testerra
 *
 * (C) 2020, Peter Lehmann, T-Systems Multimedia Solutions GmbH, Deutsche Telekom AG
 *
 * Deutsche Telekom AG and all other contributors /
 * copyright owners license this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package eu.tsystems.mms.tic.testframework.report.pageobjects;


import eu.tsystems.mms.tic.testframework.execution.testng.AssertCollector;
import eu.tsystems.mms.tic.testframework.pageobjects.Check;
import eu.tsystems.mms.tic.testframework.pageobjects.GuiElement;
import eu.tsystems.mms.tic.testframework.pageobjects.factory.PageFactory;
import eu.tsystems.mms.tic.testframework.report.model.TestResultHelper;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractReportPage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class ClassesPage extends AbstractReportPage {

    private static final String SYNC_FAILED_WARNING_INDICATOR_TITLE = "Sync failed, see console log for details";

    //legend iterator
    @Check
    private GuiElement legendIndicatorRow = new GuiElement(this.getWebDriver(), By.xpath("//span[@title='Passed']/parent::div"), mainFrame);

    private GuiElement testsPassedLegendIndicator = legendIndicatorRow.getSubElement(By.xpath(".//span[@title='Passed']"));

    private GuiElement testsRetriedLegendIndicator = legendIndicatorRow.getSubElement(By.xpath(".//span[@title='Passed after Retry']"));

    private GuiElement testsFailedLegendIndicator = legendIndicatorRow.getSubElement(By.xpath(".//span[@title='Failed']"));

    private GuiElement configMethodsIndicator = legendIndicatorRow.getSubElement(By.xpath(".//font[@class='configMethods']"));

    //additional functions on class page
    private GuiElement hidePassedTestsCheckbox = new GuiElement(this.getWebDriver(), By.id("hidePassed"), mainFrame);

    private GuiElement buildUserString = new GuiElement(this.getWebDriver(), By.xpath("//tbody[@id='tests-3']/tr[1]/td[2]"), mainFrame);

    private GuiElement buildVerionString = new GuiElement(this.getWebDriver(), By.xpath("//tbody[@id='tests-3']/tr[2]/td[2]"), mainFrame);

    private GuiElement buildTimeStampString = new GuiElement(this.getWebDriver(), By.xpath("//tbody[@id='tests-3']/tr[3]/td[2]"), mainFrame);

    public ClassesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns all passed test classes table rows as web elements
     *
     * @return list of web elements representing the class table row (tr)
     */
    public List<GuiElement> getPassedTestClasses() {

        List <GuiElement> passedTestClassesList = new GuiElement(this.getWebDriver(), By.xpath("//span[@title='Passed']/../.."), mainFrame).getList();
        passedTestClassesList.remove(0); //remove first item of the list which is the legend symbol of the page

        return passedTestClassesList;
    }

    /**
     * Returns all failed test classes table rows as web elements
     *
     * @return list of web elements representing the class table row (tr)
     */
    public List<GuiElement> getFailedTestClasses() {

        List <GuiElement> failedTestClassesList = new GuiElement(this.getWebDriver(), By.xpath("//span[@title='Failed']/../.."), mainFrame).getList();
        failedTestClassesList.remove(0); //remove first item of the list which is the legend symbol of the page

        return failedTestClassesList;
    }


    /**
     * Returns the displayed numbers for the given class name for the given test result or all numbers if specified
     *
     * @param className  The simple displayed class name of testundertest class
     * @param testResult filter for test results. TestResult.ALL returns all possible numbers
     * @return map which contains the given test result and the corresponding number as single entry
     * or all possible test results and corresponding numbers
     */
    public Map<TestResultHelper.TestResultClassesColumn, String> getActualTestNumbers(String className, TestResultHelper.TestResultClassesColumn testResult) {


        Map<TestResultHelper.TestResultClassesColumn, String> actual = new HashMap<>();
        GuiElement classTableRow = getClassTableRowForClass(className);
        classTableRow.asserts().assertIsDisplayed();
        GuiElement classTestNumber;
        if (testResult != TestResultHelper.TestResultClassesColumn.ALL) {
            classTestNumber = classTableRow.getSubElement(By.xpath(testResult.getNumberXPath()));
            classTestNumber.setName("classTestNumber");
            classTestNumber.asserts().assertIsDisplayed();
            String actualValue = classTestNumber.getText();
            actual.put(testResult, actualValue);
        } else {
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.PASSED));
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.PASSEDMINOR));
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.FAILED));
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.FAILEDMINOR));
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.FAILEDEXPECTED));
            actual.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.SKIPPED));
        }

        return actual;

    }

    /**
     * Returns the single table row element (tr) based on the given class name
     *
     * @param className
     * @return GuiElement classTableRow
     */
    private GuiElement getClassTableRowForClass(String className) {
        GuiElement classTableRow = new GuiElement(this.getWebDriver(), By.xpath("//a[contains(text(),'" + className + "')]/../.."), mainFrame);
        classTableRow.setName("classTableRow");
        return classTableRow;
    }

    /**
     * Returns the single table row element (tr) based on the given row position
     *
     * @param position
     * @return GuiElement classTableRow
     */
    private GuiElement getClassTableRowForPosition(int position) {
        GuiElement classTableRow = new GuiElement(this.getWebDriver(), By.xpath("//*[@class='columnHeadings']/following-sibling::tr[" + position + "]"), mainFrame);
        classTableRow.setName("classTableRow");
        return classTableRow;
    }

    /**
     * Method to hide passed test class by clicking the "Hide passed Tests" checkbox
     *
     */
    public void clickButtonToHidePassedTests() {
        hidePassedTestsCheckbox.asserts().assertIsDisplayed();
        hidePassedTestsCheckbox.click();
    }

    /**
     * Asserts the green tick mark (V) as success indicator is displayed on the left of the testundertest of a given class name
     *
     * @param className
     */
    public void assertSuccessIndicatorIsDisplayedForClass(String className) {
        GuiElement classTableRow = getClassTableRowForClass(className);
        GuiElement successIndicator = classTableRow.getSubElement(By.xpath("//*[@class='textleft']/span[@title='Passed']"));
        successIndicator.scrollIntoView();
        successIndicator.setName("successIndicator");
        successIndicator.asserts("The success indicator for class name '" + className + "' should be displayed.").assertIsDisplayed();
    }

    /**
     * Asserts the red mark (X) as failed indicator is displayed on the left of the testundertest of a given class name
     *
     * @param className
     */
    public void assertBrokenIndicatorIsShownForClass(String className) {
        GuiElement classTableRow = getClassTableRowForClass(className);
        GuiElement brokenIndicator = classTableRow.getSubElement(By.xpath(".//*[@class='textleft']/span[@title='Failed']"));
        brokenIndicator.setName("brokenIndicator");
        brokenIndicator.asserts("The broken indicator should be displayed").assertIsDisplayed();
        GuiElement successIndicator = classTableRow.getSubElement(By.xpath(".//*[@class='textleft']/span[@title='Passed']"));
        successIndicator.setName("successIndicator");
        successIndicator.asserts("The success indicator should not be displayed anymore when there is a broken indicator.").assertIsNotDisplayed();
    }

    /**
     * Asserts only failed tests with the red mark (X) are displayed
     */
    public void assertAllPassedClassesAreDisplayed() {
        List<GuiElement> testClasses = getPassedTestClasses();
        for (GuiElement currentTestClass : testClasses) {
            currentTestClass.asserts().assertIsDisplayed();
            String className = currentTestClass.getSubElement(By.xpath(".//a")).getText();
            assertSuccessIndicatorIsDisplayedForClass(className);
        }
    }

    /**
     * Asserts only failed tests with the red mark (X) are displayed
     */
    public void assertAllPassedClassesAreNotDisplayed() {
        List<GuiElement> testClasses = getPassedTestClasses();

        for (GuiElement currentTestClass : testClasses) {
            currentTestClass.asserts().assertAttributeContains("style", "display: none");
        }
    }

    /**
     * Asserts only failed tests with the red mark (X) are displayed
     */
    public void assertAllFailedClassesAreDisplayed() {
        List<GuiElement> testClasses = getFailedTestClasses();
        for (GuiElement currentTestClass : testClasses) {
            String className = currentTestClass.getSubElement(By.xpath(".//a")).getText();
            assertBrokenIndicatorIsShownForClass(className);
        }
    }

    /**
     * Asserts the exclamation mark (!) - indicating a sync failed warning - is displayed on the left of the testundertest of a given class name
     *
     * @param className
     */
    public void assertSyncFailedWarningIsDisplayedForTestclass(String className) {
        GuiElement classTableRow = getClassTableRowForClass(className);
        classTableRow.setName(className + "ClassTableRow");
        GuiElement syncFailedWarningMethodIndicator = classTableRow.getSubElement(By.xpath(".//img[@title='" + SYNC_FAILED_WARNING_INDICATOR_TITLE + "']"));
        syncFailedWarningMethodIndicator.setName("syncFailedWarningMethodIndicator");
        syncFailedWarningMethodIndicator.asserts().assertIsDisplayed();
    }

    /**
     * Asserts the displayed numbers for the given class name for the given test result equals the expected numbers
     *
     * @param expectedNumbers
     * @param className
     */
    public void assertNumbersForTestResultsOfOneTestClass(Map<TestResultHelper.TestResultClassesColumn, String> expectedNumbers, String className) {
        Map<TestResultHelper.TestResultClassesColumn, String> actualClassesTableRowNumbers = new HashMap<>();
        actualClassesTableRowNumbers.putAll(getActualTestNumbers(className, TestResultHelper.TestResultClassesColumn.ALL));
        AssertCollector.assertEquals(actualClassesTableRowNumbers, expectedNumbers, "Numbers of test result are correct for Class: " + className);
    }

    /**
     * Asserts the values of the "INFORMATION" section are correctly displayed
     */
    public void assertTesterraInformationIsDisplayed() throws ParseException {

        final DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);

        buildUserString.asserts().assertIsDisplayed();
        buildVerionString.asserts().assertIsDisplayed();
        buildTimeStampString.asserts().assertIsDisplayed();
        dateFormat.parse(buildTimeStampString.getText());
    }

    /**
     * Asserts the the green tick mark (V) as success indicator is displayed in footer legend
     */
    public void assertPassedLegendSymbolIsDisplayed() {
        testsPassedLegendIndicator.asserts().assertIsDisplayed();
    }

    /**
     * Asserts the the olive green tick mark (V) as success with retry indicator is displayed in footer legend
     */
    public void assertRetryPassedLegendSymbolIsDisplayed() {
        testsRetriedLegendIndicator.asserts().assertIsDisplayed();
    }

    /**
     * Asserts the the red mark (X) as failed indicator is displayed in footer legend
     */
    public void assertFailedLegendSymbolIsDisplayed() {
        testsFailedLegendIndicator.asserts().assertIsDisplayed();
    }

    /**
     * Asserts all the footer legend labels displayed in footer legend
     */
    public void assertAllLegendSymbolsAreDisplayed() {
        assertPassedLegendSymbolIsDisplayed();
        assertRetryPassedLegendSymbolIsDisplayed();
        assertFailedLegendSymbolIsDisplayed();
    }

    /**
     * Goes to Classes Detail Page by clicking on the given class name
     *
     * @param className
     * @return ClassesDetailsPage
     */
    public ClassesDetailsPage gotoClassesDetailsPageForClass(String className) {
        GuiElement classTableRowLink = new GuiElement(this.getWebDriver(), By.partialLinkText(className), mainFrame);
        classTableRowLink.asserts().assertIsDisplayed();
        classTableRowLink.setName(className + "ClassTableRowLink");
        classTableRowLink.click();
        return PageFactory.create(ClassesDetailsPage.class, this.getWebDriver());
    }
}
