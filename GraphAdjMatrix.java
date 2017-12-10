
/**
 * Weighted undirected graph
 * @author Edmund Wong
 *
 */
public class GraphAdjMatrix implements Graph {

	private final static int INFIN = 1000000;
	private int[][] mat;
	
	public GraphAdjMatrix(int size) {
		mat = newGraph(size);
	}
	
	private int[][] newGraph(int size) {
		int[][] tempMat = new int[size][size];
		for (int r=0; r<tempMat.length; r++) {
			for (int c=0; c<tempMat[r].length; c++) {
				if (r==c) {
					tempMat[r][c] = 0;
				} else {
					tempMat[r][c] = INFIN;
				}
			}
		}
		return tempMat;
	}

	@Override
	public void addEdge(int v1, int v2) {
		mat[v1][v2] = 1;
		mat[v2][v1] = 1;
	}

	@Override
	public void topologicalSort() {
		// This has been left blank for this project
	}

	@Override
	public void addEdge(int v1, int v2, int weight) {
		mat[v1][v2] = weight;
		mat[v2][v1] = weight;
	}

	@Override
	public int getEdge(int v1, int v2) {
		return mat[v1][v2];
	}
	
	public void removeEdge(int v1, int v2) {
		if (v1!=v2) {
			mat[v1][v2] = INFIN;
			mat[v2][v1] = INFIN;
		}
	}

	@Override
	public int createSpanningTree() {
		// set up
		int minIndex;
		boolean[] known = new boolean[mat.length];
		int[] cost = new int[mat.length];
		int[] path = new int[mat.length];
		int[] neigh;
		for (int i=0; i<cost.length; i++) {
			cost[i] = INFIN;
			path[i] = -1;
			// known is originally set to false
		}
		cost[0] = 0;
		
		for (int i=1; i<mat.length; i++) {
			minIndex = minimumUnkownVertex(known, cost);
			known[minIndex] = true;
			neigh = getNeighbors(minIndex);
			for (int u: neigh) {
				if (!known[u] && cost[u]>mat[u][minIndex]) {
					cost[u] = mat[u][minIndex];
					path[u] = minIndex;
				}
			}
		}
		
		// only keep edges in minimum spanning tree
		mat = newGraph(mat.length);
		for (int i=0; i<path.length; i++) {
			if (path[i]!=-1) {
				addEdge(i,path[i],cost[i]);
			}
		}
		
		// adding up weights
		int sum = 0;
		for (int i=0; i<cost.length; i++) {
			sum+=cost[i];
		}
		return sum;
		
	}
	
	private int minimumUnkownVertex(boolean[] known, int[] cost) {
		int min = INFIN+1;
		int minIndex = -1;
		for (int i=0; i<cost.length; i++) {
			if (!known[i] && cost[i]<min) {
				min = cost[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	private int[] getNeighbors(int vertex) {
		int[] neigh =  new int[mat.length];
		int index = 0;
		for(int i=0; i<mat[vertex].length; i++) {
			if (mat[vertex][i]!=INFIN && vertex!=i) {
				neigh[index++] = i;
			}
		}
		int[] temp = new int[index];
		System.arraycopy(neigh, 0, temp, 0, temp.length);
		return temp;
	}

}
