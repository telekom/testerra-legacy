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

import eu.tsystems.mms.tic.testframework.pageobjects.Check;
import eu.tsystems.mms.tic.testframework.pageobjects.GuiElement;
import eu.tsystems.mms.tic.testframework.pageobjects.factory.PageFactory;
import eu.tsystems.mms.tic.testframework.report.model.IReportAnnotationVerifier;
import eu.tsystems.mms.tic.testframework.report.model.ReportAnnotationType;
import eu.tsystems.mms.tic.testframework.report.model.TestResultHelper;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractMethodDetailsPage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class MethodDetailsPage extends AbstractMethodDetailsPage implements IReportAnnotationVerifier {

    private String HISTORY_ELEMENT_LOCATOR = ("//div[@class='gitgraph-inner']//div[%d]");
    private String HISTORY_DATE_LOCATOR = ("");

    @Check
    private GuiElement backTab = new GuiElement(this.getWebDriver(), By.xpath("//div[@class='detailsmenu']"), mainFrame);
    private GuiElement detailsTab = new GuiElement(this.getWebDriver(), By.id("buttondetails"), mainFrame);
    private GuiElement stepsTab = new GuiElement(this.getWebDriver(), By.id("buttonlogs"), mainFrame);
    private GuiElement stackTab = new GuiElement(this.getWebDriver(), By.id("buttonstack"), mainFrame);
    private GuiElement screenShotTab = new GuiElement(this.getWebDriver(), By.id("buttonscreen"), mainFrame);
    private GuiElement minorErrorTab = new GuiElement(this.getWebDriver(), By.id("buttonminor"), mainFrame);
    private GuiElement dependenciesTab = new GuiElement(this.getWebDriver(), By.id("buttondeps"), mainFrame);
    private GuiElement evolutionTab = new GuiElement(this.getWebDriver(), By.id("buttonhistory"), mainFrame);
    private GuiElement assertionsTab = new GuiElement(this.getWebDriver(), By.id("buttoncollectedasserts"), mainFrame);

    private GuiElement historyElementsGraph = new GuiElement(this.getWebDriver(), By.id("gitGraph"), mainFrame);

    /**
     * Method
     */
    private GuiElement methodNameString = new GuiElement(this.getWebDriver(), By.xpath("(//*[@class='dashboardTextSmall'])[1]"), mainFrame);
    private GuiElement classNameString = new GuiElement(this.getWebDriver(), By.xpath("//tbody/tr[1]/td[3]/*[4]"), mainFrame);
    private GuiElement methodResultString = new GuiElement(this.getWebDriver(), By.xpath("(//h5[text()='Method']/../div)[2]"), mainFrame);
    private GuiElement stepString = new GuiElement(this.getWebDriver(), By.xpath("//tbody/tr[1]/td[3]/*[5]/*[2]"), mainFrame);

    /**
     * Context
     */
    private GuiElement contextButton = new GuiElement(this.getWebDriver(), By.xpath("//*[@title=\"Show Fingerprint\" and @onclick=\"toggleElement('context');\"]"), mainFrame);
    private GuiElement context = new GuiElement(this.getWebDriver(), By.id("context"), mainFrame);

    private GuiElement repairedFailsIndication = new GuiElement(this.getWebDriver(), By.xpath("//div[@class='skipped']"), mainFrame);

    public String durationLocator = "//*[@class='cellTop']//*[contains(text(), 'Duration')]/..";
    private GuiElement duration = new GuiElement(this.getWebDriver(), By.id("actualRunDuration"), mainFrame);
    private GuiElement startTime = new GuiElement(this.getWebDriver(), By.xpath(durationLocator + "//div[@class='dashboardTextSmall'][1]"), mainFrame);
    private GuiElement finishTime = new GuiElement(this.getWebDriver(), By.xpath(durationLocator + "//div[@class='dashboardTextSmall'][2]"), mainFrame);

    private GuiElement evolutionEntry1 = new GuiElement(this.getWebDriver(), By.xpath("//*[@class='highcharts-markers highcharts-series-0 highcharts-tracker']/*[1]"), mainFrame);
    private GuiElement evolutionEntry2 = new GuiElement(this.getWebDriver(), By.xpath("//*[@class='highcharts-markers highcharts-series-0 highcharts-tracker']/*[2]"), mainFrame);

    //TODO  IDs einfügen
    private GuiElement minorCount = new GuiElement(this.getWebDriver(), By.xpath("//td[@class='cellTop']//div[@class='error clickable']"), mainFrame);

    //TODO  IDs einfügen
    private GuiElement errorMessageString = new GuiElement(this.getWebDriver(), By.xpath("//div[@style='color: red; font-size: 30px; padding: 25px; line-height: 40px;']"), mainFrame);
    private GuiElement errorMessageExtraInfo = errorMessageString.getSubElement(By.xpath("/div"));
    private GuiElement fingerprintButton = new GuiElement(this.getWebDriver(), By.xpath("//*[@title=\"Show Fingerprint\" and @onclick=\"toggleElement('fingerprint');\"]"), mainFrame);
    private GuiElement fingerprintString = new GuiElement(this.getWebDriver(), By.xpath("//div[@id='fingerprint']//div[@class='error']"), mainFrame);

    public GuiElement durationEvo = new GuiElement(this.getWebDriver(), By.xpath("//*[@class=' highcharts-background']"), mainFrame);
    public String DurEvoPoint_LOCATOR = ("//*[@class='highcharts-markers highcharts-series-0 highcharts-tracker']//*[%d]");

    public MethodDetailsPage(WebDriver driver) {

        super(driver);
    }

    public GuiElement getBackTab() {
        return backTab;
    }

    public GuiElement getDetailsTab() {
        return detailsTab;
    }

    public GuiElement getStepsTab() {
        return stepsTab;
    }

    public GuiElement getStackTab() {
        return stackTab;
    }

    public GuiElement getScreenShotTab() {
        return screenShotTab;
    }

    public GuiElement getMinorErrorTab() {
        return minorErrorTab;
    }

    public GuiElement getDependenciesTab() {
        return dependenciesTab;
    }

    public GuiElement getEvolutionTab() {
        return evolutionTab;
    }

    public MethodDetailsPage toggleContext() {
        contextButton.click();
        return PageFactory.create(MethodDetailsPage.class, this.getWebDriver());
    }

    public String getContextClassString() {
        String classString = context.getText().split("Class: ")[1].split("\n")[0];
        return classString;
    }

    public String getContextSuiteString() {
        String suiteString = context.getText().split("Suite: ")[1].split("\n")[0];
        return suiteString;
    }

    public String getContextTestString() {
        String testString = context.getText().split("Test: ")[1].split("\n")[0];
        return testString;
    }

    public GuiElement getRepairedFailsIndication() {
        return repairedFailsIndication;
    }

    public String getDuration() {
        return duration.getText().toString();
    }

    public String getStartTime() {
        return startTime.getText().toString();
    }

    public String getFinishTime() {
        return finishTime.getText().toString();
    }

    public GuiElement getEvolutionEntry1() {
        return evolutionEntry1;
    }

    public GuiElement getEvolutionEntry2() {
        return evolutionEntry2;
    }

    public GuiElement getMinorCount() {
        return minorCount;
    }

    public GuiElement getErrorMessageString() {
        return errorMessageString;
    }

    public GuiElement getErrorMessageExtraInfo() {
        return errorMessageExtraInfo;
    }

    public GuiElement getFingerprintString() {
        return fingerprintString;
    }

    public MethodDetailsPage toggleFingerprint() {
        getFingerprintString().asserts("Expected the fingerprint detail is not shown before clicking the details button").assertIsNotDisplayed();
        fingerprintButton.click();
        getFingerprintString().asserts("Expected the fingerprint detail is shown after clicking the details button").assertIsDisplayed();
        return PageFactory.create(MethodDetailsPage.class, this.getWebDriver());
    }

    /**
     * History Timeline
     */

    public int getNumberOfAllEntries() {
        this.getWebDriver().switchTo().defaultContent();
        this.getWebDriver().switchTo().frame(0);
        List<WebElement> elements = this.getWebDriver().findElements(By.xpath("//div[@class='gitgraph-inner']/div[@class='gitgraph-detail']"));
        int numberOfAllEntries = elements.size();
        return numberOfAllEntries;
    }

    public int historyTimelineFirstTestCounter() {
        String historyEntry = getTextforHistoryElementByPosition(1);
        String subStr = historyEntry.substring(0, (historyEntry.indexOf("t")) - 1);
        int numberofTests = Integer.parseInt(subStr.replace("Passed ", ""));
        return numberofTests;
    }

    public String getDateforHistoryElementByPosition(int position) {
        String dateLocator = String.format(HISTORY_DATE_LOCATOR, position + 1);
        GuiElement historyDateElement = new GuiElement(this.getWebDriver(), By.xpath(dateLocator), mainFrame);
        return historyDateElement.getText();
    }

    public String getTextforHistoryElementByPosition(int position) {
        return getHistoryElementByPosition(position).getText();
    }

    public GuiElement getHistoryElementByPosition(int position) {
        final int positionOffsetDOM = 1; // First history position is 2, second is 3, etc.
        if (position < 1) {
            throw new RuntimeException("Invalid position in HISTORY of " + MethodDetailsPage.class.getSimpleName() + ": " + position);
        }
        String elementLocator = String.format(HISTORY_ELEMENT_LOCATOR, position + positionOffsetDOM);
        GuiElement historyElement = new GuiElement(this.getWebDriver(), By.xpath(elementLocator), mainFrame);
        historyElement.setName("historyEntry Position # " + position);
        return historyElement;
    }

    /**
     * Duration Evolution - Only Passed Tests
     */

    public int countTestsInDurEvo() {
        List<WebElement> CountedTests = this.getWebDriver().findElements(By.xpath("//*[@class='highcharts-markers highcharts-series-0 highcharts-tracker']//*"));
        return CountedTests.size();
    }

    public MethodDetailsPage mouseOverDurEvoPoint(int parameter) {
        String LOCATOR = String.format(DurEvoPoint_LOCATOR, parameter);
        GuiElement DurEvoPoint = new GuiElement(this.getWebDriver(), By.xpath(LOCATOR));
        DurEvoPoint.mouseOver();
        return PageFactory.create(MethodDetailsPage.class, this.getWebDriver());
    }

    public String getMethodNameString() {
        return methodNameString.getText();
    }

    public GuiElement getMethodNameElement() {
        return methodNameString;
    }

    public String getClassNameString() {
        return classNameString.getText();
    }

    public String getMethodResultString() {
        return methodResultString.getText();
    }

    public GuiElement getMethodResultElement() {
        return methodResultString;
    }

    public String getStepString() {
        return stepString.getText();
    }

    public GuiElement getDurEvo() {
        return durationEvo;
    }

    public GuiElement getMinorErrors() {
        return minorErrorButton;
    }

    public DashboardPage clickBackTab() {
        backTab.click();
        return PageFactory.create(DashboardPage.class, this.getWebDriver());
    }

    public MethodDetailsPage clickDetailsTab() {
        detailsTab.click();
        return PageFactory.create(MethodDetailsPage.class, this.getWebDriver());
    }

    public MethodStepsPage clickStepsTab() {
        stepsTab.click();
        return PageFactory.create(MethodStepsPage.class, this.getWebDriver());
    }

    public MethodStackPage clickStackTab() {
        stackTab.click();
        return PageFactory.create(MethodStackPage.class, this.getWebDriver());
    }

    public MethodScreenshotPage clickScreenShotTab() {
        screenShotTab.click();
        return PageFactory.create(MethodScreenshotPage.class, this.getWebDriver());
    }

    public MethodMinorErrorsPage clickMinorErrorsTab() {
        minorErrorTab.click();
        return PageFactory.create(MethodMinorErrorsPage.class, this.getWebDriver());
    }

    public MethodDependenciesPage clickDependenciesTab() {
        dependenciesTab.click();
        return PageFactory.create(MethodDependenciesPage.class, this.getWebDriver());
    }

    public MethodEvolutionPage clickEvolutionTab() {
        evolutionTab.click();
        return PageFactory.create(MethodEvolutionPage.class, this.getWebDriver());
    }

    public MethodAssertionsPage clickAssertionsTab() {
        assertionsTab.click();
        return PageFactory.create(MethodAssertionsPage.class, this.getWebDriver());
    }

    public void assertAssertionsTabIsDisplayed(){
        assertionsTab.asserts().assertIsDisplayed();
    }

    public void assertAssertionsTabIsNotDisplayed(){
        assertionsTab.asserts().assertIsNotDisplayed();
    }

    @Override
    public void assertAnnotationMarkIsDisplayed(ReportAnnotationType annotationType, String methodName) {
        GuiElement methodNameElement = getMethodNameElement();
        GuiElement annotationElement = methodNameElement.getSubElement(By.xpath(String.format(LOCATOR_FONT_ANNOTATION, annotationType.getAnnotationDisplayedName())));
        annotationElement.setName("annotationElementFor" + annotationType);
        annotationElement.asserts().assertIsDisplayed();
    }

    @Override
    public void assertAllAnnotationMarksAreDisplayed(String methodName) {
        for (ReportAnnotationType annotationType : ReportAnnotationType.values()) {
            assertAnnotationMarkIsDisplayed(annotationType, methodName);
        }
    }

    public void assertCorrectTestMethodIsDisplayed(String methodName, TestResultHelper.TestResult methodResult) {
        getMethodNameElement().asserts("The correct element of the method name should be displayed.").assertTextContains(methodName);
        getMethodResultElement().asserts("The correct result of the element should be displayed.").assertTextContains(methodResult.getXpathClassesDetailsHeader());
    }

    //@Override
    /*public void assertRetryMarkerIsDisplayed(String methodName) {
        GuiElement methodNameElement = getMethodNameElement();
        GuiElement annotationElement = methodNameElement.getSubElement(By.xpath(String.format(LOCATOR_FONT_ANNOTATION, RETRIED_NAME)));
        annotationElement.setName("annotationElementFor" + RETRIED_NAME);
        annotationElement.asserts().assertIsDisplayed();
    }*/
}
