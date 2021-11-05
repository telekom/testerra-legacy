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

import eu.tsystems.mms.tic.testframework.exceptions.SystemException;
import eu.tsystems.mms.tic.testframework.info.ReportInfo;
import eu.tsystems.mms.tic.testframework.internal.ConsumptionMeasurementsCollector;
import eu.tsystems.mms.tic.testframework.internal.TimingInfo;
import eu.tsystems.mms.tic.testframework.internal.Timings;
import eu.tsystems.mms.tic.testframework.internal.utils.TimingInfosCollector;
import eu.tsystems.mms.tic.testframework.monitor.JVMMonitor;
import eu.tsystems.mms.tic.testframework.report.Report;
import eu.tsystems.mms.tic.testframework.report.ReportingData;
import eu.tsystems.mms.tic.testframework.report.TestStatusController;
import eu.tsystems.mms.tic.testframework.report.model.context.LogMessage;
import eu.tsystems.mms.tic.testframework.report.TesterraListener;
import eu.tsystems.mms.tic.testframework.report.model.context.ClassContext;
import eu.tsystems.mms.tic.testframework.report.model.context.MethodContext;
import eu.tsystems.mms.tic.testframework.report.perf.PerfTestReportUtils;
import eu.tsystems.mms.tic.testframework.report.perf.TimingsGraphGenerator;
import eu.tsystems.mms.tic.testframework.report.threadvisualizer.ThreadVisualizer;
import eu.tsystems.mms.tic.testframework.report.utils.ExecutionContextController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility functions for Reporting.
 *
 * @author sepr
 */
