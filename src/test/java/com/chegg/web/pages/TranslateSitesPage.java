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

    public SiteForTranslatePage enterSiteURLAndClick(String url) {
        el.getByRole(AriaRole.TEXTBOX,"Website").fill(url);
        Page page1 = page.waitForPopup(() -> {
            el.getByRole(AriaRole.BUTTON,"Translate website").click();
            page.waitForTimeout(5000);
        });
        return new SiteForTranslatePage(page1);
    }



}
