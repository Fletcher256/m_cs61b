package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    public HistoryHandler(NGramMap map) {
        //使用防御性拷贝。
        this.map =map;
    }
    NGramMap map;
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());

        System.out.println("But I'm totally ignoring that and just plotting a parabola\n" +
                "and a sine wave, because your job will be to figure out how to\n" +
                "actually use the query data.");

        TimeSeries parabola =new TimeSeries();
        TimeSeries sinWave = new TimeSeries();
        var ts = map.summedWeightHistory(words, startYear, endYear);
        var year = ts.years();
//        int i = 0;
        //????逃课写法是吧????
        for (int i = 0;i<100;i++) {
            parabola.put(i, ( i- 50.0) * (i - 50.0) + 3);
            sinWave.put(i, 1000 + 500 * Math.sin(i/100.0*2*Math.PI));
//            i++;
        }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        labels.add("parabola");
        labels.add("sine wave");

        lts.add(parabola);
        lts.add(sinWave);

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
