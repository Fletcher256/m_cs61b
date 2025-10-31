package bomb;

import common.IntList;

import java.util.List;

public class BombMain {
    public static void answers(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct inputs (passwords) to each phase using debugging techniques
        Bomb b = new Bomb();

        //IntList phase1key = new ;

        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            b.phase1(IntList.of(0,9,3,0,8)); // Figure this out too
        }
        if (phase >= 2) {
            /*在1337那个检查点下断点记录那个随机数转为字符串输入即可。*/
            b.phase2("-81201430");
        }
    }

    public static void main(String[] args)
    {
        int phase = 0;
        answers(args);
    }
}
