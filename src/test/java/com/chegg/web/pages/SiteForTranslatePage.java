package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SiteForTranslatePage {
    private Page page;
    private Locator title;
    private BasePage el;

    public SiteForTranslatePage(Page page) {
        this.page = page;
        title = page.locator(".container h1");
        el = new BasePage(page);
    }

    public boolean verifyTranslatedWord(String word) {
        boolean isTranslatedWord = false;
        if (title.isVisible()) {
            isTranslatedWord = el.getByRole(AriaRole.HEADING, word).isVisible();
        }
        page.close();
        return isTranslatedWord;
    }
}
