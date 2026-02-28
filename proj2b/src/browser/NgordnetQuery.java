package browser;

import java.util.List;

/**
 * Created by hug.
 */
//这个record就是一种结构体吧
public record NgordnetQuery(List<String> words,
        int startYear,
        int endYear,
        int k,
        NgordnetQueryType ngordnetQueryType) {}
