/**
 * 
 */
package com.mentor.bp.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mentor.nucleus.bp.core.util.BridgePointDemoEncryptor;

/**
 * 
 */
public class BridgePointDemoKeyCreator extends BridgePointDemoEncryptor {
	/**
	 * This constructor is here only for access to the demo encryptor. There
	 * is no reason for anyone outside this class to instantiate it.  Therefore
	 * it is private.
	 */
	BridgePointDemoKeyCreator() {
		super();
	}
	
	/**
	 * Prompt the user for a date in YYYYMMDD format.
	 * 
	 * @param prompt Tell the user if this is the start or end date for the demo
	 * @param defaultValue Supply a default
	 * @return The date given by the user in YYYYMMDD format
	 */
	private static String getDate(final String prompt, final String defaultValue) {
		Object response = JOptionPane.showInputDialog(new JFrame(), prompt,
				"Enter a date (YYYYMMDD)", JOptionPane.QUESTION_MESSAGE, null,
				null, defaultValue);
		System.out.println("Response: " + response);

		return (String) response;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Create encrypter/decrypter class
			BridgePointDemoKeyCreator encrypter = new BridgePointDemoKeyCreator();

			// Get start date (YYYYMMDD). Default to today
			String demoStart = getDemoDate(0);
			demoStart = BridgePointDemoKeyCreator.getDate(
					"Enter the demo start date.", demoStart);

			// Get end date (YYYYMMDD)
			// Default to 30 days from today
			String demoEnd = getDemoDate(30);
			demoEnd = BridgePointDemoKeyCreator.getDate(
					"Enter the demo end date.", demoEnd);

			// Encrypt
			String copyrightWithLicense = ExpecectedCopyrightAndLicense;
			String originalKeyString = demoStart + "\n" + demoEnd + "\n"
					+ copyrightWithLicense;
			String encryptedKeyString = encrypter.encrypt(originalKeyString);

			// Write out the file
			// This used to rely on MGLS_LICENSE_FILE env var, but that had 
			// problems, so now we just hard-code the path.
			String demoFilePath = "C:/BridgePointDemo.txt";
			FileOutputStream fileOutStream = new FileOutputStream(demoFilePath);
			PrintStream printStream = new PrintStream(fileOutStream);
			printStream.print(encryptedKeyString);

			// Read the file, Decrypt,  and test to make sure there will be no problem
			// Extract the start date, end date, and other info from the decrypted string
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(demoFilePath)));
			StringBuilder decryptedResultBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				decryptedResultBuilder.append(line + "\n");
			}

			String encryptedStringFromFile = decryptedResultBuilder.toString();
			String decryptedKeyString = encrypter
					.decrypt(encryptedStringFromFile);

			if (!decryptedKeyString.equals(originalKeyString)) {
				// Show an error and delete the file
				JOptionPane.showMessageDialog(new JFrame(),
						"Error creating the demo key! \n"
								+ "The decryption test failed.", "Error",
						JOptionPane.ERROR_MESSAGE);

				fileOutStream.close();
				File demoFile = new File(demoFilePath);
				demoFile.delete();
			}

			// One last check...  Verify that the call to check the 
			// demo license is going to work as expected.
			if (!encrypter.demoIsInProgress(true)) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Failed to acquire a demo license. If the specified date range has not\n" +
						"yet started, this result is expected.\n", "Warning",
						JOptionPane.WARNING_MESSAGE);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}

}
