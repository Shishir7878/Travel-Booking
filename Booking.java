package connect;

public class Booking {
	private String bookingId;
	private String userId;
	private int packageId;
	private String travelDate;
	private int numberOfTravelers;

	public Booking() {
	}

	public Booking(String bookingId, String userId, int packageId, String travelDate, int numberOfTravelers) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.packageId = packageId;
		this.travelDate = travelDate;
		this.numberOfTravelers = numberOfTravelers;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public int getNumberOfTravelers() {
		return numberOfTravelers;
	}

	public void setNumberOfTravelers(int numberOfTravelers) {
		this.numberOfTravelers = numberOfTravelers;
	}


	public String toJson() {
		return String.format(
				"{\"bookingId\":\"%s\", \"userId\":\"%s\", \"packageId\":\"%s\", \"travelDate\":\"%s\", \"numberOfTravelers\":%d}",
				bookingId, userId, packageId, travelDate, numberOfTravelers);
	}
}
