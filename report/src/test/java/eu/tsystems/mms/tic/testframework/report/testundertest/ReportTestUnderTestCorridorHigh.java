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
package eu.tsystems.mms.tic.testframework.report.testundertest;

import eu.tsystems.mms.tic.testframework.report.FailureCorridor;
import org.testng.annotations.Test;

public class ReportTestUnderTestCorridorHigh extends AbstractTest {


    @FailureCorridor.High
    @Test
    public void test_testHighCorridorFailed1() throws Exception {
        throw new Exception();
    }
    @FailureCorridor.High
    @Test
    public void test_testHighCorridorFailed2() throws Exception {
        throw new Exception();
    }


}
