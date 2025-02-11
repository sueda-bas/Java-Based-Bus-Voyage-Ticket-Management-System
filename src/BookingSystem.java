import java.util.ArrayList;
/**
 * The Main class represents the entry point of the application.
 * It reads commands from an input file, processes them, and writes the output to an output file.
 */
public class BookingSystem {
    //counter for checking if the current line is the last line of the input file
    static int counter = 0;

    /**
     * The main method of the application.
     * Reads commands from the input file, processes them, and writes the output to the output file.
     *
     * @param args the command-line arguments, where args[0] is the path to the input file and args[1] is the path to the output file
     */
    public static void main(String[] args) {
        try{
            if(args.length != 2){
               throw new ArrayIndexOutOfBoundsException();
            }
        String[] inputFile = FileInput.readFile(args[0], true, true);

        //arraylist to access available voyages in the system
        ArrayList<Voyage> voyageList = new ArrayList<Voyage>();
        FileOutput.writeToFile(args[1],"",false,false);
        boolean isLastLine = false;

        if(inputFile.length == 0){
            String newCommand = "Z_REPORT";
            Management.management(newCommand, voyageList, isLastLine, args);
        }

        for (String line : inputFile) {
            counter += 1;
            if (counter == inputFile.length) {
                isLastLine = true;}
            FileOutput.writeToFile(args[1], "COMMAND: " + line, true, true);
            Management.management(line, voyageList, isLastLine, args);
            if (counter == inputFile.length) {
                isLastLine = true;
                if (!line.equals("Z_REPORT")) {
                    String newCommand = "Z_REPORT";
                    Management.management(newCommand, voyageList, isLastLine, args);
                }
            }

        }
        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("ERROR: Arguments must consist of two arguments, the first one must be input.txt and the second one must be output.txt!");
            }



        }
    }
