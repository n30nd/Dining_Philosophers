package btl.os;

import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vu Quang Nam
 */
public class TrietGia extends Thread {
    private int idex;
    private static Semaphore[] forks = new Semaphore[5];
    private GUI gui = new GUI();
    public TrietGia(int idex, GUI gui) {
        this.idex = idex;
        this.gui = gui;
    }

    public void run() {
        do {
            int j = idex % 2;
            try {            
                forks[(idex + j) % 5].acquire();
                showChoosedLeft();
                          
                forks[(idex + 1 - j) % 5].acquire();
                showChoosedRight();

                eating();
                
                showUNChoosedRight();
                forks[(idex + 1 - j) % 5].release();

                showUnChoosedLeft();
                forks[(idex + j) % 5].release();
  
                thinking();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(TrietGia.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }
 void showChoosedLeft(){
    int j=idex%2;
    System.out.println("Dia thu " + (idex + j) % 5 + " duoc lay boi triet gia thu"+idex);
    gui.getFLabel((idex + j) % 5).setForeground(Color.GREEN);
    gui.getFLabel((idex + j) % 5).setText((idex+j)%5+"Choosed by TrietGia"+idex);
    
    
            System.out.println("Triet gia "+idex+"dang cho dia thu"+ (idex+1-j)%5);
}
 void showChoosedRight(){
    int j=idex%2;
    System.out.println("Dia thu " + (idex + 1 - j) % 5 + " duoc lay boi triet gia thu "+idex);
    gui.getFLabel((idex + 1 - j) % 5).setForeground(Color.GREEN);
    gui.getFLabel((idex + 1 - j) % 5).setText((idex+1-j)%5+"Choosed by TrietGia"+idex);
}
 void showUnChoosedLeft(){
    int j=idex%2;
    System.out.println("Dia thu " + (idex + j) % 5 + " khong duoc dung nua");
    gui.getFLabel((idex + j) % 5).setForeground(Color.black);
    gui.getFLabel((idex+j)%5).setText((idex+j)%5+"UNChoosed "); 
}
 void showUNChoosedRight(){
    int j=idex%2;
    System.out.println("Dia thu " + (idex + 1 - j) % 5 + " khong duoc dung nua");
    gui.getFLabel((idex + 1 - j) % 5).setForeground(Color.black);
    gui.getFLabel((idex+1-j)%5).setText((idex+1-j)%5+"UNChoosed ");
}

    
    void eating() {
        try {
            //int j=idex%2;

            System.out.println("Triet gia dang an"+idex);
            gui.getTLabel(idex).setForeground(Color.red);
            gui.getTLabel(idex).setText("TrietGia"+idex+"Eating");
            
            Thread.sleep(2000);
            gui.getTLabel(idex).setForeground(Color.black);
          
        } catch (InterruptedException ex) {
            Logger.getLogger(TrietGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  void thinking() {
        try {                
            System.out.println("triet gia dang nghi"+idex);
            gui.getTLabel(idex).setText("TrietGia"+idex+"Thinking");
            Thread.sleep(2000);
            gui.getTLabel(idex).setText("TrietGia"+idex+"Hungrying");

        } catch (InterruptedException ex) {
            Logger.getLogger(TrietGia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        GUI mainframe = new GUI();
        mainframe.setVisible(true);
        TrietGia trietGia0 = new TrietGia(0,mainframe);
        TrietGia trietGia1 = new TrietGia(1,mainframe);
        TrietGia trietGia2 = new TrietGia(2,mainframe);
        TrietGia trietGia3 = new TrietGia(3,mainframe);
        TrietGia trietGia4 = new TrietGia(4,mainframe);
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }

        trietGia0.start();
        trietGia1.start();
        trietGia2.start();
        trietGia3.start();
        trietGia4.start();
    }
}
