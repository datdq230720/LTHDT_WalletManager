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
public class DashboardFrame extends JFrame {
    
    private WalletService service;
    private User user;
    private JLabel balanceLabel;
    private HelperService helper;

    public DashboardFrame(WalletService service, User user) {

        this.service = service;
        this.user = user;
        this.helper = new HelperService(service);

        setTitle("Wallet Dashboard - " + user.getUsername());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel welcome = new JLabel("Welcome, " + user.getUsername());
        welcome.setFont(new Font("Arial", Font.BOLD, 16));

        balanceLabel = new JLabel("Balance: " + user.getBalance() + " đ");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(welcome);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(balanceLabel);

        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton transferBtn = new JButton("Transfer Money");
        JButton historyBtn = new JButton("View History");
        JButton changePassBtn = new JButton("Change Password");

        centerPanel.add(transferBtn);
        centerPanel.add(historyBtn);
        centerPanel.add(changePassBtn);

        add(centerPanel, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel();
        JButton logoutBtn = new JButton("Logout");
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== ACTIONS =====

        transferBtn.addActionListener(e -> {
            String to = JOptionPane.showInputDialog(this, "Nhập username người nhận:");
            helper.transferBalance(this, user, to);
            refreshBalance();
        });

        historyBtn.addActionListener(e ->
                helper.showTransactionTable(this, user.getUsername())
        );

        changePassBtn.addActionListener(e -> changePassword());

        logoutBtn.addActionListener(e -> {
            Session.logout();
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // ===== REFRESH BALANCE =====
    private void refreshBalance() {
        balanceLabel.setText("Balance: " + user.getBalance() + " đ");
    }

    // ===== CHANGE PASSWORD WITH OTP =====
    private void changePassword() {
        helper.changePasswordWithOTP(this, user);
    }
}
