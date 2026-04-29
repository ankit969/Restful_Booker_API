
package models.response;

public class BookingResponse {
	
	private int bookingid;
	private BookingData booking;
	
	public BookingResponse() {}
	
	public int getBookingid() {
		return bookingid;
	}
	
	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}
	
	public BookingData getBooking() {
		return booking;
	}
	
	public void setBooking(BookingData booking) {
		this.booking = booking;
	}

}
