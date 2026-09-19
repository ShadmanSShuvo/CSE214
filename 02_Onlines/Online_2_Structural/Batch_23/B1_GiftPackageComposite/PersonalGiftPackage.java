/**
 * Concrete Composite in the Composite Pattern.
 * Intended for an individual recipient (e.g. for friends, family, loved ones on
 * Eid).
 */
public class PersonalGiftPackage extends GiftPackage {
    public PersonalGiftPackage(String name, String creatorName, PackagingStyle packagingStyle) {
        super(name, creatorName, packagingStyle);
    }

    @Override
    public String getPackageType() {
        return "Personal Gift Package";
    }
}
