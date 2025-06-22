import java.util.Random; // Creates random number generation for the box's number
public class Box {
    /*************************
     * CLASS TITLE: Box
     * CLASS AUTHOR: Varsan Jeyakkumar
     * CLASS PURPOSE:
     Boxes are being sorted by the sorting algorithms in BoxDrawer. Each box has certain attributes, such as an
     array of colours in RGB form, the number for that array, and the x and y positions of that box. When the
     constructor of the box is called, it creates a box and assigns values for these attributes.
     * CLASS LAST MODIFIED: 2025-06-16
     ************************/
    int num, xPos, yPos; // Integers defining properties of the box
    Random r = new Random(); // Creates instance of a random class to generate colour values in RGB form
    int[] colours = {r.nextInt(1,255),r.nextInt(1,255),r.nextInt(1,255)}; // Integers define the colour of the box, default blue
    Box(int xPos, int yPos){ // Box constructor: creates a box
        this.num= new Random().nextInt(1,99); // Randomly assigns value for the value within specified range
        this.xPos=xPos; // X position of the box
        this.yPos=yPos; // Y position of the box
    }
}
