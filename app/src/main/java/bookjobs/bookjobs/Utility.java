package bookjobs.bookjobs;

/**
 * Created by mac on 7/10/16.
 */
public class Utility {

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    static public double calculateDistance(Address ownerUser, Address wanterUser) {

        double userLat   = ownerUser.getLatitude();
        double userLng   = ownerUser.getLongitude();
        double venueLat  = wanterUser.getLatitude();
        double venueLng  = wanterUser.getLongitude();

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return  (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
    }
}
