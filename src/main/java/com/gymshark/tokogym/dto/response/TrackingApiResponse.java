package com.gymshark.tokogym.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TrackingApiResponse {

    private Meta meta;
    private Data data;

    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    public static class Meta {
        private String message;
        private int code;
        private String status;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Data {
        private boolean delivered;
        private Summary summary;
        private Details details;

        @JsonProperty("delivery_status")
        private DeliveryStatus deliveryStatus;

        private List<Manifest> manifest;

        public boolean isDelivered() { return delivered; }
        public void setDelivered(boolean delivered) { this.delivered = delivered; }

        public Summary getSummary() { return summary; }
        public void setSummary(Summary summary) { this.summary = summary; }

        public Details getDetails() { return details; }
        public void setDetails(Details details) { this.details = details; }

        public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
        public void setDeliveryStatus(DeliveryStatus deliveryStatus) { this.deliveryStatus = deliveryStatus; }

        public List<Manifest> getManifest() { return manifest; }
        public void setManifest(List<Manifest> manifest) { this.manifest = manifest; }
    }

    public static class Summary {
        @JsonProperty("courier_code")
        private String courierCode;

        @JsonProperty("courier_name")
        private String courierName;

        @JsonProperty("waybill_number")
        private String waybillNumber;

        @JsonProperty("service_code")
        private String serviceCode;

        @JsonProperty("waybill_date")
        private String waybillDate;  // ganti ke String supaya aman

        @JsonProperty("shipper_name")
        private String shipperName;

        @JsonProperty("receiver_name")
        private String receiverName;

        private String origin;
        private String destination;
        private String status;

        public String getCourierCode() { return courierCode; }
        public void setCourierCode(String courierCode) { this.courierCode = courierCode; }

        public String getCourierName() { return courierName; }
        public void setCourierName(String courierName) { this.courierName = courierName; }

        public String getWaybillNumber() { return waybillNumber; }
        public void setWaybillNumber(String waybillNumber) { this.waybillNumber = waybillNumber; }

        public String getServiceCode() { return serviceCode; }
        public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }

        public String getWaybillDate() { return waybillDate; }
        public void setWaybillDate(String waybillDate) { this.waybillDate = waybillDate; }

        public String getShipperName() { return shipperName; }
        public void setShipperName(String shipperName) { this.shipperName = shipperName; }

        public String getReceiverName() { return receiverName; }
        public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }

        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class Details {
        @JsonProperty("waybill_number")
        private String waybillNumber;

        @JsonProperty("waybill_date")
        private String waybillDate;  // ganti ke String

        @JsonProperty("waybill_time")
        private String waybillTime;  // ganti ke String

        private String weight;
        private String origin;
        private String destination;

        @JsonProperty("shipper_name")
        private String shipperName;

        @JsonProperty("shipper_address1")
        private String shipperAddress1;

        @JsonProperty("shipper_address2")
        private String shipperAddress2;

        @JsonProperty("shipper_address3")
        private String shipperAddress3;

        @JsonProperty("shipper_city")
        private String shipperCity;

        @JsonProperty("receiver_name")
        private String receiverName;

        @JsonProperty("receiver_address1")
        private String receiverAddress1;

        @JsonProperty("receiver_address2")
        private String receiverAddress2;

        @JsonProperty("receiver_address3")
        private String receiverAddress3;

        @JsonProperty("receiver_city")
        private String receiverCity;

        public String getWaybillNumber() { return waybillNumber; }
        public void setWaybillNumber(String waybillNumber) { this.waybillNumber = waybillNumber; }

        public String getWaybillDate() { return waybillDate; }
        public void setWaybillDate(String waybillDate) { this.waybillDate = waybillDate; }

        public String getWaybillTime() { return waybillTime; }
        public void setWaybillTime(String waybillTime) { this.waybillTime = waybillTime; }

        public String getWeight() { return weight; }
        public void setWeight(String weight) { this.weight = weight; }

        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }

        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }

        public String getShipperName() { return shipperName; }
        public void setShipperName(String shipperName) { this.shipperName = shipperName; }

        public String getShipperAddress1() { return shipperAddress1; }
        public void setShipperAddress1(String shipperAddress1) { this.shipperAddress1 = shipperAddress1; }

        public String getShipperAddress2() { return shipperAddress2; }
        public void setShipperAddress2(String shipperAddress2) { this.shipperAddress2 = shipperAddress2; }

        public String getShipperAddress3() { return shipperAddress3; }
        public void setShipperAddress3(String shipperAddress3) { this.shipperAddress3 = shipperAddress3; }

        public String getShipperCity() { return shipperCity; }
        public void setShipperCity(String shipperCity) { this.shipperCity = shipperCity; }

        public String getReceiverName() { return receiverName; }
        public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

        public String getReceiverAddress1() { return receiverAddress1; }
        public void setReceiverAddress1(String receiverAddress1) { this.receiverAddress1 = receiverAddress1; }

        public String getReceiverAddress2() { return receiverAddress2; }
        public void setReceiverAddress2(String receiverAddress2) { this.receiverAddress2 = receiverAddress2; }

        public String getReceiverAddress3() { return receiverAddress3; }
        public void setReceiverAddress3(String receiverAddress3) { this.receiverAddress3 = receiverAddress3; }

        public String getReceiverCity() { return receiverCity; }
        public void setReceiverCity(String receiverCity) { this.receiverCity = receiverCity; }
    }

    public static class DeliveryStatus {
        private String status;

        @JsonProperty("pod_receiver")
        private String podReceiver;

        @JsonProperty("pod_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate podDate;

        @JsonProperty("pod_time")
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime podTime;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getPodReceiver() { return podReceiver; }
        public void setPodReceiver(String podReceiver) { this.podReceiver = podReceiver; }

        public LocalDate getPodDate() { return podDate; }
        public void setPodDate(LocalDate podDate) { this.podDate = podDate; }

        public LocalTime getPodTime() { return podTime; }
        public void setPodTime(LocalTime podTime) { this.podTime = podTime; }
    }

    public static class Manifest {
        @JsonProperty("manifest_code")
        private String manifestCode;

        @JsonProperty("manifest_description")
        private String manifestDescription;

        @JsonProperty("manifest_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate manifestDate;

        @JsonProperty("manifest_time")
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime manifestTime;

        @JsonProperty("city_name")
        private String cityName;

        public String getManifestCode() { return manifestCode; }
        public void setManifestCode(String manifestCode) { this.manifestCode = manifestCode; }

        public String getManifestDescription() { return manifestDescription; }
        public void setManifestDescription(String manifestDescription) { this.manifestDescription = manifestDescription; }

        public LocalDate getManifestDate() { return manifestDate; }
        public void setManifestDate(LocalDate manifestDate) { this.manifestDate = manifestDate; }

        public LocalTime getManifestTime() { return manifestTime; }
        public void setManifestTime(LocalTime manifestTime) { this.manifestTime = manifestTime; }

        public String getCityName() { return cityName; }
        public void setCityName(String cityName) { this.cityName = cityName; }
    }
}
