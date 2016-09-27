import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Keyword transposition cipher. This class encrypts file. Enter filename and
 * key(in caps) as input
 * 
 * @author shaileshvajpayee
 *
 */
public class Encryption {
	public static String KEY;
	public static String PT;
	public static String CIPHER;
	public static String SUB;

	/**
	 * The constructor of the class
	 * 
	 * @param key
	 *            the key
	 * @param pt
	 *            plain text
	 */
	public Encryption(String key, String pt) {
		KEY = remove_dup(key);
		PT = pt;
	}

	/**
	 * Used to map the remaining alphabets the key map
	 * 
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
	 * 
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
	 * 
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

	public String encrypt(String pt) {
		char[] str = SUB.toCharArray();
		char[] text = pt.toCharArray();
		String cipher = "";
		int temp = 0;
		for (int i = 0; i < text.length; i++) {
			if (text[i] >= 65 && text[i] <= 97) {
				temp = text[i] - 65;
				cipher += str[temp] + "";
			} else
				cipher += text[i] + "";
		}
		return cipher;
	}

	/**
	 * Used to remove duplicates
	 * 
	 * @param k
	 *            The string from which duplicates are to be removed
	 * @return
	 */
	public String remove_dup(String k) {
		Set<Character> set = new LinkedHashSet<Character>();
		for (char c : k.toCharArray()) {
			if (c != ' ' || 65 <= c && c <= 97) {
				set.add(c);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (Character c : set) {
			sb.append(c + "");
		}
		return sb.toString();
	}

	/**
	 * This method decrypts the cipher once the key map is generated.
	 * 
	 * @param c:
	 *            The cipher to be decrypted
	 */
	public String decrypt(String c) {
		char[] cipher = c.toCharArray();
		char[] sub = SUB.toCharArray();
		String pt = "";
		int length = cipher.length;
		int mappings = 0;
		while (mappings < length) {
			loop: for (int i = 0; i < sub.length; i++) {
				if (cipher[mappings] >= 65 && cipher[mappings] <= 97) {
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
		return pt;
	}

	/**
	 * The main function of this class
	 * 
	 * @param args
	 *            ignored
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Enter File name:");
		Scanner s = new Scanner(System.in);
		String fileName = s.nextLine();
		String everything = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Enter Key(in caps):");
		String key = s.nextLine();
		String pt = everything.toUpperCase();

		Encryption e = new Encryption(key, pt);
		SUB = e.map_key(KEY);
		System.out.println("Encrypted File");
		String cip = e.encrypt(pt);
		System.out.println(cip);
		System.out.println("Decrypted file;-");
		System.out.println(e.decrypt(cip));
		System.out.println("Successful encryption");
	}
}
