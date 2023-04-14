package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class HandwriteCanvasPage {
    private Page page;
    private BasePage el;
    private String canvasClose = "[class*=close]";
    private String canvasEnter = "[class*=enter][class*=action]";
    private Locator textArea;


    public HandwriteCanvasPage(Page page) {
        this.page = page;
        el = new BasePage(page);
        textArea = el.getByRole(AriaRole.COMBOBOX, "Source text");
    }

    @Step("write on canvas")
    public HandwriteCanvasPage writeOnCanvas() {
        for (int i = 58; i < 100; i++) {
            el.clickWithCoordinate("canvas", 86, i);
        }
        textArea.click();
        return this;
    }

    @Step("canvas click submit")
    public HandwriteCanvasPage canvasClickEnter() {
        el.clickBy(canvasEnter);
        return this;
    }

    public boolean canvasClose() {
        el.clickBy(canvasClose);
        el.waitForTimeout(100);
        return el.isVisible("canvas") ? false : true;
    }

}
