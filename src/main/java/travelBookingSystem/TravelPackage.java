package travelBookingSystem;

public class TravelPackage {
    private int packageId;
    private String destination;
    private double price;
    private String description;
    
    

    public TravelPackage() {
	}

	public TravelPackage(int packageId, String destination, double price, String description) {
        this.packageId = packageId;
        this.destination = destination;
        this.price = price;
        this.description = description;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
