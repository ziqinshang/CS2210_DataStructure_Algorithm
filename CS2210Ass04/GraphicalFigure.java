
public class GraphicalFigure implements GraphicalFigureADT{
	
	private int id, height, width;
	private String type;
	private Location offset;						
	private BinarySearchTree BST;
	
	public GraphicalFigure (int id, int width, int height, String type, Location pos) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.offset = pos;
		this.BST = new BinarySearchTree();
	}
	
	public void setType (String type) {
		this.type = type;
	}
	
	public int getWidth () {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}

	public Location getOffset() {
		return offset;
	}
	
	public void setOffset(Location value) {
		offset = value;
	}
	
	public void addPixel(Pixel pix) throws DuplicatedKeyException{
		try{
			 BST.put(BST.getRoot(),pix);
		}
		catch(Exception e){	
			throw new DuplicatedKeyException();
		}
	}
	
	public boolean intersects (GraphicalFigure gobj) {
		if(ifcollide(gobj)) {
			for(Pixel p = BST.smallest(BST.getRoot()); p != null; p = BST.successor(BST.getRoot(),p.getLocation())) {
				// iterate from smallest to largest node
				if(gobj.getBST().get(gobj.BST.getRoot(),genLoc(p,gobj)) != null){
					return true;
				}
			}
		}
		return false;
			
	}
	private boolean ifcollide(GraphicalFigure fig){
		//figure 1= figure to be compared figure(fixed) 2= comparing figure(moving)
		//when figure1 and figure 2 are not overlapping on x axis, they are not collided
		if (fig.getOffset().xCoord()+fig.getWidth()<offset.xCoord()) return false;
		else if(fig.getOffset().xCoord()>offset.xCoord()+width) return false;
		//when figure1 and figure 2 are overlapping on x axis, but not overlapping on y axis,they are not collided
		else if (fig.getOffset().yCoord()+fig.getHeight()<offset.yCoord()) return false;
		else if (fig.getOffset().yCoord()>offset.yCoord()+height) return false;
		//when figure 1 and figure 2 are both overlapping in x and y axis, they are collided
		else return true;
	}

	
	private Location genLoc(Pixel p, GraphicalFigure fig){
		int x = p.getLocation().xCoord(); 
		int y = p.getLocation().yCoord();

		int xf = this.getOffset().xCoord();
		int yf = this.getOffset().yCoord();

		int figxoff = fig.getOffset().xCoord();
		int figyoff = fig.getOffset().yCoord();
				
		return new Location(x + xf - figxoff, y + yf - figyoff);
		
	}
	private BinarySearchTree getBST() {
		return BST;
	}
	

}
