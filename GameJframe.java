package cola.ice;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJframe extends JFrame implements KeyListener, ActionListener {

    public GameJframe(){

        //setting frame
        init();

        //set JmenuBar
        initJmenuBar();

        // cancel default picture in middle pattern.
        setLayout(null);

        //initiate data
        initData();
        //initiate win arr
        initWin();
        //initiating images
        initImage();
        //add key listener to current object
        addKeyListener(this);

        this.setVisible(true);
    }

    private void initData() {
        // initiate data
        step = 0;
        data = new int[sz][sz];
        int[] arr = new int[sz*sz];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Random r = new Random();
        // randomize array
        for(int i = 0; i < arr.length; ++i)
        {
            int index = r.nextInt(arr.length);
            int t = arr[i];
            arr[i] = arr[index];
            arr[index] = t;
        }
        // copy the random array into data[][]
        for(int i = 0; i < arr.length; i++) {
            data[i/sz][i%sz] = arr[i];
            // check where's the empty image is and save it into x, y
            if(arr[i] == 0)
            {
                x = i / sz;
                y = i % sz;
            }
        }
    }

    private void initImage() {
        // remove the previous content on the content pane.
        this.getContentPane().removeAll();
        if(isWin())
        {
            //display winning
            ImageIcon winIcon = new ImageIcon("win.png");
            JLabel winJlable = new JLabel(winIcon);
            winJlable.setBounds(170,240,245,50);

            this.getContentPane().add(winJlable);
        }
        JLabel stepCount = new JLabel("Step used: "+ step);
        stepCount.setBounds(350, 70, 100, 20);
        this.getContentPane().add(stepCount);
        // add the images to the pane.
        for(int i = 0; i < sz*sz; ++i)
        {
            //extract the picture from file
            ImageIcon icon = new ImageIcon(path+"_0"+data[i/sz][i%sz]+".jpg");
            JLabel jLabel = new JLabel(icon);
            // set the picture exact position
            jLabel.setBounds(i % sz * 105 + 125,i / sz * 105 + 100,105,105);
            // add border
            jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
            // add picture to the content pane.
            this.getContentPane().add(jLabel);
        }

        // make a background picture.
        addBackGroundPic();
        this.getContentPane().repaint();
    }

    private void addBackGroundPic() {
        ImageIcon background = new ImageIcon("background1.png");
        JLabel jLabel = new JLabel(background);
        jLabel.setBounds(60,0,454,680);
        // add background picture border.
        jLabel.setBorder(new LineBorder(Color.BLACK, 5));
        // add background picture to the pane
        this.getContentPane().add(jLabel);
        // repaint it so it could cover the previous pane
    }

    private void initJmenuBar() {
        // create a Menu Bar
        JMenuBar jMenuBar = new JMenuBar();
        // create two Menu on the Menu Bar
        JMenu functionMenu = new JMenu("function");
        JMenu aboutMenu = new JMenu("about");

        JMenu changePicJMenuItem = new JMenu("Change Picture");
        changePicJMenuItem.add(animalJMenuItem);
        changePicJMenuItem.add(peopleJMenuItem);
        changePicJMenuItem.add(otherJMenuItem);

        //add actions to the Items
        restartJMenuItem.addActionListener(this);
        reLoginJMenuItem.addActionListener(this);
        quitJMenuItem.addActionListener(this);
        aboutJMenuItem.addActionListener(this);
        animalJMenuItem.addActionListener(this);
        peopleJMenuItem.addActionListener(this);
        otherJMenuItem.addActionListener(this);

        // add items to Jmenu and Jmenu bar
        functionMenu.add(restartJMenuItem);
        functionMenu.add(changePicJMenuItem);
        functionMenu.add(reLoginJMenuItem);
        functionMenu.add(quitJMenuItem);
        aboutMenu.add(aboutJMenuItem);
        jMenuBar.add(functionMenu);
        jMenuBar.add(aboutMenu);
        this.setJMenuBar(jMenuBar);
    }

    private void init() {
        //set size of the pane
        this.setSize(603,680);
        //set title
        this.setTitle("Puzzle Game v1.0");
        //set location in middle
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(isWin())
        {
            return;
        }
        if(e.getKeyCode() == 65)
        {
            // remove all the content from previous panel
            this.getContentPane().removeAll();
            // add the full picture to the panel
            ImageIcon backgroundIcon = new ImageIcon(path+"full.png");
            JLabel backgroundjLabel = new JLabel(backgroundIcon);
            backgroundjLabel.setBounds(125, 100, 315, 315);
            backgroundjLabel.setBorder(new BevelBorder(1));
            this.getContentPane().add(backgroundjLabel);

            // make a background picture.
            addBackGroundPic();
            // repaint it so it could cover the previous pane
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // left 37, down 38, right 39, up 40
        // if left arrow is pressed. move pic left.
        if(isWin())
        {
            return;
        }
        if(e.getKeyCode() == 37)
        {
            if(y != sz - 1)
            {
                data[x][y] = data[x][y+1];
                data[x][y+1] = 0;
                ++y;
                ++step;
                initImage();
            }
        }

        // if up arrow is pressed. move pic up.
        else if(e.getKeyCode() == 38)
        {
            if(x != sz - 1)
            {
                data[x][y] = data[x+1][y];
                data[x+1][y] = 0;
                ++x;
                ++step;
                initImage();
            }
        }

        // if right arrow is pressed. move pic right.
        else if(e.getKeyCode() == 39)
        {
            if(y != 0)
            {
                data[x][y] = data[x][y-1];
                data[x][y-1] = 0;
                --y;
                ++step;
                initImage();
            }
        }

        // if down arrow is pressed. move pic down.
        else if(e.getKeyCode() == 40)
        {
            if(x != 0)
            {
                data[x][y] = data[x-1][y];
                data[x-1][y] = 0;
                --x;
                ++step;
                initImage();
            }
        }
        // if a is pressed
        else if (e.getKeyCode() == 65)
        {
            initImage();
        }
        // if w is pressed
        else if(e.getKeyCode() == 87)
        {
            for(int i = 0; i < sz*sz; ++i)
            {
                data[i/sz][i%sz] = i+1;
            }
            initImage();
        }
    }

    private boolean isWin(){
        for(int i = 0; i < sz * sz - 1; ++i) {
            if(win[i / sz][i % sz] != data[i/sz][i%sz])
            {
                return false;
            }
        }
        return true;
    }

    private void initWin() {
        // initiating win array by size
        win = new int[sz][sz];
        for(int i = 0; i < sz * sz; ++i) {
            win[i / sz][i % sz] = i+1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // get the source is clicked
        Object obj = e.getSource();
        if(obj == restartJMenuItem)
        {
            initData();
            initImage();
        }
        else if(obj == reLoginJMenuItem){
            this.setVisible(false);
            LoginJframe l = new LoginJframe();
        }
        else if(obj == quitJMenuItem)
        {
            System.exit(0);
        }
        else if(obj == aboutJMenuItem)
        {
            //create a new window
            JDialog jDialog = new JDialog();
            // load the picture into jlable
            JLabel jLabel = new JLabel(new ImageIcon("contact.png"));
            // set the position for the pic
            jLabel.setBounds(0,0,304,134);
            // add the pic to the window
            jDialog.getContentPane().add(jLabel);
            //set the window size
            jDialog.setSize(350,210);
            // set the window always on top
            jDialog.setAlwaysOnTop(true);
            // set the location in middle of the screen
            jDialog.setLocationRelativeTo(null);
            // could not play until the windows is closed.
            jDialog.setModal(true);
            // display the window
            jDialog.setVisible(true);
        }
        else if(obj == animalJMenuItem)
        {
            Random r = new Random();
            path = "animal" + (r.nextInt(5) + 1)+"\\";
            initData();
            initImage();

        }
        else if(obj == peopleJMenuItem)
        {
            Random r = new Random();
            path = "people" + (r.nextInt(3) + 1)+"\\";
            initData();
            initImage();
        }
        else if(obj == otherJMenuItem)
        {
            Random r = new Random();
            path = "other" +(r.nextInt(3) + 1)+"\\";
            initData();
            initImage();
        }
    }

    //position where the empty pic is
    int x, y;
    private static int sz = 3;
    //data use to store current picture's sequences.
    int[][] data;
    // data use to store winning sequences.
    int[][] win;
    // counter for steps used.
    int step;
    // path to the file
    String path = "people1\\";
    // four items under function Menu
    JMenuItem restartJMenuItem = new JMenuItem("restart");
    JMenuItem reLoginJMenuItem = new JMenuItem("reLogin");
    JMenuItem quitJMenuItem = new JMenuItem("quit");
    // item under about Menu
    JMenuItem aboutJMenuItem = new JMenuItem("producer info");
    // item under change pic
    JMenuItem animalJMenuItem = new JMenuItem("Animal");
    JMenuItem peopleJMenuItem = new JMenuItem("People");
    JMenuItem otherJMenuItem = new JMenuItem("Other");



}
