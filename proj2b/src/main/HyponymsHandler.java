package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler  extends NgordnetQueryHandler {

    public HyponymsHandler(String synsetFile,String hyponymFile)
    {

    }

    @Override
    public String handle(NgordnetQuery q) {
        var words = q.words();
        return "Hello";
    }
}
