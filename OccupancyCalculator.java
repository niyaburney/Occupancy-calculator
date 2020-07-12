// **********************************************************************************************
// Program Name: Occupancy Calculator
// Name: Alba Minxha, Cheyenne Deibert, Niya Burney, Hannah Stepp
// Date: 7/11/2020
// Program Description: This program will calculate the number of students that can fit in a
// classroom while maintaining social distancing guidelines and provides seating diagrams.
// **********************************************************************************************
import java.util.Scanner;

public class OccupancyCalculator {
    //declare constants
    public static final double SAFE_DISTANCE = 6.0; //distance of separation in ft.
    public static final double TEACHER_SPACE = 48.0; //space in classroom reserved for teacher

    public static void main(String[] args) {

        RoomBase room = new RoomBase();

        //Initialize input scanner
        Scanner scnr = new Scanner(System.in);

        //declare array for dimensions
        double[] dimensions = new double[2];

        printStatement();

        //call method for valid dimensions and set length and width
        getDimensions(dimensions, scnr);
        System.out.printf("Classroom Dimension: %.2f x %.2f\n", dimensions[0], dimensions[1]);

        room.setLength(dimensions[0]);
        room.setWidth(dimensions[1]);

        //determine if the classroom has arrangeable seating
        boolean isArrangeable = isArrangeable(scnr);

        //if the classroom does not have arrangeable seating prompt for seating arrangement
        if (!isArrangeable) {
            int[] chairDimensions = new int[2];
            getChairNums(chairDimensions, scnr);
            room.setChairRows(chairDimensions[0]);
            room.setNumChairs(chairDimensions[1]);
        }

        //determine classroom capacity
        int people = numPeople(room, isArrangeable);
        printNumPeople(people);
        if(isArrangeable){
            flexibleDiagram(room, people);
        }

        if(!isArrangeable) {
            seatingDiagram(room);
        }

    }

    public static void printStatement(){
        System.out.println("This program will calculate the number of students that can fit in a" +
                " classroom while maintaining social distancing guidelines. \n Please follow the" +
                " prompts to enter data about your classroom.");
    }


    /**
     * getDimensions- this method prompts the user to enter dimensions.
     * @param dimensions
     * @param scnr
     */

    public static double[] getDimensions(double[] dimensions, Scanner scnr) {
        //whether the given measurement is in feet or inches
        boolean isfeet;
        isfeet = getUnitMeasure(scnr);


        //prompt for length
        System.out.println("Enter the length of the classroom. The length must be greater than 10 ft..");
        double length = getValidDimension(scnr, isfeet);
        dimensions[0] = length;


        //prompt for width
        System.out.println("Enter the width of the classroom. The width must be greater than 10 ft.");
        double width = getValidDimension(scnr, isfeet);
        dimensions[1] = width;

        return dimensions;

    }

    /**
     * getValidDimensions- This validates the data entered by the user
     * @param scnr
     * @return
     */

    public static double getValidDimension(Scanner scnr, boolean isfeet) {

        String userString = scnr.nextLine(); //console scanner
        Scanner strScanner = new Scanner(userString); //secondary scanner

        //continue to prompt if input empty/blank
        while (userString.trim().length() == 0 || !(strScanner.hasNextDouble())) {
            System.out.println("Input must be a valid dimension");
            userString = scnr.nextLine();
            strScanner = new Scanner(userString);
        }

        //initialize variable
        double dimension = strScanner.nextDouble();

        //determine if input is within range
        while (dimension <= 10) {
            while (userString.trim().length() == 0 || !(strScanner.hasNextDouble())) {
                System.out.println("Input must be a valid dimension");
                userString = scnr.nextLine();
                strScanner = new Scanner(userString);
            }
            dimension = strScanner.nextDouble();
        }

        //convert from inches to feet
        if (!isfeet) {
            dimension = dimension / 12.0;
        }

        return dimension;
    }

    /**
     * getUnitMeasure - this method prompts the user for the unit measurement for the room, ft or in
     * @param scnr
     * @return - whether or not the input dimension are in ft
     */

