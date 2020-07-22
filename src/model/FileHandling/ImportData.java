package model.FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import model.Reply;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;

/**
 * This class Imorts the Unilink Data from a file
 * 
 * @author sumeet
 * @version 1.0
 */
public class ImportData {

	/**
	 * This method imports the data from the text into the Unilink System
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void fileToObjects(String filePath) throws FileNotFoundException, ClassNotFoundException, SQLException, DatabaseException {
		File importFile = new File(filePath);
		Scanner scan = new Scanner(importFile);
		String str = scan.nextLine();
		
		TableOperations op = new TableOperations();
			
		do{
			if(!str.equalsIgnoreCase("--------------------START OF POSTS LIST---------------------") && !str.equals("\n")) {
				String[] post = str.split("::");
				if(post[0].substring(0, 3).equalsIgnoreCase("EVE")) {
					op.insertPosts(post[0], post[1], post[2], post[3], post[4], post[6], post[7], Integer.parseInt(post[8]), Integer.parseInt(post[9]), 0, 0, 0, 0, 0, post[5]);
				}else if(post[0].substring(0, 3).equalsIgnoreCase("JOB")) {
					op.insertPosts(post[0], post[1], post[2], post[3], post[4], "", "", 0, 0, Double.parseDouble(post[6]), Double.parseDouble(post[7]), 0, 0, 0, post[5]);
				}else if(post[0].substring(0, 3).equalsIgnoreCase("SAL")) {
					op.insertPosts(post[0], post[1], post[2], post[3], post[4], "", "", 0, 0, 0, 0, Double.parseDouble(post[6]), Double.parseDouble(post[8]), Double.parseDouble(post[7]), post[5]);
				}
			}
			str = scan.nextLine();
		}while(!str.equalsIgnoreCase("----------------------END OF POSTS LIST---------------------") && scan.hasNextLine());
		
		str = scan.nextLine();
		str = scan.nextLine();
		
		do {
			if(!str.equalsIgnoreCase("--------------------START OF REPLY LIST---------------------") && !str.equals("\n")) {
				String[] reply = str.split("::");
				Reply r = new Reply(reply[0],Double.parseDouble(reply[1]),reply[2]);
				op.insertReplies(r);
			}
			str = scan.nextLine();
		}while(scan.hasNextLine() && !str.equalsIgnoreCase("----------------------END OF REPLY LIST---------------------"));
		
		scan.close();
	}

}
