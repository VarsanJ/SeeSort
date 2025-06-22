/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;
import java.awt.Color;


public class Window{
    /*************************
     * CLASS TITLE: Window
     * CLASS AUTHOR: Varsan Jeyakkumar
     * CLASS PURPOSE:
     To generate a window to create graphics to perform program functionality, and connect the graphics
     to the window and control its visibility.
     * CLASS LAST MODIFIED: 2025-06-16
     ************************/

    // Creates the drawing class to control user interface
    BoxDrawer draw = new BoxDrawer();

    Window() {
        // Generates window frame and objects
        JFrame window = new JFrame(); // Creates instance of a window
        window.setSize(900,900); // Establishes the dimensions of the window
        window.setBackground(Color.WHITE); // Sets the background colour of the window to white
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(draw); // Adds draw class to the window
        window.setVisible(true); // Makes the window visible for the user
    }


}
