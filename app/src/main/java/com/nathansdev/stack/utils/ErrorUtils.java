package com.nathansdev.stack.utils;

import android.util.Pair;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.data.model.Error;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * a utils class to process the error message and code.
 */
public class ErrorUtils {

    public static Pair<Integer, String> errorMessage(Throwable throwable, Moshi moshi) {
        Timber.e(throwable);
        String message = "Network failure";
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            switch (code) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    message = "Authorized Error ...., Please Login Again";
                    break;
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    message = "Server is down, you can wait or try again later ......";
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    message = "Server is down, you can wait or try again later ......";
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    message = "Access Denied";
                    break;
                case HttpURLConnection.HTTP_BAD_GATEWAY:
                    message = "Access Denied";
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    message = "Server is down, you can wait or try again later ......";
                    break;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    message = "Access Denied";
                    break;
                default:
                    ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
                    try {
                        message = parseErrorMessage(responseBody.string(), moshi);
                    } catch (IOException ex) {
                        Timber.e(ex);
                    }
                    break;
            }
            return new Pair<>(code, message);
        } else if (throwable instanceof SocketTimeoutException) {
            return new Pair<>(AppConstants.SOCKET_TIME_OUT, message);
        } else if (throwable instanceof IOException) {
            return new Pair<>(AppConstants.IO_EXCEPTION, message);
        } else {
            return new Pair<>(AppConstants.UNKNOWN, message);
        }
    }

    /**
     * @param error error json.
     * @param moshi
     * @return errorMessage.
     */
    private static String parseErrorMessage(String error, Moshi moshi) {
        Timber.d("Error message is %s", error);
        String errorMessage = "";
        try {
            if (error != null) {
                JsonAdapter<Error> jsonAdapter = moshi.adapter(Error.class);
                Error errorResponse = jsonAdapter.fromJson(error);
                //Timber.d("error %s",error);
                errorMessage = errorResponse.message();
            }
        } catch (IOException e) {
            Timber.e(e, "Error message parsing exception");
        }
        return errorMessage;
    }
}
