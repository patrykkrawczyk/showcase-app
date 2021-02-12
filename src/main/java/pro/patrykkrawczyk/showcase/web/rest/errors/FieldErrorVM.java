package pro.patrykkrawczyk.showcase.web.rest.errors;

import lombok.Value;

import java.io.Serializable;

@Value
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    String objectName;
    String field;
    String message;
}
