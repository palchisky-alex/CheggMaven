package com.chegg.web.tests;
import com.chegg.web.core.BaseTest;
import com.chegg.web.core.utill.LangList;
import com.chegg.web.pages.SiteForTranslatePage;
import com.chegg.web.pages.SourceLangPage;
import com.chegg.web.pages.TargetLangPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleTranslateTest extends BaseTest {
    private static final String BASE_URL = "https://translate.google.com";
    private static final String TRANSLATE_SITE = "https://sannysoft.com/";

    @Test(description = "Translate typed Text")
    @AllureId("1")
    @Owner("admin")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("translate text from any language")
    @Story("the user has the ability to translate the text")
    public void testTranslateText() {
        step("Navigate to " + BASE_URL, () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate text", () -> {
            translateTexts = google.translateText();

            step("Open Source Language list", () -> {
                SourceLangPage srcLang = google.openSourceLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isSourceClosed = srcLang.searchLangAndPick(LangList.English);
                    assertThat(isSourceClosed).as("the lang list is closed").isFalse();
                });
            });
            step("Open Target Language list", () -> {
                TargetLangPage trgList = google.openTargetLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isTargetClosed = trgList.searchLangAndPick(LangList.Spanish);
                    assertThat(isTargetClosed).as("tthe lang list is closed").isFalse();
                });
            });
            step("Type text in the selected language", () -> {
                translateTexts.typeText("Dog");
            });
            step("Verify translation", () -> {

            });
        });
    }

    @Test(description = "Translate from Image")
    @AllureId("2")
    @Owner("admin")
    public void testTranslateImage() {
        step("Navigate to " + BASE_URL, () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate image", () -> {
            translateImage = google.translateImage();

            step("Click the 'detect language' button", () -> {
                translateImage.setDetectLang();
            });
            step("Click the 'Browser your computer' button and upload file", () -> {
                boolean isFileUploaded = translateImage.loadImage();
                assertThat(isFileUploaded).as("File uploaded successfully").isTrue();
            });
            step("Verify translation");
            step("Download new translation file by clicking the download translation button", () -> {
                boolean isFileExist = translateImage.downloadTranslation();

                step("Check if the file has downloaded", () -> {
                    assertThat(isFileExist).as("Download successful").isTrue();
                });
            });
        });
    }

    @Test(description = "Translate Site")
    @AllureId("5")
    @Owner("admin")
    public void testTranslateSite() {
        step("Navigate to " + BASE_URL, () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate site", () -> {
            translateSites = google.translateSite();

            step("Open Source Language list", () -> {
                SourceLangPage srcLang = google.openSourceLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isSourceClosed = srcLang.searchLangAndPick(LangList.English);

                    step("Verify Lang list is closed", () -> {
                        assertThat(isSourceClosed).as("the lang list is closed").isFalse();
                    });
                });
            });
            step("Open Target Language list", () -> {
                TargetLangPage trgList = google.openTargetLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isTargetClosed = trgList.searchLangAndPick(LangList.Spanish);

                    step("Verify Lang list is closed", () -> {
                        assertThat(isTargetClosed).as("the lang list is closed").isFalse();
                    });
                });
            });
            step("Enter site URL '"+ TRANSLATE_SITE +"' and click the translate button", () -> {
                SiteForTranslatePage site = translateSites.enterSiteURLAndClick(TRANSLATE_SITE);

                step("New tab opened with translated site", () -> {
                    boolean isTranslateSuccess = site.verifyTranslatedWord("Alejandro Romanov");

                    step("Verify translation", () -> {
                        assertThat(isTranslateSuccess).as("Site translated").isTrue();
                    });
                });
            });
        });
    }


}
