package com.chegg.web.core.utill;
import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:./src/config.properties", "system:env"})
public interface Prop extends Config {

    @DefaultValue("https://translate.google.com")
    String basicHost();

    @DefaultValue("https://sannysoft.com/")
    String siteForTranslation();

    @Key("headless.browser.mode")
    Boolean mode();

    @Key("slow.motion")
    Integer sloMotion();

    @Key("viewport.size.height")
    Integer viewportHeight();

    @Key("viewport.size.width")
    Integer viewportWidth();

    @Key("locale.ID")
    String local();

    @Key("timezone.ID")
    String timeZone();

    @Key("geolocation.latitude")
    Double latitude();

    @Key("geolocation.longitude")
    Double longitude();

    @Key("date.time.pattern")
    String dateTimePattern();

}
