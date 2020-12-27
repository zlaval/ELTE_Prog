package preexam.delivery;

public class RangedDelivery extends DeliveryService {

    public RangedDelivery(String name, int zipCode) {
        super(name, zipCode);
    }

    public RangedDelivery(String name, int zipCode, Integer... zips) {
        super(name, zipCode, zips);
    }

    public RangedDelivery(String fileName) {
        super(fileName);
    }

    protected boolean checkZipCode(int orderZipCode) {
        var min = servedZipCodes.stream().min(Integer::compareTo).orElse(zipCode);
        var max = servedZipCodes.stream().max(Integer::compareTo).orElse(zipCode);
        if (zipCode < min) {
            min = zipCode;
        }
        if (zipCode > max) {
            max = zipCode;
        }
        return orderZipCode <= max && orderZipCode >= min || orderZipCode == zipCode;

    }
}
