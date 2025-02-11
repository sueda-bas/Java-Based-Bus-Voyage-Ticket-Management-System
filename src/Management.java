import java.util.ArrayList;
/**
 * The Management class provides methods for managing voyages based on commands.
 */
public class Management {
    /**
     * Executes the given command to manage voyages.
     *
     * @param command    the command to execute
     * @param voyageList the list of voyages to manage
     * @param isLastLine indicates if the command is the last line in the input file
     * @param args       additional arguments for file output
     */
    public static void management(String command, ArrayList<Voyage> voyageList,boolean isLastLine, String[] args){
        String[] parts = command.split("\t");
        String operation = parts[0];

        switch(operation){
            case "INIT_VOYAGE" :
                try {
                    String busType = parts[1];
                    int voyageID = Integer.parseInt(parts[2]);
                    String from = parts[3];
                    String destination = parts[4];
                    int rows = Integer.parseInt(parts[5]);
                    float price = Float.parseFloat(parts[6]);
                    float refundCut = 0.F;
                    float premiumFee = 0.F;
                    String formattedPrice = String.format("%.2f", price);

                    //check if the given values are positive or negative
                    if (!isPositive(voyageID, rows, price, args)) {
                        break;
                    }
                    if (busType.trim().equals("Minibus")) {
                        if (parts.length > 7) {
                            throw new IllegalArgumentException();
                        }
                    }
                    if (!busType.trim().equals("Minibus")) {
                        if (busType.trim().equals("Standard")) {
                            if (parts.length > 8) {
                                throw new IllegalArgumentException();
                            }
                        }

                        refundCut = Float.parseFloat(parts[7]);
                        if (refundCut < 0 || refundCut > 100) {
                            FileOutput.writeToFile(args[1], "ERROR: " + (int) refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", true, true);
                            break;
                        }
                        if (!busType.trim().equals("Standard")) {
                            if (parts.length > 9) {
                                throw new IllegalArgumentException();
                            }
                            premiumFee = Float.parseFloat(parts[8]);
                            if (premiumFee < 0) {
                                FileOutput.writeToFile(args[1], "ERROR: " + (int) premiumFee + " is not a non-negative integer, premium fee must be a non-negative integer!", true, true);
                                break;
                            }
                        }
                    }
                    boolean isValidVoyage = true;
                    for (Voyage voyage : voyageList) {
                        if (voyage.getBusId() == voyageID) {
                            FileOutput.writeToFile(args[1], "ERROR: There is already a voyage with ID of " + voyageID + "!", true, true);
                            isValidVoyage = false;
                        }
                    }
                    if (!isValidVoyage) {
                        break;
                    }
                    Operations.initVoyage(voyageList, busType, voyageID, from, destination, rows, price, refundCut, premiumFee);
                    if (busType.trim().equals("Minibus")) {
                        int regularSeat = rows * 2;
                        FileOutput.writeToFile(args[1], "Voyage " + voyageID + " was initialized as a minibus (2) voyage from "
                                + from + " to " + destination + " with " + formattedPrice + " TL priced " + regularSeat
                                + " regular seats. Note that minibus tickets are not refundable.", true, true);
                    } else if (busType.trim().equals("Standard")) {
                        int regularSeat = rows * 4;
                        FileOutput.writeToFile(args[1], "Voyage " + voyageID + " was initialized as a standard (2+2) voyage from "
                                + from + " to " + destination + " with " + formattedPrice + " TL priced " + regularSeat
                                + " regular seats. Note that refunds will be " + (int) refundCut + "% less than the paid amount.", true, true);
                    } else if (busType.trim().equals("Premium")) {
                        int regularSeat = rows * 2;
                        int premiumSeat = rows;
                        float premiumPrice = price * (100 + premiumFee) / 100;
                        String formattedPremiumPrice = String.format("%.2f", premiumPrice);

                        FileOutput.writeToFile(args[1], "Voyage " + voyageID + " was initialized as a premium (1+2) voyage from "
                                + from + " to " + destination + " with " + formattedPrice + " TL priced " + regularSeat
                                + " regular seats and " + formattedPremiumPrice + " TL priced " + premiumSeat + " premium seats. Note that refunds will be " + (int) refundCut + "% less than the paid amount.", true, true);
                    }
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                    FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!", true, true);
                }
                break;

            case "SELL_TICKET":
                try {
                    if(parts.length > 3){
                        throw new IllegalArgumentException();
                    }
                    int sellVoyageID = Integer.parseInt(parts[1]);
                    boolean isValidVoyage = false;
                    String[] soldTicketNumbers = parts[2].split("_");
                    for(Voyage voyage : voyageList){
                        if(voyage.getBusId() == sellVoyageID){
                            isValidVoyage = true;
                        }
                    }if(!isValidVoyage){
                        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + sellVoyageID + "!", true, true);
                        break;
                    }
                    Operations.sellTicket(voyageList, sellVoyageID, soldTicketNumbers, args);
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                    FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"SELL_TICKET\" command!", true, true);
                }
                break;

            case "REFUND_TICKET":
                try {
                    if(parts.length > 3){
                        throw new IllegalArgumentException();
                    }
                    int refundVoyageID = Integer.parseInt(parts[1]);
                    if(refundVoyageID < 0){
                        FileOutput.writeToFile(args[1], "ERROR: " + refundVoyageID + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
                        break;
                    }
                    boolean isValidVoyage = false;
                    String[] refundedSeatNumbers = parts[2].split("_");
                    for(Voyage voyage : voyageList){
                        if(voyage.getBusId() == refundVoyageID){
                            isValidVoyage = true;
                        }
                    }if(!isValidVoyage){
                        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + refundVoyageID + "!", true, true);
                        break;
                    }
                    Operations.refundTicket(voyageList, refundVoyageID, refundedSeatNumbers, args);
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                        FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"REFUND_TICKET\" command!", true, true);
                    }
                break;

