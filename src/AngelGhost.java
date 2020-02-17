
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author S331473306
 */
public class AngelGhost extends Player implements Runnable{
    
    private Level1 lvl1;
    private Player pacman;
    Random random = new Random();
    public int direction = 1;
    
    public AngelGhost(int lives, int xAxis, int yAxis, String image, boolean isAlive, Level1 lvl1, Player pacman) {
        super(lives, xAxis, yAxis, image, isAlive);
        this.lvl1 = lvl1;
        this.pacman = pacman;
    }

    public void kill() {
        //method to see if the ghost and the pacman are at the same place,
        //if they are, the ghost kills the pacman and ends the game
        Rectangle pacmanRect = new Rectangle(pacman.getxAxis(),pacman.getyAxis(), 20, 20);
        Rectangle ghostRect = new Rectangle(this.getxAxis(),this.getyAxis(), 20, 20);
        if(pacmanRect.contains(ghostRect)){
            JOptionPane.showMessageDialog(null, "You died");
            System.exit(0);
        }
    }
    
    @Override
    public void run() {
       while(true){
           try{
               //sleep the thread so the ghost won't fly off the screen or go too fast
               Thread.sleep(100);
           }catch (InterruptedException e){
               JOptionPane.showMessageDialog(null, "Thread interrupted");
               System.exit(1);
           }
           //determines the movement of the ghost
           if(getyAxis() <= 60){
               direction = 1;
               setImage("images//angelGhostRight.png");
           
           }else if(getyAxis() >= 400){
               direction = -1;
               setImage("images//angelGhostLeft.png");
               
           }
            kill();
            setyAxis(getyAxis() + 20 * direction);
            lvl1.repaint();
       }
    }
    
}
