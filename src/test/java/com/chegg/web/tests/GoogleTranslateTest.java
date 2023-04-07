package com.chegg.web.tests;

import com.chegg.web.core.BaseTest;
import com.chegg.web.core.utill.LangList;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleTranslateTest extends BaseTest {

    @Test
    void testTranslateText() throws IOException {
        googleTranslatePage = app.navigate().toGoogleTranslateSite();
//        googleTranslatePage.swapLanguages();


//        assertThat(isSourceLangListClosed).as("the list of source languages is closed").isTrue();


        boolean isLangListWindow = googleTranslatePage
                .openSourceLangList()
                .searchLangAndPick(LangList.Hebrew);
        assertThat(isLangListWindow).as("the list of source languages is closed").isFalse();

        boolean isLangListWindowClosed = googleTranslatePage
                .openTargetLangList()
                .searchLangAndPick(LangList.Russian);
        assertThat(isLangListWindowClosed).as("the list of target languages is closed").isFalse();

        googleTranslatePage.translateText().typeText("привет").setDetectLang();
    }

    @Test
    void testTranslateImage() {
        googleTranslatePage = app.navigate().toGoogleTranslateSite();

        boolean isLangListWindowClosed = googleTranslatePage.openTargetLangList().searchLangAndPick(LangList.Russian);
        assertThat(isLangListWindowClosed).as("the list of target languages is closed").isFalse();

        boolean isFileUploaded = googleTranslatePage.translateImage().loadImage();
        assertThat(isFileUploaded).as("File uploaded successfully").isTrue();

        boolean isFileExist = googleTranslatePage.translateImage().downloadTranslation();
        assertThat(isFileExist).as("Download successful").isTrue();
    }

    @Test
    void testTranslateSite() {
        googleTranslatePage = app.navigate().toGoogleTranslateSite();

        translateSites = googleTranslatePage.translateSite();
        assertThat(googleTranslatePage.openSourceLangList().searchLangAndPick(LangList.English)).isFalse();
        assertThat(googleTranslatePage.openTargetLangList().searchLangAndPick(LangList.Spanish)).isFalse();

        boolean isTranslateSuccess = translateSites
                .enterSiteURLAndClick("https://sannysoft.com/")
                .verifyTranslatedWord("Alejandro Romanov");

        assertThat(isTranslateSuccess).as("Site translated").isTrue();
    }
}
