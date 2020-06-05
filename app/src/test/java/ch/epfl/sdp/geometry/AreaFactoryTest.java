package ch.epfl.sdp.geometry;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.geometry.area.AreaFactory;
import ch.epfl.sdp.geometry.area.CircleArea;
import ch.epfl.sdp.geometry.area.RectangleArea;
import ch.epfl.sdp.geometry.area.UnboundedArea;
import ch.epfl.sdp.map.location.GeoPoint;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class AreaFactoryTest {
    private AreaFactory areaFactory;

    @Before
    public void setup() {
        areaFactory = new AreaFactory();
    }

    @Test
    public void testGetAreaReturnsCircleAreaWhenInputIsCircleAreaCode() {
        CircleArea circleArea = new CircleArea(10, new GeoPoint(20, 30));
        CircleArea circle = (CircleArea) areaFactory.getArea(circleArea.toString());

        assertEquals(circleArea.getRadius(), circle.getRadius(), 0.01);
        assertEquals(circleArea.getLocation().distanceTo(circle.getLocation()), 0, 0.01);
    }

    @Test
    public void testGetAreaReturnsRectangleAreaWhenInputIsRectangleAreaCode() {
        RectangleArea rectangleArea = new RectangleArea(5, 10, new GeoPoint(20, 30));
        RectangleArea rectangle = (RectangleArea) areaFactory.getArea(rectangleArea.toString());

        assertEquals(rectangleArea.getWidth(), rectangle.getWidth(), 0.01);
        assertEquals(rectangleArea.getHeight(), rectangle.getHeight(), 0.01);
        assertEquals(rectangleArea.getLocation().distanceTo(rectangle.getLocation()), 0, 0.01);
    }

    @Test
    public void testGetAreaReturnsUnboundedAreaWhenInputIsUnboundedAreaCode() {
        UnboundedArea unboundedArea = new UnboundedArea();
        UnboundedArea unbounded = (UnboundedArea) areaFactory.getArea(unboundedArea.toString());

        assertEquals(unboundedArea.toString(), unbounded.toString());
    }

    @Test
    public void testGetAreaReturnsNullWhenInputIsNotRecognized() {
        assertNull(areaFactory.getArea("nothing"));
    }
}
