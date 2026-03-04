/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.model.User;
import ptit.walletmanager.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 *
 * @author nhocd
 */
public class UserTableFrame extends JFrame {
    WalletService service;
    User admin;
    JTable table;
    DefaultTableModel model;
    private HelperService helper;

    public UserTableFrame(WalletService service, User admin) {
        this.service = service;
        this.admin = admin;

        setTitle("User Management");
        setSize(700, 400);
        setLayout(new BorderLayout());

        // ===== Table =====
        String[] cols = {"Username", "Balance", "Role"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        loadUsers();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel panel = new JPanel();

        JButton createBtn = new JButton("Create user (-10đ)");
        JButton deleteBtn = new JButton("Delete");
        JButton resetBtn = new JButton("Reset Password");
        JButton transferBtn = new JButton("Transfer Money");
        JButton historyBtn = new JButton("View History");

        panel.add(createBtn);
        panel.add(deleteBtn);
        panel.add(resetBtn);
        panel.add(transferBtn);
        panel.add(historyBtn);

        add(panel, BorderLayout.SOUTH);

        // ===== Actions =====

        // CREATE USER
        createBtn.addActionListener(e -> createUser());

        // DELETE USER
        deleteBtn.addActionListener(e -> deleteUser());

        // RESET PASSWORD
        resetBtn.addActionListener(e -> resetPassword());

        // ADMIN TRANSFER MONEY
        transferBtn.addActionListener(e -> transferMoney());

        // VIEW HISTORY
        historyBtn.addActionListener(e -> viewHistory());

        setVisible(true);
    }

        void loadUsers() {
        model.setRowCount(0);
        for (User u : service.users.values()) {
            model.addRow(new Object[]{
                    u.getUsername(),
                    u.getBalance(),
                    u.isAdmin() ? "ADMIN" : "USER"
            });
        }
    }

    String getSelectedUsername() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn user!");
            return null;
        }
        return model.getValueAt(row, 0).toString();
    }

    // ================= Create =================
    void createUser() {
        helper.createUser(this, admin);
    }
    
    // ================= DELETE =================
    void deleteUser() {
        String username = getSelectedUsername();
        if (username == null) return;

        if (username.equals(admin.getUsername())) {
            JOptionPane.showMessageDialog(this, "Không thể xóa chính mình!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn chắc chắn muốn xóa user " + username + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;
        
        if(service.deleteUser(username)){
            JOptionPane.showMessageDialog(this, "Đã xóa user!");
            loadUsers();
        }else{
            JOptionPane.showMessageDialog(this, "Xóa user thất bại!");
        }
        
    }

    // ================= RESET PASSWORD =================
    void resetPassword() {
        String username = getSelectedUsername();
        if (username == null) return;

        String newPass = JOptionPane.showInputDialog("Mật khẩu mới:");
        if (newPass == null || newPass.length() < 3) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ!");
            return;
        }

        service.resetPassword(username, newPass);
        JOptionPane.showMessageDialog(this, "Đã reset mật khẩu!");
    }

    // ================= TRANSFER MONEY =================
    void transferMoney() {
        String to = getSelectedUsername();
        if (to == null) return;
        
        helper.transferBalance(this, admin, to);
        loadUsers();
    }

    // ================= VIEW HISTORY =================
    void viewHistory() {
        String username = getSelectedUsername();
        helper.showTransactionTable(this, username);
    }
}
