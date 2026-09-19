/**
 * Predefined gift package offered officially by the company.
 * Typically uses Standard Gift Box (adds no extra cost) and has creator
 * "Company".
 */
public class PredefinedPackage extends GiftPackage {
    public PredefinedPackage(String name) {
        super(name, "Company Store", new PackagingStyle.StandardGiftBox());
    }

    public PredefinedPackage(String name, PackagingStyle packagingStyle) {
        super(name, "Company Store", packagingStyle);
    }

    @Override
    public String getPackageType() {
        return "Company Predefined Package";
    }
}
