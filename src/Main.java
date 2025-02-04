import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.net.Socket;
import java.util.Arrays;

class GFG {
    public static void main(String[] args) throws IOException {
        // Creating a new frame using JFrame
        JFrame f = new JFrame();

        // Creating an instance of JButton
        JButton connectButton = new JButton("CONNECT");

        // x axis, y axis, width, height
        connectButton.setBounds(150, 75, 100, 25);

        JTextField username = new JTextField();

        username.setBounds(125, 150, 150, 25);

        JPasswordField password = new JPasswordField();

        password.setBounds(125, 175, 150, 25);

        JTextField ip = new JTextField("echo.disbroad.com");

        ip.setBounds(125, 200, 150, 25);

        JTextField port = new JTextField("443");

        port.setBounds(125, 225, 150, 25);

        JButton exitButton = new JButton("EXIT");

        exitButton.setBounds(150, 275, 100, 25);

        // Adding action listener to the button
        connectButton.addActionListener(e -> {
            String host = ip.getText();
            String portNumber = port.getText();
            String user = username.getText();
            String pass = Arrays.toString(password.getPassword());

            String hostAddress = "https://" + host + ":" + portNumber;
            System.out.println("Attempting to connect to server (" + hostAddress + ")...");
            connectToServer(host, portNumber, user, pass);
        });

        exitButton.addActionListener(e -> {
            System.out.println("Exiting the program, Have a nice day!");
            System.exit(0);
        });

        // Adding button to the frame
        f.add(connectButton);
        f.add(exitButton);
        f.add(username);
        f.add(password);
        f.add(ip);
        f.add(port);

        // Setting Frame width and height
        f.setSize(500, 500);

        // Using no layout managers
        f.setLayout(null);

        // Making the frame visible
        f.setVisible(true);

        f.setTitle("Echo");
        f.setIconImage(ImageIO.read(new File("res/Echo.png")));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static boolean checkHost(String host) {
        return (host != null && !host.isEmpty()) && (host.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$") || host.matches("^:?[a-z]{2,}\\.[a-z]{2,6}$") || host.matches("^:?[a-z]{2,}\\.[a-z]{2,}\\.[a-z]{2,6}$"));
    }

    public static void connectToServer(String host, String port, String user, String pass) {
        if (!checkHost(host)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid IP address", "Invalid IP", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (port == null || port.isEmpty()) {
            port = "443";
        }
        if (!port.matches("^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid port number", "Invalid Port", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            System.out.println("Connected to server (" + host + ":" + port + ")");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            output.writeUTF(user);
            output.writeUTF(pass);

            String response = input.readUTF();
            System.out.println("Server response: " + response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}