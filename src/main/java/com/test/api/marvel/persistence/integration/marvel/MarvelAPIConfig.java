package com.test.api.marvel.persistence.integration.marvel;

import com.test.api.marvel.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MarvelAPIConfig {

    @Autowired
    MD5Encoder md5Encoder;

    private long timestamp = System.currentTimeMillis();

    @Value("${marvel.apikey}")
    private String apikey;

    @Value("${marvel.privatekey}")
    private String privateKey;

    private String getHash() {
        String decodedHash = timestamp + privateKey + apikey;
        return md5Encoder.encode(decodedHash);
    }

    public Map<String, String> getAuthParams() {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("ts", String.valueOf(timestamp));
        authParams.put("apikey", apikey);
        authParams.put("hash", getHash());
        return authParams;
    }
}