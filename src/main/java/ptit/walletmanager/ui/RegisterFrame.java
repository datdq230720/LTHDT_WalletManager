/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.service.WalletService;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nhocd
 */
public class RegisterFrame extends JFrame {
    JTextField userField = new JTextField();
    JPasswordField passField = new JPasswordField();

    public RegisterFrame(WalletService service) {
        
        setTitle("Register");
        setSize(500,200);
        setLayout(new GridLayout(3,1));
        
        JButton btn = new JButton("Create");

        add(userField);
        add(passField);
        add(btn);

        btn.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword());

            if (u.length() < 3 || p.length() < 3) {
                JOptionPane.showMessageDialog(this, "Username & password >= 3 ký tự");
                return;
            }

            if (service.register(u, p)) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User đã tồn tại!");
            }
        });

        setVisible(true);
    }
}
