package com.softpos.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.EnumMap;
import java.util.Map;

/**
 * สร้าง QR PromptPay (Thai QR Payment, EMVCo) พร้อมจำนวนเงิน
 * รองรับ เบอร์มือถือ (10 หลัก), เลขบัตรประชาชน/เลขผู้เสียภาษี (13 หลัก), e-Wallet ID (15 หลัก)
 */
public final class PromptPayQr {

    private PromptPayQr() {
    }

    /** @return payload ของ QR หรือ null ถ้า ID ไม่ถูกต้อง */
    public static String payload(String promptPayId, double amount) {
        String id = promptPayId == null ? "" : promptPayId.replaceAll("[^0-9]", "");
        String tag;
        String value;
        if (id.length() == 10 && id.startsWith("0")) {
            tag = "01";
            value = "0066" + id.substring(1);
        } else if (id.length() == 13) {
            tag = "02";
            value = id;
        } else if (id.length() == 15) {
            tag = "03";
            value = id;
        } else {
            return null;
        }
        boolean hasAmount = amount > 0;
        StringBuilder sb = new StringBuilder();
        sb.append(field("00", "01"));
        sb.append(field("01", hasAmount ? "12" : "11")); // 12 = ใช้ครั้งเดียว (มียอดเงิน)
        sb.append(field("29", field("00", "A000000677010111") + field(tag, value)));
        sb.append(field("53", "764"));                   // THB
        if (hasAmount) {
            sb.append(field("54", String.format(java.util.Locale.US, "%.2f", amount)));
        }
        sb.append(field("58", "TH"));
        sb.append("6304");
        sb.append(String.format("%04X", crc16(sb.toString())));
        return sb.toString();
    }

    public static BitMatrix matrix(String payload) {
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 0);
            return new QRCodeWriter().encode(payload, BarcodeFormat.QR_CODE, 0, 0, hints);
        } catch (WriterException e) {
            return null;
        }
    }

    private static String field(String id, String value) {
        return id + String.format("%02d", value.length()) + value;
    }

    /** CRC-16/CCITT-FALSE (poly 0x1021, init 0xFFFF) */
    private static int crc16(String s) {
        int crc = 0xFFFF;
        for (byte b : s.getBytes(java.nio.charset.StandardCharsets.US_ASCII)) {
            crc ^= (b & 0xFF) << 8;
            for (int i = 0; i < 8; i++) {
                crc = (crc & 0x8000) != 0 ? (crc << 1) ^ 0x1021 : crc << 1;
                crc &= 0xFFFF;
            }
        }
        return crc;
    }
}
