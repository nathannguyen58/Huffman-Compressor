/**
 * Nathan Nguyen - A class that implements a huffman node.
 * @author <em>Nathan Nguyen</em>
 */
import java.util.LinkedList;

public class HuffmanNode
{
  /*field storing a huffman node's character*/
  private Character inChar;
  /*field storing a huffman node's frequency*/
  private int frequency;
  /*field storing a huffman node's left child*/
  private HuffmanNode leftChild;
  /*field storing a huffman node's right child*/
  private HuffmanNode rightChild;
  
  /**
   * Constructor that initializes a new huffman node
   * @param inChar the inputted character
   * @param frequency the inputted frequency
   * @param leftChild the left child of the node
   */  
  public HuffmanNode(Character inChar, int frequency, HuffmanNode leftChild, HuffmanNode rightChild)
  {
    this.inChar = inChar;
    this.frequency = frequency;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }
  
  /**
   * Method that gets the character of a huffman node
   * @return the character of a huffman node
   */    
  public Character getInChar()
  {
    return this.inChar;
  }
  
  /**
   * Method that sets the character of a huffman node
   * @param inChar the character of a huffman node
   */  
  public void setInChar(Character inChar)
  {
    this.inChar = inChar;
  }
  
  /**
   * Method that gets the frequency of a huffman node
   * @return the frequency of a huffman node
   */   
  public int getFrequency()
  {
    return this.frequency;
  }

  /**
   * Method that sets the frequency of a huffman node
   * @param frequency the frequency of a huffman node
   */
  public void setFrequency(int frequency)
  {
    this.frequency = frequency;
  }
  
  /**
   * Method that gets the left child of a huffman node
   * @return the left child of a huffman node
   */  
  public HuffmanNode getLeftChild()
  {
    return this.leftChild;
  }
  
  /**
   * Method that sets the left child of a huffman node
   * @param leftChild the left child of a huffman node
   */  
  public void setLeftChild(HuffmanNode leftChild)
  {
    this.leftChild = leftChild;
  }

  /**
   * Method that gets the right child of a huffman node
   * @return the left child of a huffman node
   */    
  public HuffmanNode getRightChild()
  {
    return this.rightChild;
  }

  /**
   * Method that sets the right child of a huffman node
   * @param rightChild the right child of a huffman node
   */    
  public void setRightChild(HuffmanNode rightChild)
  {
    this.rightChild = rightChild;
  }
}
    