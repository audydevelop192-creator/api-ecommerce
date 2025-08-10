package com.gymshark.tokogym.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymshark.tokogym.dto.response.TrackingApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrackingService {

    private static final String API_KEY = "rqd1pcF3d61dd47198759ad2ErqkNhCa"; // Ganti dengan API Key RajaOngkir kamu
    private static final String BASE_URL = "https://rajaongkir.komerce.id/api/v1/track/waybill";

    /**
     * Method untuk tracking paket via RajaOngkir API
     * @param awb nomor resi / waybill
     * @param courier kode kurir (misal "wahana", "jne", "pos", dll)
     * @return TrackingApiResponse hasil deserialisasi response API
     */
    public TrackingApiResponse track(String awb, String courier) {
        try {
            String url = BASE_URL + "?awb=" + awb + "&courier=" + courier;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("key", API_KEY)
                    .POST(HttpRequest.BodyPublishers.noBody())  // POST request tanpa body
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(response.body(), TrackingApiResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
