package ru.osk.qiwi4j.bill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.osk.qiwi4j.Qiwi4JClient;
import ru.osk.qiwi4j.bill.info.BillAmount;
import ru.osk.qiwi4j.bill.info.BillCustomer;

import java.io.IOException;
import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Bill {

    BillAmount amount;
    ZonedDateTime expirationDateTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    BillCustomer customer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String comment;

    public String getExpirationDateTime() {
        return expirationDateTime.toString();
    }

    public BillInfo create(String secretKey, String billId) throws JsonProcessingException {

        String billUrl = Qiwi4JClient.BILLS_URL + billId;

        RequestBody body = RequestBody.create(
                Qiwi4JClient.OBJECT_MAPPER.writeValueAsString(this),
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(billUrl)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + secretKey)
                .build();

        try (Response response = Qiwi4JClient.HTTP_CLIENT.newCall(request).execute()) {
            return Qiwi4JClient.OBJECT_MAPPER.readValue(response.body().string(), BillInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
