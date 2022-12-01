package ru.osk.qiwi4j.bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.osk.qiwi4j.bill.info.BillAmount;
import ru.osk.qiwi4j.bill.info.BillCustomer;
import ru.osk.qiwi4j.bill.info.BillRecipient;
import ru.osk.qiwi4j.bill.info.BillStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillInfo {

    String siteId;
    String billId;
    BillAmount amount;
    BillStatus status;
    String creationDateTime;
    String expirationDateTime;
    String payUrl;
    String recipientPhoneNumber;
    BillRecipient recipient;
    BillCustomer customer;
    String comment;

}
