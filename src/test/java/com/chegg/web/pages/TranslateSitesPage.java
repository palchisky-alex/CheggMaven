package com.chegg.web.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TranslateSitesPage {
    Page page;

    public TranslateSitesPage(Page page) {
        this.page = page;
    }

    public SiteForTranslatePage enterSiteURLAndClick(String url) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Website")).fill(url);
        Page page1 = page.waitForPopup(() -> {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Translate website")).click();
            page.waitForTimeout(5000);
        });



        return new SiteForTranslatePage(page1);
    }



}
