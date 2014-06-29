package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

import java.awt.*;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 22:35
 */
public class WArrow extends Widget<WArrow> {

    private Direction direction = Direction.NORTH;

    public WArrow() {
        super(ResourceLocale.ARROW);
    }

    @Override
    protected final WArrow get() {
        return this;
    }

    public final Direction getDirection() {
        return direction;
    }

    public final WArrow setDirection(Direction direction) {
        if (this.direction != direction && direction != null) {
            this.direction = direction;
            this.invalidate();
        }
        return get();
    }

    public static enum Direction {
        NORTH(new int[]{-5, 5, 0}, new int[]{5, 5, -5}, 3),
        EAST(new int[]{-5, 5, -5}, new int[]{5, 0, -5}, 3),
        SOUTH(new int[]{0, 5, -5}, new int[]{5, -5, -5}, 3),
        WEST(new int[]{-5, 5, 5}, new int[]{0, 5, -5}, 3);

        private final Polygon polygon;

        Direction(int[] x, int[] y, int n) {
            this.polygon = new Polygon(x, y, n);
        }

        public final Polygon getPolygon() {
            return polygon;
        }
    }
}
