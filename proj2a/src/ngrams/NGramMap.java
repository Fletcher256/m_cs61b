package ngrams;

import edu.princeton.cs.algs4.In;

import java.time.Year;
import java.util.*;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static utils.Utils.SHORT_WORDS_FILE;

import static utils.Utils.TOTAL_COUNTS_FILE;

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

    //用hash存值可能比较快?
    HashMap<String, TimeSeries> wordMap;

    TimeSeries countMap;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordMap = new HashMap<>();
        readWordsFile(wordsFilename);
        countMap = new TimeSeries();
        readCountsFile(countsFilename);
//        System.out.printf("hhh");
    }

    private void readWordsFile(String wordsFilename)
    {
        In in = new In(wordsFilename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");
            var t = wordMap.get(splitLine[0]);
            //找不到这个单词对应的时间线则直接新建一个加入hash表。
            if (t == null) {
                t = new TimeSeries();
                wordMap.put(splitLine[0], t);
            }
            //调参高手.jpg
            //加入时间线。
            t.put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
        }
    }

    private void readCountsFile(String countsFilename)
    {
        In in = new In(countsFilename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            countMap.put(Integer.parseInt(splitLine[0]),Double.parseDouble(splitLine[1]));
        }
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    public static void main() {

    }
}
