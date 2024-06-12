package com.bestrookie.springframework.dto.converter;

import com.bestrookie.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author bestrookie
 * @Date 2024/6/12 15:28
 * @Desc
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter DATE_TIME_FORMATTER;

    public StringToLocalDateConverter(String pattern){
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
    }
    @Override
    public LocalDate conver(String source) {
        return LocalDate.parse(source, DATE_TIME_FORMATTER);
    }
}
