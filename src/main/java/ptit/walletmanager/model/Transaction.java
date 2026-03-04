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
    private String from;
    private String to;
    private String type;
    private int amount;
    private LocalDateTime time;

    // Constructor khi tạo mới
    public Transaction(String from, String to, int amount, String type) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.type = type;
        this.time = LocalDateTime.now();
    }

    // Constructor khi đọc từ file
    public Transaction(LocalDateTime time, String from, String to, int amount, String type) {
        this.time = time;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.type = type;
    }

    // Parse từ 1 dòng txt
    public static Transaction fromFile(String line) {
        try {
            String[] parts = line.split("\\|");

            LocalDateTime time = LocalDateTime.parse(parts[0]);
            String from = parts[1];
            String to = parts[2];
            int amount = Integer.parseInt(parts[3]);
            String type = parts[4];

            return new Transaction(time, from, to, amount, type);

        } catch (Exception e) {
            return null;
        }
    }

    public String toFile() {
        return time + "|" + from + "|" + to + "|" + amount + "|" + type;
    }

    // Getter
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getType() { return type; }
    public int getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }
}
