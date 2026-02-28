package gh2;

/*
* https://audio-convert.com/cn/mp3-converter/mp3-to-midi
* https://freemidi.org/videogames
* */

public class Music {
    public static void main(String[] args) {
        GuitarPlayer player = new GuitarPlayer(new java.io.File("F:\\code_space\\git\\r_cs61b\\proj1c\\src\\imagry_tone.mid"));
        player.play();
//        System.getProperty("user.dir");
    }
}
