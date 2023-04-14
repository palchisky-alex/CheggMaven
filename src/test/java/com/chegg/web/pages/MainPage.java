package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;


public class MainPage {
    private Page page;
    private BasePage el;

    public MainPage(Page page) {
        this.page = page;
        el = new BasePage(page);
    }

    @Step("translate text")
    public TranslateTextsPage translateText() {
        el.getByRole(AriaRole.BUTTON, "Text").click();
        return new TranslateTextsPage(page);
    }

    @Step("translate image")
    public TranslateImagePage translateImage() {
        el.getByRole(AriaRole.BUTTON,"Image").click();
        return new TranslateImagePage(page);
    }

    @Step("translate site")
    public TranslateSitesPage translateSite() {
        el.getByRole(AriaRole.BUTTON, "Website translation").click();
        return new TranslateSitesPage(page);
    }





}
