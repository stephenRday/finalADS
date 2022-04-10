import java.util.ArrayList;

public class TernarySearchTree {

	
	private Node root;
	
	private int countStopsNamed = 0;
	
	private static class Node 
	{
		private char character;
		private Node left, middle, right;
		private String stopName = null;
		
		public Node(char c) // constructur for Node which sets the character
		{
			character = c;
		}
		
	}
	
	
	public int countStopsNamed()
	{
		return countStopsNamed;
	}
	
	public TernarySearchTree()
	{
		root = null;
	}
	
	
	public void insert(String StopName)
	{
	//	System.out.println("Going to try to insert "+ StopName);
		// inserting the stop into the nodes - set up new one if is the first
		
		Node tmpNode = insert(root,StopName, StopName);
		if (root == null) root = tmpNode; // if this was the first one, then put it into the root
		
		
	}
	
	
	private Node insert(Node currentNode, String insertText, String stopName)
	{
	
		char firstChar = insertText.charAt(0);
		if (currentNode == null) currentNode = new Node(firstChar);
		
		if(firstChar < currentNode.character) currentNode.left = insert(currentNode.left,insertText, stopName);
		else if(firstChar > currentNode.character) currentNode.right = insert(currentNode.right,insertText, stopName);
		else // since first char is not less or greater, firstchar is same as char in the current node
		{
			if (insertText.length() == 1) 
			{ // then we have come to the end of the word - save in the string and return this node
				currentNode.stopName = stopName; // store the full stop name
				countStopsNamed++; // if saving a name in, then add to the count;
				return currentNode;			// return this node with the updated stopname stored
			}
			currentNode.middle = insert(currentNode.middle, insertText.substring(1), stopName);
		}	
		return currentNode;
	}
	
	
	public ArrayList<String> getMatchingStops(String startingText)
	{
		
		ArrayList<String> stopList = new ArrayList<String>();	
		// find the node that is starting with the starting text
		Node foundNode = findNode(root, startingText);
		
		// call function to get all words in that node and below
		getMiddleStrings (foundNode, stopList);
				
		return stopList;
	}
	
	private Node findNode(Node thisNode, String startingText) 
	{
		if(startingText.length()==0) return thisNode;
		
		char firstChar = startingText.charAt(0);
		if (firstChar == thisNode.character)  // then we have found the first char of our search text in this node, and we are either done, or need to go down the middle
		{
			if (startingText.length()==1) // then we are at the end of the search string, and all words down the middle start with this prefix
			{
				return thisNode; // return the node where the last char appears (will only take strings in this node and in the middle subtree
			}
			// here so the starting text is more than 1
			if (thisNode.middle == null) return null;
			return findNode(thisNode.middle, startingText.substring(1));
		}
		else if (firstChar < thisNode.character) // then we have to go down the left tree
		{
			if (thisNode.left == null) return null;
			return findNode(thisNode.left, startingText);
		}
		else if (firstChar > thisNode.character) // then we have to go down the right tree
		{
			if (thisNode.right == null) return null;
			return findNode(thisNode.right, startingText);
		}	
	return null;	
	}
	
	private void getMiddleStrings(Node thisNode, ArrayList<String> list)
	{
		if (thisNode == null) return;
		if (thisNode.stopName != null) list.add(thisNode.stopName);
		if (thisNode.middle != null) getAllStrings(thisNode.middle, list);
		
	}
	
	private void getAllStrings(Node thisNode, ArrayList<String> list)
	{
			if (thisNode.stopName != null) list.add(thisNode.stopName);
			if (thisNode.left != null) getAllStrings(thisNode.left, list);
			if (thisNode.middle != null) getAllStrings(thisNode.middle, list);
			if (thisNode.right != null) getAllStrings(thisNode.right, list);
	}
	
}
