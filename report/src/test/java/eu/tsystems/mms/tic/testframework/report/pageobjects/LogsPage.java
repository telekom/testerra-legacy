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
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractReportPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogsPage extends AbstractReportPage {

    @Check
    public GuiElement table = new GuiElement (this.getWebDriver(), By.id("datatable"), mainFrame);

    private GuiElement excludeFilter = new GuiElement (this.getWebDriver(), By.id("dataTable_Filter"), mainFrame);
    private GuiElement includeFilter = new GuiElement (this.getWebDriver(), By.id("datatable_filter"), mainFrame);

    public LogsPage(WebDriver driver) {
        super(driver);
    }

    public void assertPageIsDisplayedCorrectly(){
        GuiElement levelColumnHead = table.getSubElement(By.xpath(".//*[contains(@aria-label,'Level')]"));
        GuiElement timeColumnHead = table.getSubElement(By.xpath(".//*[contains(@aria-label,'Time')]"));
        GuiElement threadColumnHead = table.getSubElement(By.xpath(".//*[contains(@aria-label,'Thread')]"));
        GuiElement loggerColumnHead = table.getSubElement(By.xpath(".//*[contains(@aria-label,'Logger')]"));
        GuiElement messageColumnHead = table.getSubElement(By.xpath(".//*[contains(@aria-label,'Message')]"));

        GuiElement selectAllButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='Select all']"), mainFrame);
        GuiElement deselectAllButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='Deselect all']"), mainFrame);
        GuiElement copyButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='Copy']"), mainFrame);
        GuiElement excelButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='Excel']"), mainFrame);
        GuiElement csvButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='CSV']"), mainFrame);
        GuiElement pdfButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='PDF']"), mainFrame);
        GuiElement printButton = new GuiElement (this.getWebDriver(), By.xpath("//span[text()='Print']"), mainFrame);

        levelColumnHead.asserts().assertIsDisplayed();
        timeColumnHead.asserts().assertIsDisplayed();
        threadColumnHead.asserts().assertIsDisplayed();
        loggerColumnHead.asserts().assertIsDisplayed();
        messageColumnHead.asserts().assertIsDisplayed();

        selectAllButton.asserts().assertIsDisplayed();
        deselectAllButton.asserts().assertIsDisplayed();
        copyButton.asserts().assertIsDisplayed();
        excelButton.asserts().assertIsDisplayed();
        csvButton.asserts().assertIsDisplayed();
        pdfButton.asserts().assertIsDisplayed();
        printButton.asserts().assertIsDisplayed();

        excludeFilter.asserts().assertIsDisplayed();
        includeFilter.asserts().assertIsDisplayed();
    }

    /**
     * This method check the display of the logsMessage by looking that no errorMessage will be displayed.
     */
    public void assertLogMessageIsDisplayed(){
        GuiElement errorMessage = new GuiElement (this.getWebDriver(), By.xpath("//td[text()='No matching records found']"), mainFrame);
        errorMessage.asserts("A error message should not be displayed for a valid search term").assertIsNotDisplayed();
    }

    public void insertSearchTermInInSearchBar(String level, String message, String logger, String thread){
        GuiElement includeFilterInputField = includeFilter.getSubElement(By.xpath(".//input[@type='search']"));
        includeFilterInputField.clear();
        includeFilterInputField.type(thread + ' ' + logger + ' ' + message + ' ' + level);
    }
}
