package com.valeriygulin.ssemultichatjavafx.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeriygulin.ssemultichatjavafx.App;
import com.valeriygulin.ssemultichatjavafx.dto.ResponseResult;
import com.valeriygulin.ssemultichatjavafx.model.Message;
import com.valeriygulin.ssemultichatjavafx.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.prefs.Preferences;

public class MessageRepository {
    private static InputStream getData(String link, String method) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        if (httpURLConnection.getResponseCode() == 400) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()))) {
                ObjectMapper objectMapper = new ObjectMapper();
                throw new IllegalArgumentException(objectMapper.
                        readValue(bufferedReader, new TypeReference<ResponseResult<Object>>() {
                        }).getMessage());
            }
        }
        return httpURLConnection.getInputStream();
    }


    public Message add(Message message) throws IOException {
        Preferences prefs = Preferences.userRoot().node(App.class.getName());
        String id = prefs.get("id", "");
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/sse/getChat?id=" + id +
                        "&message=" + URLEncoder.encode(message.getContent(), StandardCharsets.UTF_8)
                , "POST")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<ResponseResult<Message>>() {
            }).getData();
        }
    }

    public List<Long> get() throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/sse/getChat"
                , "GET")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<ResponseResult<List<Long>>>() {
            }).getData();
        }
    }
}
