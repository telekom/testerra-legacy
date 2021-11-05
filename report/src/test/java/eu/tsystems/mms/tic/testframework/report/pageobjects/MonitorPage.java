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

public class MonitorPage extends AbstractReportPage {

    @Check
    private GuiElement headLine = new GuiElement(this.getWebDriver(), By.xpath("//h5[text()='JVM Monitor']"), mainFrame);

    public MonitorPage(WebDriver driver) {
        super(driver);
    }

    public void assertPageIsDisplayedCorrectly(){
        GuiElement memoryUsageChart = new GuiElement(this.getWebDriver(), By.id("ConsumptionMeasurementsView1"), mainFrame);
        GuiElement memoryReservedChart = new GuiElement(this.getWebDriver(), By.id("ConsumptionMeasurementsView2"), mainFrame);
        GuiElement processorUsageChart = new GuiElement(this.getWebDriver(), By.id("ConsumptionMeasurementsView3"), mainFrame);

        memoryUsageChart.asserts().assertIsDisplayed();
        memoryReservedChart.asserts().assertIsDisplayed();
        processorUsageChart.asserts().assertIsDisplayed();
    }
}
