package ch.epfl.sdp.geometry;

public class AreaFactory {
    public Area getArea(String areaName) {
        String[] parts = areaName.split(" ");

        switch (parts[0]) {
            case "CircleArea":
                CircleArea circleArea = new CircleArea(Double.parseDouble(parts[1]), new GeoPoint(Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
                circleArea.setRemainingTimeString(parts[4]);
                return circleArea;
            case "RectangleArea":
                RectangleArea rectangleArea = new RectangleArea(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), new GeoPoint(Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
                rectangleArea.setRemainingTimeString(parts[5]);
                return rectangleArea;
            case "UnboundedArea":
                UnboundedArea unboundedArea = new UnboundedArea();
                unboundedArea.setRemainingTimeString(parts[1]);
                return unboundedArea;
        }

        return null;
    }
}