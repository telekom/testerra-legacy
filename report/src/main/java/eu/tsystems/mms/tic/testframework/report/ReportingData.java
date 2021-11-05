/*
 * Testerra
 *
 * (C) 2020,  Peter Lehmann, T-Systems Multimedia Solutions GmbH, Deutsche Telekom AG
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
package eu.tsystems.mms.tic.testframework.report;

import eu.tsystems.mms.tic.testframework.report.model.context.ClassContext;
import eu.tsystems.mms.tic.testframework.report.model.context.ExecutionContext;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportingData {

    public ReportingData() {
    }

    public boolean failureCorridorMatched;
    public Collection<ClassContext> classContexts;
    public ExecutionContext executionContext;
    public List<MethodContext> methodsWithAcknowledgements;
    public Map<ClassContext, Map> methodStatsPerClass = new LinkedHashMap<>();
    public Map<ClassContext, Map> configMethodStatsPerClass = new LinkedHashMap<>();
    public Map<String, List<MethodContext>> exitPoints;
    public Map<String, List<MethodContext>> failureAspects;
}
