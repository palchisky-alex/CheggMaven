package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class TranslateSitesPage {
    private Page page;
    private BasePage el;

    public TranslateSitesPage(Page page) {
        this.page = page;
        el = new BasePage(page);
    }

    @Step("source lang list")
    public SourceLangPage openSourceLangList() {
        el.getByRole(AriaRole.BUTTON, "More source languages").click();
        return new SourceLangPage(page);
    }

    @Step("target lang list")
    public TargetLangPage openTargetLangList() {
        el.getByRole(AriaRole.BUTTON, "More target languages").click();
        return new TargetLangPage(page);
    }

    @Step("site url")
    public SiteForTranslatePage enterSiteURLAndClick(String url) {
        el.getByRole(AriaRole.TEXTBOX, "Website").fill(url);
        Page page1 = page.waitForPopup(() -> {
            el.getByRole(AriaRole.BUTTON, "Translate website").click();
            page.waitForTimeout(5000);
        });
        return new SiteForTranslatePage(page1);
    }


}
