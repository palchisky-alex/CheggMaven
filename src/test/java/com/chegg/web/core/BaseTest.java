package com.chegg.web.core;

import com.chegg.web.pages.GoogleTranslatePage;
import com.chegg.web.pages.HomePage;
import com.chegg.web.pages.TranslateSitesPage;
import com.microsoft.playwright.Page;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseTest {
    PlaywrightFactory pf;
    public Page page;
    protected HomePage app;
    protected GoogleTranslatePage googleTranslatePage;
    protected TranslateSitesPage translateSites;

    @BeforeMethod
    public void setUp() {
        pf = new PlaywrightFactory();
        page = pf.initBrowser();
        app = new HomePage(page);
    }



    @AfterMethod
    public void tearDown(Method testInfo){
        pf.stop(testInfo);

    }
}
