package ru.viljinsky.puzzles7;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

class Base extends Container implements WindowListener{
    
    JFrame frame;
    String title = "Base";

    public Base() {
    }
    
    public void showInFrame(Component parent){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
    

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (frame!=null){
            frame.setVisible(false);
            frame.dispose();
            frame= null;
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("closed OK");
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
        

/**
 *
 * @author viljinsky
 */
public class Main extends Base implements Runnable{

    public Main() {
        title = "Main";
        setPreferredSize(new Dimension(800,600));
    }
    

    @Override
    public void run() {
        showInFrame(null);
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
    
}
