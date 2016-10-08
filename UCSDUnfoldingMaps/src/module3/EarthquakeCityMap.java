package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;
	
	// Color variables
	private final int red = color(255, 0, 0);
	private final int yellow = color(255, 255, 0);
	private final int blue = color(0, 0, 255);

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // Iterate over earthquakes, adding them as marker to markers
	    for (PointFeature earthquake : earthquakes) {
	    	markers.add(createMarker(earthquake));
	    }
	    
	    // Add markers to map
	    map.addMarkers(markers);
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// Create SimplePointMarker on feature's location
		SimplePointMarker spm = new SimplePointMarker(feature.getLocation());
		
		// Get magnitude of earthquake
		Object magObj = feature.getProperty("magnitude");
    	float mag = Float.parseFloat(magObj.toString());
    	
    	// Check magnitude and assign color and radius accordingly
    	if (mag >= 5.0) {
    		spm.setColor(red);
    		spm.setRadius(15);
    	}
    	else if (mag >= 4.0) {
    		spm.setColor(yellow);
    		spm.setRadius(10);
    	}
    	else {
    		spm.setColor(blue);
    		spm.setRadius(5);
    	}
    	
		return spm;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	/**
	 * Adds a key of magnitudes and corresponding visualisation to PApplet.  
	 */
	private void addKey() 
	{	
		// Create rectangle
        fill(color(230, 255, 255));
        rect(25, 50, 150, 250);
        
        // Draw text
        fill(color(0, 0, 0));
        textSize(14);
        text("Earthquake Key", 40, 80);
        text("5.0+ Magnitude", 60, 130);
        text("4.0+ Magnitude", 60, 180);
        text("Below 4.0", 60, 230);
        
        // Draw circles
        fill(red);
        ellipse(40, 125, 15, 15);
        fill(yellow);
        ellipse(40, 175, 10, 10);
        fill(blue);
        ellipse(40, 225, 5, 5);
	}
}
