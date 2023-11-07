package com.example.cachenow.utils.other;

import org.springframework.core.convert.converter.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {

    @Override
    public LocalDateTime convert(Long source) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.systemDefault());
    }
}