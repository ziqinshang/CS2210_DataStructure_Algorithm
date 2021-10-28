
public class BinaryNode {
	
	private Pixel value;
	private BinaryNode left, right, parent;
	
	public BinaryNode (Pixel value, BinaryNode left, BinaryNode right, BinaryNode parent) {
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	public BinaryNode () {
		value = null;
		left = null;
		right = null;
		parent = null;
	}
	
	public BinaryNode getParent() {
		return parent;
	}
	
	public void setParent(BinaryNode parent) {
		this.parent = parent;
	}
	
	public void setLeft (BinaryNode p) {
		left = p;
	}
	
	public void setRight (BinaryNode p) {
		right = p;
	}
	
	public void setData (Pixel value) {
		this.value = value;
	}
	
	public boolean isLeaf() {
		return value == null;
	}
	
	public Pixel getData () {
		return value;
	}
	
	public BinaryNode getLeft() {
		return left;
	}
	
	public BinaryNode getRight() {
		return right;
	}
}
