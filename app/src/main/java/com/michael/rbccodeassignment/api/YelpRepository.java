package com.michael.rbccodeassignment.api;

import android.util.Log;

import com.michael.rbccodeassignment.R;
import com.michael.rbccodeassignment.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class YelpRepository {

    private static YelpRepository single_instance = null;
    private static String TAG = "YelpRepository";

    // Singleton impolementation

    public static YelpRepository getInstance()
    {
        if (single_instance == null)
            single_instance = new YelpRepository();

        return single_instance;
    }

    /**
     *
     * @param count
     * @param term
     * Optional. Search term, for example "food" or "restaurants".
     * The term may also be business names, such as "Starbucks".
     * If term is not included the endpoint will default to searching across businesses
     * from a small number of popular categories.
     * @param city
     * Required if either latitude or longitude is not provided.
     * This string indicates the geographic area to be used when searching for businesses.
     * Examples: "New York City", "NYC", "350 5th Ave, New York, NY 10118".
     * Businesses returned in the response may not be strictly within the specified location.
     * @param sort
     * @return
     */
    public HashMap<String, ArrayList<Restaurant>> getBusinessItems(
            int count,
            String term,
            String city,
            String sort)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://api.yelp.com/v3/businesses/search?" +
                    "term="+ term +
                    "&location="+ city +
                    "&limit=" + count +
                    "&sort_by="+ sort);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization","Bearer "+ R.string.api_key);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("GET");
            connection.connect();


            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return validateJson(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Handling Json Response
    private HashMap<String, ArrayList<Restaurant>> validateJson(StringBuilder buffer){
        try
        {
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("businesses");

            HashMap<String, ArrayList<Restaurant>> restaurants = new HashMap<>();
            for(int i=0; i< jsonArray.length(); i++){
                JSONObject jsonRestaurant = jsonArray.getJSONObject(i);

                Restaurant restaurant = new Restaurant();
                restaurant.setId(jsonRestaurant.getString("id"));
                restaurant.setName(jsonRestaurant.getString("name"));
                restaurant.setImageURL(jsonRestaurant.getString("image_url"));
                restaurant.setIsClosed(jsonRestaurant.getString("is_closed"));
                restaurant.setUrl(jsonRestaurant.getString("url"));
                restaurant.setDisplayPhone(jsonRestaurant.getString("display_phone"));

                JSONArray categories = jsonRestaurant.getJSONArray("categories");

                for(int j=0; j< categories.length(); j++){
                    JSONObject category = categories.getJSONObject(j);
                    String title = category.getString("title");

                    ArrayList<Restaurant> tempRestaurants = new ArrayList<>();
                    if(restaurants.containsKey(title)){
                        tempRestaurants = restaurants.get(title);
                        tempRestaurants.add(restaurant);
                        restaurants.replace(title, tempRestaurants);
                    }
                    else{
                        tempRestaurants.add(restaurant);
                        restaurants.put(title, tempRestaurants);
                    }
                }

                return restaurants;
            }
        } catch (Exception jsonException){
            Log.e(TAG, jsonException.toString());
        }

        return null;
    }
}