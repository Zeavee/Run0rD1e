package ch.epfl.sdp.geometry;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class AreaFactoryTest {

    @Test
    public void areaFactoryWorks() {
        CircleArea circleArea = new CircleArea(10, new GeoPoint(20, 30));
        RectangleArea rectangleArea = new RectangleArea(5, 10, new GeoPoint(20, 30));
        UnboundedArea unboundedArea = new UnboundedArea();
        AreaFactory areaFactory = new AreaFactory();
        CircleArea circle = (CircleArea) areaFactory.getArea(circleArea.toString());
        RectangleArea rectangle = (RectangleArea) areaFactory.getArea(rectangleArea.toString());
        UnboundedArea unbounded = (UnboundedArea) areaFactory.getArea(unboundedArea.toString());
        assertEquals(circleArea.getRadius(), circle.getRadius(), 0.01);
        assertEquals(circleArea.getLocation().distanceTo(circle.getLocation()), 0, 0.01);
        assertEquals(rectangleArea.getWidth(), rectangle.getWidth(), 0.01);
        assertEquals(rectangleArea.getHeight(), rectangle.getHeight(), 0.01);
        assertEquals(rectangleArea.getLocation().distanceTo(rectangle.getLocation()), 0, 0.01);
        assertEquals(unboundedArea.toString(), unbounded.toString());
        assertNull(areaFactory.getArea("nothing"));
    }
}
