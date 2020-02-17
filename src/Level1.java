
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author S331473306
 */
public class Level1 extends JPanel {

    //
    public int widthOfScene = 820; //720 is the maze width, extra 100 to make the maze centered
    public int heightOfScene = 540; //440 is the maze height, extra 100 to make the maze centered
    public boolean running = false;
    public String d = "d", //dots pacman eats
            w = "w", //wall
            c = "c", //candy to power up pacman and enable him to eat ghosts for a few seconds
            p = "p", //portal to go from one side of the maze to the other
            s = "s", //spawn for ghosts, pacman cant enter
            q = "q", //pacman's starting point
            b = "b", //blank space
            e = "e"; //exit from spawn for ghosts

    private JFrame gamePanel = new JFrame("PacMan");
    private ImageIcon background = new ImageIcon("images//background.jpg");
    private ImageIcon candy = new ImageIcon("images//Point.png");
    private ImageIcon dot = new ImageIcon("images//Pac-Dot.png");
    private Player pacman = new Player(5, 420, 440, "images//PacmanRight.png", true);
    private KeyEvents keyEvents = new KeyEvents(pacman, this);
    private CandyGhost ghost1 = new CandyGhost(1, 360, 120, "images//candyGhostRight.png", true, this, pacman);
    private PoliceGhost ghost2 = new PoliceGhost(1, 360, 200, "images//policeGhostRight.png", true, this, pacman);
    private NinjaGhost ghost3 = new NinjaGhost(1, 600, 300, "images//ninjaGhostRight.png", true, this, pacman);
    private PirateGhost ghost4 = new PirateGhost(1, 60, 440, "images//pirateGhostRight.png", true, this, pacman);
    private AngelGhost ghost5 = new AngelGhost(1, 180, 200, "images//angelGhostRight.png", true, this, pacman);
    private DevilGhost ghost6 = new DevilGhost(1, 360, 360, "images//devilGhostRight.png", true, this, pacman);
    
    //38 by 24 maze (2 extra lines on the left side and the top for the maze to be centered on the GUI)
    final String[][] maze = new String[][]{
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
        {b, b, w, w, w, w, w, w, w, d, w, w, w, b, w, w, w, w, w, e, e, w, w, w, w, w, b, w, w, w, d, w, w, w, w, w, w, w, b}, //11
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

    public Level1() {

        gamePanel.addKeyListener(keyEvents);
        gamePanel.setFocusable(true);
        gamePanel.add(this);

        //make threads for the ghosts to move on their own
        Thread thread = new Thread(ghost1);
        Thread thread2 = new Thread(ghost2);
        Thread thread3 = new Thread(ghost3);
        Thread thread4 = new Thread(ghost4);
        Thread thread5 = new Thread(ghost5);
        Thread thread6 = new Thread(ghost6);

        //initialize the gamePanel
        gamePanel.setSize(widthOfScene, heightOfScene);
        gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel.setVisible(true);
        gamePanel.setLocationRelativeTo(null);
        gamePanel.setResizable(false);
        
        //call startGame to start the threads for the ghosts
        startGame(thread, thread2, thread3, thread4, thread5, thread6);
        
        //continuously check if you have won while the game is running
        while(running == true){
        checkWin(thread, thread2, thread3, thread4, thread5, thread6);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //draws the background (black image) onto the GUI
        g.drawImage(background.getImage(), 0, 0, null);
        //checks every element of the 2D array using a nested for loop to determine
        //which color to paint each variable
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Color color = null;
//                System.out.println("In row: " + row + "and col: " + col + "maze: " + maze[row][col]);
                switch (maze[row][col]) {
                    //if the element is a wall ("w"), paint blue square
                    case "w":
                        color = Color.BLUE;
                        g.setColor(color);
                        g.fillRect(20 * col, 20 * row, 20, 20);
                        g.setColor(color.BLACK);
                        g.drawRect(20 * col, 20 * row, 20, 20);
                        break;
                    //if the element is a dot ("d"), paint white circle
                    case "d":
                        g.drawImage(dot.getImage(), 20 * col, 20 * row, null);
                        break;
                    //if the element is a candy ("c"), paint larger white circle
                    case "c":
                        g.drawImage(candy.getImage(), 20 * col, 20 * row, null);
                        break;
                    //if the element is an exit for ghosts ("e"), paint red square
                    case "e":
                        color = Color.RED;
                        g.setColor(color);
                        g.fillRect(20 * col, 20 * row, 20, 20);
                        g.setColor(color.BLACK);
                        g.drawRect(20 * col, 20 * row, 20, 20);
                        break;

                    //other elements like blank ("b") would not need to be painted because they are already black
                }

            }

            //draw each character onto the GUI
            pacman.makePlayer(g);
            ghost1.makePlayer(g);
            ghost2.makePlayer(g);
            ghost3.makePlayer(g);
            ghost4.makePlayer(g);
            ghost5.makePlayer(g);
            ghost6.makePlayer(g);
        }
    }
    
    private void checkWin(Thread a, Thread b, Thread c, Thread d, Thread e, Thread f){
        int no = 0;
        //goes through the array to check if there are any c's or d's (candy and dots)
        //if not, no will remain zero and we will stop the threads
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
            //stop the threads after you win so the ghosts won't keep moving
            //or else they can move into you after you win and cause you to 
            //die after you win
            a.stop();
            b.stop();
            c.stop();
            d.stop();
            e.stop();
            f.stop();
            running = false;
        }
    }

    private void startGame(Thread a, Thread b, Thread c, Thread d, Thread e, Thread f) {
        //starts the threads for the ghosts when the game starts
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        f.start();
        running = true;
    }
}
