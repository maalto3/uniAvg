/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniavg;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Te4o
 */
public class UniAvg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        dialogPanel dp = new dialogPanel();
        JFrame frame = new JFrame("LIBRETTO UNIVERSITARIO");
        MyPanel panel = new MyPanel();
        frame.setSize(900, 500);
        //frame.setLocation(300, 100);
        frame.setContentPane(panel);
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        
    }
    
}
