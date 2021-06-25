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
package eu.tsystems.mms.tic.testframework.bmp.playground;

import eu.tsystems.mms.tic.testframework.bmp.AbstractTest;
import eu.tsystems.mms.tic.testframework.bmp.BMProxyManager;
import eu.tsystems.mms.tic.testframework.bmp.NoFreePortException;
import eu.tsystems.mms.tic.testframework.bmp.ProxyServer;
import eu.tsystems.mms.tic.testframework.utils.TimerUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BMProxyManagerPlaygroundTest extends AbstractTest {

    static {
        List<Integer> ports = new ArrayList<Integer>(4);
        ports.add(9991);
        ports.add(9992);
        ports.add(9993);
        ports.add(9994);
        BMProxyManager.setPortPool(ports);
    }

    @Test
    public void testProxyServer1() throws NoFreePortException {
        final ProxyServer proxyServer = BMProxyManager.getProxyServer();

        TimerUtils.sleep(3000);

        BMProxyManager.shutDownProxyServer();
    }

    @Test
    public void testProxyServer2() throws NoFreePortException {
        final ProxyServer proxyServer = BMProxyManager.getProxyServer();

        TimerUtils.sleep(3000);

        BMProxyManager.shutDownProxyServer();
    }

    @Test
    public void testProxyServer3() throws NoFreePortException {
        final ProxyServer proxyServer = BMProxyManager.getProxyServer();

        TimerUtils.sleep(3000);

        BMProxyManager.shutDownProxyServer();
    }

    @Test
    public void testProxyServer4() throws NoFreePortException {
        final ProxyServer proxyServer = BMProxyManager.getProxyServer();

        TimerUtils.sleep(3000);

        BMProxyManager.shutDownProxyServer();
    }

    @Test
    public void testProxyServer5() throws NoFreePortException {
        final ProxyServer proxyServer = BMProxyManager.getProxyServer();

        TimerUtils.sleep(3000);

        BMProxyManager.shutDownProxyServer();
    }

}
