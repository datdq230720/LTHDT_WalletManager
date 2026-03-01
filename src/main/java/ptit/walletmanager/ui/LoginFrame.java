/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.service.*;
import ptit.walletmanager.model.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nhocd
 */
public class LoginFrame extends JFrame {
    WalletService service = new WalletService();
    JTextField userField = new JTextField();
    JPasswordField passField = new JPasswordField();

    public LoginFrame() {
        setTitle("Wallet Login");
        setSize(300,200);
        setLayout(new GridLayout(5,1,5,5));

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton forgotBtn = new JButton("Forgot Password");

        add(userField);
        add(passField);
        add(loginBtn);
        add(registerBtn);
        add(forgotBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng tạm khóa!");
        });
        forgotBtn.addActionListener(e -> forgot());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void login() {
        String u = userField.getText().trim();
        String p = new String(passField.getPassword());

        String result = service.validateUser(u, p);
        if (!result.equals("OK")) {
            JOptionPane.showMessageDialog(this, result);
            return;
        }

        User user = service.users.get(u);
        
        Session.login(user);
        
        dispose();
        if (user.isAdmin())
            new AdminFrame(service, user);
        else
            new DashboardFrame(service, user);
    }

    void forgot() {
        String user = JOptionPane.showInputDialog("Nhập username:");
        if (!service.users.containsKey(user)) {
            JOptionPane.showMessageDialog(this, "User không tồn tại");
            return;
        }

        OTPService.generateOTP();
        String otp = JOptionPane.showInputDialog("Nhập OTP:");

        if (!OTPService.verify(otp)) {
            JOptionPane.showMessageDialog(this, "OTP sai!");
            return;
        }

        String newPass = JOptionPane.showInputDialog("Mật khẩu mới:");
        service.resetPassword(user, newPass);
        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
    }
}
