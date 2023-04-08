package com.chegg.web.pages;
import com.chegg.web.core.BasePage;
import com.chegg.web.core.utill.Keys;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class TranslateTextsPage {
    private Page page;
    private BasePage el;
    private Locator searchLangInput;
    private Locator textArea;
    private Locator pick;

    public TranslateTextsPage(Page page) {
        this.page = page;
        el = new BasePage(page);
        searchLangInput = el.getByRole(AriaRole.TEXTBOX, "Search languages");
        pick = el.getByRole(AriaRole.MAIN, "Text translation").locator("div");
        textArea = el.getByRole(AriaRole.COMBOBOX,"Source text");
    }

    public TranslateTextsPage typeText(String text) {
        textArea.type(text);
        page.waitForTimeout(500);
        return this;
    }

    public String translationResult() {
        return el.getInnerTextBy("[aria-live=\"polite\"]");
    }

    public TranslateTextsPage pressAnyKeyboardKey(Keys key) {
        textArea.press(key.name());
        page.waitForTimeout(500);
        return this;
    }



}
