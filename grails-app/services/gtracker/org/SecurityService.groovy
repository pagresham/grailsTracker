package gtracker.org

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import grails.gorm.transactions.Transactional

@Transactional
class SecurityService {

    def serviceMethod() {

    }

    /**
     * Returns hashed pw and salt used
     * If no salt is passed, random salt is generated
     * If salt is passed, that salt is used to create pw
     * See Link: https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     * @return Map [ 'salt': salt, 'generatedPassword': generatedPassword ]
     */
    def getSecurePassword(String passwdToHash, salt = null) {
        salt = (salt) ?: getSalt()
        def securePasswdAndSalt = get_SHA_1_SecurePassword(passwdToHash, salt)
//        log.info("Passwd to Hash: ${passwdToHash}")
//        log.info("Secure Hashed Passwd: ${securePasswdAndSalt.generatedPassword}")
//        log.info("Hash Length: ${securePasswdAndSalt.generatedPassword.size()}")
        return securePasswdAndSalt
    }

    /**
     * Generate Salt
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Generare hashed pw
     * @return hashed pw
     */
    private static Map get_SHA_1_SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return ['salt': salt, 'generatedPassword': generatedPassword]
    }
}
