// **********************************************************************************************
// Program Name: Occupancy Calculator
// Name: Alba Minxha, Cheyenne Deibert, Hannah Stepp, Niya Burney
// Date: 7/11/2020
// Program Description:This program creates an object representing a room with a certain seating
// layout.
// **********************************************************************************************

public class RoomBase {

    private double width; //width of the room
    private double length; //length of the song
    private int numRows; //number of rows of seating
    private int numChairs; //number of chairs per row

    //default constructor
    RoomBase() {
        this.width = 0;
        this.length = 0;
    }

    //overloaded constructor
    RoomBase(double width, double length) {
        setWidth(width);
        setLength(length);

    }

    // mutator methods, set length and width

    /**
     * setWidth-
     * This method sets the width of the room
     *precondition: none
     * @param width, double, room width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * setLength-
     * This method sets the length of the room
     * precondition: none
     *
     * @param length, double, length of room
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * setChairRows-
     * This method sets the number of rows of chairs
     * precondition: none
     *
     * @param rows, double, length of room
     */
    public void setChairRows(int rows) {
        this.numRows = rows;
    }

    /**
     * setChairNum-
     * This method sets the number of chairs in each row
     * precondition: none
     *
     * @param num, double, number of chairs
     */
    public void setNumChairs(int num) {
        this.numChairs = num;
    }




    // accessor methods, get values for length and width

    /**
     * getWidth-
     * This method gets the room width
     * precondition:none
     *
     * @return double, width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * getLength-
     * This method gets the room length
     * precondition:none
     *
     * @return double, length
     */
    public double getLength() {
        return this.length;
    }

    /**
     * getChairRows-
     * This method gets the number of rows of chairs
     * precondition:none
     *
     * @return int, number of rows
     */
    public int getChairRows() {
        return this.numRows;
    }

    /**
     * getNumChairs-
     * This method gets the number of chairs per row
     * precondition:none
     *
     * @return int, number of chairs
     */
    public int getNumChairs() {
        return this.numChairs;
    }




    /**
     * getArea-
     * This method return the area of the room
     * precondition:none
     *
     * @return double, area
     */
    public double getArea(){

        double area = length * width;

        return area;

    }

}
