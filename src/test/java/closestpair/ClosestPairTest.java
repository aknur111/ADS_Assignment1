import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void test1() {
        ClosestPair.Point2D[] points = {
            new ClosestPair.Point2D(1, 2),
            new ClosestPair.Point2D(4, 6),
            new ClosestPair.Point2D(7, 8),
            new ClosestPair.Point2D(2, 3)
        };
        
        ClosestPair.Result result = ClosestPair.getClosestPoints(points);
        assertEquals(1.414, result.dist, 0.001);
    }

    @Test
    void testTwoPoints() {
        ClosestPair.Point2D[] points = {
            new ClosestPair.Point2D(0, 0),
            new ClosestPair.Point2D(3, 4)
        };
        
        ClosestPair.Result result = ClosestPair.getClosestPoints(points);
        assertEquals(5.0, result.dist, 0.001);
    }

    @Test
    void testError() {
        ClosestPair.Point2D[] onePoint = { new ClosestPair.Point2D(1, 1) };
        assertThrows(IllegalArgumentException.class, () -> {
            ClosestPair.getClosestPoints(onePoint);
        });
    }
}
