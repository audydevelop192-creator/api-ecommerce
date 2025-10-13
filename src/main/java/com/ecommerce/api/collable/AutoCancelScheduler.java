package com.ecommerce.api.collable;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoCancelScheduler {

    private final AutoCancelPayment autoCancelPayment;

    public AutoCancelScheduler(AutoCancelPayment autoCancelPayment) {
        this.autoCancelPayment = autoCancelPayment;
    }

    // Jalankan setiap 1 menit (60.000 ms)
    @Scheduled(fixedRate = 60000)
    public void runAutoCancel() {
        autoCancelPayment.run();
    }
}
