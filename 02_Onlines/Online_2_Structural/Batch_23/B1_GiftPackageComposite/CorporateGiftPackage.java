/**
 * Concrete Composite in the Composite Pattern.
 * Intended to be distributed among employees or clients of an organization on
 * Eid.
 */
public class CorporateGiftPackage extends GiftPackage {
    public CorporateGiftPackage(String name, String creatorName, PackagingStyle packagingStyle) {
        super(name, creatorName, packagingStyle);
    }

    @Override
    public String getPackageType() {
        return "Corporate Gift Package";
    }
}
