/**
 * The Voyage class represents a generic voyage.
 * It contains information about the bus, route, seats, and revenue.
 */
public abstract class Voyage {
    private int busId ;
    private int rows;
    private float revenue;
    private float price;
    private String from;
    private String destination;

    public Voyage(int busId, String from,String destination, int rows, float price){
        this.busId = busId;
        this.from = from;
        this.destination = destination;
        this.rows = rows;
        this.price = price;
        //revenue = 0;
    }
    public abstract float getRefundPrice(int seatNumber);

    public abstract char[] getSeats();
    public abstract void setSeats(char[] seats ,String[] seatNumberList, boolean isSold);
    /**
     * Writes the availability of seats as a string representation.
     *
     * @return a string representing the availability of seats
     */
    public abstract String writeSeats();

    public int getBusId() {
        return busId;
    }

    public float getPrice(int seatNumber) {
        return price;
    }
    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public String getFrom() {
        return from;
    }

    public String getDestination() {
        return destination;
    }
    public abstract float calculateTotalPrice();


}


