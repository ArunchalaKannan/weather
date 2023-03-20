package bussinessLogic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Utility {

    private static final String API_KEY = "7382fcc95496dc4e9c2e18598cad3f03";
    private static final String API_ENDPOINT = "http://api.weatherstack.com/current";
    
      public static String[] get(String inp) {
    	  String[] output = new String[6]; 
             try {
                String input = inp;
                JSONObject weatherData = getWeatherData(input);
                if (weatherData == null) {
                    System.out.println("Sorry, could not retrieve weather information.");
                }
                
                JSONObject locationData = (JSONObject) weatherData.get("location");
                JSONObject currentData = (JSONObject) weatherData.get("current");
                
                double temperatureCelsius = Double.parseDouble(currentData.get("temperature").toString());
                double temperatureFahrenheit = celsiusToFahrenheit(temperatureCelsius);
                int humidity = Integer.parseInt(currentData.get("humidity").toString());
                long pressure = (long) currentData.get("pressure");
                double windSpeed = Double.parseDouble(currentData.get("wind_speed").toString());
                output[0]=""+locationData.get("name");
                output[1]=""+temperatureCelsius;
                output[2]=""+temperatureFahrenheit;
                output[3]=""+humidity;
                output[4]=""+windSpeed;
                output[5]=""+pressure;
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("An error occurred while reading user input.");
        }
			return output;
    }
    
    private static JSONObject getWeatherData(String input) {
        try {
            URL url = new URL(String.format("%s?access_key=%s&query=%s", API_ENDPOINT, API_KEY, input));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = "";
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);
            
            return json;
        } catch (Exception e) {
            return null;
        }
    }
    
    private static double celsiusToFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
