package dev.compare.mvcvsreact.react.common.datetime;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class DateProvider {

    public ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public Date getCurrentDate() {
        return Date.from(getCurrentZonedDateTime().toInstant());
    }
}
