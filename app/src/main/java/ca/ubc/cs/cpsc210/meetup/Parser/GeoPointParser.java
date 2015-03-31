package ca.ubc.cs.cpsc210.meetup.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osmdroid.util.GeoPoint;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a JSON to get Geopoints
 * Created by yves on 18/03/15.
 */
public class GeoPointParser {
    private JSONObject object;
    private JSONTokener tokener;
    JSONArray geoPointArray;
    double lat;
    double lon;
    GeoPoint geoPoint;
    List<GeoPoint> geoPointList;

    /**
     * Constructor
     */
    public GeoPointParser() {
    }

    /**
     * Parse JSON from Foursquare output stored into a file
     * REQUIRES: input is a file with valid data
     * EFFECTS: parsed data is put into PlaceFactory
     * @throws JSONException
     */
    public List<GeoPoint> parse(String input) {
        geoPointList = new ArrayList<GeoPoint>();
        try {
            tokener = new JSONTokener(input);
            tokener.skipTo('{');
            object = new JSONObject(tokener);
            geoPointArray = object.getJSONObject("route").getJSONObject("shape").getJSONArray("shapePoints");
            for (int i = 0 ; i < geoPointArray.length() ; i++) {
                if (i % 2 == 0) {
                    lat = geoPointArray.getDouble(i);
                } else {
                    lon = geoPointArray.getDouble(i);
                    geoPoint = new GeoPoint(lat, lon);
                    geoPointList.add(geoPoint);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return geoPointList;
    }

}
