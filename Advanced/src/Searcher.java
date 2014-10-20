
import java.io.*;

/**
 * The Searcher class contains several methods for creating
 * a linked list and searching through it.
 * 
 * @author dawartz
 * @author tonny
 * @author buchvart
 * @author lomholdt
 * @version 0.1
 */
public class Searcher {
	
	/* Constants */
	private static final String PREFIX_STRING = "*PAGE:";
	
	/**
	 * The method traverses a HTMLlist and returns boolean true
	 * if the word passed in is already present in the list.
	 * 
	 * @param l pointer to front of list
	 * @param word the word to search for
	 * @return If a given word exists in a list
	 */
    public static boolean exists(HTMLlist l, String word) {
        while (l != null) {
            if (l.word != null && l.word.equals(word)) {
                return true;
            }
            l = l.next;
        }
        return false;
    }
    
    /**
     * Finds the HTMLlist object that matches the word parameter if present
     * otherwise it returns the last object in the list.
     * 
     * @param front the pointer to the front of the list
     * @param word the word to search for
     * @return returns the HTMLlist object
     */
    public static HTMLlist HtmlListExists(HTMLlist front, String word){
    	HTMLlist previous = front;
    	while(front != null){
    		if (front.word != null && front.word.equals(word)){
    			return front;
    		}
    		previous = front;
    		front = front.next;
    	}
    	return previous;
    }

    /**
     * The method traverses a URLlist and returns boolean true
     * if the URL passed in is already present in the list.
     * 
     * @param l pointer to front of list
     * @param url the url to search for
     * @return boolean true or false
     */
    public static boolean UrlExists(URLlist l, String url) {
        while (l != null) {
            if (l.url != null && l.url.equals(url)) {
                return true;
            }
            l = l.next;
        }
        return false;
    }
    
    /**
     * Finds the URLlist object that matches the url parameter if present
     * otherwise it returns the last object in the list.
     * 
     * @param front
     * @param url
     * @return
     */
    public static URLlist UrlListExists(URLlist front, String url) {
    	URLlist previous = front;
        while (front != null) {
            if (front.url != null && front.url.equals(url)) {
                return front;
            }
            previous = front;
            front = front.next;
        }
        return previous;
    }

    /**
     * The existsOnPage method loops over a HTMLlist and searches
     * for the given PREFIX_STRING. If the prefix is found it is stored
     * so whenever you find a word, it will print the URL to the terminal
     * where the word was found, but only if the URL has not been printed before.
     * 
     * @param l a HTMLlist list node
     * @param word a string word to search for
     */
    public static void existsOnPage(HTMLlist l, String word){
    	String currentURL = "";
    	boolean isUsed = false;
    	
    	while(l != null){
    		if(l.word.startsWith(PREFIX_STRING)){
    			currentURL = l.word.substring(PREFIX_STRING.length());
    			isUsed = false;
    		}
    		else if(l.word.equals(word) && !isUsed){
    			System.out.println("Exists on " + currentURL);
    			isUsed = true;
    		}
    		l = l.next;
    	}
    }


    
    
