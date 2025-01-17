// Thong Nguyen
// 05/31/2024
// Program project 3: Huffman
// TA: Shreya Nambi
// Class comments: this class storing the character of the the file in to the tree
import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanNode overallRoot;

// Behavior: 
//   - This construct creating a binary tree using the file input for HuffmanCode
// Parameters:
//   - input: file input;
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    public HuffmanCode(Scanner input){
        while (input.hasNextLine()){
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = buildTree(code, asciiValue, overallRoot, 0);
        }
    }

// Behavior: 
//   - This method helps to building the tree by input the overallRoot
///    and modify the tree by number that tell the location of acsiiValue will be
// Parameters:
//   - Track: location where acsiiValue will be in binary tree
//   - asciiValue: a ascii value
//   - root: HuffmanNode
//   - index: index of track location.
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    private HuffmanNode buildTree(String track, int asciiValue, HuffmanNode root, int index){
        if (index == track.length()){
            return new HuffmanNode(asciiValue, 0);
        }
        else if (root == null){
            root = new HuffmanNode(0, 0);
        }
        char character = track.charAt(index);
        if (character == '0'){
            root.left = buildTree(track, asciiValue, root.left, index + 1);
        }
        else if (character == '1'){
            root.right = buildTree(track, asciiValue, root.right, index + 1);
        }
        return root;
    }

// Behavior: 
//   - This method creating a tree node base on the fe of the char in the file
// Parameters:
//   - frequencies: 1-D array which contains charatcer and frequencies of the character
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> pq = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i++){
            if (frequencies[i] != 0){
                HuffmanNode node = new HuffmanNode(i, frequencies[i]);
                pq.add(node);
            }
        }
        while (pq.size() != 1){
            HuffmanNode one = pq.remove();
            HuffmanNode two = pq.remove();
            HuffmanNode total = new HuffmanNode(0, one.frequencies + two.frequencies, one, two);
            pq.add(total);
        }
        overallRoot = pq.remove(); 
    }

// Behavior: 
//   - This method call the private method to save the character in the file with the location
//     of that character in binary tree
// Parameters:
//   - output: a file to put character in.
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    public void save(PrintStream output){
        String location = "";
        save2(output, overallRoot, location);
    }

// Behavior: 
//   - This private method help to putting the character and location in the file
// Parameters:
//   - output: a file to put character in
//   - root: a huffman node.
//   - location: where the charatcer locate in the string
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    private void save2(PrintStream output, HuffmanNode root, String location){
        if (root != null){
            if (root.left == null && root.right == null) {
                output.println(root.character);
                output.println(location);
            } 
            else {
            save2(output, root.left, location + "0");
            save2(output, root.right, location + "1");
            }
        }
    }

// Behavior: 
//   - This  method call private method to print out he character into file using 
//     location of the character location in binary tree
// Parameters:
//   - output: a file to put character in
//   - input: a location of all the character locate
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    public void translate(BitInputStream input, PrintStream output){
        translate(input , output, overallRoot);
    }

// Behavior: 
//   - This private method help to putting the character and location in the file
//     base on the location file
// Parameters:
//   - output: a file to put character in
//   - input: a location of all the character locate.
//   - root: a huffman node.
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
    private void translate(BitInputStream input, PrintStream output, HuffmanNode root){
        while (input.hasNextBit()){
            int num = input.nextBit();
            if (num == 0){
                root = root.left;
            }
            else if (num == 1){
                root = root.right;
            }
            if (root.left == null && root.right == null){
                output.write(root.character);
                root = overallRoot;
            }

        }
    }

    // it represent the node for the HuffmanCode
    public class HuffmanNode implements Comparable<HuffmanNode>{
        public final int character;
        public final int frequencies;
        public HuffmanNode right;
        public HuffmanNode left;

// Behavior: 
//   - This construct creating a empty node
// Parameters:
//   - No Parameters
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
        public HuffmanNode(){
            this(0, 0, null, null);
        }

// Behavior: 
//   - This construct creating a a node with frequencies and acsiiValue
//     with no left and right.
// Parameters:
//   - c: acsiiValue
//   - frequencies: how many time character appear
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
        public HuffmanNode(int c, int frequencies){
            this.character = c;
            this.frequencies = frequencies;
        }

// Behavior: 
//   - This construct creating a node with frequencies and acsiiValue
//     with left and right.
// Parameters:
//   - c: acsiiValue
//   - frequencies: how many time character appear
//   - left/right: a link to the left and right stuff
// Returns:
//   - Nothing is return
// Exception:
//   - no Exception being pass in
        public HuffmanNode(int c, int frequencies, HuffmanNode left, HuffmanNode right){
            this.character = c;
            this.frequencies = frequencies;
            this.right = right;
            this.left = left;
        }

// Behavior: 
//   - This method compare two diffierent node to see which one go first and return
//     integer tell which node go first
// Parameters:
//   - other: another HuffmanNode
// Returns:
//   - return integer number tell the order of HuffmanNode of which one go first
// Exception:
//   - no Exception being pass in
        public int compareTo(HuffmanNode other){
            if (frequencies < other.frequencies){
                return -1;
            }
            else if (frequencies == other.frequencies){
                return 0;
            }
            else {
                return 1;
            }
        }
    }  
}


