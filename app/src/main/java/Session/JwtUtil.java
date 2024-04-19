package Session;

import android.util.Base64;

import org.json.JSONObject;

public class JwtUtil {
    public static String decodeTokenAndGetUserId(String token) {
        try {
            // Split the token into parts
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Token is not a valid JWT token");
            }

            // Decode the payload
            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE));

            // Parse the payload to a JSON object
            JSONObject jsonPayload = new JSONObject(payload);

            // Extract the user ID from the payload
            String userId = jsonPayload.getString("userId"); // Adjust the key as per your token's payload

            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
