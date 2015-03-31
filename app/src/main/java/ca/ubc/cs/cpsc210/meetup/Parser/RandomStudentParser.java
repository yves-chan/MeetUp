package ca.ubc.cs.cpsc210.meetup.Parser;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import ca.ubc.cs.cpsc210.meetup.model.Course;
import ca.ubc.cs.cpsc210.meetup.model.CourseFactory;
import ca.ubc.cs.cpsc210.meetup.model.Schedule;
import ca.ubc.cs.cpsc210.meetup.model.Section;

/**
 *  Parses Random Student information
 * Created by yves on 19/03/15.
 */
public class RandomStudentParser {
    private JSONObject object;
    private JSONTokener tokener;
    JSONArray sectionArray;

    private String ranFirstName;
    private String ranLastName;
    private int ranStudentId;
    private SortedSet<Section> ranStudentSections;
    private CourseFactory courseFactory;


    /**
     * Constructor
     */
    public RandomStudentParser() {
    }

    /**
     * Parse JSON from Foursquare output stored into a file
     * REQUIRES: input is a file with valid data
     * EFFECTS: parsed data is put into PlaceFactory
     * @throws org.json.JSONException
     */
    public SortedSet<Section> parse(String input) {
        ranStudentSections = new TreeSet<Section>();
        try {
            tokener = new JSONTokener(input);
            object = new JSONObject(tokener);
            ranFirstName = object.getString("FirstName");
            ranLastName = object.getString("LastName");
            ranStudentId = object.getInt("Id");
            JSONArray sectionArray = object.getJSONArray("Sections");
            courseFactory = CourseFactory.getInstance();
            for (int i= 0; i<sectionArray.length(); i++){
                JSONObject arrayObject = sectionArray.getJSONObject(i);
                Course course = courseFactory.getCourse(arrayObject.getString("CourseName"),
                        Integer.valueOf(arrayObject.getString("CourseNumber")));
                ranStudentSections.add(course.getSection(arrayObject.getString("SectionName")));
            };
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return ranStudentSections;
    }
    public String getRanFirstName() {
        return ranFirstName;
    }

    public String getRanLastName() {
        return ranLastName;
    }

    public int getRanStudentId() {
        return ranStudentId;
    }

    public JSONArray getSectionArray() {
        return sectionArray;
    }
}
