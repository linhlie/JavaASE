import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static String readFile(File f){
        String originalString="";
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                originalString +=line;
            }
            System.out.println(originalString);
            fr.close();
            br.close();
        } catch (Exception ex) {
            System.out.println("Loi doc file: "+ex);
        }
        return originalString;
    }
    public static String writeFile(String str,File f) {
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(str);
            fw.close();
        } catch (IOException ex) {
            System.out.println("Loi ghi file: " + ex);
        }
        return f.getAbsolutePath();
    }
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static void main(String[] args)
    {
        //đọc file cần mã hóa
        File f = new File("C:/Users/User/Desktop/input.txt");
        String originalString = readFile(f);

        //khai báo khóa
        final String secretKey = "=AES ENCRYPTION=";

        //Thực hiện mã hóa
        long tEnStart = System.currentTimeMillis();
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        long tEnEnd = System.currentTimeMillis();
        System.out.println("Thời gian mã hóa = "+(tEnEnd - tEnStart)+" ms");
        //Ghi chuỗi mã hóa ra file
        File f2 = new File("C:/Users/User/Desktop/output.txt");
        String path = writeFile(encryptedString,f2);

        //thực hiện đọc file mã hóa
        File readEncode = new File(path);
        String encode = readFile(readEncode);

        long tDeStart = System.currentTimeMillis();
        //Thực hiện giải mã
        String decryptedString = AES.decrypt(encode, secretKey) ;
        long tDeEnd = System.currentTimeMillis();
        System.out.println("Thời gian giải mã = "+(tDeEnd - tDeStart)+" ms");

        System.out.println("Bản rõ từ file "+originalString);

        System.out.println("Chuỗi mã hóa "+encryptedString);

        System.out.println("Bản giải mã "+decryptedString);
    }
}