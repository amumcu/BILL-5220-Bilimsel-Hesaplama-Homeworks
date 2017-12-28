package bill5220.bilimsel_hesaplama;

import bill5220.bilimsel_hesaplama.image_editing.poission.PoissonImageEditing;
import bill5220.bilimsel_hesaplama.linear_equations.MatrixDeterminant;
import bill5220.bilimsel_hesaplama.linear_equations.algorithms.Gauss_Elimination;
import bill5220.bilimsel_hesaplama.linear_equations.algorithms.Gauss_Seidel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Test extends JFrame  implements ActionListener {

    JTextArea jtxAMatrix, jxtBVector, jtxXVector;
    JButton btnGaussElimination, btnGaussSeidal, btnPoissionImageEditing;
    Test(){
        JFrame f= new JFrame("BILL 5220 Bilimsel Hesaplama");


        JLabel label  = new JLabel();
        label.setText("<html><h2>A x = b denklemi icin: </h2></html>");
        label.setOpaque(true);
        label.setBounds(20,10,250, 30);
        label.setBackground(Color.RED);
        label.setForeground(Color.WHITE);
        f.add(label);


        JLabel labelA  = new JLabel();
        labelA.setText("A Matrisi");
        labelA.setOpaque(true);
        labelA.setBounds(100,50,70, 20);
        labelA.setBackground(Color.GREEN);
        labelA.setForeground(Color.WHITE);
        f.add(labelA);

        jtxAMatrix = new JTextArea();
        JScrollPane scrollA = new JScrollPane(jtxAMatrix);
        scrollA.setBounds(50, 75, 200, 200);
        f.add(scrollA);


        JLabel labelX  = new JLabel();
        labelX.setText("X Cozumu");
        labelX.setOpaque(true);
        labelX.setBounds(310,50,70, 20);
        labelX.setBackground(Color.BLUE);
        labelX.setForeground(Color.WHITE);
        f.add(labelX);
        jtxXVector = new JTextArea();
        JScrollPane scrollB = new JScrollPane(jtxXVector);
        scrollB.setBounds(300, 75, 100, 200);
        jtxXVector.setEditable(false);
        f.add(scrollB);


        JLabel labelEsit  = new JLabel();
        labelEsit.setText("=");
        labelEsit.setOpaque(true);
        labelEsit.setBounds(410,150,20, 20);
        labelEsit.setBackground(Color.ORANGE);
        labelEsit.setForeground(Color.WHITE);
        labelEsit.setFont(new Font("sansserif", Font.BOLD, 20));
        f.add(labelEsit);



        JLabel labelB  = new JLabel();
        labelB.setText("b Degerleri");
        labelB.setOpaque(true);
        labelB.setBounds(430,50,100, 20);
        labelB.setBackground(Color.GREEN);
        labelB.setForeground(Color.WHITE);
        f.add(labelB);
        jxtBVector = new JTextArea();
        JScrollPane scrollC = new JScrollPane(jxtBVector);
        scrollC.setBounds(450, 75, 30, 200);
        f.add(scrollC);



        btnGaussElimination=new JButton("Gauss\n Elimination");
        btnGaussElimination.setBounds(60,350,200,60);
        btnGaussElimination.addActionListener(this);
        f.add(btnGaussElimination);

        btnGaussSeidal=new JButton("Gauss Seidel");
        btnGaussSeidal.setBounds(300,350,150,60);
        btnGaussSeidal.addActionListener(this);
        f.add(btnGaussSeidal);


        btnPoissionImageEditing=new JButton("Poission Image Editing");
        btnPoissionImageEditing.setBounds(100,450,250,60);
        btnPoissionImageEditing.addActionListener(this);
        f.add(btnPoissionImageEditing);


        JLabel labelExp  = new JLabel();
        labelExp.setText("<html><h3>NOT:</h3>Degerler A matrisi ve b Vektoru alanlarina Girilecek.<br>" +
                "Degerler gilirken arada bosluk birakilmalidir.<br>Alt satira enter tusu ile gecilebilir.</html>");
        labelExp.setOpaque(true);
        labelExp.setBounds(50,550,400, 90);
        labelExp.setBackground(Color.GREEN);
        labelExp.setForeground(Color.WHITE);
        f.add(labelExp);


        jtxAMatrix.setText("1 2\n6 5");
        jxtBVector.setText("3\n4");
        f.setSize(600,650);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Test();

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnPoissionImageEditing){
            PoissonImageEditing.start();
        }else {
            String aMatrix = jtxAMatrix.getText();
            String bVector = jxtBVector.getText();

            String [] aMatrixRows = aMatrix.split("\n+");
            String [] aMatrixFirstRow = aMatrixRows[0].split(" +");

            String [] bValues = bVector.split("\n+");

            int i = aMatrixRows.length;
            int j = aMatrixFirstRow.length;

            int bLength = bValues.length;

            double [][] A = new double[i][j];
            double [] b = new double[bLength];

            for (int k = 0; k < i; k++){
                String [] row = aMatrixRows[k].split(" +");
                for (int t = 0; t< j; t++){
                    try {
                        A[k][t] = Double.valueOf(row[t]);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }


            for (int t = 0; t< bValues.length; t++){
                try {
                    b[t] = Double.valueOf(bValues[t]);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

                MatrixDeterminant matrixDeterminant = new MatrixDeterminant();
                Double det = matrixDeterminant.findDeterminant(A, i);

            if (det == 0 ){
                JOptionPane.showMessageDialog( null, "<html>Girilen Matrisin Determinanti 0 !!!<br> Lutfen Degerleri Kontrol Edip Tekrar Deneyiniz...</html>");
            }
            else {
                if (e.getSource() == btnGaussSeidal) {

                    if (i != j) {
                        JOptionPane.showMessageDialog( null, "Girilen Matris Dikdortgen matris");
                    }
                    double[][] M = new double[i][j + 1];
                    int t = 0;
                    for (int k = 0; k < i; k++) {
                        for (t = 0; t < j; t++) {

                            M[k][t] = A[k][t];
                        }
                        M[k][t] = b[k];
                    }

                    Gauss_Seidel gausSeidel = new Gauss_Seidel(M);



                    if (!gausSeidel.diagonal())

                    {

                        JOptionPane.showMessageDialog( null, "Sistem diogonal degil!!!");

                    }


                    System.out.println();


                    double[] sol = gausSeidel.solve();
                    int N = sol.length;
                    String solString = "";
                    for (t = 0; t < N; t++) {

                        solString += sol[t] + "\n";
                    }

                    jtxXVector.setBackground(Color.BLUE);
                    jtxXVector.setForeground(Color.red);
                    jtxXVector.setFont(new Font("sansserif", Font.BOLD, 13));
                    jtxXVector.setText(solString);


                } else if (e.getSource() == btnGaussElimination) {

                    if (i == j) {
                        Gauss_Elimination g = new Gauss_Elimination();
                        double[] sol = g.solve(A, b);
                        int N = sol.length;
                        String solString = "";
                        for (int t = 0; t < N; t++) {

                            solString += sol[t] + "\n";
                        }

                        jtxXVector.setBackground(Color.red);
                        jtxXVector.setForeground(Color.BLUE);
                        jtxXVector.setFont(new Font("sansserif", Font.BOLD, 13));
                        jtxXVector.setText(solString);
                    }else{
                        JOptionPane.showMessageDialog( null, "<html>Girilen Matris Dikdortgen matris<br>Denklem Sayisi ile Degisken Sayisi Ayni Degil! <br>" +
                                "Lutfen Degerleri Kontrol Edip Tekrar Deneyiniz...</html>");

                    }
                }
            }
        }

    }



}