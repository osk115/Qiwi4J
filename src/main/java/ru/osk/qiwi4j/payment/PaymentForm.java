package ru.osk.qiwi4j.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import okhttp3.HttpUrl;

import java.io.IOException;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentForm {

    private static final String URL = "https://oplata.qiwi.com/create";

    String billId;
    BigDecimal amount;
    String phone;
    String email;
    String account;
    String comment;
    String successUrl;
    String customFields;

    public String createUrl(String publicKey) throws IOException {

        HttpUrl httpUrl = HttpUrl.parse(URL);

        HttpUrl.Builder urlBuilder = httpUrl.newBuilder()
                .addQueryParameter("publicKey", publicKey)
                .addQueryParameter("billId", billId);

        if (amount != null) urlBuilder.addQueryParameter("amount", amount.toPlainString());
        if (phone != null) urlBuilder.addQueryParameter("phone", phone);
        if (email != null) urlBuilder.addQueryParameter("email", email);
        if (comment != null) urlBuilder.addQueryParameter("comment", comment);
        if (successUrl != null) urlBuilder.addQueryParameter("successUrl", successUrl);
        if (customFields != null) urlBuilder.addQueryParameter("customFields", customFields);

        return urlBuilder.build().url().toString();

    }

}
