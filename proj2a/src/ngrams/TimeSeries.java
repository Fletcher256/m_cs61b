package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    //从特定ts中创建特定切片。
    //即,本类从中提取一个片段,创建一个新实例。
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        if(ts!=null)
        {
            var years = ts.years();
            for(Integer y : years)
            {
                if(y >= startYear && y <= endYear)
                {
                    put(y, ts.get(y));
                }
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.

        return keySet().stream().toList();
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        //这个var就是C++的auto吧。。
//        var list = new ArrayList<Double>();
        List<Double> datas = new ArrayList<>();
        var years = years();
        for (Integer year : years) {
            datas.add(get(year));
        }
        return datas;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    //将输入的ts与本ts相加。
    //两个都有怎么办,值相加?
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries ret = new TimeSeries();
        var years = years();

        //需要相加吗??
        for(Integer year : years)
        {
            ret.put(year, get(year) );
        }

        var otherYears = ts.years();

        for(Integer year : otherYears)
        {
            //若ret已包含这一年,那么说明他已经添加上了。对值相加即可。
            var yt = ret.get(year);
            if(yt != null)
            {
                ret.replace(year, yt + ts.get(year));
            }
            //没包含上,那么就执行添加。
            else
            {
                ret.put(year,ts.get(year));
            }
        }
        return ret;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries ret = new TimeSeries();
        var years = years();

        //需要相加吗??
        for(Integer year : years)
        {
            var dividedYear = ts.get(year);
            if(dividedYear != null)
            {
                ret.put(year, get(year)/dividedYear);
            }
            else
            {
                throw new IllegalArgumentException("Divided by null year");
            }
        }
        return ret;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    public static void main(String[] args) {
        TimeSeries ts = new TimeSeries();
        ts.put(1931, 1.0);
        ts.put(1933, 2.0);
        ts.put(1936, 3.0);
        ts.put(1959, 6.0);
//        for (Integer year : ts.years()) {
//            System.out.println(year);
//        }
        TimeSeries ts2 = new TimeSeries(ts,1930,1950);
//        TimeSeries  ts3 = ts.plus(ts2);
//        TimeSeries ts4 = ts.dividedBy(ts2);
    }
}
