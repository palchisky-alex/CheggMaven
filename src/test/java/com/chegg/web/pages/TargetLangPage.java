package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.chegg.web.core.utill.LangList;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TargetLangPage {
    private Page page;
    private BasePage el;
    private Locator searchLangInput;

    public TargetLangPage(Page page)  {
        this.page = page;
        el = new BasePage(page);
        searchLangInput = el.getByRole(AriaRole.TEXTBOX, "Search languages");
    }


    public boolean searchLangAndPick(LangList lang)  {
        searchLangInput.fill(lang.name());
        el.clickBy("//span[contains(text(), '"+lang.name()+"')]/ancestor::div[@data-language-code]");
        el.waitForTimeout(2000);
        return searchLangInput.isVisible();
    }

}
