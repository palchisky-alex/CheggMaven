package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class MainPage {
    private Page page;
    private BasePage el;

    public MainPage(Page page) {
        this.page = page;
        el = new BasePage(page);
    }

    public TranslateTextsPage translateText() {
        el.getByRole(AriaRole.BUTTON, "Text").click();
        return new TranslateTextsPage(page);
    }

    public TranslateImagePage translateImage() {
        el.getByRole(AriaRole.BUTTON,"Image").click();
        return new TranslateImagePage(page);
    }

    public TranslateSitesPage translateSite() {
        el.getByRole(AriaRole.BUTTON, "Website translation").click();
        return new TranslateSitesPage(page);
    }





}
