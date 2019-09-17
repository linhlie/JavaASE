/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ase;

import static ase.AES.eTable;
import static ase.AES.lTbale;
import static ase.AES.sBox;

/**
 *
 * @author thonglt
 */
public class Encrypt {
    private String key;
    private String plainText;

    public Encrypt() {
    }

    public Encrypt(String key, String plainText) {
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
    
    
    
    public int[][] addRoundKey(int[][] key, int[][] plainText) {
        int[][] rs = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rs[i][j] = key[i][j] ^ plainText[i][j];
            }
        }
        return rs;
    }

    public int[][] subByte(int[][] s) {
        int[][] sb = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb[i][j] = sBox[s[i][j]];
            }
        }
        return sb;
    }

    public int[][] shiftRows(int[][] s) {
        int[] sr = new int[4];
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                sr[c] = s[r][(c + r) % 4];
            }
            for (int c = 0; c < 4; c++) {
                s[r][c] = sr[c];
            }
        }
        return s;
    }

    public int[][] MixColumns(int[][] s, int[][] state) { // 's' is the main State matrix, 'ss' is a temp matrix of the same dimensions as 's'.
        int[][] rs = new int[4][4];

        for (int i = 0; i < 4; i++) {
            if (s[0][i] == 0) {
                if (s[1][i] == 0) {
                    rs[0][i] = s[2][i] ^ s[3][i];
                } else {
                    rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff] ^ s[2][i] ^ s[3][i];
                }
            } else {
                if (s[1][i] == 0) {
                    rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff] ^ s[2][i] ^ s[3][i];
                } else {
                    rs[0][i] = eTable[(lTbale[s[0][i]] + lTbale[state[0][0]]) % 0xff]
                            ^ eTable[(lTbale[s[1][i]] + lTbale[state[0][1]]) % 0xff] ^ s[2][i] ^ s[3][i];
                }
            }

            if (s[1][i] == 0) {
                if (s[2][i] == 0) {
                    rs[1][i] = s[0][i] ^ s[3][i];
                } else {
                    rs[1][i] = s[0][i]
                            ^ eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff] ^ s[3][i];
                }
            } else {
                if (s[2][i] == 0) {
                    rs[1][i] = s[0][i] ^ eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                            ^ s[3][i];
                } else {
                    rs[1][i] = s[0][i] ^ eTable[(lTbale[s[1][i]] + lTbale[state[1][1]]) % 0xff]
                            ^ eTable[(lTbale[s[2][i]] + lTbale[state[1][2]]) % 0xff] ^ s[3][i];
                }
            }

            if (s[2][i] == 0) {
                if (s[3][i] == 0) {
                    rs[2][i] = s[0][i] ^ s[1][i];
                } else {
                    rs[2][i] = s[0][i] ^ s[1][i] ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];
                }
            } else {
                if (s[3][i] == 0) {
                    rs[2][i] = s[0][i] ^ s[1][i] ^ eTable[(lTbale[s[2][i]]
                            + lTbale[state[2][2]]) % 0xff];
                } else {
                    rs[2][i] = s[0][i] ^ s[1][i] ^ eTable[(lTbale[s[2][i]]
                            + lTbale[state[2][2]]) % 0xff] ^ eTable[(lTbale[s[3][i]] + lTbale[state[2][3]]) % 0xff];
                }
            }

            if (s[0][i] == 0) {
                if (s[3][i] == 0) {
                    rs[3][i] = s[1][i] ^ s[2][i];
                } else {
                    rs[3][i] = s[1][i]
                            ^ s[2][i] ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
                }
            } else {
                if (s[3][i] == 0) {
                    rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff] ^ s[1][i]
                            ^ s[2][i];
                } else {
                    rs[3][i] = eTable[(lTbale[s[0][i]] + lTbale[state[3][0]]) % 0xff] ^ s[1][i]
                            ^ s[2][i] ^ eTable[(lTbale[s[3][i]] + lTbale[state[3][3]]) % 0xff];
                }
            }

        }

        return rs;
    }

}
