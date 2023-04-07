package com.chegg.web.pages;

import com.chegg.web.core.utill.Keys;
import com.chegg.web.core.utill.LangList;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;


public class TranslateTextsPage {
    private Page page;
    private SourceLangPage langPage;
    private Locator sourceTextArea;
    private Locator searchLangInput;
    private Locator pick;
    private Locator pickFromLangList;


    public TranslateTextsPage(Page page) {
        this.page = page;
        langPage = new SourceLangPage(page);
        this.sourceTextArea = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Source text"));
        this.searchLangInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search languages"));
        this.pick = page.getByRole(AriaRole.MAIN, new Page.GetByRoleOptions().setName("Text translation")).locator("div");
        this.pickFromLangList = page.getByRole(AriaRole.MAIN, new Page.GetByRoleOptions().setName("Text translation"));
    }

    public TranslateTextsPage typeText(String text) {

        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Source text")).type(text);
        System.out.println("привет2");
        return this;
    }

    public TranslateTextsPage setDetectLang() {
        page.locator("(//button[@data-language-code='auto'])[1]").click();
        return this;
    }

    public TranslateTextsPage pressAnyKeyboardKey(Keys key) {
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Source text")).press(key.name());
        return this;
    }


    public TargetLangPage openTargetLangList() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("More source languages")).click();
        return new TargetLangPage(page);
    }

    public boolean searchLangAndPick(LangList lang)  {
        searchLangInput.fill(lang.name());
        pick.filter(new Locator.FilterOptions().setHasText(Pattern.compile("^"+lang.name()+"$"))).nth(1).click();
        page.waitForTimeout(500);

        return searchLangInput.isVisible();
    }


}
