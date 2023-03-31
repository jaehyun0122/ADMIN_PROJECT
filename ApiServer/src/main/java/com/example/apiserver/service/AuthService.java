package com.example.apiserver.service;

import java.util.List;
import java.util.Map;

public interface AuthService {
    boolean headerCheck(String requestHeader);

    String authBodyCheck(Map<String, String> body);
}
