/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Following imports are controls for the window, part of graphics
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.Color;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;

class BoxDrawer extends JPanel {
    /*************************
     * CLASS TITLE: BoxDrawer
     * CLASS AUTHOR: Varsan Jeyakkumar
     * CLASS PURPOSE:
     To generate boxes from the box class, visually demonstrate boxes using shapes and colours, and visually
     demonstrate three possible sorting routines for the boxes (insertion sort, bubble sort, selection sort)
     using steps and a mouse controlled user interface. Also provides controls for users to reset the program
     or exit the program after each sorting algorithm is completed.
     * CLASS LAST MODIFIED: 2025-06-16
     * Few base graphics sections of the code were provided by Dimos Raptou as template code, such as the timer
     and certain other elements. Reused with authorization and consent.
     ************************/

    // Initially sets the graphics to be drawn to null
    Graphics me = null;

    // Private variables (objects) used for interface control are timer, off screen image, and its graphics
    private Timer timer = new Timer();
    private Image offScreenImage = null;
    private Graphics offScreenGraphics = null;

    // These arrays are of the object itself, and also stores the unsorted value
    Box[] toSort = new Box[10];

    // These booleans control which sorting algorithm is used, and is selected using keyboard controls
    boolean insertionSort = false, selectionSort = false, bubbleSort = false;

    // This boolean establishes a control such that only one sorting algorithm can be generated
    boolean isSortSelected = false;

    // This boolean identifies whether a click should sort, true when the values are sorted
    boolean isSorted = false;

    // This boolean determines if the mouse is clicked, to allow a sorting stage to take place
    boolean mouseClicked = false;

    // This integer is used during the sorting algorithm to keep track of steps
    int currentSort = 0;

    // This class enables mouse inputs to be processed by the application
    MouseListener mouselistener = new MyMouseListener();

    BoxDrawer(){
        // Constructor creates the graphics, mouse controls, and the box drawing code
        timer.schedule(new MyTimer(), 0, 10); //1 ms delay set on timer
        setFocusable(true);
        MouseListener mouselistener = new MyMouseListener();
        addMouseListener(mouselistener);
    }

    /*****************************
     * MyMouseListener
     * Does: Container for mouse actions
     */
    public class MyMouseListener implements MouseListener {
        public void mouseClicked (MouseEvent mouseEvent) {
            if (!isSortSelected){
                if ((mouseEvent.getY()>=700)&&(mouseEvent.getY()<=750)) // Y Values in Range of Buttons
                {
                    if ((mouseEvent.getX()>=25)&&(mouseEvent.getX()<=205)){ // X Value in Range of Button
                        // Activates sorting routine and establishes sort to have been selected
                        isSortSelected = true;
                        insertionSort = true;
                    }
                    else if ((mouseEvent.getX()>=225)&&(mouseEvent.getX()<=405)){ // X Value in Range of Button
                        // Activates sorting routine and establishes sort to have been selected
                        isSortSelected = true;
                        bubbleSort = true;
                    }
                    else if ((mouseEvent.getX()>=425)&&(mouseEvent.getX()<=605)){ // X Value in Range of Button
                        // Activates sorting routine and establishes sort to have been selected
                        isSortSelected = true;
                        selectionSort = true;
                    }
                    else if ((mouseEvent.getX()>=625)&&(mouseEvent.getX()<=805)){ // X Value in Range of Button
                        // Activates sorting routine and establishes sort to have been selected
                        System.exit(0);
                    }
                }
            } else{
                // Establishes that the mouse has been clicked by amending boolean
                mouseClicked = true;
            }
        }
        // Below mouse events are not used in the program
        public void mouseEntered (MouseEvent mouseEvent) {}
        public void mousePressed (MouseEvent mouseEvent) { }
        public void mouseReleased (MouseEvent mouseEvent) {  }
        public void mouseExited (MouseEvent mouseEvent) {}
    }


