package com.chegg.web.core;

import com.microsoft.playwright.*;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

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
                .setSlowMo(0)
                .setHeadless(false)));

        tlContext.set(getTlBrowser().newContext(new Browser.NewContextOptions()
//                .setLocale("de-DE")
                .setTimezoneId("America/New_York")
                .setGeolocation(41.890221, 12.492348)));

        getTlContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        tlPage.set(getTlContext().newPage());
        getTlPage().onRequest(request -> System.out.println(">> " + request.method() + " " + request.url()));
        getTlPage().onResponse(response -> System.out.println("<<" + response.status() + " " + response.url()));
        return getTlPage();
    }

    public void stop(Method testInfo) {
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));

        String traceName = String.format("%s_%s", formattedDateTime, testInfo.getName());
        getTlContext().tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("src/test/resources/" + traceName + ".zip")));
        getTlContext().close();
        getTlBrowser().close();
    }
}
