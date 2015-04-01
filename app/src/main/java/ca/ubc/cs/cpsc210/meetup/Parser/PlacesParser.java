package ca.ubc.cs.cpsc210.meetup.Parser;
import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.ubc.cs.cpsc210.meetup.model.EatingPlace;
import ca.ubc.cs.cpsc210.meetup.model.PlaceFactory;
import ca.ubc.cs.cpsc210.meetup.util.LatLon;

/**
 * Parses places from Foursquare
 * Created by yves on 23/03/15.
 */

/**
 * Foursquare location result parse (JSON)
 */

public class PlacesParser {

    private JSONObject object;
    private JSONTokener tokener;
    String rating;

    /**
     * Parse JSON from Foursquare output stored into a file
     * REQUIRES: input is a file with valid data
     * EFFECTS: parsed data is put into PlaceFactory
     * @throws org.json.JSONException
     */
    public void parse(String input) {
        PlaceFactory.getInstance();
        JSONArray itemArray;
        try {
            tokener = new JSONTokener(input);
            object = new JSONObject(tokener);
            itemArray = object.getJSONObject("response").getJSONArray("groups").getJSONObject(0).getJSONArray("items");
            for (int i = 0 ; i < itemArray.length() ; i++) {
                String name = itemArray.getJSONObject(i).getJSONObject("venue").getString("name");
                JSONObject location = itemArray.getJSONObject(i).getJSONObject("venue").getJSONObject("location");
                rating = itemArray.getJSONObject(i).getJSONObject("venue").getString("rating")+"";
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                LatLon latLon = new LatLon(lat, lng);
                EatingPlace place = new EatingPlace(name,latLon, rating);
                PlaceFactory.getInstance().add(place);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
