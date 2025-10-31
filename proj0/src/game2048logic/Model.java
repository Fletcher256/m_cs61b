package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    /*这个final关键字是const修饰。。。*/
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        // TODO: Task 2. Fill in this function.

        /*
        * 这个东西似乎是用来检查整个棋盘是否有空位可以用来添加可移动的砖块路径。
        * 它为什么要检查所有空格以检测可移动路径呢。。。
        * 看演示动画它的消值只会在边界实现,且每次移动瓷砖会让瓷砖直接移动到边界。
        * */

        //int emptySpaces = 0;

//        for(int x = 0; x < board.size() && emptySpaces < 2; x++)
//        {
//            for(int y= 0; y< board.size() && emptySpaces < 2; y++)
//            {
//                /*????这个测试用例全通过了。。*/
//                if(board.tile(x,y) == null)
//                {
//                    emptySpaces++;
//                    /*在这里四个方向随机移动来看是否它是否可以有位置能走。*/
//                    return true;
//                }
//            }
//        }

        /*谁在测试用例里头放了个空矩阵???*/
        for(int x = 0; x < board.size(); x++)
        {
            for(int y= 0; y< board.size(); y++)
            {
                /*????这个测试用例全通过了。。*/
                if(board.tile(x,y) == null)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        // TODO: Task 3. Fill in this function.

        /*最大值为2048,即2^11。这就是胜利值了。*/

        for(int x = 0; x < board.size(); x++)
        {
            for(int y= 0; y< board.size(); y++)
            {
                if(board.tile(x,y) != null && board.tile(x,y).value() == MAX_PIECE)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

    /*发送需要检查的瓷砖的坐标进行检查。*/
    private boolean CheckNeighborEqual(boolean is_checkable,int x, int y,int value)
    {
        /*这个瓷砖是存在或者与本值相同
        * 这个就是模块化的处理逻辑了。它不用处理最大值。最大值是否存在的逻辑应该已经在这个判断之前执行了
        * 这个就是吗，模块化的好处。单个函数不用处理太多的功能。从而分离控制。
        * */
        if(is_checkable && (board.tile(x,y) == null || value == board.tile(x,y).value()))
        {
            return true;
        }
        return false;
    }

    public boolean atLeastOneMoveExists() {
        // TODO: Fill in this function.

        /*至少存在一个空位(这个对空检测在内部已经封装过了。。)
        * 四向检查是否有位置可以移动
        * */

        for(int x = 0; x < board.size(); x++) {
            for(int y= 0; y< board.size(); y++) {
                /*先检查这个瓷砖是否有存在,再检查它四周是否有有效相同数值。*/
                if(board.tile(x,y) != null) {
                    if(
                            CheckNeighborEqual(x>0,x-1,y,board.tile(x,y).value()) ||
                                    CheckNeighborEqual(x< board.size()-1,x+1,y,board.tile(x,y).value()) ||
                                        CheckNeighborEqual(y>0,x,y-1,board.tile(x,y).value()) ||
                                            CheckNeighborEqual(y< board.size()-1,x,y+1,board.tile(x,y).value())
                    )
                    {
                        return true;
                    }
                }
            }
        }

        return board.size() == 1 && board.tile(0,0) == null;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    /*它只用检查向上移动就可以复用移动逻辑,然后推广到其它三个方向上。
    *本函数只执行移动一个瓷砖,与复合值的逻辑
    * 注意它是先从移动方向的头部开始合并。
    * 这个函数只是移动单个瓷砖啊!
    * */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        /*若其本来就在最上面那么我们不需要移动它。*/
        if(y<board.size()-1){
            Tile currTile = board.tile(x, y);
            int myValue = currTile.value();
            int targetY = y;

            // TODO: Tasks 5, 6, and 10. Fill in this function.

            /*每次判断时若未执行合并要移动瓷砖啊。。*/
            while(++targetY<board.size()) {
                /*有空位,可以移动*/
//            if(board.tile(x,targetY) == null){
//                /*不是这个瓷砖怎么动不了啊。。*/
                /*它是一次性移到边界或者是合并到一个特定瓷砖上。*/
//                board.move(x,targetY,currTile);
//            }
                if(board.tile(x,targetY) != null){
                    /*注意排除一下当前瓷砖是刚合并过的。(状态测定。)*/
                    if(board.tile(x,targetY).value() == myValue && !board.tile(x,targetY).wasMerged())
                    {
                        score += myValue*2;
                        board.move(x,targetY,currTile);
                    }
                    /*指针排除自身。*/
                    else if(currTile != board.tile(x,targetY-1)){
                        /*这个逻辑其实有问题的,若其需要移动的瓷砖下一个不等于其自身那么它可能会把自己与自己相消?
                        * 是的。它会将自己与自己相乘X(,也就是说合并是放在move里的。
                        * 也可以检测这种相邻的是否是一样的地址,即可实现优化了。
                        * */
                        board.move(x,targetY-1,currTile);
                    }
                    return;
                }
                /*到边界了。*/
            }
            board.move(x,--targetY,currTile);
        }

    }

    /** Handles the movements of the tilt in column x of the board
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        // TODO: Task 7. Fill in this function.

        /*从上边框头开始的*/
        for(int y = board.size()-2; y>-1; y--)
        {
            if(board.tile(x,y) != null)
            {
                moveTileUpAsFarAsPossible(x,y);
            }
        }
    }

    public void tilt(Side side) {
        // TODO: Tasks 8 and 9. Fill in this function.

        /*真是不错啊。这里它已经把问题推广到整个板子了。
        * 它这个Side类的实现应该是一个矩阵的旋转变换吧。。
        * 看行为setViewingPerspective应该是设置最上面的边为原棋盘的某个边吧。
        * 即每次设置它的边为某个特定值,对其他次的设置是没有关联的。
        * 也就是说每次设置为其它边,移动完就可以直接设置为NORTH即可返回原方阵的边状态。
        * */

        board.setViewingPerspective(side);
        for(int x = 0; x < board.size(); x++) {
            tiltColumn(x);
        }
        board.setViewingPerspective(Side.NORTH);
    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
