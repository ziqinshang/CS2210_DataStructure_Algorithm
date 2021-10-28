
public class Location {
	
	private int x, y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public int xCoord() {
		return x;
	}
	
	public int yCoord() {
		return y;
	}
	
	public int compareTo (Location p) {
		if(x < p.xCoord()) {
			return -1;
		}else if(x == p.xCoord()) {
			if(y < p.yCoord()) return -1;
			else if(y > p.yCoord()) return 1;
			else return 0;
		}else {
			return 1;
		}
	}
	
}
