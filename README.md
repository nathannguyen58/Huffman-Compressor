# Huffman-Compressor

Implements the Huffman encoding of English characters using a combined linked list and binary tree data structure.

## Overview

This program will read in and compress an inputted text file based on the Huffman encoding produced on the input file.  

## Output

The program will output the resulting compressed text file, with binary bits represented as "0" and "1" characters.

The program will also print out the Huffman encoding of characters in the form of a table of character/frequency/encoding triples - one triple per line, with ":" separating the elements.  Example:

a: 315: 10010
b: 855: 1010

The program will also print out the calculated amount of space savings between the original inputted file and the resulting compressed file.

## HuffmanNode

This program implements a custom HuffmanNode class with the following fields:
- inChar: the character denoted by the node.
- frequency: the frequency of occurrences of all characters in the subtree rooted at this node.  For a leaf node, this frequency is the frequency of the character in the leaf node; for an interior node, the frequency is the sum of all frequency values in the leaves of the subtree.
- left: left child of a node in the Huffman tree
- right: right child of a node in the Huffman tree

