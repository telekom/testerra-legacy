/*
 * Testerra
 *
 * (C) 2020, Alex Rockstroh, T-Systems Multimedia Solutions GmbH, Deutsche Telekom AG
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
package eu.tsystems.mms.tic.testframework.report.test.dashboard;

import eu.tsystems.mms.tic.testframework.annotations.TestClassContext;
import eu.tsystems.mms.tic.testframework.execution.testng.AssertCollector;
import eu.tsystems.mms.tic.testframework.pageobjects.GuiElement;
import eu.tsystems.mms.tic.testframework.report.general.AbstractTestDashboard;
import eu.tsystems.mms.tic.testframework.report.general.ReportDirectory;
import eu.tsystems.mms.tic.testframework.report.general.SystemTestsGroup;
import eu.tsystems.mms.tic.testframework.report.pageobjects.DashboardPage;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractTestReportNumbers;
import org.testng.Assert;
import org.testng.annotations.Test;

@TestClassContext(name = "View-Dashboard-FailureCorridor")
public class DashboardModuleFailureCorridorTest extends AbstractTestDashboard {

    /**
     * This test checks the color of the label that indicates whether the failure corridor is matched or not.
     */
    @Test(groups = {SystemTestsGroup.SYSTEMTESTSFILTER1}, dataProvider = "testResultNumbers")
    // Test case #852
    public void testT01_checkFailureCorridorMatchingForColor(ReportDirectory report, AbstractTestReportNumbers numbers) {
        DashboardPage dashboardPage = getDashboardPage(report);
        GuiElement corridorMatch = dashboardPage.dashboardModuleFailureCorridor.failureCorridorDescription;
        String style = numbers.getFailureCorridorMatched();
        Assert.assertTrue(corridorMatch.getAttribute("style").contains(style), "The failure corridor matches. The style in the reportFilter " + report + " is " + style + ".");
    }


    /**
     * This test checks the numbers of the failure corridor.
     */
    @Test(groups = {SystemTestsGroup.SYSTEMTESTSFILTER1}, dataProvider = "testResultNumbers")
    // Test case #424
    public void testT02_checkFailureCorridorNumbers(ReportDirectory report, AbstractTestReportNumbers numbers) {
        DashboardPage dashboardPage = getDashboardPage(report);

        String highActualClass = dashboardPage.dashboardModuleFailureCorridor.highCorridorActualButton.getAttribute("class");
        int highActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.highCorridorActualButton.getText().replaceAll(" ", ""));
        int highLimitActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.highCorridorLimitButton.getText().replaceAll(" ", ""));
        AssertCollector.assertEquals(highActual, numbers.getHighCorridorActual(), "Expected another number for current high corridor.");
        AssertCollector.assertEquals(highLimitActual, numbers.getHighCorridorLimit(), "Expected another number for the high corridor limit.");
        AssertCollector.assertEquals(highActualClass, numbers.getHighMatched(), "Expecteted another style for the high corridor.");

        String midActualClass = dashboardPage.dashboardModuleFailureCorridor.midCorridorActualButton.getAttribute("class");
        int midActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.midCorridorActualButton.getText().replaceAll(" ", ""));
        int midLimitActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.midCorridorLimitButton.getText().replaceAll(" ", ""));
        AssertCollector.assertEquals(midActual, numbers.getMidCorridorActual(), "Expected another number for current mid corridor.");
        AssertCollector.assertEquals(midLimitActual, numbers.getMidCorridorLimit(), "Expected another number for the mid corridor limit.");
        AssertCollector.assertEquals(midActualClass, numbers.getMidMatched(), "Expecteted another style for the mid corridor.");


        String lowActualClass = dashboardPage.dashboardModuleFailureCorridor.lowCorridorActualButton.getAttribute("class");
        int lowActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.lowCorridorActualButton.getText().replaceAll(" ", ""));
        int lowLimitActual = Integer.parseInt(dashboardPage.dashboardModuleFailureCorridor.lowCorridorLimitButton.getText().replaceAll(" ", ""));
        AssertCollector.assertEquals(lowActual, numbers.getLowCorridorActual(), "Expected another number for current low corridor");
        AssertCollector.assertEquals(lowLimitActual, numbers.getLowCorridorLimit(), "Expected another number for the low corridor limit.");
        AssertCollector.assertEquals(lowActualClass, numbers.getLowMatched(), "Expecteted another style for the low corridor.");
    }

}
