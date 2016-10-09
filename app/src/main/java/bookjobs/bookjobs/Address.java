package bookjobs.bookjobs;

/**
 * Created by Hung on 9/12/2016.
 */
public class Address {

    private double Latitude;
    private double Longitude;

    public Address(double Latitude, double Longitude)
    {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

}
