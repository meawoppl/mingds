package io.txcl.mingds.render;

import com.google.common.base.Preconditions;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Box {
    private final Vector2D lower;
    private final Vector2D upper;

    public Box(Vector2D lower, Vector2D upper) {
        Preconditions.checkArgument(lower.getX() <= upper.getX());
        Preconditions.checkArgument(lower.getY() <= upper.getY());
        this.lower = lower;
        this.upper = upper;
    }

    public static Box covering(List<Vector2D> points) {
        Preconditions.checkArgument(points.size() > 0);
        DoubleSummaryStatistics xss =
                points.stream().mapToDouble(Vector2D::getX).summaryStatistics();
        DoubleSummaryStatistics yss =
                points.stream().mapToDouble(Vector2D::getY).summaryStatistics();

        Vector2D lower = new Vector2D(xss.getMin(), yss.getMin());
        Vector2D upper = new Vector2D(xss.getMax(), yss.getMax());

        return new Box(lower, upper);
    }

    /**
     * Return a box that is this box plus additional padding `amount` in all directions.
     *
     * @param amount Amount of padding to add
     * @return The padded box
     */
    public Box padded(double amount) {
        Preconditions.checkArgument(
                amount >= 0, "Can not have negative padding %f was specified", amount);
        Vector2D newLower = new Vector2D(lower.getX() - amount, lower.getY() - amount);
        Vector2D newUpper = new Vector2D(upper.getX() + amount, upper.getY() + amount);
        return new Box(newLower, newUpper);
    }

    public Box paddedPercent(double percentage) {
        Preconditions.checkArgument(percentage <= 1.0);
        Preconditions.checkArgument(percentage > 0.0);

        Vector2D center = upper.add(lower).scalarMultiply(0.5);
        Vector2D diff = upper.subtract(lower).scalarMultiply(0.5);
        Vector2D newDiff = diff.scalarMultiply(1 + percentage);

        return new Box(center.subtract(newDiff), center.add(newDiff));
    }

    public Box paddedToSquare() {
        Vector2D center = upper.add(lower).scalarMultiply(0.5);
        Vector2D diff = upper.subtract(lower).scalarMultiply(0.5);

        return new Box(center.subtract(diff), center.add(diff));
    }

    public Vector2D getLower() {
        return lower;
    }

    public Vector2D getUpper() {
        return upper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return lower.equals(box.lower) && upper.equals(box.upper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lower, upper);
    }
}
