/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ptit.walletmanager.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linhth
 */
public class HelperService {

    WalletService service;

    public HelperService(WalletService service) {
        this.service = service;
    }
    
    // ===== Create User =====
    public void createUser(JFrame parent, User admin){

        if (admin.getBalance() < 10) {
            JOptionPane.showMessageDialog(parent, "Không đủ điểm!");
            return;
        }

        String username = JOptionPane.showInputDialog("Username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Username không được để trống");
            return;
        }
        
        if (service.users.containsKey(username)) {
            JOptionPane.showMessageDialog(parent, "User đã tồn tại!");
            return;
        }

        String password = JOptionPane.showInputDialog("Password:");
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Password không được để trống");
            return;
        }

        service.users.put(username, 
            new User(username, HashUtil.hash(password), 10, false));

        admin.setBalance(admin.getBalance() - 10);
        FileService.saveUsers(service.users);

        JOptionPane.showMessageDialog(parent, "Tạo user thành công!");
    }
    
    // ===== Transfer Balance =====
    public void transferBalance(JFrame parent, User from, String to) {
        if (to == null || to.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Không được để trống người nhận!");
            return;
        }

        if (!service.users.containsKey(to)) {
            JOptionPane.showMessageDialog(parent, "Người nhận không tồn tại!");
            return;
        }

        if (to.equals(from.getUsername())) {
            JOptionPane.showMessageDialog(parent, "Không thể chuyển cho chính mình!");
            return;
        }

        String amtStr = JOptionPane.showInputDialog(parent, "Số tiền chuyển:");
        if (amtStr == null) {
            return;
        }

        try {
            int amt = Integer.parseInt(amtStr);
            if (amt <= 0) {
                JOptionPane.showMessageDialog(parent, "Số tiền phải > 0");
                return;
            }
            
            if (from.getBalance() < amt) {
                JOptionPane.showMessageDialog(parent, "Số tiền hiện tại của bạn không đủ để thực hiện");
                return;
            }

            OTPService.generateOTP();
            String otp = JOptionPane.showInputDialog(parent, "Nhập OTP:");

            if (!OTPService.verify(otp)) {
                JOptionPane.showMessageDialog(parent, "OTP sai!");
                return;
            }

            if (service.transfer(from, to, amt)) {
                JOptionPane.showMessageDialog(parent, "Chuyển tiền thành công!");
            } else {
                JOptionPane.showMessageDialog(parent, "Thất bại!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Số không hợp lệ!");
        }
    }
    
    // ===== Show Transaction =====
    public void showTransactionTable(JFrame parent, String username) {

        List<Transaction> list = service.getHistoryByUser(username);

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Không có lịch sử!");
            return;
        }

        String[] columns = {"From", "To", "Amount", "Type", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getFrom(),
                    t.getTo(),
                    t.getAmount(),
                    t.getType(),
                    t.getTime()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(parent,
                scrollPane,
                "Lịch sử giao dịch",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void changePasswordWithOTP(JFrame parent, User user) {
        String oldPass = JOptionPane.showInputDialog(parent, "Nhập mật khẩu cũ:");
        if (oldPass == null) return;

        if (!service.checkPassword(user.getUsername(), oldPass)) {
            JOptionPane.showMessageDialog(parent, "Mật khẩu cũ sai!");
            return;
        }

        OTPService.generateOTP();
        String otp = JOptionPane.showInputDialog(parent, "Nhập OTP:");

        if (!OTPService.verify(otp)) {
            JOptionPane.showMessageDialog(parent, "OTP sai!");
            return;
        }

        String newPass = JOptionPane.showInputDialog(parent, "Nhập mật khẩu mới:");
        if (newPass == null || newPass.length() < 3) {
            JOptionPane.showMessageDialog(parent, "Mật khẩu không hợp lệ!");
            return;
        }

        service.resetPassword(user.getUsername(), newPass);
        JOptionPane.showMessageDialog(parent, "Đổi mật khẩu thành công!");
    }
}
