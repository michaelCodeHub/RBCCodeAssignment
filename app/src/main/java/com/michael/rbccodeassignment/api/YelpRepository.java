package com.michael.rbccodeassignment.api;

import android.util.Log;

import com.michael.rbccodeassignment.model.CustomList;
import com.michael.rbccodeassignment.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class YelpRepository {

    private static YelpRepository single_instance = null;
    private static final String TAG = "YelpRepository";
    private static final String API_KEY = "mK6YJcOFHaXmg7Hwndj4ITuOONP19tpZgFKOWxhZGltv7_SnEcPNTvrEV3lL11jlpoDg9xaVJ7zyZh4PB_I4SoFJXQFN4uEeZcSpLvMsKMf1SwHyj3fi1fJyAQcHYHYx";

    // Singleton implementation
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
    public CustomList getBusinessItems(
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
            connection.setRequestProperty("Authorization","Bearer "+ API_KEY);
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

        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }

        return null;
    }

    /**
     * Handling Json result from the API
     * Creating a custom object with two items
     * HashMap - contains the business item details mapped to the category
     * ArrayList - contains the list of the cateogories from the sreach
     */
    private CustomList validateJson(StringBuilder buffer){
        try
        {
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("businesses");

            CustomList customList = new CustomList();

            for(int i=0; i< jsonArray.length(); i++){
                JSONObject jsonRestaurant = jsonArray.getJSONObject(i);

                Restaurant restaurant = new Restaurant();
                restaurant.setId(jsonRestaurant.getString("id"));
                restaurant.setName(jsonRestaurant.getString("name"));
                restaurant.setImageURL(jsonRestaurant.getString("image_url"));
                restaurant.setIsClosed(jsonRestaurant.getString("is_closed"));
                restaurant.setUrl(jsonRestaurant.getString("url"));
                restaurant.setRating(jsonRestaurant.getInt("rating"));
                restaurant.setReviewCount(jsonRestaurant.getInt("review_count"));
                restaurant.setDisplayPhone(jsonRestaurant.getString("display_phone"));

                //Getting lat and lon from coordinsates array
                JSONObject coordinates = jsonRestaurant.getJSONObject("coordinates");

                restaurant.setLatitude(coordinates.getString("latitude"));
                restaurant.setLongitude(coordinates.getString("longitude"));

                //Getting the categories as JsonArray
                JSONArray categories = jsonRestaurant.getJSONArray("categories");

                for(int j=0; j< categories.length(); j++){
                    JSONObject category = categories.getJSONObject(j);
                    String title = category.getString("title");

                    //Adding category to the list
                    if(!customList.getCategories().contains(title)){
                        customList.getCategories().add(title);
                    }

                    //Adding the restaurant to the hashmap with the category
                    ArrayList<Restaurant> tempRestaurants = new ArrayList<>();
                    if(customList.getRestaurants().containsKey(title)){
                        tempRestaurants = customList.getRestaurants().get(title);
                        tempRestaurants.add(restaurant);
                        customList.getRestaurants().replace(title, tempRestaurants);
                    }
                    else{
                        tempRestaurants.add(restaurant);
                        customList.getRestaurants().put(title, tempRestaurants);
                    }
                }
            }

            return customList;

        } catch (Exception jsonException){
            Log.e(TAG, jsonException.toString());
        }

        return null;
    }
}