    public void renderOffScreen(Graphics g) {
        /****************************************
         METHOD TITLE: renderOffScreen
         METHOD INPUT: graphics to add on to / modify
         METHOD DESCRIPTION:
         This section of the code creates the graphics outside the screen prior to transferring it onto the
         screen. By drawing strings and rectangles, the required display is created. Mouse inputs help the user
         make decisions such as where to proceed (e.g. which sorting algorithm, reset program, and exit program).
         Boolean controls and step counters help create the correct output, and boxes are created based on an
         array of boxes, where each box has its own attributes as per the Box class.
         LAST MODIFIED: 2025-06-16
         ********************************************/
        me = g;
        if (!isSorted&&mouseClicked&&isSortSelected){ // If not fully sorted, user wants to sort, and selected preferred sort
            // Based on which sort is active (checks each boolean), one step of that sorting algorithm is performed
            if (insertionSort) insertionSorting();
            else if (bubbleSort) bubbleSorting();
            else if (selectionSort) selectionSorting();
            // Since the event of mouse click has concluded, the boolean mouse click is reset to false
            mouseClicked = false;
        } else if (isSorted&&mouseClicked){ // If the user initiates reset by clicking window after sorted
            // Reset procedure for all variables, and new boxes are generated
            isSorted = false;
            isSortSelected = false;
            insertionSort = false;
            bubbleSort = false;
            selectionSort = false;
            currentSort = 0; // Resets to step 0 of the sorting algorithm
            makeBoxes(); // Recreates the boxes
        }

        for (int i = 0; i < toSort.length; i++){ // For each box existing in the array
            // Sets colour of the box using randomly generated colours in box class
            g.setColor(new Color(toSort[i].colours[0],toSort[i].colours[1],toSort[i].colours[2]));
            // Creates the box to its corresponding length, using positions from box class
            // Box height indicates the value of the number within the box
            g.fillRect(toSort[i].xPos, toSort[i].yPos, 50, 5*toSort[i].num);
            // Creates red labels for the values of each box, to align with and help user numerically see sorting
            g.setColor(new Color(250,0,0));
            g.setFont(new Font("Default", Font.PLAIN, 25));
            g.drawString(Integer.toString(toSort[i].num), toSort[i].xPos, toSort[i].yPos - 25);
        }

        if (!isSortSelected) { // Displays if a user has not selected which sort to perform for the boxes
            // Generates four yellow boxes enabling user to select their choice
            g.setColor(Color.yellow);
            for (int a = 0; a < 4; a++){ // Four rectangles created, evenly spaced apart horizontally
                g.fillRect(25+(200*a), 700, 180, 50);
            }
            // Generates the black labels for the boxes in the corresponding positions
            g.setColor(Color.black);
            g.setFont(new Font("Default", Font.PLAIN, 20));
            g.drawString("Insertion Sort", 30, 725);
            g.drawString("Bubble Sort", 230, 725);
            g.drawString("Selection Sort", 430, 725);
            g.drawString("Exit", 630, 725);
        } else if (!isSorted){ // Displays if a user selected which sort to perform but still stages remaining
            g.setFont(new Font("Default", Font.PLAIN, 25));
            g.setColor(new Color(200,0,200));
            g.drawString("Click to sort. This will disappear when sorted", 0, 700);
        } else { // Displays once sorting is complete, allowing user to reset
            String typeSort = "";
            // Displays which sort has been performed on the boxes
            if (insertionSort) typeSort = "Insertion Sort";
            if (bubbleSort) typeSort = "Bubble Sort";
            if (selectionSort) typeSort = "Selection Sort";
            g.setColor(Color.black);
            g.drawString("Values have been sorted using " + typeSort + ", click to reset!", 0, 700);
        }
    }

    /*
    THE FOLLOWING CLASS (MyTimer) and METHOD (paint) are part of the template code
    Credit: Dimos Raptou
    Reused with his authorization
     */
    /********************************************* 
     This timer class uses the built-in function
     to create a delay... it's nicer as a function
     so it runs in the background calling the
     paint function 
     **********************************************/
    private class MyTimer extends java.util.TimerTask {
        public void run() {
            // Run thread on event dispatching thread
            if (!EventQueue.isDispatchThread()) {
                EventQueue.invokeLater(this);
            } else {
                if (BoxDrawer.this != null) {
                    BoxDrawer.this.repaint();
                }
            }
        } // End of Run
    }

    /**********************************************
     *  the paint function is called based on the
     *  delay used in the constructor instantiation of
     *  Timer class
     ********************************************/
    public void paint(Graphics g) {
        final Dimension d = getSize();

        if (offScreenImage == null) {
            // Double-buffer: clear the offscreen image.
            offScreenImage = createImage(d.width, d.height);
        }
        offScreenGraphics  = offScreenImage.getGraphics();
        offScreenGraphics.setColor(Color.white);
        offScreenGraphics.fillRect(0, 0, d.width, d.height) ;
        renderOffScreen(offScreenImage.getGraphics());
        g.drawImage(offScreenImage, 0, 0, null);
    }

