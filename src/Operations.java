import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * The Operations class provides static methods for various operations related to voyages.
 * These operations include initializing voyages, selling tickets, refunding tickets, canceling voyages,
 * printing voyage details, and generating Z reports.
 */
public class Operations {

    /**
     * Initializes a new voyage based on the provided parameters and adds it to the voyage list.
     *
     * @param voyageList  the list of voyages
     * @param busType     the type of the bus for the voyage
     * @param busId       the ID of the bus
     * @param from        the starting point of the voyage
     * @param destination the destination of the voyage
     * @param rows        the number of rows in the bus
     * @param price       the price of each seat in the voyage
     * @param refundCut   the percentage cut for refund
     * @param premiumFee  the additional fee for premium seats
     * @return the initialized voyage
     */
    public static Voyage initVoyage(ArrayList<Voyage> voyageList, String busType, int busId, String from, String destination, int rows, float price, float refundCut, float premiumFee) {
        if (busType.trim().equals("Minibus")) {
            Voyage voyage = new Minibus(busId, from , destination , rows, price);
            voyageList.add(voyage);
            return voyage;
        } else if (busType.trim().equals("Standard")) {
            Voyage voyage = new Standard(busId, from, destination , rows, price, refundCut);
            voyageList.add(voyage);
            return voyage;
        } else if (busType.trim().equals("Premium")) {
            Voyage voyage = new Premium(busId, from, destination , rows, price, refundCut, premiumFee);
            voyageList.add(voyage);
            return voyage;
        }
        Voyage voyage = null;
        return voyage;
    }

    /**
     * Sells tickets for the specified voyage and updates the revenue accordingly.
     *
     * @param voyageList       the list of voyages
     * @param busId            the ID of the bus
     * @param ticketNumberList the list of seat numbers for tickets
     * @param args             additional arguments for file output
     */
    public static void sellTicket(ArrayList<Voyage> voyageList, int busId, String[] ticketNumberList, String[] args) {
        boolean isSold = true;
        float price = 0;
        if (isAvailableSeat(voyageList, busId, ticketNumberList, isSold, args)) {
            for (Voyage bus : voyageList) {
                if (bus.getBusId() == busId) {
                    bus.setSeats(bus.getSeats(), ticketNumberList, isSold);
                    for (String seatNumberStr : ticketNumberList){
                        int seatNumber = Integer.parseInt(seatNumberStr);
                        price += bus.getPrice(seatNumber);
                    }
                    String ticketNumbers = "";
                    for (int i = 0; i < ticketNumberList.length; i++) {
                        ticketNumbers += ticketNumberList[i];
                        if (i != ticketNumberList.length - 1) {
                            ticketNumbers += "-";
                        }
                    }
                    String formattedPrice = String.format("%.2f", price);
                    FileOutput.writeToFile(args[1],"Seat " +  ticketNumbers + " of the Voyage " + bus.getBusId() + " from " + bus.getFrom() + " to " + bus.getDestination() + " was successfully sold for " + formattedPrice + " TL."  ,true,true);
                    bus.setRevenue(bus.getRevenue() + price);
                }
            }
        }
    }

    /**
     * Refunds tickets for the specified voyage and updates the revenue accordingly.
     *
     * @param voyageList       the list of voyages
     * @param busId            the ID of the bus
     * @param ticketNumberList the list of seat numbers for tickets
     * @param args             additional arguments for file output
     */
    public static void refundTicket(ArrayList<Voyage> voyageList, int busId, String[] ticketNumberList, String[] args) {
        boolean isSold = false;
        float price = 0.F;

        if (isAvailableSeat(voyageList, busId, ticketNumberList, isSold, args)){
            for (Voyage bus : voyageList) {
                if (bus.getBusId() == busId) {
                    bus.setSeats(bus.getSeats(), ticketNumberList, isSold);

                    for(String seatNumberStr : ticketNumberList){
                        int seatNumber = Integer.parseInt(seatNumberStr);
                        price += bus.getRefundPrice(seatNumber);
                    }
                    String ticketNumbers = "";
                    for (int i = 0; i < ticketNumberList.length; i++) {
                        ticketNumbers += ticketNumberList[i];
                        if (i != ticketNumberList.length - 1) {
                            ticketNumbers += "-";
                        }
                    }
                    if (price != 0){
                        String formattedPrice = String.format("%.2f", price);

                        FileOutput.writeToFile(args[1],"Seat " +  ticketNumbers + " of the Voyage " + bus.getBusId() + " from " + bus.getFrom() + " to " + bus.getDestination() + " was successfully refunded for " + formattedPrice + " TL."  ,true,true);
                    }else{
                        FileOutput.writeToFile(args[1],"ERROR: Minibus tickets are not refundable!"  ,true,true);
                    }

                    bus.setRevenue(bus.getRevenue() - price);
                }
            }
        }
    }

