package com.farmerretailer.continuousauth;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;
import com.farmerretailer.gemini.dto.SecurityAnalysisRequest;

@Service
public class AnomalyDetectionService {

    @Value("${continuous.auth.url:http://localhost:5000}")
    private String pythonServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Evaluates security risk based on behavioral telemetry.
     * Rewritten to be robust and performant for live demonstrations.
     */
    public String evaluateRisk(String userId, SecurityAnalysisRequest sr) {
        if (sr == null) return "LOW";

        double mouseSpeed = sr.getMouseMovementAvgSpeed() != null ? sr.getMouseMovementAvgSpeed() : 0.0;
        double typingSpeed = sr.getTypingSpeedWpm() != null ? sr.getTypingSpeedWpm() : 0.0;

        // 🚨 HACKATHON DEMO LOGIC
        // Fast mouse movements are a classic sign of automated scripts or bot behavior
        if (mouseSpeed > 2000) {
            return "MEDIUM";
        }

        // Extreme speed or zero movement during active sessions can be flagged in a real production app
        if (typingSpeed > 200) {
            return "SUSPICIOUS";
        }

        // IsolationForest/ML logic would typically go here or call a Python service.
        // For the hackathon demo, we use the weighted heuristic approach for 0-latency.
        return "LOW";
    }

    // Overloaded for backward compatibility with DTO if needed
    public ContinuousAuthResponseDTO evaluateRisk(ContinuousAuthRequestDTO requestDTO) {
        ContinuousAuthResponseDTO response = new ContinuousAuthResponseDTO();
        response.setUserId(requestDTO.getUserId());
        response.setScore(0.5); // Default confidence score
        response.setRiskLevel("LOW");

        if (requestDTO.getTelemetry() == null) {
            return response;
        }

        try {
            // Build the payload for Flask ML service
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", requestDTO.getUserId());
            
            Map<String, Object> telemetryMap = new HashMap<>();
            telemetryMap.put("typingSpeedWpm", requestDTO.getTelemetry().getTypingSpeedWpm());
            telemetryMap.put("mouseMovementAvgSpeed", requestDTO.getTelemetry().getMouseMovementAvgSpeed());
            telemetryMap.put("scrollFrequency", requestDTO.getTelemetry().getScrollFrequency());
            payload.put("telemetry", telemetryMap);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            String url = pythonServiceUrl + "/api/v1/auth/predict";
            System.out.println("[AnomalyDetectionService] Calling Python ML Service: " + url + " for user: " + requestDTO.getUserId());
            
            ResponseEntity<Map> restResponse = restTemplate.postForEntity(url, entity, Map.class);

            if (restResponse.getStatusCode().is2xxSuccessful() && restResponse.getBody() != null) {
                Map<String, Object> body = restResponse.getBody();
                String riskLevel = (String) body.get("riskLevel");
                Double score = null;
                if (body.get("score") != null) {
                    try {
                        score = Double.valueOf(body.get("score").toString());
                    } catch (Exception ignored) {}
                }
                
                response.setRiskLevel(riskLevel != null ? riskLevel : "LOW");
                if (score != null) {
                    response.setScore(score);
                }
                
                // If baseline not established, trigger train in background
                String message = (String) body.get("message");
                if ("Baseline not established".equals(message)) {
                    triggerAsyncTraining(requestDTO.getUserId());
                }
                System.out.println("[AnomalyDetectionService] ML Service returned: Risk=" + response.getRiskLevel() + ", Score=" + response.getScore());
            }
        } catch (Exception e) {
            System.err.println("[AnomalyDetectionService] Error calling Flask ML service: " + e.getMessage() + ". Falling back to local heuristics.");
            // Fallback to local heuristic evaluation
            SecurityAnalysisRequest sr = new SecurityAnalysisRequest();
            sr.setMouseMovementAvgSpeed(requestDTO.getTelemetry().getMouseMovementAvgSpeed());
            sr.setTypingSpeedWpm(requestDTO.getTelemetry().getTypingSpeedWpm());
            sr.setScrollFrequency(requestDTO.getTelemetry().getScrollFrequency());
            response.setRiskLevel(evaluateRisk(requestDTO.getUserId(), sr));
        }

        return response;
    }

    private void triggerAsyncTraining(String userId) {
        new Thread(() -> {
            try {
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", userId);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

                String url = pythonServiceUrl + "/api/v1/auth/train";
                System.out.println("[AnomalyDetectionService] Requesting ML Service model training for user: " + userId);
                restTemplate.postForEntity(url, entity, Map.class);
                System.out.println("[AnomalyDetectionService] Async model training completed successfully for user: " + userId);
            } catch (Exception e) {
                System.err.println("[AnomalyDetectionService] Async model training failed for user: " + userId + " - " + e.getMessage());
            }
        }).start();
    }
}
