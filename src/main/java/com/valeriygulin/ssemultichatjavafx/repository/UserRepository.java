package com.valeriygulin.ssemultichatjavafx.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeriygulin.ssemultichatjavafx.dto.ResponseResult;
import com.valeriygulin.ssemultichatjavafx.model.User;
import com.valeriygulin.ssemultichatjavafx.util.Constants;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserRepository {
    private ObjectMapper objectMapper = new ObjectMapper();

    private <T> InputStream getData(String link, String method, T value) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setRequestProperty("Content-Type", "application/json;utf-8");
        httpURLConnection.setDoOutput(true);
        try (BufferedOutputStream bufferedOutputStream
                     = new BufferedOutputStream(httpURLConnection.getOutputStream())) {
            this.objectMapper.writeValue(bufferedOutputStream, value);
            if (httpURLConnection.getResponseCode() == 400) {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream()))) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    throw new IllegalArgumentException(objectMapper.
                            readValue(bufferedReader, new TypeReference<ResponseResult<Object>>() {
                            }).getMessage());
                }
            }
        }
        return httpURLConnection.getInputStream();
    }

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


    public User add(User user) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/sse/User", "POST", user)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<ResponseResult<User>>() {
            }).getData();
        }
    }


    public User get(String login, String password) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/sse/User?" +
                        "login=" + URLEncoder.encode(login, StandardCharsets.UTF_8) + "&password=" +
                        URLEncoder.encode(password, StandardCharsets.UTF_8)
                , "GET")) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<ResponseResult<User>>() {
            }).getData();
        }
    }
}
