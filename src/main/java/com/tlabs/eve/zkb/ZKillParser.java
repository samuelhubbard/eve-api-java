package com.tlabs.eve.zkb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlabs.eve.EveParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ZKillParser<T extends ZKillResponse> implements EveParser<T> {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final Class<T> responseClass;

    public ZKillParser(final Class<T> responseClass) {
        super();
        this.responseClass = responseClass;
    }

    @Override
    public T parse(InputStream in) throws IOException {
        try {
            final StringBuilder b = new StringBuilder()
            .append("{\"kills\":")
            .append(IOUtils.toString(in, Charset.forName("UTF-8")))
            .append("}");

            return mapper.readValue(b.toString(), this.responseClass);
        }
        catch (JsonProcessingException e) {
            throw new IOException(e);
        }
    }
}
