package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private int[] edgeTo;

    public NGramMap(int size) {
        edgeTo = new int[size];
    }

    public Boolean find(String word){return false;}

    public void union(int src, int dest) {
        edgeTo[dest] = src;
    }

    public void bfs(int v){};

    public void dfs(int v){}

    public int[] getEdgeTo() {
        return edgeTo;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    public static void main() {

    }
}
