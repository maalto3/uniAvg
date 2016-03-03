/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniavg;

import javax.swing.JOptionPane;

/**
 *
 * @author te4o
 */
public class dialogPanel {

    String path;
    
    public String dialog() {
        return path = JOptionPane.showInputDialog(null, "Inserisci nome libretto txt", "Input File", JOptionPane.QUESTION_MESSAGE);

    }
    
}
