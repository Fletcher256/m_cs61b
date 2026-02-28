package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

import static utils.Utils.*;

import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.slf4j.LoggerFactory;

import browser.NgordnetServer;

public class HistoryTextHandler extends NgordnetQueryHandler {
    public HistoryTextHandler(NGramMap map) {
        //使用防御性拷贝。
        this.map =map;
    }
    NGramMap map;
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = new String();
        //权重切片。
        TimeSeries[] aTs = new TimeSeries[words.size()];
        int j = 0;
        //每个单词一个时间线。
        for(String word : words)
        {
            aTs[j] = map.weightHistory(word, startYear, endYear);
            var y = aTs[j].years();
            response += word+": {";
            for(var year : y)
            {
                response += year.toString() + '=' + (aTs[j].get(year)).toString();
                if(year != endYear)
                {
                    response += ", ";
                }
            }
            response += "}\n";
            j++;
        }
//        System.out.println(response);
        return response;
    }
}
