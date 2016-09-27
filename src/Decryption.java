import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Keyword transposition cipher 
 * For input give number of cases as input then the key then
 * the cipher
 * This code decrypts the cipher.
 * 
 * @author shaileshvajpayee
 *
 */

public class Decryption {

	public static String SUB = "";
	public static String PT = "";

	/**
	 * Used to map the remaining alphabets the key map
	 * @param k
	 * @return
	 */
	public String values(String k) {
		Set<Character> set1 = new HashSet<Character>();
		for (char c : k.toCharArray()) {
			set1.add(c);
		}

		Set<Character> set2 = new HashSet<Character>();
		for (int i = 65; i < 91; i++) {
			set2.add((char) i);
		}
		set2.removeAll(set1);
		StringBuffer sb = new StringBuffer();
		for (Character c : set2) {
			sb.append(c + "");
		}
		return sb.toString();
	}

	/**
	 * Used to sort the key
	 * @param s
	 * @return
	 */
	public char[] sort(String s) {
		char[] c = s.toCharArray();
		int l = c.length;
		char temp;
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < l - 1; j++) {
				if ((int) c[i] < (int) c[j]) {
					temp = c[i];
					c[i] = c[j];
					c[j] = temp;
				}
			}
		}
		return c;
	}

	/**
	 * This function is used to create the key map
	 * @param k
	 * @return
	 */
	public String map_key(String k) {
		int rows = (int) Math.ceil((26 / k.length())) + 1;
		int cols = k.length();
		char[] key = k.toCharArray();
		char[][] grid = new char[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = '$';
			}
		}

		for (int i = 0; i < cols; i++) {
			grid[0][i] = key[i];
		}

		char[] vals = values(k).toCharArray();
		int index = 0;
		loop: for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = vals[index];
				index++;
				if (index == vals.length)
					break loop;
			}
		}
		key = sort(k);
		String sub = "";
		int search = 0;
		int length = key.length;
		while (search < length) {
			for (int i = 0; i < cols; i++) {
				if (key[search] == grid[0][i]) {
					for (int j = 0; j < rows; j++) {
						if (grid[j][i] != '$')
							sub = sub + grid[j][i] + "";
					}
				}
			}
			search++;
		}
		return sub;
	}

	/**
	 * Used to remove duplicates
	 * @param k
	 * 			The string from which duplicates are to be removed
	 * @return
	 */
	public String remove_dup(String k) {
		Set<Character> set = new LinkedHashSet<Character>();
		for (char c : k.toCharArray()) {
			set.add(c);
		}
		StringBuffer sb = new StringBuffer();
		for (Character c : set) {
			sb.append(c + "");
		}
		return sb.toString();
	}

	/**
	 * This method decrypts the cipher once the key map is generated.
	 * @param c:
	 * 			The cipher to be decrypted
	 */
	public void decrypt(String c) {
		char[] cipher = c.toCharArray();
		char[] sub = SUB.toCharArray();
		String pt = "";
		int length = cipher.length;
		int mappings = 0;
		while (mappings < length) {
			loop: for (int i = 0; i < sub.length; i++) {
				if (cipher[mappings] != ' ') {
					if (cipher[mappings] == sub[i]) {
						pt = pt + (char) (i + 65) + "";
					}
				} else {
					pt = pt + ' ';
					break loop;
				}
			}
			mappings++;
		}
		PT = pt;
	}

	/**
	 * this is the main method of the program
	 * 
	 * @param args:
	 *            ignored
	 */
	public static void main(String[] args) {
		Decryption d = new Decryption();
		Scanner s = new Scanner(System.in);
		int cases = Integer.parseInt(s.nextLine());
		for (int i = 0; i < cases; i++) {
			String k = s.nextLine();
			String KEY = d.remove_dup(k);// removed duplicates in key
			// create matrix map for all alphabets
			SUB = d.map_key(KEY);
			String cipher = s.nextLine();
			d.decrypt(cipher);
			System.out.println(PT);
			SUB = "";
			PT = "";
		}
	}
}
