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
package eu.tsystems.mms.tic.testframework.report.model;

import java.util.Arrays;
import java.util.List;

public class TestReportTwoFailureAspects implements IFailurePointEntryHelper {

    public static final FailureAspectEntry FA1 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILED,
            1,
            22,
            "Exception"
    );

    public static final FailureAspectEntry FA2 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILED,
            2,
            6,
            "Assert: expected [true] but found [false]",
            Arrays.asList("ReportTestUnderTestExecutionFilter - test_FailedInheritedFilter",
                          "ReportTestUnderTestExecutionFilter - test_FilterFailedMinor",
                          "ReportTestUnderTestExecutionFilter - test_FilterFailedNoMinor",
                          "ReportTestUnderTestCorridorLow - test_testLowCorridorFailed2 (in Report- TestsUnderTest - Low-Corridor Creator)",
                          "ReportTestUnderTestCorridorMid - test_testMidCorridorFailed2",
                          "ReportTestUnderTestCorridorMid - test_testMidCorridorFailed3"),
            Arrays.asList("Assert: expected [true] but found [false]",
                          "Assert: expected [true] but found [false]",
                          "Assert: expected [true] but found [false]",
                          "Assert: expected [true] but found [false]",
                          "Assert: expected [true] but found [false]",
                          "Assert: expected [true] but found [false]")
    );

    public static final FailureAspectEntry FA3 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILED,
            3,
            6,
            "Failing of test expected. Description: This is a known bug.."
    );

    public static final FailureAspectEntry FA4 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILED,
            4,
            3,
            "ArithmeticException: / by zero"
    );

    public static final FailureAspectEntry FA5 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILEDEXPECTED_NOT_INTOREPORT,
            5,
            2,
            "Exception: RetryUnderTest"
    );

    public static final FailureAspectEntry FA6 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILED,
            6,
            1,
            "Exception: matchting unique failure aspect:",
            Arrays.asList("ReportTestUnderTestExpectedtoFail - test_UnexpectedFailedWithRelatedExpectedFailed"),
            Arrays.asList("Failure aspect matches known issue:  Description: Known issue with same aspect as unmarked failed test")
    );

    public static final FailureAspectEntry FA7 = new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILEDEXPECTED_INTOREPORT,
            7,
            1,
            "Failing of test expected. Description: Known issue with same aspect as unmarked failed test"
    );

    public static final FailureAspectEntry FA8= new FailureAspectEntry(
            TestResultHelper.TestResultFailurePointEntryType.FAILEDEXPECTED_INTOREPORT,
            8,
            1,
            "Failing of test expected. Description: This is an unknown bug.."
    );

    public static List<FailureAspectEntry> getAllFailureAspectEntryTestObjects() {
        return Arrays.asList(FA1, FA2, FA3, FA4, FA5, FA6, FA7, FA8);
    }

}
