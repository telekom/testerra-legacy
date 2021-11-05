/*
 * Testerra
 *
 * (C) 2020, Eric Kubenka, T-Systems Multimedia Solutions GmbH, Deutsche Telekom AG
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
package eu.tsystems.mms.tic.testframework.report.general;

import eu.tsystems.mms.tic.testframework.report.AbstractReportTest;
import eu.tsystems.mms.tic.testframework.report.model.ResultTableFailureType;
import eu.tsystems.mms.tic.testframework.report.model.TestReportTwoNumbers;
import eu.tsystems.mms.tic.testframework.report.model.TestResultHelper;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractFailurePointsPage;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractResultTableFailureEntry;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class AbstractReportFailuresTest extends AbstractReportTest {

    protected ResultTableFailureType failurePointType;
    protected String reportFilter;
    protected List<? extends AbstractResultTableFailureEntry> failurePointEntryTestObjects;

    @BeforeMethod(alwaysRun = true)
    public abstract void initTestObjects();

    /**
     * This test checks whether the ranking of the Failure Entries is correct.
     * It asserts that the first entry includes the highest amount of test.
     * The second test second highest etc.
     *
     * Furthermore it checks the possibility that the last entry could have a higher amount
     * of tests than the others, if it contains all tests with an unspecified default error.
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #838
    public void testT01_checkFailureRanking() {
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertFailurePointRanking(failurePointEntryTestObjects);
    }

    /**
     * This test checks whether the total number of ALL failure point entries is equal to expected one
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #839
    public void testT02_checkTotalNumberOfFailures() {
        final int expectedNumberOfFailurePoints = getNumberOfExpectedFailurePointsForReport();
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertTotalNumberOfFailurePoints(expectedNumberOfFailurePoints);
    }
    /**
     * This test checks whether the total number of FAILED failure point entries is equal to expected one
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #840
    public void testT03_checkNumberOfFailedTests() {
        TestReportTwoNumbers testReportTwoNumbers = new TestReportTwoNumbers();
        int expectedNumberOfFailedTests = testReportTwoNumbers.getAllBroken() + testReportTwoNumbers.getFailedExpected();
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertNumberOfTestsForAllFailurePoints(expectedNumberOfFailedTests);
    }
    /**
     * This test checks the HEADER INFORMATION for each single failure point entry in the listed order
     * @deprecated Checking header information elements is done implicitely
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2, enabled = false)
    // Test case #841
    public void testT04_checkHeaderForEachSingleFailurePoint() {
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertHeaderInformation(failurePointEntryTestObjects);
    }

    /**
     * This test checks the DESCRIPTION for each single failure point entry in the listed order
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #842
    public void testT05_DescriptionForSingleFailure() {
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertDescriptionsForFailurePointsIsCorrect(failurePointEntryTestObjects);
    }

    /**
     * This test checks the listed METHOD for a single failure point entry in the list
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #843
    public void testT06_ListedTestsForSingleFailure() {
        final int failurePointPositionToCheck = 2;
        AbstractResultTableFailureEntry expectedEntry = failurePointEntryTestObjects.get(failurePointPositionToCheck - 1);
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertTestMethodInformation(expectedEntry);
    }

    /**
     * This test checks whether test with an @Fails annotation are displayed correctly
     * It considers the cases 'intoReport = false' and 'intoReport = true'
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #844
    public void testT07_checkMarkExpectedFailedTests() {
        checkExpectedFailedMarkWorkflow(true);
        checkExpectedFailedMarkWorkflow(false);
    }

    /**
     * This test checks whether the navigation to the respective MethodDetailPage is working correctly
     */
    @Test(groups = SystemTestsGroup.SYSTEMTESTSFILTER2)
    // Test case #845
    public void testT08_checkForwardingToMethodDetailsPage() {
        final int failurePointPositionToCheck = 2;
        final int methodPositionToCheck = 1;
        AbstractResultTableFailureEntry entryWithMethods = getExpectedFailurePointEntries().get(failurePointPositionToCheck - 1);
        AbstractFailurePointsPage failurePointsPage = openFailuresPointsPage(ReportDirectory.REPORT_DIRECTORY_2);
        failurePointsPage.assertDetailsLinkNavigation(entryWithMethods, methodPositionToCheck);
    }

    protected abstract int getNumberOfExpectedFailurePointsForReport();

    protected abstract int getNumberOfExpectedFailurePointsForTestResult(TestResultHelper.TestResultFailurePointEntryType entryType);

    protected abstract List<? extends AbstractResultTableFailureEntry> getExpectedFailurePointEntries();

    protected abstract void checkExpectedFailedMarkWorkflow(boolean intoReport);

    protected abstract AbstractFailurePointsPage openFailuresPointsPage(ReportDirectory reportDirectory);

}
