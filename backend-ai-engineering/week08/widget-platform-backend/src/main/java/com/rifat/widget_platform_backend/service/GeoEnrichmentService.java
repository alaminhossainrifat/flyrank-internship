package com.rifat.widget_platform_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class GeoEnrichmentService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getGeoData(String ipAddress) {
        // When testing from localhost, the real IP is not available (127.0.0.1 comes up).
        // So for testing purposes, we are using a Google public IP as the default.
        if (ipAddress == null || ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "8.8.8.8";
        }

        // Provider A: ip-api.com (Primary)
        try {
            String urlA = "http://ip-api.com/json/" + ipAddress;
            Map<String, Object> responseA = restTemplate.getForObject(urlA, Map.class);
            if (responseA != null && "success".equals(responseA.get("status"))) {
                return responseA; // If successful, Provider A will return the data.
            }
        } catch (Exception e) {
            System.out.println("Provider A failed: " + e.getMessage());
        }

        // Fallback Provider B: ipapi.co (If Provider A fails)
        try {
            String urlB = "https://ipapi.co/" + ipAddress + "/json/";
            Map<String, Object> responseB = restTemplate.getForObject(urlB, Map.class);
            if (responseB != null && !responseB.containsKey("error")) {
                return responseB;
            }
        } catch (Exception e) {
            System.out.println("Provider B failed: " + e.getMessage());
        }

        // If both APIs are down, it will return null, but the system will not crash (Graceful Degradation)
        return null;
    }
}
