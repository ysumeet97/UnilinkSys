package model.FileHandling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Post;
import model.Reply;
import view.UnilinkGUI;

/**
 * This class Exports the Unilink Data to a file
 * 
 * @author sumeet
 * @version 1.0
 */
public class ExportData {

	/**
	 * This method is used to export the Post and Reply Data to a File
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void objectsToFile(String filePath) throws IOException {
		ArrayList<Post> postList = UnilinkGUI.getFinalPostList();
		ArrayList<Reply> replyList = UnilinkGUI.getFinalReplyList();
		
		if(filePath == null || filePath.isEmpty()) {
			filePath = "files/export_data.txt";
		}
		else {
			filePath += "/export_data.txt"; 
		}
		
		FileWriter fwriter = new FileWriter(filePath);
		
		fwriter.write("--------------------START OF POSTS LIST---------------------\n");
		String postDetails = "";
		for(Post post : postList) {
			postDetails += post.getPostDetails() + System.getProperty("line.separator");
		}
		fwriter.write(postDetails);
		fwriter.write("----------------------END OF POSTS LIST---------------------\n\n");
		
		fwriter.write("--------------------START OF REPLY LIST---------------------\n");
		String replyDetails = "";
		for(Reply reply : replyList) {
			replyDetails += reply.getReplyDetails() + System.getProperty("line.separator");
		}
		fwriter.write(replyDetails);
		fwriter.write("----------------------END OF REPLY LIST---------------------");
		
		fwriter.close();
	}

}