            case "CANCEL_VOYAGE":
                try {
                    if(parts.length > 2){
                        throw new IllegalArgumentException();
                    }
                    boolean isValidVoyage = false;
                    int cancelledVoyageID = Integer.parseInt(parts[1]);
                    if(cancelledVoyageID < 0){
                        FileOutput.writeToFile(args[1], "ERROR: " + cancelledVoyageID + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
                        break;
                    }
                    for(Voyage voyage : voyageList){
                        if(voyage.getBusId() == cancelledVoyageID){
                            isValidVoyage = true;
                        }
                    }if(!isValidVoyage){
                        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + cancelledVoyageID + "!", true, true);
                        break;
                    }
                    Operations.cancelVoyage(voyageList, cancelledVoyageID, args);
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                    FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!", true, true);
                }
                break;

            case "PRINT_VOYAGE":
                try {
                    if(parts.length > 2){
                        throw new IllegalArgumentException();
                    }
                    int printedVoyageID = Integer.parseInt(parts[1]);
                    if(printedVoyageID < 0){
                        FileOutput.writeToFile(args[1], "ERROR: " + printedVoyageID + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
                        break;
                    }
                    boolean isValidVoyage = false;
                    for(Voyage voyage : voyageList){
                        if(voyage.getBusId() == printedVoyageID){
                            isValidVoyage = true;
                        }
                    }if(!isValidVoyage){
                        FileOutput.writeToFile(args[1], "ERROR: There is no voyage with ID of " + printedVoyageID + "!", true, true);
                        break;
                    }
                    Operations.printVoyage(voyageList, printedVoyageID, args);
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                    FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!", true, true);
                }
                break;
            case "Z_REPORT":
                try {
                    if(parts.length > 1){
                        throw new IllegalArgumentException();
                    }
                    Operations.zReport(voyageList,isLastLine, args);
                }
                catch(ArrayIndexOutOfBoundsException | IllegalArgumentException illegalArgumentException){
                        FileOutput.writeToFile(args[1], "ERROR: Erroneous usage of \"Z_REPORT\" command!", true, true);
                    }
                break;
            default:
                FileOutput.writeToFile(args[1], "ERROR: There is no command namely " + operation + "!", true, true);


}
}

    /**
     * Checks if the given values are positive.
     *
     * @param voyageID the ID of the voyage
     * @param rows     the number of seat rows
     * @param price    the price of the voyage
     * @param args     additional arguments for file output
     * @return true if all values are positive, otherwise false
     */
    public static boolean isPositive (int voyageID, int rows, float price,String[] args ){
        if(voyageID < 0){
            FileOutput.writeToFile(args[1],"ERROR: " + voyageID + " is not a positive integer, ID of a voyage must be a positive integer!", true, true);
            return false;
        }
        if(rows < 0){
            FileOutput.writeToFile(args[1],"ERROR: " + rows + " is not a positive integer, number of seat rows of a voyage must be a positive integer!", true, true);
            return false;
        }
        if(price < 0){
            FileOutput.writeToFile(args[1], "ERROR: " + (int) price + " is not a positive number, price must be a positive number!", true, true);
            return false;
        }
        return true;
    }
}