/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.model;

/**
 *
 * @author nhocd
 */
public class User {
    private String username;
    private String password;
    private int balance;
    private boolean admin;

    public User(String u, String p, int b, boolean a) {
        username = u;
        password = p;
        balance = b;
        admin = a;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getBalance() { return balance; }
    public boolean isAdmin() { return admin; }

    public void setBalance(int b) { balance = b; }
    public void setPassword(String p) { password = p; }
}
