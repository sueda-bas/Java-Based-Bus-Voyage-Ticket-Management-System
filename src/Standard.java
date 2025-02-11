/**
 * The Standard class represents a type of voyage using a standard bus.
 * It extends the Voyage class and implements methods specific to standard voyages.
 */
public class Standard extends Voyage{
    private float price;
    private float refundCut;
    private float refundPrice;
    private char[] seats;
    private float revenue;
    /**
     * Constructs a new Standard object with the specified parameters.
     *
     * @param busId        the ID of the bus
     * @param from         the starting point of the voyage
     * @param destination  the destination of the voyage
     * @param rows         the number of rows in the bus
     * @param price        the price of each seat in the voyage
     * @param refundCut    the percentage cut for refund
     */
    public Standard(int busId, String from, String destination, int rows, float price, float refundCut) {
        super(busId, from,destination, rows, price);
        this.price = price;
        this.refundCut = refundCut;
        refundPrice = price * (100 - refundCut) / 100;
        seats = new char[rows * 4];
        for( int i = 0; i < rows * 4 ; i++){
            seats[i] = '*';
        }
        revenue = 0;
    }
    /**
     * Sets the availability of seats in the standard bus based on the provided seat numbers and sold status.
     *
     * @param seats          the array representing the availability of seats
     * @param seatNumberList the list of seat numbers to be set
     * @param isSold         the status indicating whether the seats are sold
     */
    @Override
    public void setSeats(char[] seats ,String[] seatNumberList, boolean isSold) {
        for( String seatNumberStr : seatNumberList){
            int seatNumber = Integer.parseInt(seatNumberStr);
            if(seats[seatNumber - 1] == '*' && isSold){
                seats[seatNumber -1] = 'X';
            }else if(seats[seatNumber - 1] == 'X' && !isSold){
                seats[seatNumber -1] = '*';
            }
        }

    }
    /**
     * Writes the availability of seats in the standard bus as a string representation.
     *
     * @return a string representing the availability of seats in the standard bus
     */
    public String writeSeats(){
        String seat = "";
        for (int i = 0; i < this.getSeats().length; i++) {
            if((i + 1) % 4 == 0){
                seat += (this.getSeats()[i]);
            }else{
                seat += (this.getSeats()[i] + " ");
            }

            if ((i + 1) % 4 == 2) {
                seat += "| ";
            }
            if ((i + 1) % 4 == 0 && i != this.getSeats().length -1) {
                seat += "\n";
            }
        }
        return seat;
    }
    @Override
    public char[] getSeats() {
        return seats;
    }

    /**
     * Retrieves the refund price for the specified seat number in the standard bus.
     *
     * @param seatNumber the seat number for which refund price is calculated
     * @return the refund price for the specified seat number
     */
    @Override
    public float getRefundPrice(int seatNumber) {
        float price = 0.F ;
        if(refundCut != 0.F){
            price = super.getPrice(seatNumber) * (100 - refundCut) / 100 ;
        }else{
            price = super.getPrice(seatNumber);
        }
        return price;
    }
    public float getRevenue() {
        return revenue;
    }
    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    @Override
    public float calculateTotalPrice() {
        float price = 0;
        for (char seat : this.getSeats()){
            if(seat == 'X'){
                price += this.price;
            }
        }
        return price;
    }
}
