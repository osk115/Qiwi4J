package ru.osk.qiwi4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import okhttp3.*;
import ru.osk.qiwi4j.bill.Bill;
import ru.osk.qiwi4j.bill.BillInfo;
import ru.osk.qiwi4j.payment.PaymentForm;

import java.io.IOException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Qiwi4JClient {

    public static final String BILLS_URL = "https://api.qiwi.com/partner/bill/v1/bills/";
    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    String secretKey;
    String publicKey;

    public static Qiwi4JClient create(String secretKey) {
        return new Qiwi4JClient(secretKey, "");
    }

    public static Qiwi4JClient create(String secretKey, String publicKey) {
        return new Qiwi4JClient(secretKey, publicKey);
    }

    public String createPaymentForm(PaymentForm paymentForm) {
        try {
            return paymentForm.createUrl(publicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BillInfo createBill(Bill bill, String id) {
        try {
            return bill.create(secretKey, id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public BillInfo getBillInfo(String billId) {

        String billUrl = BILLS_URL + billId;

        Request request = new Request.Builder()
                .url(billUrl)
                .get()
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

    public BillInfo cancelBill(String billId) {

        String billUrl = BILLS_URL + billId + "/reject";

        Request request = new Request.Builder()
                .url(billUrl)
                .post(RequestBody.create(
                        "{\"billId\":\"" + billId + "\"}",
                        MediaType.parse("application/json")))
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
