package ca.mcgill.ecse428.unitrade.unitradebackend.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CredentialsEncoder implements PasswordEncoder {

    /**
     * Hash a raw password
     * @param rawPassword : CharSequence
     * @return String - Hashed password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
    }

    /**
     * Compare the raw password to the hashed password
     * @param rawPassword : CharSequence
     * @param encodedPassword : String
     * @return boolean - Matching result
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
    
}