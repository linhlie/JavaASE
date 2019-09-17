/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ase;

import static ase.AES.eTable;
import static ase.AES.invSBox;
import static ase.AES.lTbale;
import static ase.AES.roundKey;

/**
 *
 * @author thonglt
 */
public class Decrypt {
    private String key;
    private String plainText;

    public Decrypt() {
    }

    public Decrypt(String key, String plainText) {
        this.key = key;
        this.plainText = plainText;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
    
    
    static int[][] invSubByte(int[][] s) {
        int[][] sb = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb[i][j] = invSBox[s[i][j]];
            }
        }
        return sb;
    }

    static int[][] invShiftRows(int[][] s) {
        int[] sr = new int[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                sr[c] = s[r][(4 - r + c) % 4];
            }
            for (int c = 0; c < 4; c++) {
                s[r][c] = sr[c];
            }
        }
        return s;
    }

    static int[][] invMixColumns(int[][] s, int[][] state) { 
        int[][] rs = new int[4][4];

        for (int i = 0; i < 4; i++) {
            //E(L(04)+L(0E)) XOR E(L(66)+L(0B)) XOR E(L(81)+L(0D)) XOR E(L(E5)+L(09))
            
            if (s[0][i] == 0 || s[1][i] == 0 || s[2][i] == 0 || s[3][i] == 0) {
                if (s[0][i] == 0) {
                rs[0][i] = eTable[(lTbale[s[1][i]] + lTbale[state[0][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[0][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[0][3]]) % 0xff];

            rs[1][i] =  eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[1][3]]) % 0xff];

            rs[2][i] =  eTable[(lTbale[s[1][i]] + lTbale[state[2][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[2][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];

            rs[3][i] = eTable[(lTbale[s[1][i]] + lTbale[state[3][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[3][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
            }
            
            if (s[1][i] == 0) {
                rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff]
                    ^  eTable[(lTbale[s[2][i]] + lTbale[state[0][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[0][3]]) % 0xff];

            rs[1][i] = eTable[(lTbale[s[0][i]] + lTbale[state[1][0]]) % 0xff]
                    ^  eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[1][3]]) % 0xff];

            rs[2][i] = eTable[(lTbale[s[0][i]] + lTbale[state[2][0]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[2][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];

            rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[3][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
            }
            
            if (s[2][i] == 0) {
                rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[0][1]]) % 0xff]
                    ^  eTable[(lTbale[s[3][i]] + lTbale[state[0][3]]) % 0xff];

            rs[1][i] = eTable[(lTbale[s[0][i]] + lTbale[state[1][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                    ^  eTable[(lTbale[s[3][i]] + lTbale[state[1][3]]) % 0xff];

            rs[2][i] = eTable[(lTbale[s[0][i]] + lTbale[state[2][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[2][1]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];

            rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[3][1]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
            }
            
            if (s[3][i] == 0) {
                rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[0][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[0][2]]) % 0xff];
                   
            rs[1][i] = eTable[(lTbale[s[0][i]] + lTbale[state[1][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff];

            rs[2][i] = eTable[(lTbale[s[0][i]] + lTbale[state[2][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[2][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[2][2]]) % 0xff];

            rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[3][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[3][2]]) % 0xff];
            }
            }else{
            
            rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[0][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[0][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[0][3]]) % 0xff];

            rs[1][i] = eTable[(lTbale[s[0][i]] + lTbale[state[1][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[1][3]]) % 0xff];

            rs[2][i] = eTable[(lTbale[s[0][i]] + lTbale[state[2][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[2][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[2][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];

            rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff]
                    ^ eTable[(lTbale[s[1][i]] + lTbale[state[3][1]]) % 0xff]
                    ^ eTable[(lTbale[s[2][i]] + lTbale[state[3][2]]) % 0xff]
                    ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
            }
            
        }

        return rs;
    }

     public int[][] keyrch(int[][] k, int index) {
        int[][] rs = new int[4][4];
        rs = k;
        for (int i = 1; i <= index; i++) {
            rs = roundKey(rs, i);
        }

        return rs;
    }
}
