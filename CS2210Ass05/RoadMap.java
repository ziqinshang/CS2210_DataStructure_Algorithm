
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;

public class RoadMap {
	
	private Graph graph;
	private int scale;//SCALE is the scale factor used to display the map on the screen.
	private int start;//START is the starting vertex
	private int end;//END is the destination vertex
	private int width;//WIDTH is the width of the map,The number of vertical roads in each row
	private int length;//LENGTH is the length of the map, or the number of horizontal roads in each column
	private int initial_budget;//INITIAL BUDGET is the initial amount of money available to pay for private roads
	private int toll;//TOLL is the amount of money that needs to be paid to use a private road.
	private int gain;//GAIN is the amount of money received when using a reward road.
	public RoadMap(String inputFile) throws MapException{
		try {
			BufferedReader buff = new BufferedReader(new FileReader(inputFile));
			scale = Integer.parseInt(buff.readLine());
			start = Integer.parseInt(buff.readLine());
			end = Integer.parseInt(buff.readLine());
			width = Integer.parseInt(buff.readLine());
			length = Integer.parseInt(buff.readLine());
			initial_budget = Integer.parseInt(buff.readLine());
			toll = Integer.parseInt(buff.readLine());
			gain = Integer.parseInt(buff.readLine());
			graph = new Graph(width * length);
			
			String line1;
            String line2;
            String line3;
            String combinedstring;
            int stringlength=width*2-1;
            int nodenumber = 0;
            int counter2 = 0;
            //line1=buff.readLine();
            while ((line1 = buff.readLine()) != null) {
            	//below is an alternative method of constructing graph
				//but it ended up with "error reading input file"
				//some feedback of why this method doesnt work will be great
				//basically, I put combined 3 lines into one combined string and then construct paths between them
/*             	line2=buff.readLine();
				line3=buff.readLine();
				combinedstring=line1+line2+line3;
				for(int i=1;i<stringlength;i=i+2){
					char symbol=combinedstring.charAt(i);
					if(symbol == 'F') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), 0);
					else if(symbol == 'T') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), 1);
					else if(symbol == 'C') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), -1);
					counter++;
				}
				for(int j=stringlength*2+1;j<stringlength*3;j=j+2){
					char symbol=combinedstring.charAt(j);
					if(symbol == 'F') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), 0);
					else if(symbol == 'T') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), 1);
					else if(symbol == 'C') graph.insertEdge(graph.getNode(counter), graph.getNode(counter + 1), -1);
					counter++;
				}
				for(int k=stringlength;k<stringlength*2;k=k+2){
					char symbol=combinedstring.charAt(k);
					if(symbol == 'F') graph.insertEdge(graph.getNode(counter2), graph.getNode(counter2 + width), 0);
					else if(symbol == 'T') graph.insertEdge(graph.getNode(counter2), graph.getNode(counter2 + width), 1);
					else if(symbol == 'C') graph.insertEdge(graph.getNode(counter2), graph.getNode(counter2 + width), -1);
					counter2++;
				}
				counter2=counter;*/
            	line2 = buff.readLine();
            	for (int i = 0; i < stringlength; i++) {
				if (i % 2 == 0) {//see if it is a odd or even number(even)
            			if (line2 != null) {
            				char type = line2.charAt(i);//access the character at that location
 					//connect nodes with different kind of edges
            				if(type == 'F') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + width), 0);
            				else if(type == 'T') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + width), 1);
            				else if(type == 'C') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + width), -1);
            			}
            		}else {//if it is a odd number
            			char type = line1.charAt(i);
            			if(type == 'F') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + 1), 0);
        				else if(type == 'T') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + 1), 1);
        				else if(type == 'C') graph.insertEdge(graph.getNode(nodenumber), graph.getNode(nodenumber + 1), -1);
                        nodenumber++;
            		}
            	}
            	nodenumber++;
            }
            buff.close();
		}
		catch(Exception e){
			throw new MapException("file does not exist");
		}
	}
	
	
	public Graph getGraph() {
		return graph;
	}
	
	public int getStartingNode() {
		return start;
	}
	
	public int getDestinationNode() {
		return end;
	}
	
	public int getInitialMoney() {
		return initial_budget;
	}

	public Iterator<Node> findPath(int start, int destination, int initialMoney) throws GraphException{
		Stack<Node> path = new Stack<>();
        if (DFS(path, graph.getNode(start), graph.getNode(destination),initialMoney)) {
            return path.iterator(); 
        }

        return null;
	}
	//DFS= Depth First Search
	private boolean DFS(Stack<Node> stack, Node startnode, Node destinationnode, int budget) throws GraphException {
		stack.push(startnode);
		if(budget < 0) return false;
		if(startnode == destinationnode) return true;//if start = end , end reached
		startnode.setMark(true);//mark the node
		Iterator<Edge> incidentEdges = graph.incidentEdges(startnode);
		while(incidentEdges.hasNext()==true)
		{
			Edge nextedge = incidentEdges.next();
			Node currentnode;
			if(startnode == nextedge.firstEndpoint())
			{
				currentnode = nextedge.secondEndpoint();
			}
			else
			{
				currentnode = nextedge.firstEndpoint();
			}

			if(!currentnode.getMark()) {//if the node is not marked
				if(nextedge.getType() == 0)
				{
					if(DFS(stack, currentnode, destinationnode, budget)) return true;

				}
				else if(nextedge.getType() == 1) {
					if(budget - toll >= 0) {
						if(DFS(stack, currentnode,destinationnode, budget - toll)) return true;
					}
				}
				else //if the type of next road is reward road, add gain to the existing budget
					{
					if(DFS(stack, currentnode,destinationnode, budget + gain)) return true;
				}
			}
		}

		startnode.setMark(false);//means it is not in the path, so it can be accessed from other node
		stack.pop();
		return false;
	}
}
