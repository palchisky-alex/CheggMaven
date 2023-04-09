package com.chegg.web.pages;

import com.chegg.web.core.BasePage;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TranslateImage {
    private Page page;
    private BasePage el;
    private String btnDetectLang = "(//button[@data-language-code='auto'])[1]";
    private String imageForTranslationPath = "src/test/resources/translate.jpg";
    private String downloadedFilePath = "src/test/resources/translate_"+Math.random()+".jpg";

    public TranslateImage(Page page) {
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

    public TranslateImage setDetectLang() {
        if(!isButtonDetectedLangSelected().equals("true")) {
            el.clickBy(btnDetectLang);
        }
        return this;
    }

    private String isButtonDetectedLangSelected() {
        return el.getByRole(AriaRole.TAB, "Detect language").getAttribute("aria-selected");
    }

    public TranslateImage loadImage() {
        el.getByRole(AriaRole.MAIN, "Image translation")
                .getByText("Browse your computer")
                .setInputFiles(Paths.get(imageForTranslationPath));;
        el.waitForTimeout(2000);
        return this;
    }

    public boolean isTranslationEqualsTo(String word) {
        return el.getLocator("img[alt='"+word+"']").isVisible();
    }

    public boolean downloadTranslation() {
        Download downloadedFile = page.waitForDownload(() -> {
                el.getByRole(AriaRole.BUTTON, "Download translation").click();
        });
        Path filePath = downloadedFile.path();
        downloadedFile.saveAs(Paths.get(downloadedFilePath));

        if (Files.exists(filePath)) {
            System.out.println("Download successful: " + filePath.toAbsolutePath());
            return true;
        } else {
            System.out.println("Download failed");
        }
        return false;
    }
}
