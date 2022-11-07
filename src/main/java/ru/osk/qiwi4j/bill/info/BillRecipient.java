package ru.osk.qiwi4j.bill.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillRecipient {

    String requisitesType;
    String requisitesValue;

    public enum Type {
        PHONE_NUMBER
    }

}
