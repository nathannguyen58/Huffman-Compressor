/**
 * Nathan Nguyen - A class that implements the huffman encoding - a type of variable-length encoding that is based on the character frequencies in a given document.
 * @author <em>Nathan Nguyen</em>
 */
import java.io.*;
import java.util.*;

/*References: Java API, Yuchen Huang, Data Structures and Algorithm Analysis in Java - Mark Allen Weiss*/
public class HuffmanCompressor
{
  /*field storing the status message of the huffman encoding process*/
  private static String statusOfEncoding = new String("OK");
  /*field storing the hashmap of characters and frequencies*/
  private static HashMap<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
  /**
   * Method that facilitates the process of encoding a text file
   * @param inputFileName the input file to read through
   * @param outputFileName the output file to read through
   * @return the end status of the encoding process
   */
  public static String huffmanCoder(String inputFileName, String outputFileName)
  {
    /*local variable storing the initial sorted linked list of huffman nodes*/
    LinkedList<HuffmanNode> initialList = inputScan(inputFileName);  /*I used a linked list for this project because the running time for removing from the front in a linked list has a running time O(1).  Although the running time to add to the back is O(n), on average we are removing from the front twice as much as we are adding to the back.  So in this case, it is more efficient to use a linked list instead of an array list.*/
    /*local variable storing the huffman tree built using the initial list*/
    HuffmanNode huffmanTree = createTree(initialList);
    /*local variable storing the huffman encoding table*/
    HashMap<Character, String> encodingTable = traverseTree(huffmanTree);
    
    writeEncodingTable(encodingTable, frequencyTable);
    outputScan(inputFileName, outputFileName, encodingTable);
    return statusOfEncoding;
  }
  
  /**
   * Method that takes an input file, maps out a table of characters along with their frequencies, and returns a sorted linked list of huffman nodes in ascending order
   * @param inputFileName the input file to read through
   * @return the linked list storing the huffman nodes sorted in ascending order of frequencies
   */
  public static LinkedList<HuffmanNode> inputScan(String inputFileName)
  {
    /*local variable storing the linked list of huffman nodes*/
    LinkedList<HuffmanNode> huffmanNodeList = new LinkedList<HuffmanNode>();
    /*local variable storing the hashmap that includes all of the included characters in a text along with their frequency values*/
    HashMap<Character, Integer> localTable = new HashMap<Character,Integer>();
    /*local variable storing the integer value of the current character being read through*/
    int characterIntegerValue = 0;
    /*local variable storing a certain character's frequency in the inputted text*/
    int frequency = 0;
    
    try
    {
      /*local variable storing the buffered reader that reads through the inputted text file*/
      BufferedReader br = new BufferedReader(new FileReader(inputFileName));  /** the reader for the file**/
      
      /*The purpose of this loop is to iterate through the entire inputted text character by character until the end of the text file*/
      /*Iteration subgoal: Read a single character*/
      /*Preconditions: None*/
      while ((characterIntegerValue = br.read()) != -1)
      {
        
        if (localTable.containsKey((char) characterIntegerValue))    /*if the character is already in the existing hashmap, increment 1 to its existing frequency*/
        {
          frequency = localTable.get((char) characterIntegerValue);
          frequency += 1;
          localTable.put((char) characterIntegerValue, frequency);
        }
        
        else
        {
          localTable.put((char)characterIntegerValue, 1);
        }
        
        frequencyTable = localTable;
      }
    }
    catch (IOException e)
    {
      statusOfEncoding = ("Error.  Please input a valid file name.");
    }
    
    /*The purpose of this loop is to iterate through the entire hashmap in order to create the linked list of huffman nodes of every character and their respective frequency values*/
    /*Iteration subgoal: Read a single entry in the hashmap*/
    /*Preconditions: None*/
    for (Character character : localTable.keySet())
    {
      huffmanNodeList.add(new HuffmanNode(character, localTable.get(character), null, null));
    }
    
    huffmanNodeList.sort(new frequencyComparator());
    return huffmanNodeList;
  }
  
