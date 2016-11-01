import java.util.LinkedList;
import java.util.Scanner;

/**
 * Solution to the Twitter University Coding Challenge.
 * 
 * This class takes in a number of emails and phone numbers and  masks
 * personally identifiable information as per the problem.
 * @author Maryam Husain
 * @version October 31st, 2016
 */
public class Solution {
	public static void main(String[] args) throws Exception{
		LinkedList<String> input;
		LinkedList<String> output;
		
		try {
			input = getInput();
			output = processInput(input);
			
			for(String answer:output) {
				System.out.println(answer);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Gets input from STDin. Input can have multiple entries, each entered on a different line.
	 * Stops reading when the user enters an empty line.
	 * 
	 * @return a list of the input lines
	 */
	private static LinkedList<String> getInput() {
		LinkedList<String> input = new LinkedList<String>();
		
		Scanner scan = new Scanner(System.in);
		String line = null; // the current line
		
		// while the user has not entered an empty line, add the current line to the list
		while(!(line = scan.nextLine()).isEmpty()) {
			input.add(line.trim());
		}
		
		scan.close();
		return input;
	}
	
	/**
	 * Iterates through the list of input and processes each input.
	 * 
	 * @param input - a list of input lines
	 * @return a list of output lines with personal data hidden
	 */
	private static LinkedList<String> processInput(LinkedList<String> input) throws Exception{
		LinkedList<String> output = new LinkedList<String>();
		
		// iterate through the input list and process each line, add it to output list
		for(String line:input) {
			if(line.charAt(0) == 'E') {
				output.add(hideEmailData(line.substring(2, line.length()).trim()));
			} else if(line.charAt(0) == 'P') {
				output.add(hidePhoneData(line.substring(2, line.length()).trim()));
			} else { // if the line does not begin with E: or P: throw an exception
				throw new Exception("Error: Line not a phone number or email address.");
			}
		}
		
		return output;
	}
	
	/**
	 * Masks an email address to show only first letter, last letter, and domain with five
	 * stars in between.
	 * 
	 * For example, twitter@twitter.com would be masked as t*****r@twitter.com
	 * 
	 * Note: This method assumes the email address passed through is valid. 
	 * 
	 * @param email - the email we want to mask
	 * @return the masked version of the email
	 */
	private static String hideEmailData(String email) {
		// figure out where the @ character divides the username and the domain
		int indexOfAt = email.indexOf("@");
		
		// we're only showing the first and last letters of the username and the domain,
		// so get that information
		char firstLetter = email.charAt(0);
		char lastLetter = email.charAt(indexOfAt - 1);
		String domain = email.substring(indexOfAt + 1, email.length());
		
		// return a masked address with only the necessary info
		return "E: " + firstLetter + "*****" + lastLetter + "@" + domain;
	}
	
	/**
	 * Masks a phone number to show only the last four numbers and the country code length
	 * (if there is one). 
	 * 
	 * For example, +1 333 444-5678 would be masked as +*-***-***-5678.
	 * 
	 * Note: This method assumes the phone number passed through is valid.
	 * 
	 * @param phone - the phone number we want to mask
	 * @return the masked version of the phone number
	 */
	private static String hidePhoneData(String phone) {
		String cleanedNumber = "";
		String processedNumber = "";
		
		// iterate across the phone number, removing anything that's not a number
		// or the + character
		for(int i = 0; i < phone.length(); i++) {
			char c = phone.charAt(i);
			if((c >= '0' && c <= '9') || c == '+') {
				cleanedNumber += c;
			}
		}
		
		// 
		processedNumber = "***-***-" + cleanedNumber.substring(cleanedNumber.length() - 4, cleanedNumber.length());
		
		// if we have an area code, deal with it
		if(cleanedNumber.length() > 10) {
			String countryCode = cleanedNumber.substring(1, cleanedNumber.length() - 10);
			String hiddenCountryCode = "";
			for(int i = 0; i < countryCode.length(); i++) {
				hiddenCountryCode += "*";
			}
			
			processedNumber = "+" + hiddenCountryCode + "-" + processedNumber;
		}
		return "P: " + processedNumber;
	}
	
	/**
	 TEST DATA
	 Input:
	 
E:jackAndJill@twitter.com
P:(333)456-7890
P:+1(333) 456-7890
E: mh3685@columbia.edu
E: maryamhusain98@gmail.com
E: 0123456789!#$%^&*())?_[]{}|~`.?@random.biz
P: +1 303 881 5209
P: +1 (333) 444-5678
P: +91 (333) 444-5678
P: +111 (333) 444-5678
P: 333 444 5678
P: (333) 444-5678

	Output:

E: j*****l@twitter.com
P: ***-***-7890
P: +*-***-***-7890
E: m*****5@columbia.edu
E: m*****8@gmail.com
E: 0*****?@random.biz
P: +*-***-***-5209
P: +*-***-***-5678
P: +**-***-***-5678
P: +***-***-***-5678
P: ***-***-5678
P: ***-***-5678

	 */
}
