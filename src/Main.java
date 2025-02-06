import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.net.Socket;

class GFG {
    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame("Echo");
        f.setIconImage(ImageIO.read(new File("Echo.png")));

        f.setSize(500, 500);
        f.setResizable(true);
        f.setLocationRelativeTo(null);
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH; // Expand components with the window
        gbc.weightx = 1.0; // Allow horizontal expansion

        JButton connectButton = new JButton("CONNECT");
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.1;
        f.add(connectButton, gbc);

        JTextField username = new JTextField(15);
        username.setPreferredSize(null);
        gbc.gridy = 1;
        f.add(username, gbc);

        JPasswordField password = new JPasswordField(15);
        password.setPreferredSize(null);
        gbc.gridy = 2;
        f.add(password, gbc);

        JTextField ip = new JTextField("echo.disbroad.com", 15);
        ip.setPreferredSize(null);
        gbc.gridy = 3;
        f.add(ip, gbc);

        JTextField port = new JTextField("443", 15);
        port.setPreferredSize(null);
        gbc.gridy = 4;
        f.add(port, gbc);

        JButton exitButton = new JButton("EXIT");
        gbc.gridy = 5; gbc.weighty = 0.1;
        f.add(exitButton, gbc);

        connectButton.addActionListener(e -> {
            String host = ip.getText();
            String portNumber = port.getText();
            String user = username.getText();
            String pass = new String(password.getPassword());

            String hostAddress = "https://" + host + ":" + portNumber;
            System.out.println("Attempting to connect to server (" + hostAddress + ")...");
            connectToServer(host, portNumber, user, pass);
        });

        exitButton.addActionListener(e -> {
            System.out.println("Exiting the program, Have a nice day!");
            System.exit(0);
        });

        f.setVisible(true);
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
