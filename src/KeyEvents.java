
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class KeyEvents implements KeyListener {

    private Player pacman;
    private Level1 lvl1;
    private String right = "images//PacmanRight.png", left = "images//PacmanLeft.png",
            upRight = "images//PacmanRightUp.png", upLeft = "images//PacmanLeftUp.png",
            downRight = "images//PacmanRightDown.png", downLeft = "images//PacmanLeftDown.png";
    private String lastMove = ""; //to determine what image to use for pacman depending on where he was facing
    private int speed = 20; //pacman moves by 20 because each block is 20 by 20, he moves one block at a time
    public String d = "d", //dots pacman eats
            w = "w", //wall
            c = "c", //candy to power up pacman and enable him to eat ghosts for a few seconds
            p = "p", //portal to go from one side of the maze to the other
            s = "s", //spawn for ghosts, pacman cant enter
            q = "q", //pacman's starting place
            b = "b", //blank space
            ex = "e"; //exit from spawn for ghosts
    private String[][] maze = new String[][]{
        //0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38
        {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b}, //0
        {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b}, //1
        {b, b, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, b}, //2
        {b, b, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, b}, //3
        {b, b, w, d, w, w, w, w, w, d, w, w, w, w, w, w, w, w, d, w, w, d, w, w, w, w, w, w, w, w, d, w, w, w, w, w, d, w, b}, //4
        {b, b, w, c, w, w, w, w, w, d, w, w, w, w, w, w, w, w, d, w, w, d, w, w, w, w, w, w, w, w, d, w, w, w, w, w, c, w, b}, //5 
        {b, b, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, b}, //6
        {b, b, w, d, w, w, w, w, w, d, w, w, w, d, w, w, w, w, w, w, w, w, w, w, w, w, d, w, w, w, d, w, w, w, w, w, d, w, b}, //7
        {b, b, w, d, d, d, d, d, d, d, w, w, w, d, d, d, d, d, d, w, w, d, d, d, d, d, d, w, w, w, d, d, d, d, d, d, d, w, b}, //8
        {b, b, w, w, w, w, w, w, w, d, w, w, w, w, w, w, w, w, b, w, w, b, w, w, w, w, w, w, w, w, d, w, w, w, w, w, w, w, b}, //9
        {b, b, b, b, b, b, b, b, w, d, w, w, w, b, b, b, b, b, b, b, b, b, b, b, b, b, b, w, w, w, d, w, b, b, b, b, b, b, b}, //10
        {b, b, w, w, w, w, w, w, w, d, w, w, w, b, w, w, w, w, w, ex, ex, w, w, w, w, w, b, w, w, w, d, w, w, w, w, w, w, w, b}, //11
        {b, b, p, b, b, b, b, b, b, d, b, b, b, b, w, s, s, s, s, s, s, s, s, s, s, w, b, b, b, b, d, b, b, b, b, b, b, p, b}, //12
        {b, b, w, w, w, w, w, w, w, d, w, w, w, b, w, w, w, w, w, w, w, w, w, w, w, w, b, w, w, w, d, w, w, w, w, w, w, w, b}, //13
        {b, b, b, b, b, b, b, b, w, d, w, w, w, b, b, b, b, b, b, b, b, b, b, b, b, b, b, w, w, w, d, w, b, b, b, b, b, b, b}, //14
        {b, b, w, w, w, w, w, w, w, d, w, w, w, b, w, w, w, w, w, w, w, w, w, w, w, w, b, w, w, w, d, w, w, w, w, w, w, w, b}, //15
        {b, b, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, b}, //16
        {b, b, w, d, w, w, w, w, w, d, w, w, w, w, w, w, w, w, d, w, w, d, w, w, w, w, w, w, w, w, d, w, w, w, w, w, d, w, b}, //17
        {b, b, w, c, d, d, d, w, w, d, d, d, d, d, d, d, d, d, d, b, b, d, d, d, d, d, d, d, d, d, d, w, w, d, d, d, c, w, b}, //18
        {b, b, w, w, w, w, d, w, w, d, w, w, w, d, w, w, w, w, w, w, w, w, w, w, w, w, d, w, w, w, d, w, w, d, w, w, w, w, b}, //19
        {b, b, w, d, d, d, d, d, d, d, w, w, w, d, d, d, d, d, d, w, w, d, d, d, d, d, d, w, w, w, d, d, d, d, d, d, d, w, b}, //20
        {b, b, w, d, w, w, w, w, w, w, w, w, w, w, w, w, w, w, d, w, w, d, w, w, w, w, w, w, w, w, w, w, w, w, w, w, d, w, b}, //21
        {b, b, w, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, q, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, w, b}, //22
        {b, b, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, b}, //23
    };

    public KeyEvents(Player pacman, Level1 lvl1) {
        this.lvl1 = lvl1;
        this.pacman = pacman;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                //if pacman's next move to the left is not a wall(w), allow him to make a move
                if (!maze[pacman.getyAxis() / 20][(pacman.getxAxis() - speed) / 20].equals(w)) {
                    pacman.setxAxis(pacman.getxAxis() - speed);
                    pacman.setImage(left);
                    lvl1.repaint();
                    lastMove = left;
                    
                    //if the space the pacman is going to is equal to a d(dot) or a c(candy)
                    //change d or c to b(blank) to show he "ate" the dot or candy
                    if(maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(d)
                            || maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(c)){
                        lvl1.maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                        maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                    }
                    checkWin();
                }
                break;

            case KeyEvent.VK_RIGHT:
                //if pacman's next move to the right is not a wall(w), allow him to make a move
                if (!maze[pacman.getyAxis() / 20][(pacman.getxAxis() + speed) / 20].equals(w)) {
                    pacman.setxAxis(pacman.getxAxis() + speed);
                    pacman.setImage(right);
                    lvl1.repaint();
                    lastMove = right;
                    
                    //if the space the pacman is going to is equal to a d(dot) or a c(candy)
                    //change d or c to b(blank) to show he "ate" the dot or candy
                    if(maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(d)
                            || maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(c)){
                        lvl1.maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                        maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                    }
                    checkWin();
                }
                break;

            case KeyEvent.VK_UP:
                //if pacman's next move upward is not a wall(w), allow him to make a move
                if (!maze[(pacman.getyAxis() - speed) / 20][pacman.getxAxis() / 20].equals(w)) {
                    pacman.setyAxis(pacman.getyAxis() - speed);
                    //checks what the last sideways move was to choose the right way pacman should we facing
                    if (lastMove.equals(right)) {
                        pacman.setImage(upRight);
                    } else {
                        pacman.setImage(upLeft);
                    }
                    lvl1.repaint();
                    
                    //if the space the pacman is going to is equal to a d(dot) or a c(candy)
                    //change d or c to b(blank) to show he "ate" the dot or candy
                    if(maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(d)
                            || maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(c)){
                        lvl1.maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                        maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                    }
                    checkWin();
                }
                break;
                
            case KeyEvent.VK_DOWN:
                //if pacman's next move downward is not a wall(w) or an exit(ex for ghosts),
                //allow him to make a move
                if (!maze[(pacman.getyAxis() + speed) / 20][pacman.getxAxis() / 20].equals(w)
                        && !maze[(pacman.getyAxis() + speed) / 20][pacman.getxAxis() / 20].equals(ex)) {
                    pacman.setyAxis(pacman.getyAxis() + speed);
                    //checks what the last sideways move was to choose the right way pacman should we facing
                    if (lastMove.equals(right)) {
                        pacman.setImage(downRight);
                    } else {
                        pacman.setImage(downLeft);
                    }
                    lvl1.repaint();
                    
                    //if the space the pacman is going to is equal to a d(dot) or a c(candy)
                    //change d or c to b(blank) to show he "ate" the dot or candy
                    if(maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(d)
                            || maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20].equals(c)){
                        lvl1.maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                        maze[pacman.getyAxis() / 20][pacman.getxAxis() / 20] = b;
                    }
                    checkWin();
                }
                break;
                
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void checkWin() {
        int no = 0;
        //goes through the array to check if there are any c's or d's (candy and dots)
        //if not, no will remain zero and we will end the game saying the player won
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                switch (maze[row][col]){
                    case "d":
                        no++;
                        break;
                        
                    case "c":
                        no++;
                        break;
                }
            }
        }
        if(no == 0){
            JOptionPane.showMessageDialog(null, "You win!");
            System.exit(0);
        }
    }

}
