package cola.ice;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class LoginJframe extends JFrame implements MouseListener {

    public LoginJframe(){
        //initiate frame
        init();
        //initiate images and background
        initImage();
        this.setVisible(true);
    }

    private void initImage() {

        //create username password and verification code Jlabel
        JLabel userNameJLabel = new JLabel(new ImageIcon("username.png"));
        JLabel passwordJLabel = new JLabel(new ImageIcon("password.png"));
        JLabel verifyJLabel = new JLabel(new ImageIcon("verificationcode.png"));
        //create login and login pressed jlabel
        loginJLabel.setBorder(new LineBorder(Color.black, 1));
        loginJLabel.addMouseListener(this);
        registerJLabel.addMouseListener(this);
        //create register and register pressed jlabel
        registerJLabel.setBorder(new LineBorder(Color.black, 1));
        //generate new verification code
        generateNewVrCode();
        //add vrcode to jlabel
        vrcodeJlabel.setText(vrCode);
        //create backgroundJlabel
        JLabel backgroudJLabel = new JLabel(new ImageIcon("loginBackground.jpg"));

        //create three text field
        username.setBounds(150, 100, 200,20);
        verifyCode.setBounds(150, 200, 100, 20);
        vrcodeJlabel.setBounds(250, 200, 100,20);
        vrcodeJlabel.setBorder(new CompoundBorder());
        passwordField.setBounds(150, 150, 200, 20);

        //set bounds for all the pics
        backgroudJLabel.setBounds(0,0, 488, 320);
        userNameJLabel.setBounds(50, 100, 100, 20);
        passwordJLabel.setBounds(50,150, 100,20);
        verifyJLabel.setBounds(50,200,100,20);
        loginJLabel.setBounds(175,230,50, 20);
        registerJLabel.setBounds(245, 230, 50,20);

        //add all of the pics to frame
        this.add(vrcodeJlabel);
        this.add(passwordField);
        this.add(verifyCode);
        this.add(userNameJLabel);
        this.add(username);
        this.add(passwordJLabel);
        this.add(verifyJLabel);
        this.add(loginJLabel);
        this.add(registerJLabel);
        this.add(backgroudJLabel);

    }

    public void showJDialog(String content) {
        //create a new J dialog
        JDialog jDialog = new JDialog();
        //set the size for the dialog
        jDialog.setSize(200, 150);
        //set always on top
        jDialog.setAlwaysOnTop(true);
        //set in middle
        jDialog.setLocationRelativeTo(null);
        //set if it not close cannot do other things
        jDialog.setModal(true);
        //create a new Jlabel that shows the warning content
        JLabel warning = new JLabel(content);
        //set position and size for warnings
        warning.setBounds(0, 0 , 200, 150);
        //add warning to jdialog
        jDialog.getContentPane().add(warning);
        //show the jdialog
        jDialog.setVisible(true);
    }

    private void init() {
        //set size
        this.setSize(488,320);
        //set title
        this.setTitle("Login");
        //set location in middle
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //if button was pressed show a darker icon
        if(e.getSource().equals(registerJLabel))
            registerJLabel.setIcon(new ImageIcon("registerPressed.png"));
        else
            loginJLabel.setIcon(new ImageIcon("loginPressed.png"));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource().equals(registerJLabel)) {
            //show the lighter icon
            registerJLabel.setIcon(new ImageIcon("register.png"));
            //register
        }
        else {
            //show lighter icon
            loginJLabel.setIcon(new ImageIcon("login.png"));
            //get username and login password, check if Users have corresponding user.
            String user = username.getText();
            String pwd = passwordField.getText();
            //check if empty username
            if(user.isEmpty())
            {
                //show warning
                showJDialog("  please enter username");
                //generate new vrcode
                generateNewVrCode();
                //set the vrcode on jlabel
                vrcodeJlabel.setText(vrCode);

            }
            //check if password was empty
            else if(pwd.isEmpty())
            {
                //show warning dialog
                showJDialog("  please enter password");
                //generate new vrcode
                generateNewVrCode();
                //set the vrcode on jlabel
                vrcodeJlabel.setText(vrCode);
            }
            //if entered correct password then login
            else if(haveUser(user, pwd)) {
                //check the virification code
                if(verifyCode.getText().equalsIgnoreCase(vrCode))
                {
                    //set visible to false
                    this.setVisible(false);
                    //show the game frame
                    GameJframe j = new GameJframe();
                }
                else
                {
                    //show warning dialog
                    showJDialog("  wrong verification code");
                    //generate new vrcode
                    generateNewVrCode();
                    //set the vrcode on jlabel
                    vrcodeJlabel.setText(vrCode);
                }
            }
            //wrong password or username
            else{
                //pop a window says login failed.
                showJDialog("  wrong username/password");
                //generate new vrcode
                generateNewVrCode();
                //set the vrcode on jlabel
                vrcodeJlabel.setText(vrCode);
            }
        }
    }

    private boolean haveUser(String user, String pwd) {
        //compare each data in users list, if user list have the user, return true
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getUserName().equals(user) && users.get(i).getPassword().equals(pwd))
                return true;
        }
        return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void generateNewVrCode()
    {
        vrCode = "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 5; i++)
        {
            int t = r.nextInt(chars.length);
            sb.append(chars[t]);
        }
        vrCode = sb.toString();
    }

    String vrCode = new String();
    JLabel loginJLabel = new JLabel(new ImageIcon("login.png"));
    JLabel registerJLabel = new JLabel(new ImageIcon("register.png"));
    JLabel vrcodeJlabel = new JLabel();
    JTextField username = new JTextField();
    JTextField verifyCode = new JTextField();
    JTextField passwordField = new JTextField();

    //String codeStr = CodeUtil.getCode();
    private static char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};
    Random r = new Random();
    static ArrayList<User> users = new ArrayList<>();
    static{
        users.add(new User("test", "123"));
        users.add(new User("test2", "1234"));
    }
}


class User{
    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }
    String userName;
    String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
