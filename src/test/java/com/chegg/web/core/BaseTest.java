package com.chegg.web.core;

import com.chegg.web.pages.*;
import com.microsoft.playwright.Page;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {
    PlaywrightFactory pf;
    public Page page;
    protected HomePage app;
    protected GoogleTranslatePage google;
    protected TranslateSitesPage translateSites;
    protected TranslateTextsPage translateTexts;
    protected TranslateImage translateImage;

    @BeforeMethod
    public void setUp() {
        pf = new PlaywrightFactory();
        page = pf.initBrowser();
        app = new HomePage(page);
    }



    @AfterMethod
    public void tearDown(Method testInfo, ITestResult iTestResult) throws IOException {
        pf.stop(testInfo, iTestResult);

    }
}