public final class ReportUtils {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtils.class);
    private static Report report = TesterraListener.getReport();
    private static final String FRAMES_FOLDER_NAME = "frames";
    private static final String METHODS_FOLDER_NAME = "methods";
    private static File FRAMES_DIRECTORY = report.getReportDirectory(FRAMES_FOLDER_NAME);
    private static File METHODS_DIRECTORY = new File(FRAMES_DIRECTORY, METHODS_FOLDER_NAME);

    static {
        FRAMES_DIRECTORY.mkdirs();
        METHODS_DIRECTORY.mkdirs();
    }

    /**
     * Hide constructor
     */
    private ReportUtils() {
    }

    /**
     * Copy the reosurce files needed for report in the target directory. Files included are style.css, report.js,
     * sorttable.js and icons.
     */
    public static void copyReportResources() {

        final File targetDir = FRAMES_DIRECTORY;

        String[] resources = new String[] {
                "js/kis/main.js",
                "js/kis/modernizr.js",
                "js/kis/jquery-migrate-1.2.1.min.js",

                //                "js/jsapi.js",
                "js/easy.js",
                "js/main.js",
                "js/report.js",
                "js/sorttable.js",

                "js/chart.2.7.2.bundle.min.js",
                "js/moment2.22.2.js",

                "js/datatables.min.js",

                // jira
                "js/jira.js",

                // metrics gauges
                "js/gaugeSVG.js",

                "js/pace.min.js",

                "js/cytoscape.min.js",
                "js/dagre.js",
                "js/cytoscape-drage.js",

                // piechart
                "js/piechart.js",

                // code format
                "js/highlight.pack.js",
                "js/highlight-styles/zenburn.css",

                // dependencies tree diagram
                "js/dependencies-tree-view.js",

                // CSS Resources
                "style/easy.css",
                "style/easyprint.css",

                "style/kis/default.css",
                "style/kis/fonts.css",
                "style/kis/layout.css",

                // fonts
                "style/kis/fonts/opensans/OpenSans-Bold-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-Bold-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-Bold-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-Bold-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-BoldItalic-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-BoldItalic-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-BoldItalic-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-BoldItalic-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-ExtraBold-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-ExtraBold-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-ExtraBold-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-ExtraBold-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-ExtraBoldItalic-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-ExtraBoldItalic-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-ExtraBoldItalic-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-ExtraBoldItalic-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-Italic-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-Italic-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-Italic-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-Italic-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-Light-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-Light-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-Light-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-Light-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-LightItalic-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-LightItalic-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-LightItalic-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-LightItalic-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-Regular-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-Regular-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-Regular-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-Regular-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-Semibold-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-Semibold-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-Semibold-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-Semibold-webfont.woff",
                "style/kis/fonts/opensans/OpenSans-SemiboldItalic-webfont.eot",
                "style/kis/fonts/opensans/OpenSans-SemiboldItalic-webfont.svg",
                "style/kis/fonts/opensans/OpenSans-SemiboldItalic-webfont.ttf",
                "style/kis/fonts/opensans/OpenSans-SemiboldItalic-webfont.woff",

                "style/style.css",
                "style/dataTables.tableTools.css",
                "style/jquery-ui.css",


                "style/tlred.png",
                "style/tlyellow.png",
                "style/tlgreen.png",

                // PNG resources
                "style/gears.png",
                "style/notes.png",
                "style/progress.png",
                "style/plus.png",
                "style/minus.png",
                "style/page.png",
                "style/sort_asc_disabled.png",
                "style/sort_desc_disabled.png",
                "style/sort_asc.png",
                "style/sort_desc.png",
                "style/sort_both.png",
                "style/warnung.svg",
                "style/retry.png",
                "style/barcode.png",
                "style/directions.png",

                "style/cloud_download.png",
                "style/sun.png",
                "style/delete_page.png",
                "style/add_notes.png",

                "style/ui-bg_glass_55_fbf9ee_1x400.png",
                "style/ui-bg_glass_65_ffffff_1x400.png",
                "style/ui-bg_glass_75_dadada_1x400.png",
                "style/ui-bg_glass_75_e6e6e6_1x400.png",
                "style/ui-bg_glass_95_fef1ec_1x400.png",
                "style/ui-bg_highlight-soft_75_cccccc_1x100.png",
                "style/ui-icons_222222_256x240.png",
                "style/ui-icons_2e83ff_256x240.png",
                "style/ui-icons_454545_256x240.png",
                "style/ui-icons_888888_256x240.png",
                "style/ui-icons_cd0a0a_256x240.png",

                // Other
                "swf/copy_csv_xls.swf",

                // fotorama
                "js/fotorama.js",
                "style/fotorama.css",
                "style/fotorama.png",
                "style/fotorama@2x.png",

                // new png
                "style/paper-clip-attach-interface-symbol.png",
                "style/cancel.png",
                "style/business-circular-pie-chart.png",
                "style/line-chart-for-business.png",
                "style/feather-black-shape.png",
                "style/note-of-rectangular-rounded-shape-with-text-lines.png",
                "style/thumb_full-screen.png",

                // font-awesome
                "style/font-awesome/css/font-awesome.min.css",
                "style/font-awesome/fonts/FontAwesome.otf",
                "style/font-awesome/fonts/fontawesome-webfont.eot",
                "style/font-awesome/fonts/fontawesome-webfont.svg",
                "style/font-awesome/fonts/fontawesome-webfont.ttf",
                "style/font-awesome/fonts/fontawesome-webfont.woff",
                "style/font-awesome/fonts/fontawesome-webfont.woff2",

                // font
                "style/font/DancingScript.ttf",
                "style/font/PWFreeArrows.ttf",

                //logo
                "Testerra_Logo_0512px.png",
                "Testerra_favicon.png"
        };

        for (String resource : resources) {
            copyFile(resource, targetDir);
        }
    }

    /**
     * Copies a File to destination destination.
     *
     * @param relativeFile File path.
     * @param targetDir    .
     */
    public static void copyFile(final String relativeFile, final File targetDir) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativeFile);
        if (is == null) {
            throw new SystemException("Could not find " + relativeFile);
        }
        try {
            File destFile = new File(targetDir, relativeFile);

            File dirs = destFile.getParentFile();
            if (!dirs.exists()) {
                dirs.mkdirs();
            }

            FileUtils.copyInputStreamToFile(is, destFile);
        } catch (Exception e) {
            throw new SystemException("Could not copy resource " + relativeFile, e);
        }
    }

    // HELPERS

    /**
     * Create html output
     */
    public static void createReport(ReportingData reportingData) {

        /*
        Copy resources
         */
        copyReportResources();

        /*
        Create guielement timing graphs
         */
        if (Timings.TIMING_GUIELEMENT_FIND.size() > 0) {
            TimingsGraphGenerator.createGuiElementFindTimingsGraph();
        }
        if (Timings.TIMING_GUIELEMENT_FIND_WITH_PARENT.size() > 0) {
            TimingsGraphGenerator.createGuiElementFindWithParentTimingsGraph();
        }

        /*
         * add additional report tabs
         */
        if (ThreadVisualizer.hasData()) {
            ThreadVisualizer.generateReport();
        }

        if (reportingData.methodsWithAcknowledgements != null && reportingData.methodsWithAcknowledgements.size() > 0) {
            createAcknowledgements(reportingData.methodsWithAcknowledgements);
        }

        // jvm monitor tab
        addExtraTopLevelConsumptionMeasurementsTab(
                "JVM Monitor",
                "jvmmonitor.html",
                true,
                JVMMonitor.getConsumptionMeasurementsCollector());

        /*
        define base top level tabs (added to existing ones that are extra tabs)
         */
        int index = 0;
        TOP_LEVEL_TABS.add(index++, new TabInfo("Dashboard", "Dashboard", "dashboard.html", false));
        TOP_LEVEL_TABS.add(index++, new TabInfo("Classes", "Classes", "classesStatistics.html", false));
        TOP_LEVEL_TABS.add(index++, new TabInfo("ExitPoints", "Exit Points", "classes/exitPoints.html", true));
        TOP_LEVEL_TABS.add(index++, new TabInfo("FailureAspects", "Failure Aspects", "classes/failureAspects.html", false));
        TOP_LEVEL_TABS.add(index++, new TabInfo("Logs", "Logs", "logs.html", true));
        TOP_LEVEL_TABS.add(index++, new TabInfo("Timings", "Timings", "measurements.html", true));

        /*
        execute threads
         */
        ExecutorService executorService = Executors.newWorkStealingPool(20);

        /*
         * main report index.html
         */
        final File reportFileIndex = report.getReportDirectory("index.html");
        ReportFormatter.createTestClassesView(reportFileIndex, reportingData, "index.vm", null, null);

        /*
        create all tabs that are not base tabs
         */
        for (TabInfo tabInfo : TOP_LEVEL_TABS) {
            TabCreationInfo tabCreationInfo = tabInfo.getTabCreationInfo();
            if (tabCreationInfo != null) {
                createExtraTopLevelTab(
                        tabCreationInfo.vmTemplateFileInResources,
                        tabInfo.getRelativeHtmlFilePath(),
                        tabInfo.getTabName(),
                        tabCreationInfo.velocityContext);
            }
        }


        /*
        Dashboard
         */
        final File reportFileDashboard = new File(FRAMES_DIRECTORY, "dashboard.html");
        ReportFormatter.createDashboardHtml(reportingData, reportFileDashboard, "dashboard.vm");

        final File reportFileEmailable = report.getReportDirectory("emailable-report.html");
        ReportFormatter.createEmailableReportHtml(reportingData, reportFileEmailable, "emailable-report.vm");

        /*
        measurements output
         */
        final File reportFileMeasurements = new File(FRAMES_DIRECTORY, "measurements.html");
        Map<String, Long> avgMeasurementPerActions = PerfTestReportUtils.getAverageResponseTimePerTestStepAction();
        Map<String, Long> minMeasurementPerActions = PerfTestReportUtils.getMinResponseTimePerTestStepAction();
        Map<String, Long> maxMeasurementPerActions = PerfTestReportUtils.getMaxResponseTimePerTestStepAction();
        Map<String, List<TimingInfo>> pageloadInfosPerAction = PerfTestReportUtils.getPageLoadInfosPerTestStepAction();
        ReportFormatter.createTimingsHtml(reportFileMeasurements, minMeasurementPerActions, avgMeasurementPerActions, maxMeasurementPerActions,
                pageloadInfosPerAction, "measurements.vm");

        /*
         * Classes
         */
        final File reportFileClassesStats = new File(FRAMES_DIRECTORY, "classesStatistics.html");
        final ReportInfo.RunInfo runInfo = ReportInfo.getRunInfo();
        ReportFormatter.createTestClassesView(reportFileClassesStats, reportingData, "classesStatistics.vm", null, runInfo);

        /*
        create classes dir
         */
        final File classesLogDir = new File(FRAMES_DIRECTORY, "classes/");
        classesLogDir.mkdir();

        /*
         * Write test class files.
         */
        Runnable createMethodsRunnable = () -> {
            for (ClassContext classContext : reportingData.classContexts) {
                File reportFile0 = new File(classesLogDir, classContext.getId() + ".html");
                ReportFormatter.createMethodsView(reportFile0, classContext, "methods.vm");
                // short view for dashboard
                /*reportFile0 = new File(classesLogDir, classContext.name + "_dashboard.html");
                ReportFormatter.createMethodsView(reportFile0, classContext, "methodsDashboard.vm");*/
            }
        };
        executorService.submit(createMethodsRunnable);

        /*
        create method details
         */
        for (ClassContext classContext : reportingData.classContexts) {
            /*
            multi threaded details generation, 1 thread per class
             */
            Runnable createMethodDetailsRunnable = () -> {
                for (MethodContext testMethodContainer : classContext.methodContexts) {
                    createMethodDetailsView(testMethodContainer);
                }
            };
            executorService.submit(createMethodDetailsRunnable);
        }

        /*
        Exit points
         */
        final File reportFileExitPoints = new File(classesLogDir, "exitPoints.html");
        ReportFormatter.createMultiMethodsHtml(reportingData, reportFileExitPoints, "exitPoints.vm");

        /*
        Failure aspects
         */
        final File reportFileFailureAspects = new File(classesLogDir, "failureAspects.html");
        ReportFormatter.createMultiMethodsHtml(reportingData, reportFileFailureAspects, "failureAspects.vm");

        /*
        Logs
         */
        final File reportFileGlobalLogs = new File(FRAMES_DIRECTORY, "logs.html");
        ReportFormatter.createTestClassesView(reportFileGlobalLogs, reportingData, "log.vm", ExecutionContextController.getCurrentExecutionContext().readMethodContextLessLogs().collect(Collectors.toList()), null);

        /*
        Memory consumption
         */
        JVMMonitor.label("End");
        final File reportFileMemory = new File(FRAMES_DIRECTORY, "memory.html");
        ReportFormatter.createMemoryHtml(reportFileMemory, "memory.vm");

        /*
        finish all threads
         */
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            throw new SystemException("Report generation took too long", e);
        }
    }

    private static void createAcknowledgements(List<MethodContext> methodsWithAcknowledgements) {
        VelocityContext context = new VelocityContext();
        context.put("methods", methodsWithAcknowledgements);
        context.put("pagetitle", "Acknowledgements");
        context.put("status", TestStatusController.Status.values());
        ReportUtils.addExtraTopLevelTab("stateChanges.vm", "classes/acknowledgements.html", "Acknowledgements", "Acknowledgements", context, false);
    }

    public static void createMethodDetailsStepsView(MethodContext methodContext) {
        try {
            File reportFile2 = new File(METHODS_DIRECTORY, "steps" + methodContext.methodRunIndex + ".html");
            ReportFormatter.createMethodsFrame(reportFile2, methodContext, "methodDetailsSteps.vm");
            LOGGER.trace("Created method details steps view for " + methodContext);
        } catch (Exception e) {
            LOGGER.error("Could not create method details steps view for " + methodContext, e);
        }
    }

    public static void createMethodDetailsView(MethodContext methodContext) {
        try {
            File reportFile2 = new File(METHODS_DIRECTORY, methodContext.methodRunIndex + ".html");
            ReportFormatter.createMethodsFrame(reportFile2, methodContext, "methodDetails.vm");
            LOGGER.trace("Created method details view for " + methodContext);
        } catch (Exception e) {
            LOGGER.error("Could not create method details view for " + methodContext + ": " + e);
        }
    }

    public static String getReportName() {
        return ExecutionContextController.getCurrentExecutionContext().runConfig.getReportName();
    }

    public static class TabInfo {

        String tabId;
        String tabName;
        String relativeHtmlFilePath;
        TabCreationInfo tabCreationInfo;
        boolean hamburger;

        public TabInfo(String tabId, String tabName, String relativeHtmlFilePath, boolean hamburger) {
            this.tabId = tabId;
            this.tabName = tabName;
            this.relativeHtmlFilePath = relativeHtmlFilePath;
            this.hamburger = hamburger;
        }

        public void setTabCreationInfo(TabCreationInfo tabCreationInfo) {
            this.tabCreationInfo = tabCreationInfo;
        }

        public String getTabName() {
            return tabName;
        }

        public String getTabId() {
            return tabId;
        }

        public String getRelativeHtmlFilePath() {
            return relativeHtmlFilePath;
        }

        public TabCreationInfo getTabCreationInfo() {
            return tabCreationInfo;
        }

        public boolean isHamburger() {
            return hamburger;
        }
    }

    public static class TabCreationInfo {

        String vmTemplateFileInResources;
        VelocityContext velocityContext;

        public TabCreationInfo(String vmTemplateFileInResources, VelocityContext velocityContext) {
            this.vmTemplateFileInResources = vmTemplateFileInResources;
            this.velocityContext = velocityContext;
        }
    }

    static final List<TabInfo> TOP_LEVEL_TABS = new LinkedList<>();

    private static void createExtraTopLevelTab(String vmTemplateFileInResources, String htmlOutputFileName, String tabName,
                                               VelocityContext velocityContext) {
        File htmlOutputFile = new File(FRAMES_DIRECTORY, htmlOutputFileName);
        try {
            ReportFormatter.createHtml(vmTemplateFileInResources, htmlOutputFile, velocityContext);
        } catch (IOException e) {
            LOGGER.error("Could not create " + tabName + " tab", e);
        }
    }

    public static void addExtraTopLevelTab(String vmTemplateFileInResources, String relativeHtmlFilePath, String tabName, String tabId,
                                           VelocityContext velocityContext, boolean hamburger) {
        TabInfo tabInfo = new TabInfo(tabId, tabName, relativeHtmlFilePath, hamburger);
        tabInfo.setTabCreationInfo(new TabCreationInfo(vmTemplateFileInResources, velocityContext));
        TOP_LEVEL_TABS.add(tabInfo);
    }

    public static void addExtraTopLevelTimingsTab(String tabName, String relativeHtmlFilePath, boolean hamburger,
                                                  TimingInfosCollector timingInfosCollector) {
        timingInfosCollector.terminate();

        TimingInfosCollector.Calculations calculations = timingInfosCollector.getCalculations();

        if (calculations == null || calculations.isEmpty()) {
            calculations = timingInfosCollector.calculate();
        }

        VelocityContext context = new VelocityContext();
        context.put("tabName", tabName);
        context.put("tabId", tabName);
        context.put("allActions", calculations.getTimingInfosPerAction());
        context.put("minPerActions", calculations.getMinPerActions());
        context.put("avgPerActions", calculations.getAvgPerActions());
        context.put("maxPerActions", calculations.getMaxPerActions());

        addExtraTopLevelTab("measurements.vm", relativeHtmlFilePath, tabName, tabName, context, hamburger);
    }

    public static void addExtraTopLevelConsumptionMeasurementsTab(String tabName, String relativeHtmlFilePath, boolean hamburger,
                                                                  ConsumptionMeasurementsCollector consumptionMeasurementsCollector) {
        VelocityContext context = new VelocityContext();
        context.put("tabName", tabName);
        context.put("tabId", tabName);
        context.put("cmc", consumptionMeasurementsCollector);

        addExtraTopLevelTab("consumptions.vm", relativeHtmlFilePath, tabName, tabName, context, hamburger);
    }

}
