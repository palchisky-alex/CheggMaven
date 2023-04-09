package com.chegg.web.pages;
import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TranslateSitesPage {
    private Page page;
    private BasePage el;

    public TranslateSitesPage(Page page) {
        this.page = page;
        el = new BasePage(page);
    }

    public SourceLangPage openSourceLangList() {
        el.getByRole(AriaRole.BUTTON, "More source languages").click();
        return new SourceLangPage(page);
    }

    public TargetLangPage openTargetLangList() {
        el.getByRole(AriaRole.BUTTON, "More target languages").click();
        return new TargetLangPage(page);
    }

    public SiteForTranslatePage enterSiteURLAndClick(String url) {
        el.getByRole(AriaRole.TEXTBOX,"Website").fill(url);
        Page page1 = page.waitForPopup(() -> {
            el.getByRole(AriaRole.BUTTON,"Translate website").click();
            page.waitForTimeout(5000);
        });
        return new SiteForTranslatePage(page1);
    }



}
