package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chegg.web.core.BasePage.getMethodName;


public class GoogleTranslatePage  {
    private Page page;
    private BasePage el;

    public GoogleTranslatePage(Page page) {
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

    public TranslateTextsPage translateText() {
        el.getByRole(AriaRole.BUTTON, "Text").click();
        return new TranslateTextsPage(page);
    }

    public TranslateImage translateImage() {
        el.getByRole(AriaRole.BUTTON,"Image").click();
        return new TranslateImage(page);
    }

    public TranslateSitesPage translateSite() {
        el.getByRole(AriaRole.BUTTON, "Website translation").click();
        return new TranslateSitesPage(page);
    }





}
