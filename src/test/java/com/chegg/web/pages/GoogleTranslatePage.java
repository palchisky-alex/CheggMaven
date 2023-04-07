package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;


public class GoogleTranslatePage extends BasePage {

    private Page page;
    private TranslateTextsPage textPage;
    private String currentSourceLang;
    private String currentDestinationLang;

    public GoogleTranslatePage(Page page) {
        this.page = page;
    }


    public GoogleTranslatePage setDetectLang() {
        if(!isButtonDetectedLangSelected().equals("true")) {
            page.locator("(//button[@data-language-code='auto'])[1]").click();
        }
        return this;
    }

    public String isButtonDetectedLangSelected() {
        String detectLanguage = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Detect language")).getAttribute("aria-selected");
        System.out.println("detectLanguage " + detectLanguage);
        return detectLanguage;
    }

    public SourceLangPage openSourceLangList() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More source languages")).click();
        return new SourceLangPage(page);
    }
    public GoogleTranslatePage closeSourceLangList() {
        return this;
    }

    public TargetLangPage openTargetLangList() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More target languages")).click();
        return new TargetLangPage(page);
    }
    public GoogleTranslatePage closeDestinationLangList() {
        return this;
    }

    public void swapLanguages() {
        Locator byRole = page.getByRole(AriaRole.TABLIST).filter(new Locator.FilterOptions().setHasText("Detect languageEnglishSpanishFrench")).getByRole(AriaRole.TAB, new Locator.GetByRoleOptions().setName("English"));
//        String s = page.locator("button[role=\"tab\"][aria-selected=\"true\"]").innerText();
        String detectLanguage = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Detect language")).getAttribute("aria-selected");
        System.out.println("detectLanguage " + detectLanguage);
        byRole.click();
        detectLanguage = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Detect language")).getAttribute("aria-selected");
        System.out.println("detectLanguage after " + detectLanguage);
    }


    public TranslateTextsPage translateText() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Text", Pattern.CASE_INSENSITIVE))).click();
        return new TranslateTextsPage(page);
    }

    public TranslateImage translateImage() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Image", Pattern.CASE_INSENSITIVE))).click();
        return new TranslateImage(page);
    }

    public TranslateSitesPage translateSite() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Website translation")).click();
        return new TranslateSitesPage(page);
    }





}