    /**
     * Checks if the specified seats are available for selling or refunding.
     *
     * @param voyageList       the list of voyages
     * @param busId            the ID of the bus
     * @param ticketNumberList the list of seat numbers for tickets
     * @param isSold           the status indicating whether the seats are sold or not
     * @param args             additional arguments for file output
     * @return true if the seats are available, false otherwise
     */
    public static boolean isAvailableSeat(ArrayList<Voyage> voyageList, int busId, String[] ticketNumberList, boolean isSold, String[] args){
        for(Voyage bus : voyageList){
            if(bus.getBusId() == busId){
                for( String seatNumberStr : ticketNumberList){
                    int seatNumber = Integer.parseInt(seatNumberStr);
                    if(seatNumber < 0){
                        FileOutput.writeToFile(args[1],"ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!"  ,true,true);
                        return false;
                    }else if(seatNumber > bus.getSeats().length) {
                        FileOutput.writeToFile(args[1], "ERROR: There is no such a seat!", true, true);
                        return false;
                    }
                }
                for(String seatNumberStr : ticketNumberList){
                    int seatNumber = Integer.parseInt(seatNumberStr);
                    if(isSold){
                        if(bus.getSeats()[seatNumber - 1] == 'X'){
                            FileOutput.writeToFile(args[1],"ERROR: One or more seats already sold!"  ,true,true);
                            return false;
                        }
                    }else if(!isSold){
                        if (bus.getSeats()[seatNumber - 1] == '*') {
                            FileOutput.writeToFile(args[1],"ERROR: One or more seats are already empty!"  ,true,true);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Cancels the voyage with the specified bus ID from the voyage list.
     *
     * @param voyageList the list of voyages
     * @param busId      the ID of the bus for the voyage to be cancelled
     * @param args       additional arguments for file output
     */
    public static void cancelVoyage(ArrayList<Voyage> voyageList, int busId, String[] args){
        if(isAvailableVoyage(voyageList, busId)){
            for(int i = 0; i < voyageList.size(); i++){
                Voyage bus = voyageList.get(i);
                if(bus.getBusId() == busId){
                    float price = bus.calculateTotalPrice();
                    bus.setRevenue(bus.getRevenue() - price);
                    FileOutput.writeToFile(args[1], "Voyage " + bus.getBusId() + " was successfully cancelled!"
                            +"\nVoyage details can be found below:" ,true,true);
                            printVoyage(voyageList, busId, args);
                    voyageList.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Checks if a voyage with the specified bus ID exists in the voyage list.
     *
     * @param voyageList the list of voyages
     * @param busId      the ID of the bus to be checked
     * @return true if a voyage with the specified bus ID exists, otherwise false
     */
    public static boolean isAvailableVoyage(ArrayList<Voyage> voyageList ,int busId){
        for (Voyage bus : voyageList){
            if(bus.getBusId() == busId){
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the details of the voyage with the specified bus ID.
     *
     * @param voyageList the list of voyages
     * @param busId      the ID of the bus for which details are to be printed
     * @param args       additional arguments for file output
     */
    public static void printVoyage(ArrayList<Voyage> voyageList, int busId, String[] args){
        for (Voyage voyage : voyageList){
            if(voyage.getBusId() == busId){
                String seats = voyage.writeSeats();
                String formattedRevenue = String.format("%.2f", voyage.getRevenue());
                FileOutput.writeToFile(args[1], "Voyage " + voyage.getBusId() + "\n"
                        + voyage.getFrom() + "-" + voyage.getDestination() + "\n"
                        + seats + "\n" +"Revenue: " + formattedRevenue  ,true,true);
            }
        }
    }

    /**
     * Generates the Z report for all voyages in the voyage list.
     *
     * @param voyageList  the list of voyages
     * @param isLastLine  true if this is the last line of the report, otherwise false
     * @param args        additional arguments for file output
     */
    public static void zReport(ArrayList<Voyage> voyageList, boolean isLastLine, String[] args){
        FileOutput.writeToFile(args[1], "Z Report:" ,true,true);
        if(voyageList.size() == 0){
            FileOutput.writeToFile(args[1], "----------------" ,true,true);
            FileOutput.writeToFile(args[1], "No Voyages Available!" ,true,true);
            if(isLastLine){
                FileOutput.writeToFile(args[1], "----------------" ,true,false);
            }else{
                FileOutput.writeToFile(args[1], "----------------" ,true,true);
            }
        }else{
            ArrayList<Voyage> sortedList = new ArrayList<>(voyageList);
            Collections.sort(sortedList, new Comparator<Voyage>() {
                @Override
                public int compare(Voyage v1, Voyage v2) {
                    return Integer.compare(v1.getBusId(), v2.getBusId());
                }
            });
            for( Voyage voyage : sortedList) {
                FileOutput.writeToFile(args[1], "----------------", true, true);
                printVoyage(voyageList, voyage.getBusId(), args);
            }

                if(isLastLine){
                    FileOutput.writeToFile(args[1], "----------------" ,true,false);
                }else{
                    FileOutput.writeToFile(args[1], "----------------" ,true,true);
                }

        }

    }
}
