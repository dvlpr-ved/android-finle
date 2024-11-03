package com.dkglabs.remote.manager;

import com.dkglabs.remote.interceptor.AuthenticationInterceptor;
import com.dkglabs.remote.utils.AppUrlConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class PostalApiManager {

    private static final String TAG = "ApiManager";
    /**
     * Enable the Network Logs
     */
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(false);

    private static OkHttpClient client = httpClient.build();

    private static ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    private static JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(AppUrlConstants.POSTAL_SERVER_URL)
            .addConverterFactory(jacksonConverterFactory)
            .client(client);

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
        checkInterceptorPresentOrNot(new Interceptor[]{authenticationInterceptor, logging});
        return retrofit.create(serviceClass);
    }

    private static void checkInterceptorPresentOrNot(Interceptor[] interceptors) {
        boolean isNewInterceptorAdded = false;
        for (Interceptor interceptor : interceptors) {
           /* if (interceptor instanceof HttpLoggingInterceptor) {
                continue;
            }*/
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                isNewInterceptorAdded = true;
            }
        }
        if (isNewInterceptorAdded) {
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
