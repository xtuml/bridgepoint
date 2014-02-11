//=====================================================================
//
//File:      $RCSfile: BridgePointDemoEncryptor.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/03/13 22:59:56 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

package com.mentor.nucleus.bp.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

public class BridgePointDemoEncryptor {
    private Cipher ecipher;
    private Cipher dcipher;

    // 8-byte Salt: "MentorBP"
    private byte[] salt = { (byte) 0x4D, (byte) 0x65, (byte) 0x6E, (byte) 0x74,
            (byte) 0x6F, (byte) 0x72, (byte) 0x42, (byte) 0x50 };

    // Iteration count
    private int iterationCount = 19;

    protected static String keyPhrase = "BridgePoint Pass Phrase!";
    
    /**
     * This text is embedded in the encrypted information in the
     * BridgePointDemo.txt file used by customers evaluating and demoing
     * BridgePoint.
     */
    protected static final String ExpecectedCopyrightAndLicense = 
        "//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.\n" +
        "//\n" +
        "//=====================================================================\n" +
        "//This document contains information proprietary and confidential to\n" +
        "//Mentor Graphics Corp. and is not for external distribution.\n" +
        "//=====================================================================\n" +
        "//\n" +
        "//\n" +
        "//FOR DEMO AND EVALUATION PURPOSE ONLY.\n";

    protected static final String DemoFile = "../license/BridgePointDemo.txt";
    
    protected BridgePointDemoEncryptor() {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(keyPhrase.toCharArray(), salt,
                    iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                    .generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
                    iterationCount);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
        } catch (java.security.spec.InvalidKeySpecException e) {
        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }

    protected String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    protected String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }
    
    /**
     * 
     * @return The path to the BridgePointDemo.txt file
     */
    public static String getBridgePointDemoPath() {     
        String basePath = Platform.getInstallLocation().getURL().getPath();
        IPath demoFilePath = new Path(basePath + DemoFile );
        return demoFilePath.toString();
    }
    
    /**
     * Get a date in the format used in the encrypted demo date (YYYYMMDD).
     * 
     * @param daysFromToday
     *            This is an integer that allows is added to todays date to get
     *            the date in X days from now.
     * 
     * @return date string in the format used by the encrypted demo string
     */
    public static String getDemoDate(int daysFromToday) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, daysFromToday);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());       
    }
    
    /**
     * See if a valid demo license is available.
     * 
     * @return true if a demo is in progress and false if it is not.
     */
    protected boolean demoIsInProgress() {
        return demoIsInProgress(false);
    }
    protected boolean demoIsInProgress(boolean testingNewKeyFile) {
        boolean demoIsInProgress = false;
        boolean hasValidDemoTextFile = false;
        
        try {       
            // Read the file, Decrypt, and test to make sure there will be no
            // problem
            // Extract the start date, end date, and other info from the decrypted
            // string
            String demoFilePath = ""; 
            if (testingNewKeyFile == true) {
                demoFilePath = "C:/BridgePointDemo.txt";
            } else {
                demoFilePath = getBridgePointDemoPath();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(demoFilePath)));
            StringBuilder decryptedResultBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                decryptedResultBuilder.append(line + "\n");
            }
    
            String encryptedStringFromFile = decryptedResultBuilder.toString();
            String decryptedKeyString = decrypt(encryptedStringFromFile);
            String demoStartDate = "";
            String demoEndDate = "";
        
            // Validate the string.
            // The two 9's below represent the expected size of the dates in the 
            // string (YYYYMMDD) plus their included newline character
            if (decryptedKeyString.length() == (9 + 9 + ExpecectedCopyrightAndLicense.length())) {
                
                String actualCopyRightAndLicense = decryptedKeyString.substring(18);
                if (ExpecectedCopyrightAndLicense.equals(actualCopyRightAndLicense)) {
                    demoStartDate = decryptedKeyString.substring(0, 8);
                    demoEndDate = decryptedKeyString.substring(9, 18);
                    if (demoStartDate.compareTo(demoEndDate) <= 0) {
                        String currentDate =  getDemoDate(0);
                        // Start date of the evaluation must precede the current
                        // machine date
                        if (demoStartDate.compareTo(currentDate) <= 0) {
                            // Start current machine date must precede the
                            // evaluation
                            // end date
                            if (currentDate.compareTo(demoEndDate) <= 0) {
                                demoIsInProgress  = true;
                                hasValidDemoTextFile = true;
                            } else {
                                // It is a valid demo file, it is simply expired
                                hasValidDemoTextFile = true;
                            }
                        }
                        
                    }
                }
            }
    
            // If this is the first time the demo expired then
            // corrupt the demo text string.
            //
            if (hasValidDemoTextFile && !demoIsInProgress) {
                // Change a single character in the middle of the string
                int charPositionToChange = encryptedStringFromFile.length()/2;
                String corruptStr = encryptedStringFromFile.substring(0,charPositionToChange-1);
                
                char newchar = '0';
                if (encryptedStringFromFile.charAt(charPositionToChange) == '0') {
                    newchar = '1';
                }
                corruptStr += newchar;
                corruptStr += encryptedStringFromFile.substring(charPositionToChange);
                    
                // open the file and write a new new string to it
                FileOutputStream fout = new FileOutputStream (demoFilePath);
                // Print a line of text
                new PrintStream(fout).println (corruptStr);
            }
        } catch (Exception ex) {
                // No error, just continue and return false
        }
            
            
        return demoIsInProgress;
    }
    
}
