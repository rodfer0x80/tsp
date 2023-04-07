package dfstsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TSP_DFS {
    private static int numCities;
    private static int[][] distance;
    private static boolean[] visited;
    private static int minDistance;
    private static int[] minPath;
    
    private static void init(String fileName) throws IOException {
    	int n = 0;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            n++;
            line = br.readLine();
        }
        br.close();
        
        numCities = n;
        distance = new int[numCities][numCities];
        visited = new boolean[numCities];
        minDistance = Integer.MAX_VALUE;
        minPath = new int[numCities];
    }

    public static void solve(String fileName) throws IOException {  	
    	init(fileName);
        
    	readData(fileName);
        
        dfs(0, 0, new int[numCities], 1);
        
        String path = "";
        System.out.println("Solution for: " + fileName);
        System.out.println("Minimum Distance: " + minDistance);
        for (int city:minPath) {
        	if (path != "") {
        		path += "->" + city;
        	} else {
        		path += city;
        	}
            	
        }
        System.out.println("Minimum Path: " + path);
    }

    private static void readData(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        int i = 0;
        while (line != null) {
            String[] parts = line.trim().split("\\s+");
            for (int j = 1; j < parts.length; j++) {
                distance[i][j - 1] = Integer.parseInt(parts[j]);
            }
            i++;
            line = br.readLine();
        }
        br.close();
    }

    private static void dfs(int currCity, int currDist, int[] currPath, int visitedCount) {
        if (visitedCount == numCities) {
            currDist += distance[currCity][0];
            if (currDist < minDistance) {
                minDistance = currDist;
                minPath = Arrays.copyOfRange(currPath, 0, numCities+1);
            }
            return;
        }

        visited[currCity] = true;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
            	
            	currPath[i] = currCity+1;
                dfs(i, currDist + calculateDistance(currCity, i), currPath, visitedCount + 1);
            }
        }
        visited[currCity] = false;
    }

    private static int calculateDistance(int city1, int city2) {
        int x1 = distance[city1][0];
        int y1 = distance[city1][1];
        int x2 = distance[city2][0];
        int y2 = distance[city2][1];
        return (int) Math.round(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    public static void main(String[] args) throws IOException {
    	TSP_DFS.solve("./data/test1tsp.txt");
    	TSP_DFS.solve("./data/test2atsp.txt");
    	TSP_DFS.solve("./data/test3atsp.txt");
    }
}