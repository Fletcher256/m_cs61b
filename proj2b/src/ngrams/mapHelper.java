package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class mapHelper extends TreeMap<String, Integer> {

    NGramMap ngramMap;

    HashMap<String,Integer> hashMap;

    public mapHelper() {
        super();
    }

    public mapHelper(String synsetFile,String hyponymFile) {
        super();
        //设计思路大致如此:先用这个助手方法读取文件内容,再依靠那个下位词文件在图中连点成图,后续开放方法用图去找特定的节点。
        hashMap = new HashMap();
        readSynsetFile(synsetFile);
        ngramMap = new NGramMap(hashMap.size());
        readHyponymFile(hyponymFile);
    }

    private void readSynsetFile(String wordsFilename)
    {
        In in = new In(wordsFilename);

        while (!in.isEmpty()) {
            //理论上可以实现无限长的字符串读入。
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            hashMap.put(splitLine[1], Integer.parseInt(splitLine[0]));
        }
    }

    private void readHyponymFile(String countsFilename)
    {
        In in = new In(countsFilename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();

            String[] splitLine = nextLine.split(",");

            //这个是原节点。
            int src = Integer.parseInt(splitLine[0]);

            for(int i = 1; i < splitLine.length; i++)
            {
                //连接两个节点(添加到有向边中。)
                ngramMap.union(src, Integer.parseInt(splitLine[i]));
            }
        }
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    public static void main(String[] args) {
        mapHelper helper = new mapHelper("./data/wordnet/synset11.txt","./dat/a/wordnet/hyponym11.txt");
//        In in = new In("./data/wordnet/t1.txt");
//        while (!in.isEmpty()) {
//            String synsetFile = in.readLine();
//            System.out.println(synsetFile);
//        }
    }
}
