package hey.rich.edmontonwifi;

import java.util.Comparator;

import android.location.Location;

/**
 * A Wifi object that whose parameters will be filled by the json in
 * assets/wifi.json
 */
public class Wifi {

	public static float INVALID_DISTANCE = -1;

	public enum Facility {
		CITY, TRANSIT;
	};

	/** The status of the wifi. If invalid, IN_FUTURE will be used */
	public enum Status {
		ACTIVE, IN_PROGRESS, IN_FUTURE
	};

	/** ID is a HEX value with dashes in it */
	private String id;
	/** Name of the place where the WiFi antenna is located */
	private String name;
	/** Physical street address where the WiFi antenna is located */
	private String address;
	/**
	 * Type of location where WiFi antenna is found.
	 * */
	private Facility facility;
	/** Implementation status of wifi */
	private Status status;
	/** Name of organization providing the WiFi service */
	private String provider;
	/** Spatial coordinates of location */
	private Location location;
	/** Distance to a specified location */
	private double distance;

	public Wifi(String id, String name, String address, Facility facility,
			Status status, String provider, Location location) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.facility = facility;
		this.status = status;
		this.provider = provider;
		this.location = location;

		// Set distance to invalid since we don't know what location we want
		// distance from
		this.distance = -INVALID_DISTANCE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Status getStatus() {
		return status;
	}

	public String getStatusString() {
		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setDistance(Location l) {
		this.distance = l.distanceTo(this.location);
	}

	public double getDistance() {
		return this.distance;
	}


}
