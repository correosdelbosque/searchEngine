/**
 * Small test class, for testing the exists method
 * 
 * @author Mikkel
 * @version 2.0
 */
public class TestSearcher {

	public static void main(String[] args) {
		HTMLlist test = null, test1, test2, test3; 
		
		test3 = new HTMLlist("BOSS", null);
		test2 = new HTMLlist("*PAGE: URL2", test3);
		test1 = new HTMLlist("BOSS", test2);
		test = new HTMLlist("*PAGE: URL1", test1);
		
		Searcher.exists(test, "BOSS");
	}
}
