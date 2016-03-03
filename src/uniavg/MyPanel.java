/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniavg;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


/**
 *
 * @author Te4o
 */
public class MyPanel extends JPanel implements ActionListener{
    private int m;//numero esami
    private String[] year, subject, mark, credit;
    private String facolta, nome;
    private double avrg1, avrg2, avrg3, avrgPond, avrgFinal0/* con voto peggiore */, avrgFinal1/* senza voto peggiore */;
    private int m1, m2, m3, totCredit;
    private JLabel[] anni, materie, votis, crediti;
    private boolean acq = false;
    private String path = (new dialogPanel().dialog());
    
    public void nEsami(){
        int n=-2;
        try{
            BufferedReader in = new BufferedReader(new FileReader("file/"+path+".txt"));
            while(in.readLine() != null){n++;}
            in.close();
        }
        catch (FileNotFoundException ex1 ){ System.err.println("File libretto non trovato!");  } 
        catch (IOException ex2) {}
        
        m=n;
    }
    
    public void leggi(){
        nEsami();
        try{
            BufferedReader in = new BufferedReader(new FileReader("file/"+path+".txt"));
            String line; int i=0; int j=0;
            
            year = new String[m]; subject = new String[m]; mark = new String[m]; credit = new String[m];
            anni = new JLabel[m]; materie = new JLabel[m]; votis = new JLabel[m]; crediti = new JLabel[m];

            while((line = in.readLine()) != null){
                StringTokenizer st2 = new StringTokenizer(line, ",");
                int k = i-2;
         
                    if(i==0){facolta=line;}
                    if(i==1){nome=line;}
                    if(i>1){
                        while(st2.hasMoreTokens()){
                            
                            String s2 = st2.nextToken();
                            if(j==0){year[k]=s2;}
                            if(j==1){subject[k]=s2;}
                            if(j==2){
                                mark[k]=s2;
                                if(year[k].equals("1")) {if(Integer.parseInt(mark[k])!=0){avrg1+=Double.parseDouble(s2); m1++;}}
                                if(year[k].equals("2")) {if(Integer.parseInt(mark[k])!=0){avrg2+=Double.parseDouble(s2); m2++;}}
                                if(year[k].equals("3")) {if(Integer.parseInt(mark[k])!=0){avrg3+=Double.parseDouble(s2); m3++;}}
                            }
                            if(j==3){
                                credit[k]=s2; 
                                if(Integer.parseInt(mark[k])!=0){
                                totCredit+=Double.parseDouble(s2);
                                }
                            }j++;
                        }                       
                    }i++; j=0; 
            }   in.close();
        }
        catch (FileNotFoundException ex1 ){ System.err.println("File libretto non trovato!");  } 
        catch (IOException ex2) {}  
    }
    
    public void calcolaPond(){
        //calcolo media ponderata totale
        for(int k=0; k<m; k++){
            if(Integer.parseInt(mark[k])!=0){
                avrgPond+=(Double.parseDouble(mark[k])*(Double.parseDouble(credit[k])));
            }
        }
        avrgFinal0 = ((avrgPond/totCredit)*110)/30;
        
        //calcolo media ponderata senza voto peggiore
        int Vmin = 31; int Cmax = 0;
        for(int k=0; k<m; k++){
            if(Integer.parseInt(mark[k])!=0){
                if(Integer.parseInt(mark[k])<Vmin){
                    Vmin = Integer.parseInt(mark[k]);
                    Cmax = Integer.parseInt(credit[k]);
                }
                if(Integer.parseInt(mark[k])==Vmin){
                    if(Integer.parseInt(credit[k])>Cmax){
                        Cmax = Integer.parseInt(credit[k]);
                    }
                }
            }
        }
        avrgFinal1 = (((avrgPond-(Vmin*Cmax))/(totCredit-Cmax))*110)/30;
        }
    
