package RSV;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetF22FXCurvesTest {
    public static void main(String[] args) throws IOException {
        // Read UAT and Prod JSON files into objects
        GetF22FXCurvesPOJO uatResponse = new ObjectMapper().readValue(
                new String(Files.readAllBytes(Paths.get("src/test/java/RSV/fxCurveUAT.json"))),
                GetF22FXCurvesPOJO.class);
        GetF22FXCurvesPOJO prodResponse = new ObjectMapper().readValue(
                new String(Files.readAllBytes(Paths.get("src/test/java/RSV/fxCurvePROD.json"))),
                GetF22FXCurvesPOJO.class);

        // Get maps of FXRate data
        Map<String, GetF22FXCurvesPOJO.FXRate> uatRateMap = getMapofFXRatesData(uatResponse);
        Map<String, GetF22FXCurvesPOJO.FXRate> prodRateMap = getMapofFXRatesData(prodResponse);

        // Compare UAT and Prod responses
        for (String key : uatRateMap.keySet()) {
            if (prodRateMap.containsKey(key)) {
                GetF22FXCurvesPOJO.FXRate uatRate = uatRateMap.get(key);
                GetF22FXCurvesPOJO.FXRate prodRate = prodRateMap.get(key);

                // Compare bid and ask values or any other fields as needed
                if (!uatRate.getBid().equals(prodRate.getBid()) || !uatRate.getAsk().equals(prodRate.getAsk())) {
                    System.out.println("Mismatch found for key: " + key);
                    System.out.println("UAT Bid: " + uatRate.getBid() + ", Prod Bid: " + prodRate.getBid());
                    System.out.println("UAT Ask: " + uatRate.getAsk() + ", Prod Ask: " + prodRate.getAsk());
                    System.out.println();
                }
            } else {
                System.out.println("Key missing in Prod response: " + key);
            }
        }

        // Check for keys present in Prod but not in UAT (if needed)
        for (String key : prodRateMap.keySet()) {
            if (!uatRateMap.containsKey(key)) {
                System.out.println("Key missing in UAT response: " + key);
            }
        }
    }

    public static Map<String, GetF22FXCurvesPOJO.FXRate> getMapofFXRatesData(GetF22FXCurvesPOJO parsedResponse) {
        Map<String, GetF22FXCurvesPOJO.FXRate> parsedResponseMap = new HashMap<>();
        List<GetF22FXCurvesPOJO.FXRate> allFXRates = parsedResponse.getFXRates();
        for (GetF22FXCurvesPOJO.FXRate fxRate : allFXRates) {
            // Create key as combination of currency and tenor
            String key = fxRate.getCurrency() + fxRate.getTenor();
            parsedResponseMap.put(key, fxRate);
        }
        return parsedResponseMap;
    }
}
