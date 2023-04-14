package com.chegg.web.core;

import com.chegg.web.core.utill.Prop;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class PlaywrightFactory {
    Prop conf = ConfigFactory.create(Prop.class);
    private final String videoPath = "src/test/resources/video/";
    private final String networkPath = "src/test/resources/network/";
    private final String screenPath = "src/test/resources/screenshot/";
    private final String tracePath = "src/test/resources/trace/";
    private final double randomChar = Math.random();

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Playwright getTlPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getTlBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getTlContext() {
        return tlContext.get();
    }

    public static Page getTlPage() {
        return tlPage.get();
    }

    public Page initBrowser() {
        tlPlaywright.set(Playwright.create());

        tlBrowser.set(getTlPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome")
                .setArgs(Collections.singletonList("--remote-debugging-port=9222"))
                .setSlowMo(conf.sloMotion())
                .setHeadless(conf.mode())));

        tlContext.set(getTlBrowser().newContext(new Browser.NewContextOptions()
                .setLocale(conf.local())
                .setRecordHarPath(Paths.get(networkPath + randomChar + ".har"))
                .setRecordVideoDir(Paths.get(videoPath))
                .setViewportSize(conf.viewportWidth(), conf.viewportHeight())
                .setTimezoneId(conf.timeZone())
                .setGeolocation(conf.latitude(), conf.longitude())));

        getTlContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        tlPage.set(getTlContext().newPage());
        return getTlPage();
    }

    public void stop(Method testInfo, ITestResult result) throws IOException {

        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(conf.dateTimePattern()));
        String videoName = getTlPage().video().path().getFileName().toString();

        String logName = String.format("%s_%s", formattedDateTime, testInfo.getName());
        String screenPathStr = screenPath + logName + ".zip";
        String tracePathStr = tracePath + logName + ".zip";

        Path screenFilePath = getPath(screenPathStr);
        Path zipFilePath = getPath(tracePathStr);
        Path networkFilePath = getPath(networkPath + randomChar + ".har");
        Path videoFilePath = getPath(videoPath + videoName);

        getTlContext().tracing().stop(new Tracing.StopOptions()
                .setPath(zipFilePath));

        byte[] screenshot = getTlPage().screenshot(new Page.ScreenshotOptions()
                .setPath(screenFilePath).setFullPage(true));

        getTlContext().close();
        getTlBrowser().close();

        byte[] videoContents = Files.readAllBytes(videoFilePath);
        byte[] zipContents = Files.readAllBytes(zipFilePath);
        byte[] networkContents = Files.readAllBytes(networkFilePath);

        if (result.getStatus() == 2) {
            Allure.addAttachment("SCREENSHOT_" + logName,
                    new ByteArrayInputStream(screenshot));

            Allure.addAttachment("TRACE_" + logName,
                    new ByteArrayInputStream(zipContents));

            Allure.addAttachment("NETWORK_" + logName,
                    new ByteArrayInputStream(networkContents));

            Allure.addAttachment("VIDEO_" + logName,
                    new ByteArrayInputStream(videoContents));
        }
    }

    private Path getPath(String customPath) {
        return Paths.get(customPath);
    }


}
