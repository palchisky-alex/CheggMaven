package com.chegg.web.tests;
import com.chegg.web.core.BaseTest;
import com.chegg.web.core.utill.Keys;
import com.chegg.web.core.utill.LangList;
import com.chegg.web.pages.SiteForTranslatePage;
import com.chegg.web.pages.SourceLangPage;
import com.chegg.web.pages.TargetLangPage;
import com.chegg.web.pages.TranslateTextsPage;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class GoogleTranslateTest extends BaseTest {

    @Test(dataProvider = "translate_data")
    @AllureId("1")
    @Owner("admin")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("translate text from any language")
    @Story("the user has the ability to translate the text")
    @Links({@Link(name ="HARAnalyzer", url = "https://toolbox.googleapps.com/apps/har_analyzer/"),
            @Link(name ="Trace Viewer", url = "https://trace.playwright.dev/")})
    public void testTranslateText(String wordL, String wordR, LangList lFrom, LangList lTo, Keys keyboardKey) {
        step("Navigate to " + conf.basicHost(), () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate text", () -> {
            translateTexts = google.translateText();

            step("Open Source Language list", () -> {
                SourceLangPage srcLang = translateTexts.openSourceLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isSourceClosed = srcLang.searchLangAndPick(lFrom);
                    assertThat(isSourceClosed).as("the lang list is closed").isFalse();
                });
            });
            step("Open Target Language list", () -> {
                TargetLangPage trgList = translateTexts.openTargetLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isTargetClosed = trgList.searchLangAndPick(lTo);
                    assertThat(isTargetClosed).as("the lang list is closed").isFalse();
                });
            });
            step("Type text in the selected language and press any key", () -> {
                translateTexts.typeText(wordL).pressAnyKeyboardKey(keyboardKey);
            });
            step("Verify translation", () -> {
                assertThat(translateTexts.translationResult())
                        .as("is the text translated correctly").contains(wordR);
            });
        });
    }

    @Test(description = "Translate from Image")
    @AllureId("2")
    @Owner("admin")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("translate text from any image")
    @Story("the user has the ability to translate the text from image")
    @Links({@Link(name ="HARAnalyzer", url = "https://toolbox.googleapps.com/apps/har_analyzer/"),
            @Link(name ="Trace Viewer", url = "https://trace.playwright.dev/")})
    public void testTranslateImage() {
        LangList toLang = LangList.Spanish;
        String WORD_FROM = "HOUSE";
        String WORD_TO = "CASA";

        step("Navigate to " + conf.basicHost(), () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate image", () -> {
            translateImage = google.translateImage();

            step("Click the 'detect language' button", () -> {
                translateImage.setDetectLang();
            });
            step("Open Target Language list", () -> {
                TargetLangPage trgList = translateImage.openTargetLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isTargetClosed = trgList.searchLangAndPick(toLang);
                    assertThat(isTargetClosed).as("the lang list is closed").isFalse();
                });
            });
            step("Click the 'Browser your computer' button and upload file", () -> {
                translateImage.loadImage();
            });
            step("Verify translation", () -> {
                assertThat(translateImage.isTranslationEqualsTo(WORD_FROM))
                        .as("is the word %s translated into the word %s", WORD_FROM, WORD_TO).isTrue();
            });
            step("download new translation file by clicking the download translation button", () -> {
                boolean isFileExist = translateImage.downloadTranslation();

                step("Check if the file has downloaded", () -> {
                    assertThat(isFileExist).as("was the file downloaded successfully?").isTrue();
                });
            });
        });
    }

    @Test(description = "Translate Site")
    @AllureId("5")
    @Owner("admin")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("translate website text")
    @Story("the user has the ability to translate site text")
    @Links({@Link(name ="HARAnalyzer", url = "https://toolbox.googleapps.com/apps/har_analyzer/"),
            @Link(name ="Trace Viewer", url = "https://trace.playwright.dev/")})
    public void testTranslateSite() {
        String SITE = conf.siteForTranslation();

        step("Navigate to " + conf.basicHost(), () -> {
            google = app.navigate().toGoogleTranslateSite();
        });
        step("Init translate site", () -> {
            translateSites = google.translateSite();

            step("Open Source Language list", () -> {
                SourceLangPage srcLang = translateSites.openSourceLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isSourceClosed = srcLang.searchLangAndPick(LangList.English);

                    step("Verify Lang list is closed", () -> {
                        assertThat(isSourceClosed).as("the lang list is closed").isFalse();
                    });
                });
            });
            step("Open Target Language list", () -> {
                TargetLangPage trgList = translateSites.openTargetLangList();

                step("Search for the desired language and choose it", () -> {
                    boolean isTargetClosed = trgList.searchLangAndPick(LangList.Spanish);

                    step("Verify Lang list is closed", () -> {
                        assertThat(isTargetClosed).as("the lang list is closed").isFalse();
                    });
                });
            });
            step("Enter site URL '" + SITE + "' and click the translate button", () -> {
                SiteForTranslatePage site = translateSites.enterSiteURLAndClick(SITE);

                step("New tab opened with translated site", () -> {
                    boolean isTranslateSuccess = site.verifyTranslatedWord("Alejandro Romanov");

                    step("Verify translation", () -> {
                        assertThat(isTranslateSuccess).as("has the site been translated").isTrue();
                    });
                });
            });
        });
    }

    @Test
    public void testChegg() {
        google = app.navigate().toGoogleTranslateSite();
        translateTexts = google.translateText();

        boolean isClosed = translateTexts.openTargetLangList()
                .searchLangAndPick(LangList.Hebrew);

        assertThat(isClosed).as("Lang  target windows is closed").isFalse();

        boolean isClosed2 = translateTexts.openSourceLangList().searchLangAndPick(LangList.Spanish);
        assertThat(isClosed2).as("Lang source windows is closed").isFalse();


        translateTexts.typeText("DOG").pressAnyKeyboardKey(Keys.Shift);
        assertThat(translateTexts.translationResult()).as("").isEqualTo("DOG");


    }

    @Test(description = "Translate typed Text")
    @AllureId("1")
    @Owner("admin")
    public void testName() {
        step("Navigate to https://translate.google.com");
        step("Init translate text", () -> {
            step("Open Source Language list");
            step("Search for the desired language and choose it");
            step("Open Target Language list");
            step("Search for the desired language and choose it");
            step("Type text in the selected language");
            step("Verify translation");
        });
    }

    @DataProvider(name = "translate_data")
    public Object[][] testData() {
        return new Object[][]{
                {"casa", "home", LangList.Spanish, LangList.English, Keys.Enter},
                {"hus", "loger",LangList.Danish, LangList.French, Keys.ArrowUp},
                {"dom", "huis", LangList.Polish, LangList.Dutch, Keys.ArrowDown},
                {"domum or casa", "acasă sau casa",LangList.Latin, LangList.Romanian, Keys.Shift}
        };
    }


}
