/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.service;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ptit.walletmanager.model.*;

/**
 *
 * @author nhocd
 */
public class HelperService {

    WalletService service;

    public HelperService(WalletService service) {
        this.service = service;
    }

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
}
