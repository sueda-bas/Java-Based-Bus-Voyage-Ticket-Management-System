/**
 * The Premium class represents a type of voyage using a premium bus.
 * It extends the Voyage class and implements methods specific to premium voyages.
 */
public class Premium extends Voyage {

        private float price;
        private float  refundCut;
        private float refundPrice;
        private float premiumFee;
        private char[] seats ;
        private int soldSeatCounts;
        private float revenue;

        /**
         * Constructs a new Premium object with the specified parameters.
         *
         * @param busId        the ID of the bus
         * @param from         the starting point of the voyage
         * @param destination  the destination of the voyage
         * @param rows         the number of rows in the bus
         * @param price        the price of each seat in the voyage
         * @param refundCut    the percentage cut for refund
         * @param premiumFee   the additional fee for premium seats
         */
        public Premium(int busId, String from, String destination , int rows, float price, float refundCut, float premiumFee) {
            super(busId, from, destination, rows, price);
            this.refundCut = refundCut;
            this.premiumFee = premiumFee;
            refundPrice = price * (100-refundCut)/100;
            seats = new char [rows * 3];
            for( int i = 0; i < rows * 3 ; i++){
                seats[i] = '*';
            }
            revenue = 0;
        }

    /**
     * Sets the availability of seats in the premium bus based on the provided seat numbers and sold status.
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
                    this.setSoldSeatCounts(this.getSoldSeatCounts() + 1);
                }else if(seats[seatNumber - 1] == 'X' && !isSold){
                    seats[seatNumber -1] = '*';

                }
            }
        }
        /**
         * Writes the availability of seats in the premium bus as a string representation.
         *
         * @return a string representing the availability of seats in the premium bus
         */
        public String writeSeats (){
            String seat = "";
            for (int i = 0; i < this.getSeats().length; i++) {
                if((i + 1) % 3 == 0){
                    seat += (this.getSeats()[i]);
                }else{
                    seat += (this.getSeats()[i] + " ");
                }
                if ((i + 1) % 3 == 1) {
                    seat += "| ";
                }
                if ((i + 1) % 3 == 0 && i != this.getSeats().length -1) {
                    seat += "\n";
                }
            }
            return seat;
        }
        @Override
        public int getBusId() {
            return super.getBusId();
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public char[] getSeats() {
            return seats;
        }
        /**
         * Retrieves the price of a seat in the premium bus.
         *
         * @param seatNumber the seat number for which the price is retrieved
         * @return the price of the specified seat
         */
        @Override
        public float getPrice(int seatNumber) {
            float price = 0 ;
            if(premiumFee != 0.F){
                if(seatNumber % 3 == 1){
                    price = super.getPrice(seatNumber) * (100 + premiumFee) / 100 ;
                }else{
                    price = super.getPrice(seatNumber);
                }
            }else{
                price = super.getPrice(seatNumber);
            }
            return price;
        }
        /**
         * Retrieves the refund price for the specified seat number in the premium bus.
         *
         * @param seatNumber the seat number for which refund price is calculated
         * @return the refund price for the specified seat number
         */
        @Override
        public float getRefundPrice(int seatNumber) {
            float price = 0 ;
            if(refundCut != 0.F){
                price = this.getPrice(seatNumber) * (100 - refundCut) / 100 ;
            }else{
                price = this.getPrice(seatNumber) ;
            }
            return price;
        }


        public int getSoldSeatCounts() {
            return soldSeatCounts;
        }

        public void setSoldSeatCounts(int soldSeatCounts) {
            this.soldSeatCounts = soldSeatCounts;
        }
        public float getRevenue() {
        return revenue;
    }
        public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

        @Override
        public float calculateTotalPrice() {
            int i = 1;
            float price = 0;
            for(char seat : this.getSeats()){
                if(seat == 'X'){
                    price += this.getPrice(i);
                    i += 1;
                }
            }
            return price;
    }
}


