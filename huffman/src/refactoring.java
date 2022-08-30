import java.util.ArrayList;
class PriQueue1 {
	refactoring first;
	refactoring last;
	void enque(refactoring NodeToAdd, refactoring current) {
		if (first == null) 
			first = last = NodeToAdd;
		else if (first.freq > NodeToAdd.freq) {
			NodeToAdd.next = first;
			first = NodeToAdd;
		} else if (last.freq <= NodeToAdd.freq) {
			last.next = NodeToAdd;
			last = NodeToAdd;
		} else {
			while (true) {
				if (current.next.freq <= NodeToAdd.freq)
					current = current.next;
				else {
					NodeToAdd.next=current.next;
					current.next=NodeToAdd;
					break;
				}
			}
		}
	}
	refactoring dequeue() {
		refactoring temp = first;
		if (first.next == null) 
			first = last = null;
			else 
			first = first.next;
		return temp;
	}
}
public class refactoring{
	refactoring left;
	refactoring right;
	refactoring next;
	int freq;
	Character pit;
	static String pitakan="";
	static String letter="";
	static ArrayList<String> codeword = new ArrayList<>();
	static String encoded = "";
	static String decoded = "";
	public refactoring(int num, Character pit) {
		freq = num;
		this.pit = pit;
	}
	static void find_codeword(refactoring current, String s) {
		if (current.left == null && current.right == null) {
			pitakan+=(current.pit);
			codeword.add(s);
			return;
		}
		find_codeword(current.left, s + "0");
		find_codeword(current.right, s + "1");
	}
	static void encode(String text) {
		for (int i = 0; i < text.length(); i++)
			encoded+=codeword.get(pitakan.indexOf(text.charAt(i)));
	}
	static void decode(String encoded_text) {
		for (int i = 0, k = 1; i < encoded_text.length();)
			for (int j = 0; j < codeword.size(); j++)
				if ((encoded_text.substring(i, k)).equals(codeword.get(j))) {
					decoded += pitakan.charAt(j);
					i = k;
				} else if (j == codeword.size() - 1)
						k++;
	}
	static ArrayList<Integer> findFrequency(String text) {
		ArrayList<Integer> freq = new ArrayList<Integer>();
		for (int i = 0; i < text.length(); i++) {
			if (isRepeated(i, text.charAt(i), text))
				continue;
			int count=1;
			for (int j = i + 1; j < text.length(); j++)
				if (text.charAt(i) == text.charAt(j))
					count++;
			freq.add(count);
			letter+=text.charAt(i);
		}
		return freq;
	}
	static boolean isRepeated(int index, char pit,String text) {
		for (int i = 0; i < index; i++)
			if (text.charAt(i) == pit)
				return true;
		return false;
	}
	public static void main(String[] args) {
		PriQueue1 PQ = new PriQueue1();
		String input="mustafa mustafa majid mustafa majid";
		for (int i = 0; i < findFrequency(input).size(); i++) 
			PQ.enque(new refactoring(findFrequency(input).get(i), letter.charAt(i)), PQ.first);
		refactoring root = null;
		for (int i = findFrequency(input).size(); 1< i; i--) {
			refactoring tree1 = PQ.dequeue();
			refactoring tree2 = PQ.dequeue();
			refactoring parrent = new refactoring(tree1.freq + tree2.freq, null);
			parrent.left = tree1;
			parrent.right = tree2;
			PQ.enque(root, PQ.first);
		}
		find_codeword(root, "");
		encode(input);
		decode(encoded);
		System.out.println("your text==>  "+input+"\n-----------------------------------");
		System.out.println("letter\t\t\t\t"+"codeword");
		for (int i = 0; i < pitakan.length(); i++) 
			System.out.println(pitakan.charAt(i)+"\t\t\t\t"+codeword.get(i));
		System.out.println("----------------------------------------");
		System.out.println("encoded text==>  "+encoded);
		System.out.println("compression text==>  "+decoded);
	}
}

