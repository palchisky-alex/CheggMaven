package com.chegg.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SiteForTranslatePage {
    Page page;
    Locator title;

    public SiteForTranslatePage(Page page) {
        this.page = page;
        this.title = page.locator(".container h1");
    }

    public boolean verifyTranslatedWord(String word) {
        boolean isTranslatedWord = false;
        if(title.isVisible()) {
            isTranslatedWord = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(word)).isVisible();
        }
        page.close();
        return isTranslatedWord;
    }
}
