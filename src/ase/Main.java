/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ase;

import static ase.AES.invState;
import static ase.AES.roundKey;
import static ase.AES.stateMT;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author NGOC THACH
 */
public class Main {

    Encrypt encrypt = new Encrypt();
    Decrypt decrypt = new Decrypt();

    public int[][] encrypt(int[][] k, int[][] p) {
        int[][] rKey = k;
        int[][] addRoundKey = encrypt.addRoundKey(rKey, p);
        int[][] subByte = new int[4][4];
        int[][] shiftRows = new int[4][4];
        int[][] mixColumn = new int[4][4];
        for (int i = 1; i <= 9; i++) {
            subByte = encrypt.subByte(addRoundKey);
            shiftRows = encrypt.shiftRows(subByte);
            mixColumn = encrypt.MixColumns(shiftRows, stateMT);
            rKey = roundKey(rKey, i);
            addRoundKey = encrypt.addRoundKey(rKey, mixColumn);
        }
        subByte = encrypt.subByte(addRoundKey);
        shiftRows = encrypt.shiftRows(subByte);
        addRoundKey = encrypt.addRoundKey(roundKey(rKey, 10), shiftRows);

        return addRoundKey;
    }

    public int[][] decrypt(int[][] k, int[][] p) {
        //giai ma
        int[][] rKey = decrypt.keyrch(k, 10);
        int[][] invShiftRow = new int[4][4];
        int[][] invSubByte = new int[4][4];
        int[][] invMixColumn = new int[4][4];
        int[][] addRoundKey = new int[4][4];

        addRoundKey = encrypt.addRoundKey(rKey, p);
        invShiftRow = decrypt.invShiftRows(addRoundKey);
        invSubByte = decrypt.invSubByte(invShiftRow);
        rKey = decrypt.keyrch(k, 9);
        addRoundKey = encrypt.addRoundKey(rKey, invSubByte);

        for (int i = 8; i >= 1; i--) {
            invMixColumn = decrypt.invMixColumns(addRoundKey, invState);
            invShiftRow = decrypt.invShiftRows(invMixColumn);
            invSubByte = decrypt.invSubByte(invShiftRow);
            rKey = decrypt.keyrch(k, i);
            addRoundKey = encrypt.addRoundKey(rKey, invSubByte);
        }

        invMixColumn = decrypt.invMixColumns(addRoundKey, invState);
        invShiftRow = decrypt.invShiftRows(invMixColumn);
        invSubByte = decrypt.invSubByte(invShiftRow);
        rKey = k;
        addRoundKey = encrypt.addRoundKey(rKey, invSubByte);
        return addRoundKey;
    }

    public String MTtoText(int[][] mt) {
        String s = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s += (char) mt[j][i];
            }
        }
        return s;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Main m = new Main();
        String key = "=AES ENCRYPTION=";
        List<String> pEncrypt = new ArrayList<>();
        List<String> pDecrypt = new ArrayList<>();
        String cEncrypt = "";
        String cDecrypt = "";
        String text = sc.nextLine();
        long tEnStart = System.currentTimeMillis();
        //Encript
        int[][] kEncrypt = AES.makeKey(key);
        pEncrypt = AES.makePlainText(text);
        for (String pStr : pEncrypt) {
            int[][] rs = m.encrypt(kEncrypt, AES.maTrix(pStr));
            cEncrypt += m.MTtoText(rs);
        }
        System.out.println("Kêt quả mã hóa: " + cEncrypt);
        long tEnEnd = System.currentTimeMillis();
        System.out.println("Thời gian mã hóa = "+(tEnEnd - tEnStart)+" ms");
        long tDeStart = System.currentTimeMillis();
        //Decrypt
        int[][] kDecrypt = AES.makeKey(key);
        pDecrypt = AES.makePlainText(cEncrypt);
        for (String pStr : pDecrypt) {
            int[][] rs = m.decrypt(kDecrypt, AES.maTrix(pStr));
            cDecrypt += m.MTtoText(rs);
        }

        System.out.println("Kết quả giải mã: " + cDecrypt);
        long tDeEnd = System.currentTimeMillis();
        System.out.println("Thời gian giải mã = "+(tDeEnd - tDeStart)+" ms");

    }

}
