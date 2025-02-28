package com.test.api.marvel.persistence.integration.marvel;

/*Server-side applications must pass two parameters in addition to the apikey parameter:

ts - a timestamp (or other long string which can change on a request-by-request basis)
hash - a md5 digest of the ts parameter, your private key and your public key (e.g. md5(ts+privateKey+publicKey)

 */


import com.test.api.marvel.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        return Map.of("ts", String.valueOf(timestamp), "apikey", apikey, "hash", getHash());
    }

}