  /**
   * Method that takes two huffman nodes and merges them together to create a huffman node with a frequency value of the sum of the two inputted nodes
   * @param firstNode the first huffman node to merge
   * @param secondNode the second huffman node to merge
   * @return the final merged node
   */
  public static HuffmanNode mergeNodes(HuffmanNode firstNode, HuffmanNode secondNode)
  {
    /*local variable storing the newly merged node*/
    HuffmanNode mergedNode = new HuffmanNode(null, (firstNode.getFrequency() + secondNode.getFrequency()), null, null);
    
    if (firstNode.getFrequency() < secondNode.getFrequency() || firstNode.getFrequency() == secondNode.getFrequency()) /*if the first node's frequency is less than or equal to the second node's frequency, make it the left child of the merged node*/
    {
      mergedNode.setLeftChild(firstNode);
      mergedNode.setRightChild(secondNode);
    }
    
    else /*if the second node's frequency is greater than the first node's frequency*/
    {
      mergedNode.setLeftChild(secondNode);
      mergedNode.setRightChild(firstNode);
    }
    
    return mergedNode;
  }
  
  /**
   * Method that takes a linked list of huffman nodes and creates a huffman tree
   * @param initialList the list inputted to create the huffman tree
   * @return the huffman tree created from the linked list
   */
  public static HuffmanNode createTree(LinkedList<HuffmanNode> initialList)
  {
    
    /*The purpose of this loop is to iterate through the entire list of huffman nodes, continuously removing the first two and merging them into one and putting them to the end of the list.  The loop will repeat until there are only two nodes left in the list.*/
    /*Iteration subgoal: Remove and combine the first two nodes at the front of the list into one node, and insert it to the end of the list*/
    /*Preconditions: None*/
    while (initialList.size() > 2)
    {
      /*local variable storing the first element of the huffmannode linked list*/
      HuffmanNode firstElement = initialList.removeFirst();
      /*local variable storing the second element of the huffmannode linked list*/
      HuffmanNode secondElement = initialList.removeFirst();
      /*local variable storing the newly merged huffmannode formed from the first two elements*/
      HuffmanNode mergedNode = mergeNodes(firstElement, secondElement);
      initialList.add(mergedNode);
      initialList.sort(new frequencyComparator());
    }
    
    /*local variable storing the final huffman tree*/
    HuffmanNode finalTree = mergeNodes(initialList.getFirst(), initialList.getLast());
    return finalTree;
  }
  
  /**
   * Method that takes a node, encoding, and encoding table to create a specific character's huffman encoding value and build a huffman encoding table
   * @param node the node that is currently being checked for a character
   * @param characterEncoding the current encoding value of a certain character
   * @param encodingTable the current table that is being built
   */
  public static void myTraverse(HuffmanNode node, String characterEncoding, HashMap<Character, String> encodingTable)
  {
    
    if (node.getInChar() != null)  /*Base case: if a character is found, add it and its respective encoding value to the encoding table*/
    {
      encodingTable.put(node.getInChar(), characterEncoding);
    }
    
    if (node.getLeftChild() != null) /*if the node being checked has a left child, apend a zero to its encoded string value*/
    {
      myTraverse(node.getLeftChild(), characterEncoding + "0", encodingTable);
    }
    
    if (node.getRightChild() != null) /*if the node being checked has a right child, apend a one to its encoded string value*/
    {
      myTraverse(node.getRightChild(), characterEncoding + "1", encodingTable);
    }
    
  }
  
  /**
   * Wrapper method that calls the traverse method in order to a huffman encoding table
   * @param tree the tree to traverse through
   * @return the final table built after traversing through the huffman tree
   */
  public static HashMap<Character, String> traverseTree(HuffmanNode tree)
  {
    /*local variable storing the huffman encoding table in the form of a hashmap*/
    HashMap<Character, String> encodingTable = new HashMap<Character, String>();
    /*local variable storing a character's encoding value*/
    String characterEncoding = new String();
    myTraverse(tree, characterEncoding, encodingTable);
    return encodingTable;
  }
  
  /**
   * Method that writes the huffman encoded table to a text file named "encodingTable.txt"
   * @param encodingTable the huffman encoding table to be outputted
   */
  public static void writeEncodingTable(HashMap<Character, String> encodingTable, HashMap<Character, Integer> frequencyTable)
  {
    /*local variable storing the frequency of the current character*/
    int frequency = 0;
    try
    {
      /*local variable storing the bufferedwriter for the text file of the outputted encoding table*/
      BufferedWriter bw = new BufferedWriter(new FileWriter("encodingTable.txt"));
      bw.write("Character: Frequency: Huffman Encoding");
      bw.newLine();
      
      /*The purpose of this loop is to iterate through the entire hashmap of characters and encoding values and output them elegantly onto a new text file*/
      /*Iteration subgoal: Output a character and its respective encoding value into a new text file*/
      /*Preconditions: None*/
      for (HashMap.Entry<Character, String> entry : encodingTable.entrySet()) 
      {
        for (HashMap.Entry<Character, Integer> frequencyEntry : frequencyTable.entrySet())
        {
          if (entry.getKey().equals(frequencyEntry.getKey()))
          {
            frequency = frequencyEntry.getValue();
          }
        }
        bw.write(escapeSpecialCharacter(entry.getKey() + ": " + Integer.toString(frequency) + ": " + entry.getValue()));
        bw.newLine();
      }
      
      bw.close();
    }
    catch (IOException e)
    {
      statusOfEncoding = ("Error.  Please input a valid file name.");
    }
  }
    
