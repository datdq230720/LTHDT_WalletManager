/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.service;

import ptit.walletmanager.model.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author nhocd
 */
public class FileService {
    private static final String USER_FILE = "data/users.txt";
    private static final String HISTORY_FILE = "data/history.txt";

    public static Map<String, User> loadUsers() {
        Map<String, User> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                map.put(p[0], new User(p[0], p[1], Integer.parseInt(p[2]), Boolean.parseBoolean(p[3])));
            }
        } catch (Exception ignored) {}
        return map;
    }

    public static void saveUsers(Map<String, User> users) {
        try (PrintWriter pw = new PrintWriter(USER_FILE)) {
            for (User u : users.values()) {
                pw.println(u.getUsername() + "|" + u.getPassword() + "|" + u.getBalance() + "|" + u.isAdmin());
            }
        } catch (Exception ignored) {}
    }

    public static void saveHistory(List<Transaction> history) {
        try (PrintWriter pw = new PrintWriter("history.txt")) {
            for (Transaction t : history) {
                pw.println(t.toFile()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Transaction> loadHistory() {

        List<Transaction> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("history.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                Transaction t = Transaction.fromFile(line);

                if (t != null) {
                    list.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
