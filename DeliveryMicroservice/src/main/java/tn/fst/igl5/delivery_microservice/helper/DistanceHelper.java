package tn.fst.igl5.delivery_microservice.helper;

import tn.fst.igl5.delivery_microservice.domain.DeliveryPerson;

import java.util.List;

public class DistanceHelper {

    // Method to calculate the distance between two coordinates using the Haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius of the earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // Distance in kilometers
    }

    // Method to find the closest delivery person to a given location
    public static DeliveryPerson findClosestDeliveryPerson(double targetLat, double targetLon, List<DeliveryPerson> deliveryPersons) {
        if (deliveryPersons == null || deliveryPersons.isEmpty()) {
            throw new IllegalArgumentException("Delivery person list cannot be null or empty");
        }



        DeliveryPerson closest = null;
        double smallestDistance = Double.MAX_VALUE;

        for (DeliveryPerson person : deliveryPersons) {
            double distance = calculateDistance(targetLat, targetLon, person.getCurrentLat(), person.getCurrentLng());

            if (distance < smallestDistance) {
                smallestDistance = distance;
                closest = person;
            }
        }

        return closest;
    }

}