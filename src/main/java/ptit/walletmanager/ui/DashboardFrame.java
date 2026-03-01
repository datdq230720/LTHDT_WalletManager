/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.service.HelperService;
import ptit.walletmanager.service.*;
import ptit.walletmanager.model.*;

import javax.swing.*;
/**
 *
 * @author nhocd
 */
public class DashboardFrame extends JFrame {
    
     public DashboardFrame(WalletService service, User user) {
        setTitle("User Dashboard - " + user.getUsername());
        setSize(300,250);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel balance = new JLabel("Balance: " + user.getBalance());

        JButton transfer = new JButton("Transfer");
        JButton history = new JButton("History");
        
        JButton logoutBtn = new JButton("Logout");

        add(balance);
        add(transfer);
        add(history);
        add(logoutBtn);
        
        HelperService helper = new HelperService(service);

        transfer.addActionListener(e -> {
            String to = JOptionPane.showInputDialog("To:");
            helper.transferBalance(this, user, to);
        });

        history.addActionListener(e -> {
            
            String username = user.getUsername();
            if (username == null) return;

            StringBuilder sb = new StringBuilder();

            for (String h : service.getUserHistory(username)) {
                if (h.contains(username)) {
                    sb.append(h).append("\n");
                }
            }
            if (sb.length() == 0) sb.append("Không có lịch sử!");
            JOptionPane.showMessageDialog(this, sb.toString());
        });
        
        
        logoutBtn.addActionListener(e -> {
            Session.logout();
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
