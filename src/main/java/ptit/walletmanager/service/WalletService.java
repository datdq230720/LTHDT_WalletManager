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
    private List<Transaction> history;

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
        Transaction trans = new Transaction(users.get("admin").getUsername(), u, 10, "CREATE");
        saveHistory(trans);
        return true;
    }   

    public boolean transfer(User from, String to, int amount) {
        if (!users.containsKey(to) || amount <= 0 || from.getBalance() < amount) return false;

        User receiver = users.get(to);
        from.setBalance(from.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        FileService.saveUsers(users);
        Transaction trans = new Transaction(from.getUsername(), to, amount, "TRANSFER");
        saveHistory(trans);
        return true;
    }

    public boolean resetPassword(String user, String newPass) {
        if (!users.containsKey(user) || newPass.length() < 3) return false;
        users.get(user).setPassword(HashUtil.hash(newPass));
        FileService.saveUsers(users);
        return true;
    }
    
    public boolean checkPassword(String username, String rawPassword) {
        User u = users.get(username);
        if (u == null) return false;

        return u.getPassword().equals(HashUtil.hash(rawPassword));
    }
    
    public boolean deleteUser(String username) {
        User u = users.remove(username);
        if (u != null) {
            // hoàn điểm về admin
            users.get("admin").setBalance(users.get("admin").getBalance() + u.getBalance());
            FileService.saveUsers(users);
            Transaction trans = new Transaction(u.getUsername(), users.get("admin").getUsername(), u.getBalance(), "DELETE");
            saveHistory(trans);
            return true;
        }
        return false;
    }

    public void saveHistory(Transaction trans) {
        history.add(trans);
        FileService.saveHistory(history);
    }
    
    public List<Transaction> getHistoryByUser(String username) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction t : history) {
            if (t.getFrom().equals(username) || 
                t.getTo().equals(username)) {

                result.add(t);
            }
        }

        return result;
    }
}
