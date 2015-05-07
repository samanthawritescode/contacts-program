/* Samantha Voigt and Aline Mitsuzawa
 * 
 * 
 */

import java.util.*;
import java.io.*;

public class ContactBook<T> {
	
	private Hashtable<String, Contact> searchTable;
	
	//empty constructor used first time using the program
	public ContactBook() {
		searchTable = new Hashtable<String, Contact>(); // create a blank hashtable
	}
	
	/* constructor that takes in a file of information
	 * makes contact objects
	 * should be called once the program is initialized to read previous user data
	 */
	public ContactBook(String srcFile) {
		try {
			Scanner scan = new Scanner(new File(srcFile));
			while(scan.hasNextLine()) { 
				System.out.println(scan.nextLine()); // temp
			}
			scan.close();
		} catch (IOException ex) { 
			System.out.println(ex);
		}
	}
	
	public LinkedList<T> contactBookSearch(String category,
			String specificCriteria) {
		return null;
	}

	public LinkedList<T> getAllNames() {
		return null;
	}
	
	public static void main (String[] args) { 
		ContactBook test = new ContactBook("testFile.txt");
	}
}
