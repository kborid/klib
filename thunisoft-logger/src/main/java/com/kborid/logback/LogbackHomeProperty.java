package com.kborid.logback;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogbackHomeProperty extends PropertyDefinerBase {

    private static final String DEFAULT_HOME = "logs";

    @Override
    public String getPropertyValue() {
        return DEFAULT_HOME;
    }
}
