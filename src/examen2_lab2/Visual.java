/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Visual extends JFrame {

    private PSNUsers psn;
    private JTextArea textArea;
    private JTextField usernameField, gameField, trophyField, descripcionField;
    private JComboBox<String> trophyTypeComboBox;

    public Visual() throws IOException {
        psn = new PSNUsers();

        setTitle("PREMIOS 2000");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setBackground(Color.LIGHT_GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton addUserButton = Boton("Agregar Usuario");
        JButton deactivateUserButton = Boton("Desactivar Usuario");
        JButton addTrophyButton = Boton("Agregar Trofeo");
        JButton playerInfoButton = Boton("Ver Información del Jugador");
        JButton salirButton = Boton("Salir");

        buttonPanel.add(addUserButton);
        buttonPanel.add(deactivateUserButton);
        buttonPanel.add(addTrophyButton);
        buttonPanel.add(playerInfoButton);
        buttonPanel.add(salirButton);

        add(buttonPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Nombre de Usuario:");
        userLabel.setFont(labelFont);
        inputPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel gameLabel = new JLabel("Nombre del juego:");
        gameLabel.setFont(labelFont);
        inputPanel.add(gameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gameField = new JTextField();
        gameField.setFont(fieldFont);
        gameField.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(gameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel trophyLabel = new JLabel("Nombre del trofeo:");
        trophyLabel.setFont(labelFont);
        inputPanel.add(trophyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        trophyField = new JTextField();
        trophyField.setFont(fieldFont);
        trophyField.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(trophyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel typeLabel = new JLabel("Tipo de trofeo:");
        typeLabel.setFont(labelFont);
        inputPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        trophyTypeComboBox = new JComboBox<>(new String[]{"PLATINO", "ORO", "PLATA", "BRONCE"});
        trophyTypeComboBox.setFont(fieldFont);
        trophyTypeComboBox.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(trophyTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel descLabel = new JLabel("Descripción del juego:");
        descLabel.setFont(labelFont);
        inputPanel.add(descLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        descripcionField = new JTextField();
        descripcionField.setFont(fieldFont);
        descripcionField.setPreferredSize(new Dimension(300, 30));
        inputPanel.add(descripcionField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setPreferredSize(new Dimension(200, 250));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.SOUTH);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    try {
                        psn.addUser(username);
                        usernameField.setText("");
                        gameField.setText("");
                        trophyField.setText("");
                        descripcionField.setText("");
                        textArea.setText("");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Visual.this, "Error al agregar usuario: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Visual.this, "Nombre de usuario inválido.");
                }
            }
        });

        deactivateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    try {
                        psn.deactivateUser(username);
                        usernameField.setText("");
                        gameField.setText("");
                        trophyField.setText("");
                        descripcionField.setText("");
                        textArea.setText("");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Visual.this, "Error al desactivar usuario: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Visual.this, "Nombre de usuario inválido.");
                }
            }
        });
        addTrophyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String game = gameField.getText().trim();
                String trophy = trophyField.getText().trim();
                String type = (String) trophyTypeComboBox.getSelectedItem();
                String descripcion = descripcionField.getText().trim();

                if (!username.isEmpty() && !game.isEmpty() && !trophy.isEmpty() && type != null && !descripcion.isEmpty()) {
                    try {
                        Trophy trophyType = Trophy.valueOf(type);
                        psn.addTrophieTo(username, game, trophy, trophyType, descripcion);
                        usernameField.setText("");
                        gameField.setText("");
                        trophyField.setText("");
                        descripcionField.setText("");
                        textArea.setText("");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Visual.this, "Error al agregar trofeo: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Visual.this, "Todos los campos deben estar completos.");
                }
            }
        });

        playerInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    try {
                        String info = psn.playerInfo(username);
                        
                        usernameField.setText("");
                        gameField.setText("");
                        trophyField.setText("");
                        descripcionField.setText("");
                        if (info == null){
                            textArea.setText("No hay información disponible.");
                        }else{
                            textArea.setText(info + "\n");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Visual.this, "Error al obtener información del usuario: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(Visual.this, "Nombre de usuario inválido.");
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    private JButton Boton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(175, 34, 34));
        button.setPreferredSize(new Dimension(200, 40));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Visual().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
