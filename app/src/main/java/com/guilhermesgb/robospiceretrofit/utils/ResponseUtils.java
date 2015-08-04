package com.guilhermesgb.robospiceretrofit.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.client.Response;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class ResponseUtils {

    private static final int BUFFER_SIZE = 0x1000;

    public static String getBodyString(Response response) throws IOException {
        TypedInput body = response.getBody();
        if (body != null) {
            if (!(body instanceof TypedByteArray)) {
                response = readBodyToBytesIfNecessary(response, body);
                body = response.getBody();
            }
            byte[] bodyBytes = ((TypedByteArray) body).getBytes();
            String bodyMime = body.mimeType();
            String bodyCharset = MimeUtil.parseCharset(bodyMime);
            return new String(bodyBytes, bodyCharset);
        }
        return null;
    }

    private static Response readBodyToBytesIfNecessary(Response response, TypedInput body) throws IOException {
        if (body == null || body instanceof TypedByteArray) {
            return response;
        }
        String bodyMime = body.mimeType();
        byte[] bodyBytes = streamToBytes(body.in());
        body = new TypedByteArray(bodyMime, bodyBytes);
        return replaceResponseBody(response, body);
    }

    private static byte[] streamToBytes(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (stream != null) {
            byte[] buf = new byte[BUFFER_SIZE];
            int r;
            while ((r = stream.read(buf)) != -1) {
                baos.write(buf, 0, r);
            }
        }
        return baos.toByteArray();
    }

    private static Response replaceResponseBody(Response response, TypedInput body) {
        return new Response(response.getStatus(), response.getReason(), response.getHeaders(), body);
    }

}
