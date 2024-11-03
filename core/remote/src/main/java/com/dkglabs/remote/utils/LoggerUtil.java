package com.dkglabs.remote.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import retrofit2.Response;

public class LoggerUtil {

    private static final String TAG = "API_LOG";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void printLog(Response<T> response) {
        if (response != null) {
            try {
                if (response.body() instanceof List<?>) {
                    // If the response body is a list
                    List<?> listBody = (List<?>) response.body();
                    String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listBody);
                    Log.d(TAG, "JSON Response (List):\n" + jsonResponse);
                } else {
                    // If the response body is a single object
                    T body = response.body();
                    String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                    Log.d(TAG, "JSON Response:\n" + jsonResponse);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Log.e(TAG, "Error parsing JSON response");
            }
        } else {
            Log.e(TAG, "Response is null");
        }
    }
}

