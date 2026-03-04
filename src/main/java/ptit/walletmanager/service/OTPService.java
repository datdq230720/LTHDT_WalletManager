/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptit.walletmanager.service;

import java.util.Random;
/**
 *
 * @author linhth
 */
public class OTPService {
    private static String otp;

    public static String generateOTP() {
        otp = String.valueOf(new Random().nextInt(900000) + 100000);
        System.out.println("OTP: " + otp);
        return otp;
    }

    public static boolean verify(String input) {
        return otp != null && otp.equals(input);
    }
}
