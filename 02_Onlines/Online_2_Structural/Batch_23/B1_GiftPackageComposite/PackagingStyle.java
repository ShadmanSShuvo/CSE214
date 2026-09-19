/**
 * Strategy/Implementor interface representing packaging styles for gift
 * packages.
 * The selected packaging style determines both the additional packaging cost
 * and the presentation of the package.
 */
public interface PackagingStyle {
    String getStyleName();

    double getAdditionalCost();

    String getPresentationDescription();

    /**
     * Standard Gift Box: Default packaging with no extra cost ($0).
     */
    class StandardGiftBox implements PackagingStyle {
        @Override
        public String getStyleName() {
            return "Standard Gift Box";
        }

        @Override
        public double getAdditionalCost() {
            return 0.0;
        }

        @Override
        public String getPresentationDescription() {
            return "Packed in a standard gift box (no extra cost)";
        }
    }

    /**
     * Premium Gift Box: Adds $15 and includes premium wrapping with a decorative
     * ribbon.
     */
    class PremiumGiftBox implements PackagingStyle {
        @Override
        public String getStyleName() {
            return "Premium Gift Box";
        }

        @Override
        public double getAdditionalCost() {
            return 15.0;
        }

        @Override
        public String getPresentationDescription() {
            return "Premium wrapping with a decorative ribbon (+$15.00)";
        }
    }

    /**
     * Eco-Friendly Gift Box: Adds $8 and uses recyclable materials.
     */
    class EcoFriendlyGiftBox implements PackagingStyle {
        @Override
        public String getStyleName() {
            return "Eco-Friendly Gift Box";
        }

        @Override
        public double getAdditionalCost() {
            return 8.0;
        }

        @Override
        public String getPresentationDescription() {
            return "Eco-friendly packaging using recyclable materials (+$8.00)";
        }
    }
}
