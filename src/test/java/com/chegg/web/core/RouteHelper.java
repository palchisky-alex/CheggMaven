package com.chegg.web.core;
import com.chegg.web.core.utill.Prop;
import com.chegg.web.pages.MainPage;
import com.microsoft.playwright.Page;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;

@Getter
public class RouteHelper {
    private Page page;
    private BasePage el;
    private Prop conf;

    public RouteHelper(Page page) {
        this.page = page;
        el = new BasePage(page);
        conf = ConfigFactory.create(Prop.class);
    }

    public MainPage toGoogleTranslateSite() {
        page.navigate(conf.basicHost());
        return new MainPage(page);
    }

}
