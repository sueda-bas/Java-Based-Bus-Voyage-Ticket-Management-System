/**
 * The Minibus class represents a type of voyage using a minibus.
 * It extends the Voyage class and implements methods specific to minibuses.
 */
public class Minibus extends Voyage {

    private float price;
    private char[] seats ;
    private float revenue;
    /**
     * Constructs a new Minibus object with the specified parameters.
     *
     * @param busId       the ID of the bus
     * @param from        the starting point of the voyage
     * @param destination the destination of the voyage
     * @param rows        the number of rows in the bus
     * @param price       the price of each seat in the voyage
     */
    public Minibus(int busId, String from, String destination, int rows, float price) {
        super(busId, from, destination, rows, price);
        this.price = price;
        seats = new char[rows * 2];
        for( int i = 0; i < rows * 2 ; i++){
            seats[i] = '*';
        }
        revenue = 0;
    }
    @Override
    public char[] getSeats() {
        return seats;
    }
    /**
     * Sets the availability of seats in the minibus based on the provided seat numbers and sold status.
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
            }else if(!isSold){
                break;
            }
        }

    }

    /**
     * Writes the availability of seats in the minibus as a string representation.
     *
     * @return a string representing the availability of seats in the minibus
     */
    public String writeSeats () {
        String seat = "";
        for (int i = 0; i < this.getSeats().length; i++) {
            if((i + 1) % 2 == 0){
                seat += (this.getSeats()[i]);
            }else{
                seat += (this.getSeats()[i] + " ");
            }
            if ((i + 1) % 2 == 0 && i != this.getSeats().length -1) {
                seat += "\n";
            }
        }
        return seat;
    }

    @Override
    public float getRefundPrice(int seatNumber){
        return 0.F;
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
        for(char seat : this.getSeats()){
            if(seat == 'X'){
                price += this.price;
            }
        }
        return price;
    }
}

