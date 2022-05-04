package com.bycsmys.ioc.spring;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;

public class DatePropertyEditor extends PropertyEditorSupport {

    private String datePattern;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(getDatePattern());
        DateTime dateTime = dateTimeFormatter.parseDateTime(text);
        setValue(dateTime.toDate());
    }


    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

}
