package model;

import java.util.Objects;

/**
 * Represents a customized menu item inside an order.
 *
 * Construction goes exclusively through {@link Builder}. The required fields
 * (menu item, quantity) are captured by the static factory; optional fields
 * (size, extra cheese, spicy, note) each have one default declared once in
 * the Builder. The private constructor performs pure field assignment only --
 * all validation and normalization happens in {@link Builder#build()}.
 */
public class OrderItem {
    public static final double EXTRA_CHEESE_PRICE = 60.0;

    private final MenuItem menuItem;
    private final int quantity;
    private final Size size;
    private final boolean extraCheese;
    private final boolean spicy;
    private final String note;

    private OrderItem(MenuItem menuItem, int quantity, Size size, boolean extraCheese, boolean spicy, String note) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.size = size;
        this.extraCheese = extraCheese;
        this.spicy = spicy;
        this.note = note;
    }

    /**
     * Entry point for building an OrderItem. menuItem and quantity are required.
     */
    public static Builder builder(MenuItem menuItem, int quantity) {
        return new Builder(menuItem, quantity);
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public Size getSize() {
        return size;
    }

    public boolean hasExtraCheese() {
        return extraCheese;
    }

    public boolean isSpicy() {
        return spicy;
    }

    public String getNote() {
        return note;
    }

    public double getUnitPrice() {
        double price = menuItem.getBasePrice() * size.getMultiplier();
        if (extraCheese) {
            price += EXTRA_CHEESE_PRICE;
        }
        return price;
    }

    public double getSubtotal() {
        return getUnitPrice() * quantity;
    }

    public String describeOptions() {
        StringBuilder options = new StringBuilder(size.name());
        if (extraCheese) {
            options.append(", extra cheese");
        }
        if (spicy) {
            options.append(", spicy");
        }
        if (!note.isEmpty()) {
            options.append(", note: ").append(note);
        }
        return options.toString();
    }

    @Override
    public String toString() {
        return String.format("%dx %-20s %-32s %8.2f",
                quantity,
                menuItem.getName(),
                describeOptions(),
                getSubtotal());
    }

    /**
     * Builder for {@link OrderItem}. Optional-field defaults (MEDIUM size,
     * no extra cheese, not spicy, no note) are declared once here.
     */
    public static class Builder {
        private final MenuItem menuItem;
        private final int quantity;

        private Size size = Size.MEDIUM;
        private boolean extraCheese = false;
        private boolean spicy = false;
        private String note = "";

        private Builder(MenuItem menuItem, int quantity) {
            this.menuItem = menuItem;
            this.quantity = quantity;
        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        public Builder extraCheese(boolean extraCheese) {
            this.extraCheese = extraCheese;
            return this;
        }

        public Builder spicy(boolean spicy) {
            this.spicy = spicy;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        /** Validates and normalizes all fields, then builds the immutable OrderItem. */
        public OrderItem build() {
            MenuItem validMenuItem = Objects.requireNonNull(menuItem, "Menu item cannot be null");
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive");
            }
            Size resolvedSize = size != null ? size : Size.MEDIUM;
            String trimmedNote = note != null ? note.trim() : "";
            return new OrderItem(validMenuItem, quantity, resolvedSize, extraCheese, spicy, trimmedNote);
        }
    }
}