    /*******************************
     The following functions and methods are not graphics code
     ******************************/

    void makeBoxes(){
        /*************************
         * METHOD NAME: makeBoxes
         * METHOD PURPOSE: To create 10 boxes and to establish positions for each box
         * METHOD LAST MODIFIED: 2025-06-16
         ************************/
        // x and y positions of the first box
        int xBox = 50;
        final int YBox = 50;
        for (int i = 0; i < toSort.length; i++){ // repeats for each individual box within array
            toSort[i] = new Box(xBox, YBox); // box is created with the corresponding coordinates
            xBox+=70; // boxes are evenly spaced apart on a horizontal line from each other
        }
    }

    void bubbleSorting(){
        /*************************
         * METHOD NAME: bubbleSorting
         * METHOD PURPOSE: To perform next step of bubble sorting until bubble sorting complete
         * METHOD LAST MODIFIED: 2025-06-16
         ************************/
        boolean flip = true; // boolean to control flipping
        for (int i=0; i< toSort.length-currentSort-1; i++) {
            flip = compareBox(toSort[i + 1], toSort[i]); // compares both values, true if flip needed

            if (flip) { // if flip is true, the array must also be flipped
                // Flips the coordinates
                int tempInt = toSort[i + 1].xPos;
                toSort[i + 1].xPos = toSort[i].xPos;
                toSort[i].xPos = tempInt;

                // Flips the Boxes
                Box temp = toSort[i + 1]; // generates temporary box
                toSort[i + 1] = toSort[i]; // flips both values
                toSort[i] = temp;
            }
        }
        currentSort++;
        if (currentSort >= toSort.length) isSorted = true;

    }

    void selectionSorting(){
        /*************************
         * METHOD NAME: selectionSorting
         * METHOD PURPOSE: To perform next step of selection sorting until bubble sorting complete
         * METHOD LAST MODIFIED: 2025-06-16
         ************************/

        // Determines minimum value in the array
        int minIndex = currentSort;
        for (int i = currentSort; i < toSort.length; i++){
            if (toSort[minIndex].num>toSort[i].num) minIndex = i;
        }

        // Swaps the currentSort index value with the smallest value by flipping coordinaes and boxes
        int tempInt = toSort[currentSort].xPos;
        toSort[currentSort].xPos = toSort[minIndex].xPos;
        toSort[minIndex].xPos = tempInt;
        Box temp = toSort[currentSort]; // generates temporary box
        toSort[currentSort] = toSort[minIndex]; // flips both values
        toSort[minIndex] = temp;

        currentSort++;
        if (currentSort>=toSort.length) isSorted = true;
    }

    void insertionSorting(){
        /*************************
         * METHOD NAME: insertionSorting
         * METHOD PURPOSE: To perform next step of insertion sorting until bubble sorting complete
         * METHOD LAST MODIFIED: 2025-06-16
         ************************/
         int current = currentSort; // counter when flipping
         boolean flip = true; // boolean to control flipping
         while (current>0&&flip){ // conditions for each box in order to finish
             flip = compareBox(toSort[current], toSort[current-1]); // compares both values, true if flip needed

             if (flip){ // if flip is true, the array must also be flipped
                 // Flips the coordinates
                int tempInt = toSort[current-1].xPos;
                toSort[current-1].xPos = toSort[current].xPos;
                toSort[current].xPos = tempInt;

                // Flips the Boxes
                Box temp = toSort[current-1]; // generates temporary box
                toSort[current-1] = toSort[current]; // flips both values
                toSort[current] = temp;
            }

            current--; // decrement indicates proceeding left
         }
         currentSort++;
         if (currentSort>= toSort.length) isSorted = true;
    }

    boolean compareBox(Box right, Box left){
        /*************************
         * FUNCTION NAME: compareBox
         * FUNCTION PURPOSE: To numerically compare the numbers of two boxes for insertion and bubble sorting
         * FUNCTION INPUT: Two boxes
         * FUNCTION OUTPUT: true if left number greater, false if right number greater OR both numbers equal
         * FUNCTION LAST MODIFIED: 2025-06-16
         ************************/
        return (right.num<left.num); // compares numbers and return if flip must occur
    }

}