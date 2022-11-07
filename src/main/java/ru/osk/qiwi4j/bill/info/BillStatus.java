package ru.osk.qiwi4j.bill.info;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillStatus {

    Value value;
    String changedDateTime;

    public ZonedDateTime getChangedZonedDateTime() {
        return ZonedDateTime.parse(changedDateTime);
    }

    public enum Value {
        WAITING,
        PAID,
        REJECTED,
        EXPIRED
    }

}
