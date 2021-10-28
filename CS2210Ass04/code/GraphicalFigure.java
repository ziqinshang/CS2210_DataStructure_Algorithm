
public class GraphicalFigure implements GraphicalFigureADT{
	
	private int id, height, width;
	private String type;
	private Location offset;						
	private BinarySearchTree tree;
	
	public GraphicalFigure (int id, int width, int height, String type, Location pos) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.offset = pos;
		tree = new BinarySearchTree();
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
			 tree.put(tree.getRoot(),pix); 
		}
		catch(Exception e){	
			throw new DuplicatedKeyException();
		}
	}
	
	public boolean intersects (GraphicalFigure gobj) {
		if(checkRectangles(gobj)) {
			for(Pixel p = tree.smallest(tree.getRoot()); p != null; p = tree.successor(tree.getRoot(), p.getLocation())) {
				// iterate from smallest to largest node
				if(gobj.getTree().get(gobj.tree.getRoot(),genLoc(p,gobj)) != null){
					return true;
				}
			}
		}
		return false;
			
	}
	
	private boolean checkRectangles(GraphicalFigure fig){
		
		if(offset.yCoord() > fig.getOffset().yCoord() + fig.getHeight()) return false;	
		else if(offset.yCoord() + height < fig.getOffset().yCoord()) return false;
		else {
			if(offset.xCoord() > fig.getOffset().xCoord() + fig.getWidth()) return false;
			else if(offset.xCoord() + width < fig.getOffset().xCoord()) return false;
			else return true;
		}					
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
	private BinarySearchTree getTree() {
		return tree;
	}
	

}
