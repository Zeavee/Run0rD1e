package ch.epfl.sdp.geometry;

/**
 * An utility class for parsing back the areas passed as data to the database.
 */
public class AreaFactory {
    /**
     * Creates an area given the code name of the area.
     * @param areaName A code name to help parsing back the area.
     * @return The parsed area.
     */
    public Area getArea(String areaName) {
        String[] parts = areaName.split(" ");

        switch (parts[0]) {
            case "CircleArea":
                return new CircleArea(Double.parseDouble(parts[1]), new GeoPoint(Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
            case "RectangleArea":
                return new RectangleArea(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), new GeoPoint(Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
            case "UnboundedArea":
                return new UnboundedArea();
        }

        return null;
    }
}