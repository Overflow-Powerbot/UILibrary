package org.overflow.ui.lang.layout;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 22:52
 */
public class Inset {

    private static final Inset DEFAULT_INSET = new Inset(0);

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Inset(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public Inset(final int i) {
        this(i, i, i, i);
    }

    public Inset() {
        this(0);
    }

    public Inset setInset(final int top, final int bottom, final int left, final int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        return this;
    }

    public Inset setInset(final int value) {
        return setInset(value, value, value, value);
    }

    public int getTop() {
        return top;
    }

    public Inset setTop(int top) {
        this.top = top;
        return this;
    }

    public int getBottom() {
        return bottom;
    }

    public Inset setBottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    public int getLeft() {
        return left;
    }

    public Inset setLeft(int left) {
        this.left = left;
        return this;
    }

    public int getRight() {
        return right;
    }

    public Inset setRight(int right) {
        this.right = right;
        return this;
    }

    public int getHorizontalTotal() {
        return left + right;
    }

    public int getVerticalTotal() {
        return left + right;
    }

    public static Inset getDefaultInset() {
        return DEFAULT_INSET;
    }
}
