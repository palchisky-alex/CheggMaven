package com.chegg.web.core;
import com.chegg.web.core.utill.Prop;
import com.chegg.web.pages.*;
import com.microsoft.playwright.Page;
import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {
    PlaywrightFactory pf;
    public Page page;
    protected HomePage app;
    public Prop conf;

    @BeforeMethod
    public void setUp() {
        conf = ConfigFactory.create(Prop.class);
        pf = new PlaywrightFactory();
        page = pf.initBrowser();
        app = new HomePage(page);
    }

    @AfterMethod
    public void tearDown(Method testInfo, ITestResult iTestResult) throws IOException {
        pf.stop(testInfo, iTestResult);

    }
}
