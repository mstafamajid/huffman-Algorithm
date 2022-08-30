import java.util.ArrayList;

class PriQueue {
	// mustafa majid saeed
	huffman_nodes first;
	huffman_nodes last;

	void enque(huffman_nodes nodeToBeAdd, huffman_nodes current) {
		if (first == null)
			first = last = nodeToBeAdd;
		else if (first.frequency > nodeToBeAdd.frequency) {
			nodeToBeAdd.next = first;
			first = nodeToBeAdd;
		} else if (last.frequency <= nodeToBeAdd.frequency) {
			last.next = nodeToBeAdd;
			last = nodeToBeAdd;
		} else {
			while (true) {
				if (current.next.frequency <= nodeToBeAdd.frequency)
					current = current.next;
				else {
					nodeToBeAdd.next = current.next;
					current.next = nodeToBeAdd;
					break;
				}
			}
		}
	}

	huffman_nodes dequeue() {
		huffman_nodes temp = first;
		if (first.next == null)
			first = last = null;
		else
			first = first.next;
		return temp;
	}
}

public class huffman_nodes {
	huffman_nodes left;
	huffman_nodes right;
	huffman_nodes next;
	int frequency;
	Character letter;
	static String letters_without_duplicate = "";
	static ArrayList<String> codewords = new ArrayList<>();
	static String encoded_text = "";
	static String decoded_text = "";

	public huffman_nodes(int num, Character pit) {
		frequency = num;
		this.letter = pit;
	}

	static void find_codeword(huffman_nodes current, String codeword_of_character) {
		if (current.left == null && current.right == null) {
			letters_without_duplicate += (current.letter);
			codewords.add(codeword_of_character);
			return;
		}
		find_codeword(current.right, codeword_of_character + "1");
		find_codeword(current.left, codeword_of_character + "0");
	}

	static void encode(String text) {
		for (int i = 0; i < text.length(); i++)
			encoded_text += codewords.get(letters_without_duplicate.indexOf(text.charAt(i)));
	}

	static void decode(String encoded_text) {
		for (int i = 0, k = 1; i < encoded_text.length();)
			for (int j = 0; j < codewords.size(); j++)
				if ((encoded_text.substring(i, k)).equals(codewords.get(j))) {
					decoded_text += letters_without_duplicate.charAt(j);
					i = k;
				} else if (j == codewords.size() - 1)
					k++;
	}

	static ArrayList<Integer> findFrequency(String text) {
		ArrayList<Integer> freq = new ArrayList<Integer>();
		for (int i = 0; i < text.length(); i++) {
			if (isRepeated(i, text.charAt(i), text))
				continue;
			int count = 1;
			for (int j = i + 1; j < text.length(); j++)
				if (text.charAt(i) == text.charAt(j))
					count++;
			freq.add(count);
			letters_without_duplicate += text.charAt(i);
		}
		return freq;
	}

	static boolean isRepeated(int index, char pit, String text) {
		for (int i = 0; i < index; i++)
			if (text.charAt(i) == pit)
				return true;
		return false;
	}

	public static void main(String[] args) {
		PriQueue PQ = new PriQueue();
		String input = "Eerie eyes seen near lake.";
		for (int i = 0; i < findFrequency(input).size(); i++)
			PQ.enque(new huffman_nodes(findFrequency(input).get(i), letters_without_duplicate.charAt(i)), PQ.first);
		huffman_nodes parrent = null;
		for (int i = findFrequency(input).size(); 1 < i; i--) {
			huffman_nodes tree1 = PQ.dequeue();
			huffman_nodes tree2 = PQ.dequeue();
			parrent = new huffman_nodes(tree1.frequency + tree2.frequency, null);
			parrent.left = tree1;
			parrent.right = tree2;
			PQ.enque(parrent, PQ.first);
		}
		letters_without_duplicate = ""; // clear for letters in huffman tree
		if (PQ.first.right != null)
			find_codeword(parrent, "");
		else
			find_codeword(PQ.dequeue(), "1");
		encode(input);
		decode(encoded_text);
		System.out.println("yourr text::  " + input + "\n----------------------------------------------------");
		System.out.println("letter\t\t|\t\t" + "codeword" + "\t   |");
		for (int i = 0; i < letters_without_duplicate.length(); i++)
			System.out.println(letters_without_duplicate.charAt(i) + "\t\t|\t\t" + codewords.get(i) + "\t\t   |");
		System.out.println("----------------------------------------------------");
		System.out.println("encoded text::  " + encoded_text);
		System.out.println("compression text::  " + decoded_text);
	}
}