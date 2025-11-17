package security;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingService {

    private String toHex(byte[] bite) {//private since only used by this class.
        StringBuilder sb = new StringBuilder(bite.length * 2);
        for (byte b : bite) {
            sb.append(String.format("%02x", b));//converts each byte into 2 lowercase hexadecimal characters.
        }
        return sb.toString();//returns the string of 64 hexcharacters that has been created.
    }
    public String hash(String password) {
        byte[]hashedBytes;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //creates the messageDigest object for SHA-256
            hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            //getBytes converts string password to bytes.
            //md.digest preforms hashing operation.
        }
        catch (NoSuchAlgorithmException e) {//messageDigest methods may throw checked exception if teh SHA-256 is not recognised.
            //it is  a checked exception so teh code will not work without handling it so:)
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }

        return toHex(hashedBytes);//converts the 32 byte to 64 hexadecimal characters.
    }
    public boolean verify(String passwordIp, String hashed){
        String userPassword=hash(passwordIp);//hashing of the new input.
        if (userPassword.equals(hashed))//compares the user input to teh stored hexadecimal hashed password.
        {return true;}
        else {return false;}
    }
}
