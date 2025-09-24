import java.util.*;

public class ClosestPair {
    
    public static class Point2D {
        public double x, y;
        
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double getDistance(Point2D other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    public static class Result {
        public Point2D a, b;
        public double dist;
        
        public Result(Point2D a, Point2D b) {
            this.a = a;
            this.b = b;
            this.dist = a.getDistance(b);
        }
    }
    
    public static Result getClosestPoints(Point2D[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }
        
        Point2D[] xSorted = points.clone();
        Point2D[] ySorted = points.clone();
        
        Arrays.sort(xSorted, (p1, p2) -> Double.compare(p1.x, p2.x));
        Arrays.sort(ySorted, (p1, p2) -> Double.compare(p1.y, p2.y));
        
        return findClosest(xSorted, ySorted, 0, points.length - 1);
    }
    
    private static Result bruteForceSearch(Point2D[] points, int start, int end) {
        double minDist = Double.MAX_VALUE;
        Point2D point1 = null, point2 = null;
        
        for (int i = start; i <= end; i++) {
            for (int j = i + 1; j <= end; j++) {
                double d = points[i].getDistance(points[j]);
                if (d < minDist) {
                    minDist = d;
                    point1 = points[i];
                    point2 = points[j];
                }
            }
        }
        
        return new Result(point1, point2);
    }
    
    private static Result findClosest(Point2D[] xSorted, Point2D[] ySorted, int left, int right) {
        int count = right - left + 1;
        
        if (count <= 3) {
            return bruteForceSearch(xSorted, left, right);
        }
        
        int mid = left + (right - left) / 2;
        Point2D midPoint = xSorted[mid];
        
        Point2D[] leftY = new Point2D[mid - left + 1];
        Point2D[] rightY = new Point2D[right - mid];
        int leftCount = 0, rightCount = 0;
        
        for (int i = 0; i < ySorted.length; i++) {
            if (ySorted[i].x <= midPoint.x) {
                leftY[leftCount++] = ySorted[i];
            } else {
                rightY[rightCount++] = ySorted[i];
            }
        }
        
        Result leftResult = findClosest(xSorted, leftY, left, mid);
        Result rightResult = findClosest(xSorted, rightY, mid + 1, right);
        
        Result best = leftResult.dist < rightResult.dist ? leftResult : rightResult;
        double minDist = best.dist;
        
        List<Point2D> strip = new ArrayList<>();
        for (int i = 0; i < ySorted.length; i++) {
            if (Math.abs(ySorted[i].x - midPoint.x) < minDist) {
                strip.add(ySorted[i]);
            }
        }
        
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minDist; j++) {
                double d = strip.get(i).getDistance(strip.get(j));
                if (d < minDist) {
                    minDist = d;
                    best = new Result(strip.get(i), strip.get(j));
                }
            }
        }
        
        return best;
    }
}
