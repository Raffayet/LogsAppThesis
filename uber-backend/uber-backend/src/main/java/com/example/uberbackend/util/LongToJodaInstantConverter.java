package com.example.uberbackend.util;

import org.joda.time.Instant;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/*
    Need this converter to transform back the timestamps from long value to localdatetime
    Elastic search (by default) stores those timestamps in long format
*/

@ReadingConverter
public class LongToJodaInstantConverter implements Converter<Long, Instant> {

    @Override
    public Instant convert(Long source) {
        return new Instant(source);
    }
}
