/**
 * Component interface in the Composite Pattern.
 * Represents both individual gift items (leaves) and composite gift packages.
 */
public interface GiftComponent {
    /**
     * @return Name or title of the gift component.
     */
    String getName();

    /**
     * Calculates the total price of the component.
     * For a single item, this is its retail price.
     * For a package, this is the sum of all contained components plus any packaging
     * style cost.
     *
     * @return total price in USD.
     */
    double getPrice();

    /**
     * Prints the hierarchical structure of the component.
     *
     * @param indent indentation spaces for visual tree presentation.
     */
    void print(String indent);

    /**
     * Returns the number of individual leaf items contained in this component.
     * Used to validate the business rule: customer packages must contain at least 2
     * individual gift items.
     *
     * @return count of individual gift items.
     */
    int getIndividualItemCount();
}
