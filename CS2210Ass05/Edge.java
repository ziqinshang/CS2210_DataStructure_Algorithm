
public class Edge {
	
	private Node firstendpoint;
    private Node secondendpoint;
    private int type;
	
	public Edge(Node u, Node v, int type) {
		firstendpoint = u;
		secondendpoint = v;
		this.type = type;
	}
	
	public Node firstEndpoint() {
		return this.firstendpoint;
	}
	
	public Node secondEndpoint() {
		return this.secondendpoint;
	}
	
	public int getType() {
		return type;
	}
}
