package com.chegg.web.pages;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TranslateImage {
    Page page;

    public TranslateImage(Page page) {
        this.page = page;
    }

    public TranslateImage setDetectLang() {
        if(!isButtonDetectedLangSelected().equals("true")) {
            page.locator("(//button[@data-language-code='auto'])[1]").click();
        }
        return this;
    }

    private String isButtonDetectedLangSelected() {
        String detectLanguage = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Detect language")).getAttribute("aria-selected");
        System.out.println("detectLanguage " + detectLanguage);
        return detectLanguage;
    }

    public boolean loadImage() {
        Locator locator = page.getByRole(AriaRole.MAIN, new Page.GetByRoleOptions().setName("Image translation")).getByText("Browse your computer");
        locator.setInputFiles(Paths.get("src/test/resources/translate.jpg"));
        return page.locator("img[alt=\"ДОМ\"]").isVisible();
    }

    public boolean downloadTranslation() {
        Download downloadedFile = page.waitForDownload(() -> {
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Download translation")).click();
        });
        Path filePath = downloadedFile.path();
        downloadedFile.saveAs(Paths.get("src/test/resources/translate_"+Math.random()+".jpg"));

        if (Files.exists(filePath)) {
            System.out.println("Download successful: " + filePath.toAbsolutePath());
            return true;
        } else {
            System.out.println("Download failed");
        }
        return false;
    }
}