    public static boolean getUnitMeasure(Scanner scnr) {
        //prompt for unit of measurement
        System.out.println("Enter Unit of Measure (ft or in)");

        String unitMeasure = scnr.nextLine();

        //determine if the input is non empty and valid
        while (unitMeasure.trim().length() == 0 || !(unitMeasure.equalsIgnoreCase("ft") || unitMeasure.equalsIgnoreCase("in"))) {
            System.out.println("Input must be valid unit of measure in Feet or Inches");
            unitMeasure = scnr.nextLine();
        }
        return unitMeasure.equalsIgnoreCase("ft");
    }

    /**
     * isArrangeable - this prompts the user whether or not the classroom seats are arrangeable
     * @param scnr
     * @return - whether or not the seats can be arranged
     */
    public static boolean isArrangeable(Scanner scnr){
        //prompt user for whether or not the seating can be rearranged
        System.out.println("Can the classroom be arranged? (y or n)");

        String response = scnr.nextLine();

        //continue to prompt until input is non empty and valid
        while (response.trim().length() == 0 || !(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n"))){
            System.out.println("Input (y) or (n)");
            response = scnr.nextLine();
        }
        return response.equalsIgnoreCase("y");
    }

    /**
     * getChairNums - this method prompts the user by calling the getNumChairs method for the number
     * of rows in the classroom and the number of chairs per row and places this information into
     * an array.
     * @param chairDimensions
     * @param scnr
     * @return - the array with the informations of the layout of a fixed classroom
     */
    public static void getChairNums(int[] chairDimensions, Scanner scnr) {
        //prompt and obtain number of rows
        System.out.println("How many rows are there in the classroom?");
        chairDimensions[0] = getNumChairs(scnr);

        //prompt and obtain number of chairs per row
        System.out.println("How many chairs per row are there?");
        chairDimensions[1] = getNumChairs(scnr);
    }

    /**
     * getNumChairs - prompts the user for a valid number for the number of rows and chairs in
     * each row.
     * @param scnr
     * @return
     */
    public static int getNumChairs(Scanner scnr){
        String userString = scnr.nextLine(); //console scanner
        Scanner strScanner = new Scanner(userString); //secondary scanner

        //continue to prompt if input empty/blank
        while (userString.trim().length() == 0 || !(strScanner.hasNextInt())) {
            System.out.println("Input must be a valid number");
            userString = scnr.nextLine();
            strScanner = new Scanner(userString);
        }

        //initialize variable
        int chair = strScanner.nextInt();

        //continue to prompt until input is in a valid range
        while (chair <= 0) {
            while (userString.trim().length() == 0 || !(strScanner.hasNextInt())) {
                System.out.println("Input must be a valid number");
                userString = scnr.nextLine();
                strScanner = new Scanner(userString);
            }
            chair = strScanner.nextInt();
        }
        return chair;
    }


    /**
     * numPeople - This method calculates how many people can safely fit in a room of a certain area
     * @param room
     * @return
     */

    public static int numPeople(RoomBase room, boolean isArrangeable) {
        //room area
        double area = room.getArea();
        int numPeople = 0;

        //use different calculations depending on whether the classroom seats can be arranged
        if(isArrangeable) {
            double netArea = (area - TEACHER_SPACE);
            numPeople = (int) (netArea / ((Math.pow(SAFE_DISTANCE, 2.0)) * Math.PI));

        }
        else if(!isArrangeable) {
            int numChairs = room.getNumChairs();
            int numRows = room.getChairRows();
            int chairArea = numChairs * numRows;

            if(chairArea % 4 == 0){
                numPeople = chairArea / 4;

            }
            else{
                numPeople = chairArea / 3;
            }
            if (numPeople == 0){
                ++numPeople;
            }

            return numPeople;
        }

        return numPeople;
    }

    /**
     * printNumPeople- This method prints how many people can fit in the room.
     * @param numPeople
     */

    public static void printNumPeople(int numPeople) {
        System.out.println("The room can safely fit " + numPeople + " people.");

    }

    /**
     * flexibleDiagram - this method creates a possible seating diagram for a room with flexible
     * seating
     * @param room
     * @param numPeople
     */

    public static void flexibleDiagram(RoomBase room, int numPeople){
        //set room width and length
        double width = room.getWidth();
        double length = room.getLength();

        for (int i = 1; i <= width; ++i) {
            System.out.print("[ ]");
        }
        System.out.println();
        for(int j = 1; j <= length -2; ++j) {
            System.out.print("[ ]");
            for (int k = 1; k <= width - 2; ++k) {
                if(j%6==0 & k%6==0 && k != width-2){
                    System.out.print(" * ");
                }
                else
                    System.out.print("   ");
            }
            System.out.print("[ ]\n");
        }

        for (int i = 1; i <= width; ++i) {
            System.out.print("[ ]");
        }

        System.out.println("\nHere is a diagram of the classroom for planning purposes. [] = 1 " +
                "ft," +
                "  * = 1 student" +
                ". ");

    }


    /**
     * seatingDiagram - This method prints a seating diagram for a room with fixed seating.
     * @param room, a room object
     */

    public static void seatingDiagram(RoomBase room) {

        int columns = room.getNumChairs();
        int rows = room.getChairRows();
        System.out.println("\nHere is a diagram of socially distanced seating for a classroom with "
                + rows + " rows and " + columns + " seats per row.\n O = an unoccupied seat and X" +
                " =" +
                " an occupied seat. \n");

        int remainder = columns % 4;
        int rowRemainder = rows % 2;

        if(rows == 1){ //prints diagram for only one row of chairs

            if(columns >= 4) {
                for (int i = 1; i <= columns / 4; ++i) {
                    System.out.print("X O O O ");
                }

//adds the correct number of seats if the number of chairs is not a multiple of four

                if (remainder == 1) {
                    System.out.print("X");
                }
                if (remainder == 2) {
                    System.out.print("X O");
                }

                if (remainder == 3) {
                    System.out.print("X O O");
                }
            }
            else if (columns == 3){
                System.out.println("X O O");
            }
            else if (columns == 2){
                System.out.println("X O");
            }
            else if (columns == 1){
                System.out.println("X");
            }
        }// end if statement

        if(rows % 2 == 0){ //prints diagram if the number of rows is even


            printTwoRows(room);
        }

        if((rows % 2 != 0) && (rows != 1)){ //prints diagram if number of rows is odd and >1

            --rows; //decreases the number of rows by one

            printTwoRows(room);
            if (columns >=4) { //prints the final row

                for (int i = 1; i <= columns / 4; ++i) {
                    System.out.print("X O O O ");
                }
                if (remainder == 1) {
                    System.out.print("X");
                }
                if (remainder == 2) {
                    System.out.print("X O");
                }

                if (remainder == 3) {
                    System.out.print("X O O");
                }
            }
            else if (columns == 3){
                System.out.print("X O O");
            }
            else if (columns == 2){
                System.out.print("X O");
            }
            else if (columns == 1){
                System.out.print("X");
            }

        } // end if statement


    }

    /**
     * printTwoRows- This method prints two rows of a seating diagram for fixeed seating
     * @param room
     */

    public static void printTwoRows(RoomBase room) {
        int columns = room.getNumChairs();
        int rows = room.getChairRows();

        int remainder = columns % 4;
        if (columns >= 4) {
            for (int j = 1; j <= rows / 2; ++j) { //prints the correct number of rows


                for (int i = 1; i <= columns / 4; ++i) {
                    System.out.print("X O O O ");
                }
//adds the correct number of seats if the number of chairs is not a multiple of four

                if (remainder == 1) {
                    System.out.print("X");
                }
                if (remainder == 2) {
                    System.out.print("X O");
                }

                if (remainder == 3) {
                    System.out.print("X O O");
                }

                System.out.println();
                for (int i = 1; i <= columns / 4; ++i) {
                    System.out.print("O O X O ");

                }
                if (remainder == 1) {
                    System.out.print("O");
                }
                if (remainder == 2) {
                    System.out.print("O O");
                }

                if (remainder == 3) {
                    System.out.print("O O X");
                }
                System.out.println();

            }
        } // end if statement
        else if (columns == 3) {

//prints a second row of shifted seating


            for (int j = 1; j <= rows / 2; ++j) {

                System.out.print("X O O ");
                System.out.println();

                System.out.print("O O X ");
                System.out.println();

            }
        } // end else if statement
        else if (columns == 2) {

            for (int j = 1; j <= rows / 2; ++j) {

                System.out.print("X O ");


                System.out.println();

                System.out.print("O Oft ");
                System.out.println();
            }
        } // end else if statement
        else if (columns == 2) {

            for (int j = 1; j <= rows / 2; ++j) {

                System.out.print("X");
                System.out.println();
                System.out.print("O");
                System.out.println();
            }
        }// end else if statement
    }


}

