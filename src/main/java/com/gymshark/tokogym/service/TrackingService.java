package com.gymshark.tokogym.service;



import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrackingService {

    private static final String API_KEY = "6a861eb2f1msh91e1d86f0f9b34ap1f141bjsn23f5fe16ce41";
    private static final String API_HOST = "cek-resi-cek-ongkir.p.rapidapi.com";

    public String track(String trackingNumber) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://cek-resi-cek-ongkir.p.rapidapi.com/tracking?logisticId=1&trackingNumber=" + trackingNumber))
                    .header("x-rapidapi-key", API_KEY)
                    .header("x-rapidapi-host", API_HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Gagal menghubungi layanan pelacakan";
        }
    }
}

