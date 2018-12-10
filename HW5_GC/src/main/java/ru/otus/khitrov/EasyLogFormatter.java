package ru.otus.khitrov;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class EasyLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + "\n";
    }
}
