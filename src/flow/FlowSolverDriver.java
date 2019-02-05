package flow;

import flow.flownetwork.ResidualGraph;
import flow.searchalgorithm.BreadthFirst;
import flow.searchalgorithm.SearchAlgorithm;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by chrisat on 26.10.16.
 */
public class FlowSolverDriver {
    public static void main(String[] args) throws IllegalFormatException, IOException {
        if(args.length != 2)
            throw new IllegalArgumentException("Not correct type of arguments");

        int[][] graph = readFile(args[0]);
        SearchAlgorithm pathFinder = new BreadthFirst();
        EdmondKarp algorithm = new EdmondKarp(pathFinder);
        ResidualGraph residualGraph = new ResidualGraph();
        residualGraph.createGraph(graph);

        FlowSolver solver = new FlowSolver(residualGraph, algorithm, graph);
        writeFile(args[1], solver.result());
    }


    public static int[][] readFile(String file) throws FileNotFoundException {
        Scanner in = new Scanner(new File(file));

        if(!in.hasNext())
            throw new FileNotFoundException("Empty file is found. Not valid preferred format.");

        int numberOfVertices = in.nextInt();
        in.nextLine();
        int[][] graph = new int[numberOfVertices][numberOfVertices];

        for(int i = 0; i < numberOfVertices; i++) {
            for(int j = 0; j < numberOfVertices; j++) {
                graph[i][j] = in.nextInt();
            }
            in.nextLine();
        }
        return graph;
    }

    public static void writeFile(String file, FlowSolution solution) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
        writer.write(solution.toString());
        writer.flush();
    }
}
