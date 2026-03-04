/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.service.*;
import ptit.walletmanager.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminFrame extends JFrame {

    private HelperService helper;

    public AdminFrame(WalletService service, User admin) {

        helper = new HelperService(service);

        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(400, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Main Panel =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ===== Header =====
        JLabel title = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(52, 73, 94));

        JLabel balance = new JLabel("Balance: " + admin.getBalance(), SwingConstants.CENTER);
        balance.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel header = new JPanel(new GridLayout(2,1));
        header.setOpaque(false);
        header.add(title);
        header.add(balance);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JButton createBtn = createStyledButton("Create User (-10đ)");
        JButton deleteBtn = createStyledButton("Delete User");
        JButton listBtn = createStyledButton("User List");
        JButton changePassBtn = createStyledButton("Change Password (OTP)");
        JButton logoutBtn = createStyledButton("Logout");

        buttonPanel.add(createBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(listBtn);
        buttonPanel.add(changePassBtn);
        buttonPanel.add(logoutBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ===== Events =====

        createBtn.addActionListener(e -> helper.createUser(this, admin));

        deleteBtn.addActionListener(e -> {
            String u = JOptionPane.showInputDialog("User cần xoá:");
            if (u == null || !service.users.containsKey(u)) return;

            admin.setBalance(admin.getBalance() + service.users.get(u).getBalance());
            service.users.remove(u);
            FileService.saveUsers(service.users);

            JOptionPane.showMessageDialog(this, "Đã xoá!");
            balance.setText("Balance: " + admin.getBalance());
        });

        listBtn.addActionListener(e -> new UserTableFrame(service, admin));

        // ⭐ Change Password with OTP
        changePassBtn.addActionListener(e -> {
            helper.changePasswordWithOTP(this, admin);
        });

        logoutBtn.addActionListener(e -> {
            Session.logout();
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // ===== Custom Button Style =====
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}