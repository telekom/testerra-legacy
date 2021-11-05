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

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class ...
 *
 * @author fakr
 */
public class ReportTestUnderTestDependsOn extends AbstractTest {

    @Test
    public void test_PassedAlways() {
        Assert.assertTrue(true);
    }

    // A
    @Test
    public void test_FailedAlways() {
        Assert.assertTrue(false);
    }

    @Test(dependsOnMethods = {"test_PassedAlways"})
    public void test_FailedDependsOnPassedMethod() {
        
        Assert.assertTrue(false);
    }

    @Test(dependsOnMethods = {"test_FailedDependsOnPassedMethod"})
    public void test_SkippedDependsOnFailedMethod() {
        
        Assert.assertTrue(true, "Skipped - Not reachable. Depends on failed method");
    }

    @Test(dependsOnMethods = {"test_FailedDependsOnPassedMethod"}, alwaysRun = true)
    public void test_PassedDependsOnFailedMethodButAlwaysRun() {
        
        Assert.assertTrue(true, "Reachable. Depends on failed method but alwaysRun flag is set");
    }

    // B dependsOn A
    @Test(dependsOnMethods = {"test_FailedAlways"}, dataProvider = "dependsOnDP")
    public void test_dependsOnFailedMethodWithDP(String actualRun) {
        final String expectedRun = "Run1";
        
        Assert.assertEquals(actualRun, expectedRun, "This test Run is not the expected Run");
    }

    // C dependsOn B
    @Test(dependsOnMethods = {"test_dependsOnFailedMethodWithDP"}, dataProvider = "dependsOnDP")
    public void test_dependsOnFailedMethodWithDP2(String actualRun) {
        final String expectedRun = "Run1";
        
        Assert.assertEquals(actualRun, expectedRun, "This test Run is not the expected Run");
    }

    @Test(dependsOnMethods = {"test_FailedDependsOnPassedMethod"}, dataProvider = "dependsOnDP", alwaysRun = true)
    public void test_dependsOnFailedMethodButAlwaysRunWithDP(String actualRun) {
        final String expectedRun = "Run1";
        
        Assert.assertEquals(actualRun, expectedRun, "This test Run is not the expected Run");
    }

    @AfterMethod(alwaysRun = true)
    public void test_AfterMethodWithDP() {
        Assert.assertTrue(true);
    }

    @DataProvider(name = "dependsOnDP")
    public Object[][] createDependsOnDP() {
        Object[][] objects = new Object[3][1];
        objects[0][0] = "Run1";
        objects[1][0] = "Run2";
        objects[2][0] = "Run3";
        return objects;
    }

}
