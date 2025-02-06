import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

import org.jdesktop.swingx.*;

class GFG {
    public static Socket clientSocket;
    public static PrintWriter out;
    public static ObjectOutputStream objOut;
    public static BufferedReader in;

    public static void main(String[] args) throws IOException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        // Creating a new frame using JFrame
        JFrame f = new JFrame("Echo");
        f.setIconImage(ImageIO.read(new File("resources/img/Echo.png")));

        f.setMinimumSize(new Dimension(250, 250));
        f.setSize(width / 5, height / 3);
        f.setResizable(true);
        f.setLocationRelativeTo(null);
        f.setLayout(new GridBagLayout());

        f.getContentPane().setBackground(new java.awt.Color(25, 25, 25));

        // Make the app close with the window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH; // Expand components with the window
        gbc.weightx = 1.0; // Allow horizontal expansion

        JButton connectButton = new JButton("CONNECT");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        f.add(connectButton, gbc);

        JXTextField username = new JXTextField("", 15);
        username.setPreferredSize(null);
        username.setPrompt("Username...");
        gbc.gridy = 1;
        f.add(username, gbc);

        JPasswordField password = new JPasswordField(15);
        password.setPreferredSize(null);
        gbc.gridy = 2;
        f.add(password, gbc);

        JXTextField ip = new JXTextField("", 15);
        ip.setPreferredSize(null);
        ip.setPrompt("IP Address...");
        gbc.gridy = 3;
        f.add(ip, gbc);

        JXTextField port = new JXTextField("", 15);
        port.setPreferredSize(null);
        port.setPrompt("Port...");
        gbc.gridy = 4;
        f.add(port, gbc);

        connectButton.addActionListener(e -> {
            String host = ip.getText();
            String portNumber = port.getText();
            String user = username.getText();
            String pass = new String(password.getPassword());

            String hostAddress = host + ":" + portNumber;
            System.out.println("Attempting to connect to server (" + hostAddress + ")...");
            try {
                connectToServer(host, portNumber, user, pass);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        f.setVisible(true);
    }

    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static boolean checkHost(String host) {
        return (host != null && !host.isEmpty()) && (host.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$") || host.matches("^:?[a-z]{2,}\\.[a-z]{2,6}$") || host.matches("^:?[a-z]{2,}\\.[a-z]{2,}\\.[a-z]{2,6}$"));
    }

    public static boolean checkPort(String port) {
        return port.matches("^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$");
    }

    public static void connectToServer(String host, String port, String user, String pass) throws IOException {
        if (!checkHost(host)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid IP address", "Invalid IP", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (port == null || port.isEmpty()) {
            port = "443";
        }
        if (!checkPort(port)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid port number", "Invalid Port", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            clientSocket = new Socket(host, Integer.parseInt(port));
            System.out.println("Connected to server (" + host + ":" + port + ")");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            HashMap<String, String> map = new HashMap<>();
            map.put("action", "login");
            map.put("username", user);
            map.put("password", pass);
            System.out.println(Arrays.toString(map.entrySet().toArray()));
            // String resp = objOut.writeObject(new Array[]{map});
            String response = sendMessage("hello server");
            System.out.println("Server: " + response);
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    public static String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }
}