    /**
     * The readHtmlList method
     * 
     * @param filename
     * @return a HTMLlist with the start pointer to the linked list
     * @throws IOException
     */
    /** =================================================================================
//    public static HTMLlist readHtmlList(String filename) throws IOException {
//        String word;
//        HTMLlist start, current, tmp;
//
//        // Open the file given as argument
//        BufferedReader infile = new BufferedReader(new FileReader (filename));
//
//        word = infile.readLine(); //Read the first line
//        start = new HTMLlist(word, null);
//        current = start;
//        word = infile.readLine(); // Read the next line
//        while(word != null) {    // Exit if there is none
//            tmp = new HTMLlist(word, null);
//            current.next = tmp;
//            current = tmp;            // Update the linked list
//            word = infile.readLine(); // Read the next line
//        }
//        infile.close(); // Close the file
//
//        return start;
//    }
    
    =================================================================================*/
    
    
    
    
    /**
     * The new readHtmlList method
     * 
     * @param filename
     * @return pointer to front of list
     * @throws IOException
     */
    public static HTMLlist readHtmlList(String filename) throws IOException{
    	String line, currentUrl;
    	URLlist tmpUrl, endOfUrlList;
    	HTMLlist start, current, tmp, tmp2, endOfList;
    	
    	//BufferedReader infile = new BufferedReader(new FileReader(filename)); // open the file
    	BufferedReader infile = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8")); // UTF-8 capable file reader
    	
    	line = infile.readLine(); // first line in file
    	
    	start = new HTMLlist(null, null, null); // first node pointer
    	current = start;
    	currentUrl = "";
    	
    	while (line != null){ // while not end of file
    		if(line.startsWith(PREFIX_STRING)){ // it's a URL
    			String url = line.substring(PREFIX_STRING.length()); // remove prefix from URL
    			currentUrl = url;
    		}
    		else{ //  it's a word
				if (current.word == null){ // if first run
					current.word = line;
					current.urls = new URLlist(currentUrl, null);
				}
    			tmp = start; // use temp as start pointer
    			tmp2 = HtmlListExists(tmp, line);
    			
    			if(!tmp2.word.equals(line)){ // it has not been seen before
    				// ADD HTMLList
					endOfList = tmp2;
					tmpUrl = new URLlist(currentUrl, null);
					// Add current to end of list
					tmp = new HTMLlist(line, tmpUrl, null);
					endOfList.next = tmp;
					current = tmp;
    			}
    			else{ // it has been seen
    				// go to HTMLlist object with the word
    				current = tmp2;
    				tmpUrl = UrlListExists(current.urls, currentUrl);
    				if (!tmpUrl.url.equals(currentUrl)){ // if URL is not already added to the word
    					// go to end of URL list
    					endOfUrlList = tmpUrl; // maybe remove and pull together with last line
    					
    					// add url to the list
    					endOfUrlList.next = new URLlist(currentUrl, null);
    				}
    			}
    		}
    		line = infile.readLine();
    	}
    	infile.close();
    
    	return start;
    }
    
    /**
     * Returns a pointer to the last node in a HTMLlist
     * 
     * @param front
     * @return pointer to last node in list
     */
    private static HTMLlist getEndOfList(HTMLlist front){
    	HTMLlist previous = front;
    	while(front != null){
    		previous = front;
    		front = front.next;
    	}
    	return previous;
    }
    
    /**
     * Returns a pointer to the last node in a URLlist
     * 
     * @param front pointer to front of list
     * @return pointer to last node in list
     */
    public static URLlist getEndOfList(URLlist front){
    	URLlist previous = front;
    	while(front != null){
    		previous = front;
    		front = front.next;
    	}
    	return previous;
    }
    
    /**
     * Returns a pointer to the first HTMLlist object with a matching word
     * 
     * @param front pointer to front of list
     * @param word the word to search for
     * @return a pointer to the node with matching word
     */
    public static HTMLlist getListObjectPosition(HTMLlist front, String word){
    	while(front != null){
    		if (front.word.equals(word)){
    			return front;
    		}
    		front = front.next;
    	}
    	return null;
    }
    
    /**
     * Prints all the URLs associated to a given HTMLlist node
     * The method searches through a HTMLlist and prints the 
     * URLs associated to the node that matches the word parameter.
     * 
     * @param word to search for in HTMLlist
     * @param l front pointer to front of list
     */
    public static void getWordUrls(String word, HTMLlist l){
    	int count = 0;
    	while (l != null){
    		if (l.word.equals(word)){
    			URLlist front = l.urls; // must copy to keep pointer
    			while(front != null){
    				System.out.println(front.url);
    				count++;
    				front = front.next; 
    			}
    		}
    		l = l.next;
    	}
    	System.out.printf("Found %d results%n", count);
    }
    
    public static void getWordUrlsInHashSet(String word, HTMLlist l){
    	int count = 0;
    	for (String url : l.urls) {
    		System.out.println(url);
    		count++;	
		}
    	System.out.printf("Found %d results%n", count);
    }
    
}
