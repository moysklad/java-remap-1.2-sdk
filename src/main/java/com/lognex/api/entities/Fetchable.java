package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface Fetchable {
    public void fetch(LognexApi api) throws IOException, LognexApiException;
}
