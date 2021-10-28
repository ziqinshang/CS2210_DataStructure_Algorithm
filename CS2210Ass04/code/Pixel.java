
public class Pixel {
	
	private Location location;
	private int color;
	
	public Pixel(Location p, int color) {
		location = p;
		this.color = color;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public int getColor() {
		return color;
	}
}
