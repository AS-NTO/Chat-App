package ChatClientApp.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton; // Add a JButton
    private PrintWriter writer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClient().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Message:");
        inputPanel.add(label, BorderLayout.WEST);

        inputField = new JTextField(30);
        inputPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);

        messageArea = new JTextArea(10, 30);
        messageArea.setEditable(false);
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        try {
            Socket socket = new Socket("localhost", 9095);
            OutputStream os = socket.getOutputStream();
            writer = new PrintWriter(os, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}
