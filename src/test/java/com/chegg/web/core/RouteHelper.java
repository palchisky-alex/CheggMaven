package com.chegg.web.core;

import com.chegg.web.pages.GoogleTranslatePage;
import com.microsoft.playwright.Page;
import lombok.Getter;

@Getter
public class RouteHelper {
    private Page page;

    public RouteHelper(Page page) {
        this.page = page;
    }

    public GoogleTranslatePage toGoogleTranslateSite() {
        page.navigate("https://translate.google.com/");
        return new GoogleTranslatePage(page);
    }

}
