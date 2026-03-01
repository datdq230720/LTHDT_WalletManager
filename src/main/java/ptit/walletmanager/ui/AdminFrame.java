/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.ui;

import ptit.walletmanager.service.*;
import ptit.walletmanager.model.*;

import javax.swing.*;

/**
 *
 * @author nhocd
 */
public class AdminFrame extends JFrame {
    public AdminFrame(WalletService service, User admin) {
        setTitle("Admin Dashboard");
        setSize(300,300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton create = new JButton("Tạo user (-10đ)");
        JButton delete = new JButton("Xóa user");
        JButton list = new JButton("Danh sách user");
        JButton logoutBtn = new JButton("Logout");

        add(create);
        add(delete);
        add(list);
        add(logoutBtn);

        create.addActionListener(e -> {

            createUser(service, admin);
        });

        delete.addActionListener(e -> {
            String u = JOptionPane.showInputDialog("User cần xoá:");
            if (!service.users.containsKey(u)) return;

            admin.setBalance(admin.getBalance() + service.users.get(u).getBalance());
            service.users.remove(u);
            FileService.saveUsers(service.users);
            JOptionPane.showMessageDialog(this, "Đã xoá!");
        });

        list.addActionListener(e -> new UserTableFrame(service, admin));

        logoutBtn.addActionListener(e -> {
            Session.logout();
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
    
    void createUser(WalletService service, User admin){
        if (admin.getBalance() < 10) {
                JOptionPane.showMessageDialog(this, "Không đủ điểm!");
                return;
            }
            
            String username = JOptionPane.showInputDialog("Username:");
            if (username == null) return;          
            username = username.trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username không được để trống");
                return;
            }

            String password = JOptionPane.showInputDialog("Password:");

            if (password == null) return;            
            password = password.trim();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password không được để trống");
                return;
            }

            if (service.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thất bại!");
            }

            service.users.put(username, new User(username, HashUtil.hash(password), 10, false));
            admin.setBalance(admin.getBalance() - 10);
            FileService.saveUsers(service.users);
            JOptionPane.showMessageDialog(this, "Đã tạo!");
    }
}
