package com.softpos.e2e;

/**
 * Event bus for E2E test instrumentation.
 * Production code calls fire() at key navigation points.
 * The bus is a no-op unless a test listener is registered.
 */
public final class TestEventBus {

    public enum Event {
        LOGIN_OPEN,
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        FLOOR_PLAN_OPEN,
        TABLE_SELECTED,
        MAIN_SALE_OPEN,
        ITEM_ORDERED,
        CHECKBILL_OPEN,
        PAYMENT_CONFIRMED,
        BILL_PRINTED,
        DIALOG_SHOWN,
        DIALOG_CLOSED
    }

    public interface Listener {
        void onEvent(Event event, String detail);
    }

    private static volatile Listener listener;

    private TestEventBus() {}

    public static void register(Listener l) {
        listener = l;
    }

    public static void unregister() {
        listener = null;
    }

    /** Fire an event with detail. Never throws — test code must not crash production. */
    public static void fire(Event event, String detail) {
        Listener l = listener;
        if (l != null) {
            try {
                l.onEvent(event, detail);
            } catch (Exception ignored) {
            }
        }
    }

    public static void fire(Event event) {
        fire(event, "");
    }
}
