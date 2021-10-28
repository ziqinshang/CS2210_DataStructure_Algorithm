
import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT{
	
	private int numofnodes;
	private Node nodes[];
	private Edge edges[][];
	
	public Graph(int n) {
		numofnodes = n;
		nodes = new Node[n];
		edges = new Edge[n][n];	
		for (int i=0;i<n;i++){
            Node tempnode = new Node(i);
            nodes[i] = tempnode;
        }	
	}
	
	public Node getNode(int name) throws GraphException{
		if(name < 0 || name >= numofnodes){
            throw new GraphException("no node with this name exist");
		}
		else{
            return nodes[name];
        }
	}
	
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException{
		if(NodeExist(u,v))
		{//means these two nodes exists
			int node1 = u.getName();
			int node2 = v.getName();
			Edge tempedge = edges[node1][node2];//a temporary edge
			if(tempedge == null)
			{
                edges[node1][node2] = new Edge(u,v,edgeType);
                edges[node2][node1] = new Edge(u,v,edgeType);
	        }
			else
			{
                throw new GraphException("there is already an edge connecting the given nodes.");
			}
		}else {
			throw new GraphException("u or v are not nodes of the graph");
		}		
	}
	
	public Iterator<Edge> incidentEdges(Node u) throws GraphException{
		try {
            getNode(u.getName());
            Stack<Edge> edgeStack = new Stack<>();//a empty stack storing edges
            for(int i = 0; i < numofnodes; i++){
                if(edges[u.getName()][i] != null) {
                    edgeStack.push(edges[u.getName()][i]);
                }
            }
            return edgeStack.iterator();
        }
		
        catch (GraphException e){
            throw new GraphException("u or v are not nodes of the graph");
        }	
	}
	
	public Edge getEdge(Node u, Node v) throws GraphException{
		if(NodeExist(u,v))
		{
            int node1 = u.getName();
            int node2 = v.getName();
            if(edges[node1][node2] == null){
                throw new GraphException("there is no edge between u and v");
            }
            else{
                return edges[node1][node2];
            }
        }
        else{
            throw new GraphException("u or v are not nodes of the graph");
        }
	}
	
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if(NodeExist(u,v))
		{//check if the nodes exist first
            int node1 = u.getName();
            int node2 = v.getName();
            //if there is no link between these two nodes
            return (edges[node1][node2] != null);
        }
        else{//if one of the node does not exist
            throw new GraphException("u or v are not nodes of the graph");
        }
	}

	private boolean NodeExist(Node u, Node v){
        try {
            getNode(u.getName());
            getNode(v.getName());
            return true;
        }
        catch (GraphException e){
            return false;
        }
    }
}
