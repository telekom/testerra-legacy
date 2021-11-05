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
package eu.tsystems.mms.tic.testframework.report.pageobjects.dashboard;

import eu.tsystems.mms.tic.testframework.pageobjects.GuiElement;
import eu.tsystems.mms.tic.testframework.report.pageobjects.abstracts.AbstractFramePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardModuleInformationCorridor extends AbstractFramePage {


    public final GuiElement repairedFailsIndicationButton = new GuiElement(this.getWebDriver(), By.xpath("//div[@class='dashboardInfo']"), mainFrame);

    public DashboardModuleInformationCorridor(WebDriver driver) {
        super(driver);
    }
}
