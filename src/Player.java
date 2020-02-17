
import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;
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
public class Player /*implements Runnable*/ {

    private int lives, xAxis, yAxis;
    private String image;
    private boolean isAlive;
    Random random = new Random();
    private Level1 lvl1;

    public Player(int lives, int xAxis, int yAxis, String image, boolean isAlive) {
        this.lives = lives;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.image = image;
        this.isAlive = isAlive;
    }

    /**
     * @return the lives
     */
    //did not have enough time to implement lives
    public int getLives() {
        return lives;
    }

    /**
     * @param lives the lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * @return the xAxis
     */
    public int getxAxis() {
        return xAxis;
    }

    /**
     * @param xAxis the xAxis to set
     */
    public void setxAxis(int xAxis) {
        if (isAlive) {
            //to make pacman go through a "portal" from one side to the other
            if (this.xAxis > 720) {
                this.xAxis = 60;

            } else if (this.xAxis < 60) {
                this.xAxis = 720;

            } else {
                this.xAxis = xAxis;
            }

        } else {
            this.xAxis = xAxis;
        }
    }

    /**
     * @return the yAxis
     */
    public int getyAxis() {
        return yAxis;
    }

    /**
     * @param yAxis the yAxis to set
     */
    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        if (image == null) {
            JOptionPane.showMessageDialog(null, "Invalid string");
        } else if (image.length() == 0) {
            JOptionPane.showMessageDialog(null, "String empty");
        } else {
            this.image = image;
        }
    }

    /**
     * @return the isAlive
     */
    public boolean isIsAlive() {
        return isAlive;
    }

    /**
     * @param isAlive the isAlive to set
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void makePlayer(Graphics g) {
        ImageIcon img = new ImageIcon(image);
        g.drawImage(img.getImage(), xAxis, yAxis, null);
    }
    
    // Tried to make pacman continuously move in one direction using the run method
    // from the Runnable interface
//    @Override
//    public void run() {
//        while(true){
//           try{
//               Thread.sleep(random.nextInt(100));
//           }catch (Exception e){
//               JOptionPane.showMessageDialog(null, "Thread interrupted");
//               System.exit(1);
//           }
//           setxAxis(getxAxis() + 10);
//            lvl1.repaint();
//        }
//    }
}
