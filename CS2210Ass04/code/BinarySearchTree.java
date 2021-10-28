
public class BinarySearchTree implements BinarySearchTreeADT{
	
	private BinaryNode root;
	
	public BinarySearchTree() {
		root = new BinaryNode();
	}
	
	public Pixel get (BinaryNode r, Location key) {
		return getNode(r, key).getData();
	}
	
	public void put (BinaryNode r, Pixel data) throws DuplicatedKeyException{
		BinaryNode node= new BinaryNode(data, null, null, null);
		if(!isLeaf(getNode(r, data.getLocation()))) throw new DuplicatedKeyException();//if there is already an internal node in BST
		if(isLeaf(r)) {//if the root is a leaf,the tree is empty
			setLeaf(node);//set the left and right child as leaf
			root = node;//set this node as the root of the tree
		}else {//if the root is not a leaf, the tree is not empty hence contains information
			BinaryNode current = r;
			while(!isLeaf(current)) {
				if(current.getData().getLocation().compareTo(data.getLocation()) == 1) current = current.getLeft();
				else current = current.getRight();			
			}
			current.setData(data);
			current.setParent(current.getParent());
			setLeaf(current);
		}
	}
	
	public void remove (BinaryNode r, Location key) throws InexistentKeyException{
		BinaryNode current = getNode(r, key);
		if(isLeaf(current)) throw new InexistentKeyException();//the key does not exist
		else {
			// no child
			if(isLeaf(current.getLeft()) && isLeaf(current.getRight())) {//the left and right child are both leaf
				if(current == root) root = new BinaryNode();// replace the root into a null binary node
				else if(current.getParent().getData().getLocation().compareTo(current.getData().getLocation()) == 1) current.getParent().setLeft(new BinaryNode(null, null, null, current.getParent()));//if the node to be removed is larger, remove the left node
				else current.getParent().setRight(new BinaryNode(null, null, null, current.getParent()));//if the node to be removed is smaller, remove the right node
			}
						
			// one child
			else if(isLeaf(current.getLeft()) || isLeaf(current.getRight())) {//if one of the two child is a leaf
				if(isLeaf(current.getRight())) {//if it is the right one
					if(current == root) {//if the node to be removed is a root
						root = current.getLeft();//set the left node of the current node as root
						current.getLeft().setParent(null);//set the parent as null
					}else if(current.getParent().getData().getLocation().compareTo(current.getData().getLocation()) == 1) {//means the parent is larger, which indicate this is a left node
						current.getParent().setLeft(current.getLeft());//set the parental node's left address
						current.getLeft().setParent(current.getParent());
					}else {//means the parent is smaller, which indicate this is right node
						current.getParent().setRight(current.getLeft());
						current.getLeft().setParent(current.getParent());
					}
				}else {//if it is the left one
					if(current == root) {
						root = current.getRight();
						current.getRight().setParent(null);
					}else if(current.getParent().getData().getLocation().compareTo(current.getData().getLocation()) == 1) {
						current.getParent().setLeft(current.getRight());
						current.getRight().setParent(current.getParent());
					}else {
						current.getParent().setRight(current.getRight());
						current.getRight().setParent(current.getParent());
						
					}
				}
			}
						
			// two child
			else if(current.getLeft() != null && current.getRight() != null) {//if the node to be remove has two internal node child
				BinaryNode smallest = smallestNode(current.getRight());//use the smallest element from the right tree as replacement (temp node)
				current.setData(smallest.getData());//replace the node to be removed with the smallest element from the right tree
				remove(smallest, smallest.getData().getLocation());
				
			}
		}
	}
	

	public Pixel successor (BinaryNode r, Location key) {

		if(isLeaf(r)) {//if the root is a leaf there is no successor
			return null;
		}else {
			BinaryNode current = getNode(r, key);
			if(!isLeaf(current) && !isLeaf(current.getRight())) {
				
				return smallest(current.getRight());

			}else {
	
				BinaryNode parent = current.getParent();
				while(current != root && parent.getRight() == current) {
					current = parent;
					parent = parent.getParent();
	
				}
				
				if(current == root) return null;//did not find
				else return parent.getData();
			}			
		}
	}
	

	public Pixel predecessor (BinaryNode r, Location key) {
		if(isLeaf(r)) {
			return null;
		}else {
			BinaryNode current = getNode(r, key);
			if(!isLeaf(current) && !isLeaf(current.getLeft())) {
				
				return largest(current.getLeft());
						
			}else {
				BinaryNode parent = current.getParent();
				while(current != root && parent.getLeft() == current) {
					current = parent;
					parent = parent.getParent();
				}
			
				if(current == root) return null;
				else return parent.getData();
			}			
		}

	}

	
	public Pixel smallest(BinaryNode r) throws EmptyTreeException{
		if(isLeaf(r)) throw new EmptyTreeException();
		return smallestNode(r).getData();
	}
	
	public Pixel largest(BinaryNode r) throws EmptyTreeException{
		if(isLeaf(r)) throw new EmptyTreeException();
		return largestNode(r).getData();
	}
	
	public BinaryNode getRoot() {
		return root;
	}
	
	private BinaryNode largestNode(BinaryNode r){
		if(isLeaf(r.getRight())) return r;
		else return largestNode(r.getRight());
	}
	
	private BinaryNode smallestNode(BinaryNode r){
		if(isLeaf(r.getLeft())) return r;
		else return smallestNode(r.getLeft());
	}
	
	private BinaryNode getNode(BinaryNode r, Location key) {
		if(isLeaf(r)) return r;
		else {
			if(key.compareTo(r.getData().getLocation()) == 0) return r;
			else if(key.compareTo(r.getData().getLocation()) == 1) return getNode(r.getRight(), key);
			else return getNode(r.getLeft(), key);
		}
	}
	
	private void setLeaf(BinaryNode node) {
		node.setLeft(new BinaryNode(null, null, null, node));
		node.setRight(new BinaryNode(null, null, null, node));
	}
	
	private boolean isLeaf(BinaryNode node) {
		return node.getData() == null;
	}
}
