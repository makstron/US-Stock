package com.klim.stock.network.retrofit;

import androidx.annotation.NonNull;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class StockHttpsURLConnection extends HttpsURLConnection {

    public static final int HTTP_TOO_MANY_REQUESTS = 429;

    protected StockHttpsURLConnection(@NonNull final URL u) {
        super(u);
    }

}
