package org.voting.gateway.web.rest.errors;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by defacto on 6/7/2018.
 */
public class MyErrorException extends RuntimeException{
    private final ErrorValue value;
    private final ArrayList parameters;

    public MyErrorException(ErrorValue value, Object ... parameters) {
        super(MessageFormat.format(value.getMessage(), parameters));
        this.value = value;
        this.parameters = new ArrayList(Arrays.asList(parameters));
    }

    public ErrorValue getValue() {
        return value;
    }

    public List<Object> getParameters() {
        return parameters;
    }
}
