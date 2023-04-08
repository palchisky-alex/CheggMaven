package com.chegg.web.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class BasePage {
    private Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public Locator getByRole(AriaRole role, String htmlText) {
        return page.getByRole(role, new Page.GetByRoleOptions().setName(Pattern.compile(htmlText, Pattern.CASE_INSENSITIVE)));
    }
    public void clickBy(String path) {
        page.waitForResponse(Response::ok, () -> {
            page.click(path);
            System.out.println("GET request completed successfully");
        });

    }

    public Locator getLocator(String path) {
        return page.locator(path);
    }

    public String getInnerTextBy(String path) {
        return page.locator(path).innerText().toLowerCase().trim();
    }
    
    public void waitForTimeout(int msec) {
        page.waitForTimeout(msec);
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
