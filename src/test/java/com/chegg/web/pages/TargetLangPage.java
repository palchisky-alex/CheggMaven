package com.chegg.web.pages;

import com.chegg.web.core.utill.LangList;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TargetLangPage {
    private Page page;
    private Locator searchLangInput;
    private Locator pick;
    private Locator pickFromLangList;

    public TargetLangPage(Page page) {
        this.page = page;
        this.searchLangInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search languages"));
        this.pick = page.getByRole(AriaRole.MAIN, new Page.GetByRoleOptions().setName("Text translation")).locator("div");
        this.pickFromLangList = page.getByRole(AriaRole.MAIN, new Page.GetByRoleOptions().setName("Text translation")).locator("div");
    }



    public boolean searchLangAndPick(LangList lang)  {
        searchLangInput.fill(lang.name());
//        pick.locator("span").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^"+lang.name()+"$"))).click();
//        pick.filter(new Locator.FilterOptions().setHasText(Pattern.compile("^"+lang.name()+"$"))).nth(1).click();
        page.click("//span[contains(text(), '"+lang.name()+"')]/ancestor::div[@data-language-code]");
        page.waitForTimeout(2000);

        return searchLangInput.isVisible();
    }
}