    public MyPanel(){
        
        nEsami();leggi();//leggi dati da file
        calcolaPond();//calcola media ponderate
        
        for(int i=0; i<m; i++){
            anni[i] = new JLabel("-",SwingConstants.CENTER);
            materie[i] = new JLabel("-",SwingConstants.CENTER);
            votis[i] = new JLabel("-",SwingConstants.CENTER);
            crediti[i] = new JLabel("-",SwingConstants.CENTER);
        }
        
        JPanel head = new JPanel(); head.setSize(500, 50);
            JLabel title = new JLabel("Facoltà: -",SwingConstants.CENTER); title.setFont(new Font("Courier",Font.BOLD,20));
            JLabel subtitle = new JLabel("Libretto di: -",SwingConstants.CENTER); subtitle.setFont(new Font("Courier",Font.BOLD,16));
        JPanel body = new JPanel();
            JPanel left = new JPanel();
                JPanel voti = new JPanel();
            JPanel right = new JPanel();
                JPanel buttonPanel = new JPanel();
                    JButton calcola = new JButton("Calcola Medie"); calcola.setBackground(new Color(235,116,116)); calcola.setFont(new Font("Courier",Font.BOLD,18)); 
                    JButton leggi = new JButton("Acquisisci Libretto"); leggi.setBackground(new Color(235,116,116));  leggi.setFont(new Font("Courier",Font.BOLD,18)); 
                JPanel avg = new JPanel();

        JLabel avg1 = new JLabel("Media aritmetica 1° anno: NA",SwingConstants.CENTER); avg.add(avg1); avg1.setForeground(new Color(76,76,76));
        JLabel avg2 = new JLabel("Media aritmetica 2° anno: NA",SwingConstants.CENTER); avg.add(avg2); avg2.setForeground(new Color(76,76,76));
        JLabel avg3 = new JLabel("Media aritmetica 3° anno: NA",SwingConstants.CENTER); avg.add(avg3); avg3.setForeground(new Color(76,76,76));
        JLabel avgArit = new JLabel("Media ponderata finale: NA",SwingConstants.CENTER); avg.add(avgArit); avgArit.setForeground(new Color(76,76,76));
        JLabel avgPond = new JLabel("Media ponderata finale senza voto peggiore: NA",SwingConstants.CENTER); avg.add(avgPond); avgPond.setForeground(new Color(76,76,76));
        JLabel avgFinal0 = new JLabel("Voto Laurea: NA",SwingConstants.CENTER); avg.add(avgFinal0); avgFinal0.setForeground(new Color(76,76,76));
        JLabel avgFinal1 = new JLabel("Voto Laurea senza voto peggiore: NA",SwingConstants.CENTER); avg.add(avgFinal1); avgFinal1.setForeground(new Color(76,76,76));

        JLabel anno = new JLabel("<html><u>ANNO</u></html>",SwingConstants.CENTER);voti.add(anno);
        JLabel materia = new JLabel("<html><u>MATERIA</u></html>",SwingConstants.CENTER);voti.add(materia);
        JLabel voto = new JLabel("<html><u>VOTO</u></html>",SwingConstants.CENTER);voti.add(voto);
        JLabel cfu = new JLabel("<html><u>CFU</u></html>",SwingConstants.CENTER);voti.add(cfu);
        for(int i=0;i<m;i++){voti.add(anni[i]); voti.add(materie[i]); voti.add(votis[i]); voti.add(crediti[i]);}//riempie tab voti (...)
        
        buttonPanel.setLayout(null);
        calcola.setSize(200, 50);
        calcola.setLocation(115, 120);
        leggi.setSize(200, 50);
        leggi.setLocation(115, 20);
        
        leggi.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setText("Facoltà: "+facolta);
                subtitle.setText("Libretto di: "+nome);
                for(int i=0;i<m;i++){
                    anni[i].setText(year[i]);
                    materie[i].setText(subject[i]);
                    if(Integer.parseInt(mark[i])==0){votis[i].setText("-");}else{votis[i].setText(mark[i]);}
                    crediti[i].setText(credit[i]);
                }
                leggi.setText("Libretto Acquisito");
                leggi.setBackground(new Color(159,239,126)); 
                acq= true;
            }
        });//acquisisce dati
        
        calcola.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(acq==true){
                    avg1.setText("Media aritmetica 1° anno: "+(Math.floor((avrg1/m1) * 100.0) / 100.0)); avg1.setForeground(Color.BLACK);
                    avg2.setText("Media aritmetica 2° anno: "+(Math.floor((avrg2/m2) * 100.0) / 100.0)); avg2.setForeground(Color.BLACK);
                    avg3.setText("Media aritmetica 3° anno: "+(Math.floor((avrg3/m3) * 100.0) / 100.0)); avg3.setForeground(Color.BLACK);
                    avgArit.setText("Media ponderata finale: "+(Math.floor((avrgPond/totCredit) * 100.0) / 100.0)); avgArit.setForeground(Color.BLACK);
                    avgPond.setText("Media ponderata finale senza voto peggiore: "+(Math.floor((avrgFinal1*30/110) * 100.0) / 100.0)); avgPond.setForeground(Color.BLACK);
                    avgFinal0.setText("Voto Laurea: "+(Math.floor((avrgFinal0) * 100.0) / 100.0)); avgFinal0.setForeground(Color.BLACK);
                    avgFinal1.setText("Voto Laurea senza voto peggiore: "+(Math.floor((avrgFinal1) * 100.0) / 100.0)); avgFinal1.setForeground(Color.BLACK);
                    calcola.setText("Medie Calcolate");
                    calcola.setBackground(new Color(159,239,126)); 
                }
                else{JOptionPane.showMessageDialog(null, "Acquisire prima il libretto, e poi calcolare le medie.",
                        "Attenzione", JOptionPane.INFORMATION_MESSAGE);}
        }});//calcola medie
        
        GridLayout gridHead = new GridLayout(2, 1, 0, 2);
        GridLayout gridBody = new GridLayout(1, 2, 5, 0);
        GridLayout gridVoti = new GridLayout(m+1, 4, 0, 7);
        GridLayout gridRight = new GridLayout(2, 1, 0, 0);
        GridLayout gridAvg = new GridLayout(7, 1, 0, 7); 
        
        head.setLayout(gridHead);
        body.setLayout(gridBody);
        voti.setLayout(gridVoti);
        right.setLayout(gridRight);
        avg.setLayout(gridAvg);
        
        voti.setPreferredSize(new Dimension(420,370));
        this.setBackground(new Color(233,233,189));
        head.setBackground(new Color(233,233,189)); body.setBackground(new Color(233,233,189));
        left.setBackground(new Color(246,246,206)); voti.setBackground(new Color(246,246,206));
        buttonPanel.setBackground(new Color(246,246,206)); avg.setBackground(new Color(246,246,206));
        
        head.add(title);
        head.add(subtitle);
        left.add(voti);
        buttonPanel.add(calcola);
        buttonPanel.add(leggi);
        right.add(buttonPanel);
        right.add(avg);
        body.add(left);
        body.add(right);
        
        add(head);
        add(body);
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}
