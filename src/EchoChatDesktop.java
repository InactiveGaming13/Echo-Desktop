import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class EchoChatDesktop extends JFrame implements WindowListener, ActionListener, MouseListener {
    public EchoChatDesktop() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        setTitle("Echo-Chat");

        try {
            setIconImage(ImageIO.read(new File("resources/img/Echo.png")));
        } catch (IOException _) {}

        setMinimumSize(new Dimension(250 ,250));
        setSize((int) (width / 1.3), (int) (height / 1.3));
        setResizable(true);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new java.awt.Color(25, 25, 25));

        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.GRAY));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.BLACK));
        setDefaultLookAndFeelDecorated(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed");
        System.out.println("Performed by: " + ((JButton)e.getSource()).getText());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse Exited");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Window Opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Window Closed");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("Window Iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("Window Deiconified");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("Window Activated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("Window Deactivated");
    }
}
