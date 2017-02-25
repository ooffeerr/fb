package com.poc.fb.fb_poc.utils;

import rx.Subscription;

public final class UnsubscribeIfPresent {
    private UnsubscribeIfPresent() {//no instance
    }

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null) subscription.unsubscribe();
    }
}
