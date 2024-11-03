package com.dkglabs.apply_loan.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static String convertImageUriToBase64(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            if (inputStream != null) {
                byte[] bytes = getBytesFromInputStream(inputStream);
                if (bytes != null) {
                    return Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
