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

package eu.tsystems.mms.tic.testframework.utils;

import eu.tsystems.mms.tic.testframework.report.TestStatusController;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class FilterUtils {

    private FilterUtils() {
    }

    public static final FilterUtils INSTANCE = new FilterUtils();

    public static FilterUtils getInstance() {
        return INSTANCE;
    }

    /**
     * Used in methods.vm
     */
    public List<MethodContext> filterMethodContexts(
            Collection<MethodContext> methodContexts,
            boolean configMethods,
            boolean testMethods,
            TestStatusController.Status status
    ) {
        return methodContexts.stream()
                .filter(methodContext -> (methodContext.isConfigMethod() && configMethods) || (methodContext.isTestMethod() && testMethods))
                .filter(methodContext -> methodContext.getStatus() == status)
                .collect(Collectors.toList());
    }
}
