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

import eu.tsystems.mms.tic.testframework.pageobjects.GuiElement;
import eu.tsystems.mms.tic.testframework.report.model.ResultTableFailureType;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractFailurePointsPage;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractResultTableFailureEntry;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class FailureAspectsPage extends AbstractFailurePointsPage {

    private final String LOCATOR_FAILURE_ASPECT_ENTRY_ID = "aspect-%d"; // counting starts with '1'

    public FailureAspectsPage(WebDriver driver) {
        super(driver);
        this.failurePointType = ResultTableFailureType.FAILURE_ASPECT;
        this.failurePointID = LOCATOR_FAILURE_ASPECT_ENTRY_ID;
    }

    @Override
    public void assertFailurePointRanking(List<? extends AbstractResultTableFailureEntry> expectedEntries) {
        int numberOfTestsThreshold = Integer.MAX_VALUE;
        for (AbstractResultTableFailureEntry failureEntry : expectedEntries) {
            assertHeaderInformation(failureEntry);
            int currentNumberOfTests = failureEntry.getNumberOfTests();
            if (currentNumberOfTests <= numberOfTestsThreshold) {
                numberOfTestsThreshold = currentNumberOfTests;
            } else {
                Assert.fail("Failure Point ranking is NOT correct. " + currentNumberOfTests + " must be less than or equal to " + numberOfTestsThreshold);
            }
        }
    }

    @Override
    public void assertExpectedFailsReportMark(AbstractResultTableFailureEntry failedEntry, boolean intoReport) {
        GuiElement headerTableRow = getHeaderInformationElementForFailurePoint(failedEntry).getSubElement(By.xpath("./../../.."));
        headerTableRow.setName("headerTableRow");
        Assert.assertEquals(headerTableRow.getAttribute("class"), failedEntry.getFailurePointEntryType().getClassAttribute(), "The Expected Failed points are NOT correct marked");
    }

}
