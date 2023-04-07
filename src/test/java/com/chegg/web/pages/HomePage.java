package com.chegg.web.pages;

import com.chegg.web.core.RouteHelper;
import com.microsoft.playwright.Page;

public class HomePage {

    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }


    public RouteHelper navigate() {
        return new RouteHelper(page);
    }




}
