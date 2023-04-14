package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.chegg.web.core.utill.Keys;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;


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
        textArea = el.getByRole(AriaRole.COMBOBOX, "Source text");
    }

    @Step("open source lang list")
    public SourceLangPage openSourceLangList() {
        el.getByRole(AriaRole.BUTTON, "More source languages").click();
        return new SourceLangPage(page);
    }

    @Step("open target lang list")
    public TargetLangPage openTargetLangList() {
        el.getByRole(AriaRole.BUTTON, "More target languages").click();
        return new TargetLangPage(page);
    }

    @Step("type text in text area")
    public TranslateTextsPage typeText(String text) {
        textArea.type(text);
        page.waitForTimeout(500);
        return this;
    }

    @Step("get translation result")
    public String getTranslationResult() {
        page.waitForTimeout(1000);
        return el.getInnerTextBy("[role=\"region\"] [aria-live=\"polite\"]");
    }

    @Step("press any keyboard key in the text area")
    public TranslateTextsPage pressAnyKeyboardKeyOnTextArea(Keys key) {
        textArea.press(key.name());
        page.waitForTimeout(500);
        return this;
    }

    @Step("open handwrite canvas")
    public HandwriteCanvasPage openHandwriteCanvas() {
        if (!el.isVisible("canvas")) {
            el.getByRole(AriaRole.BUTTON, "Show the Input Tools menu").click();
            el.getByTexts("Handwrite").click();
            textArea.type(" ");
        }

        return new HandwriteCanvasPage(page);
    }


}