  /**
   * Method that scans the inputted text file and outputs a new text file translated into huffman encoding
   * @param inputFileName the inputted file to be read through
   * @param outputFileName the outputted file to be written
   * @param encodingTable the encoding table used as a reference to encode each character in the inputted text
   */  
  public static void outputScan(String inputFileName, String outputFileName, HashMap<Character, String> encodingTable)
  {
    try
    {
      /*local variable storing the buffered reader for the inputted text*/
      BufferedReader br = new BufferedReader(new FileReader(inputFileName));
      /*local variable storing the buffered writer to output the newly translated text file*/
      BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
      /*local variable storing the buffered writer to output the calculated space savings*/
      BufferedWriter bu = new BufferedWriter(new FileWriter("spaceSavings.txt"));
      
      /*local variable storing the integer value of the current character being read*/
      int characterIntegerValue = 0;
      /*local variable storing the integer value of the original space in bits being taken up by the 8-bitcharacters*/
      int originalSpaceCount = 0;
      /*local variable storing the integer value of the new space in bits being taken up by encoded characters*/
      int finalSpaceCount = 0;
      
      /*The purpose of this loop is to iterate the entire inputted text character by character to translate each character into its respective huffman encoding and calculate the total amount of space in bits that it takes up*/
      /*Iteration subgoal: Translate a character, increment the original space counter by 8 bits, and increment the new space counter by the number of bits of the respective character's encoding*/
      /*Preconditions: None*/      
      while ((characterIntegerValue = br.read()) != -1)
      {
        bw.write(encodingTable.get((char)characterIntegerValue));
        originalSpaceCount += 8;
        finalSpaceCount += (encodingTable.get((char)characterIntegerValue).length());
      }
      
      br.close();
      bw.close();
      bu.write("Calculated Space Savings: " + Integer.toString(originalSpaceCount - finalSpaceCount) + " bits");  /*outputs the total saved space by subtracting the new amount of space being taken up from the original space being taken up*/
      bu.close();
    }
    catch (IOException e)
    {
      statusOfEncoding = ("Error.  Please input a valid file name.");
    }
  }
  
  /**
   * Method that escapes special characters -- Credit to Yuchen Huang
   * @param x the old character
   * @return the new character
   */  
  public static String escapeSpecialCharacter(String x)
  {
    /*local variable storing the string builder for the new character*/
    StringBuilder sb = new StringBuilder();
    
    /*The purpose of this loop is to iterate through the entire array of characters and check to see if it is a special character or not*/
    /*Iteration subgoal: Check a character to see if it is a special character*/
    /*Preconditions: None*/    
    for (char c : x.toCharArray())
    {
      if (c >= 32 && c < 127) /*if the character is not a special character just leave it as is*/
      {
        sb.append(c);
      }
      else  /*else make an array representation of the special character*/
      {
        sb.append(" [0x" + Integer.toOctalString(c) + "]");
      }
    }
    
    return sb.toString();
  }
  
  /**
   * Nathan Nguyen - A class that implements the comparator that compares frequency values of two huffman nodes.
   * @author <em>Nathan Nguyen</em>
   */
  public static class frequencyComparator implements Comparator<HuffmanNode>
  {
    /**
     * Method that overrides the compare method in the Comparator interface to compare two different huffman node frequencies
     * @param a huffman node "a"
     * @param b huffman node "b"
     * @return the numerical comparison between the frequency of nodes a and b
     */
    @Override
    public int compare(HuffmanNode a, HuffmanNode b)
    {
      return a.getFrequency() - b.getFrequency();
    }
  }
  
  /**
   * Launches the application and translates a file inputted by the user into a new file inputted by the user
   * @param args the inputted file name and outputted file name
   */
  public static void main(String[] args)
  {
    String statusMessage = huffmanCoder(args[0] + ".txt", args[1] + ".txt");
    System.out.println(statusMessage);
  }
}