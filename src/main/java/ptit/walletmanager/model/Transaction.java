/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.model;

import java.time.LocalDateTime;
/**
 *
 * @author nhocd
 */
public class Transaction {
    public String from, to, type;
    public int amount;
    public LocalDateTime time;

    public Transaction(String f, String t, int a, String type) {
        from = f; to = t; amount = a; this.type = type;
        time = LocalDateTime.now();
    }

    public String toFile() {
        return time + "|" + from + "|" + to + "|" + amount + "|" + type;
    }
}
