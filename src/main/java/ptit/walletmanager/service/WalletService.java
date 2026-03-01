/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.service;

import ptit.walletmanager.model.*;
import java.util.*;
/**
 *
 * @author nhocd
 */
public class WalletService {
    
    public Map<String, User> users;
    public List<String> history;

    public WalletService() {
        users = FileService.loadUsers();
        history = FileService.loadHistory();

        // tạo admin mặc định nếu chưa có
        if (!users.containsKey("admin")) {
            users.put("admin", new User("admin", HashUtil.hash("123"), 1000, true));
            FileService.saveUsers(users);
        }
    }

    public String validateUser(String u, String p) {
        if (u.isEmpty() || p.isEmpty()) return "Không được để trống!";
        if (!users.containsKey(u)) return "User không tồn tại!";
        if (!users.get(u).getPassword().equals(HashUtil.hash(p))) return "Sai mật khẩu!";
        return "OK";
    }

    public boolean register(String u, String p) {
        if (users.containsKey(u) || u.length() < 3 || p.length() < 3) return false;
        users.put(u, new User(u, HashUtil.hash(p), 100, false));
        FileService.saveUsers(users);
        saveHistory(users.get("admin").getUsername() + "->" + "CreateAccount["+u+"]" + ":10");
        return true;
    }   

    public boolean transfer(User from, String to, int amount) {
        if (!users.containsKey(to) || amount <= 0 || from.getBalance() < amount) return false;

        User receiver = users.get(to);
        from.setBalance(from.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        FileService.saveUsers(users);
        saveHistory(from.getUsername() + "->" + to + ":" + amount);
        return true;
    }

    public boolean resetPassword(String user, String newPass) {
        if (!users.containsKey(user) || newPass.length() < 3) return false;
        users.get(user).setPassword(HashUtil.hash(newPass));
        FileService.saveUsers(users);
        return true;
    }
    
    public boolean deleteUser(String username) {
        User u = users.remove(username);
        if (u != null) {
            // hoàn điểm về admin
            users.get("admin").setBalance(users.get("admin").getBalance() + u.getBalance());
            FileService.saveUsers(users);
            saveHistory("DeleteAccount["+u.getUsername()+"]" + "->" + users.get("admin").getUsername() + ":" + u.getBalance());
            return true;
        }
        return false;
    }
    
    public List<String> getUserHistory(String username) {
        return history.stream()
                .filter(h -> h.contains(username))
                .toList();
    }
    
    public void saveHistory(String mess){
        history.add(mess);
        FileService.saveHistory(history);

    }
}
