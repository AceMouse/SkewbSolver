package Cube;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Cube {
    private static final int    FRONT = 0,
                                RIGHT = 1,
                                BACK = 2,
                                LEFT = 3,
                                TOP = 4,
                                BOTTOM = 5;
    private static final int    TOP_LEFT = 0,
                                TOP_RIGHT = 1,
                                MIDDLE = 2,
                                BOTTOM_LEFT = 3,
                                BOTTOM_RIGHT = 4;
    char[][] completeFaces = new char[][]{
            //tl tr  m   bl  br
            {'f','f','f','f','f'}, //front
            {'r','r','r','r','r'}, //right
            {'b','b','b','b','b'}, //back
            {'l','l','l','l','l'}, //left
            {'t','t','t','t','t'}, //top
            {'u','u','u','u','u'},
            /*{'y','y','y','y','y'}, //front
            {'r','r','r','r','r'}, //right
            {'w','w','w','w','w'}, //back
            {'o','o','o','o','o'}, //left
            {'g','g','g','g','g'}, //top
            {'b','b','b','b','b'}, //bottom*/
    };

    char[][] faces;

    public Cube(){
        faces = new char[6][5];
        for (int i = 0; i < 6; i++) {
            System.arraycopy(completeFaces[i], 0, faces[i], 0, 5);
        }
        set(key());
    }
    public Cube(char[][] faces){
        this.faces = faces;
    }

    private String[] faceToStrings(int f){
        return new String[]{
                faces[f][TOP_LEFT] + " " + faces[f][TOP_RIGHT],
                " " + faces[f][MIDDLE] + " ",
                faces[f][BOTTOM_LEFT] + " " + faces[f][BOTTOM_RIGHT],
        };
    }
    private String[] emptyToStrings(){
        return new String[]{
                "   ",
                "   ",
                "   ",
        };
    }
    public void print(){
        String[] empty = emptyToStrings();
        String[] top = faceToStrings(TOP);
        String[] left = faceToStrings(LEFT);
        String[] front = faceToStrings(FRONT);
        String[] right = faceToStrings(RIGHT);
        String[] bottom = faceToStrings(BOTTOM);
        String[] back = faceToStrings(BACK);
        for (int i = 0; i < 3; i++) {
            System.out.println( empty[i] + "   " + top[i]);
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println( left[i] + "   " + front[i] + "   " + right[i]);
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println( empty[i] + "   " + bottom[i]);
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println( empty[i] + "   " + back[i]);
        }
        System.out.println("---------------------------------");
    }
    int[][] clockwiseMoves = new int[][]{
            {RIGHT,FRONT,TOP,BOTTOM_RIGHT,BOTTOM_LEFT,TOP_LEFT,RIGHT,RIGHT,FRONT,TOP,BOTTOM,LEFT,BACK,TOP_RIGHT,TOP_RIGHT,TOP_LEFT},
            {FRONT,LEFT,TOP,BOTTOM_RIGHT,BOTTOM_LEFT,TOP_RIGHT,FRONT,LEFT,TOP,TOP,BOTTOM,BACK,RIGHT,TOP_LEFT,TOP_RIGHT,TOP_LEFT},
            {LEFT,BACK,TOP,BOTTOM_RIGHT,BOTTOM_LEFT,BOTTOM_RIGHT,BACK,TOP,TOP,TOP,FRONT,BOTTOM,RIGHT,TOP_LEFT,BOTTOM_LEFT,TOP_RIGHT},
            {BACK,RIGHT,TOP,BOTTOM_RIGHT,BOTTOM_LEFT,BOTTOM_LEFT,BACK,BACK,BACK,RIGHT,FRONT,LEFT,BOTTOM,TOP_RIGHT,TOP_LEFT,BOTTOM_RIGHT},
            /*{BOTTOM,FRONT,RIGHT,BOTTOM_LEFT,TOP_LEFT,TOP_RIGHT,RIGHT,FRONT,BOTTOM,BOTTOM}, //unnecessary since they mirror the above
            {BOTTOM,LEFT,FRONT,BOTTOM_RIGHT,TOP_LEFT,TOP_RIGHT,BOTTOM,FRONT,LEFT,LEFT},
            {BOTTOM,BACK,LEFT,TOP_RIGHT,TOP_LEFT,TOP_RIGHT,BACK,BACK,BACK,LEFT},
            {BOTTOM,RIGHT,BACK,TOP_LEFT,TOP_LEFT,TOP_RIGHT,RIGHT,RIGHT,BACK,BACK},*/
    };
    public void applyMoves(int[] moves){
        for (int i = 0; i < moves.length; i++) {
            System.out.print(i+1 + " ");
            switch (moves[i]) {
                case 0 -> System.out.println("Turn Front Right Corner Anti-Clockwise");
                case 1 -> System.out.println("Turn Front Left Corner Anti-Clockwise");
                case 2 -> System.out.println("Turn Back Left Corner Anti-Clockwise");
                case 3 -> System.out.println("Turn Back Right Corner Anti-Clockwise");
                case 4 -> System.out.println("Turn Front Right Corner Clockwise");
                case 5 -> System.out.println("Turn Front Left Corner Clockwise");
                case 6 -> System.out.println("Turn Back Left Corner Clockwise");
                case 7 -> System.out.println("Turn Back Right Corner Clockwise");
                default -> System.out.println("nop");
            }
            turnClockwise(clockwiseMoves[moves[i] % clockwiseMoves.length]);
            if (moves[i] < clockwiseMoves.length)
                turnClockwise(clockwiseMoves[moves[i] % clockwiseMoves.length]);
            print();
        }
        System.out.println(complete());
    }
    public void scramble(int moves){
        Random rand = new Random();
        for (int i = 0; i < moves; i++) {
            int m = rand.nextInt(clockwiseMoves.length);
            int[] move = clockwiseMoves[m];
            turnClockwise(move);
            System.out.print(i+1 + " ");
            switch (m) {
                case 0 -> System.out.println("Turn Front Right clockwise");
                case 1 -> System.out.println("Turn Front Left clockwise");
                case 2 -> System.out.println("Turn Back Left clockwise");
                case 3 -> System.out.println("Turn Back Right clockwise");
                default -> System.out.println("nop");
            }
        }
        cnt = -1;
    }
    private boolean faceComplete(int f){
        for (int i = 1; i < faces[f].length; i++) {
            if (faces[f][0] != faces[f][i])
                return false;
        }
        return true;
    }
    public boolean complete(){
        for (int i = 0; i < faces.length; i++) {
            if (!faceComplete(i))
                return false;
        }
        return true;
    }
    public String key(){
        String s = new String(faces[0]);
        s += new String(faces[1]);
        s += new String(faces[2]);
        s += new String(faces[3]);
        s += new String(faces[4]);
        s += new String(faces[5]);
        return s;
    }
    private boolean done = false;
    private int[] movesToUnscramble;
    private int cnt;
    private HashSet<String> visited;
    public void set(String s){
        faces[0] = s.substring(0,5).toCharArray();
        faces[1] = s.substring(5,10).toCharArray();
        faces[2] = s.substring(10,15).toCharArray();
        faces[3] = s.substring(15,20).toCharArray();
        faces[4] = s.substring(20,25).toCharArray();
        faces[5] = s.substring(25,30).toCharArray();
    }
    public int[] BFSUnscramble(){
        visited = new HashSet<>();
        LinkedList<String> stateQueue = new LinkedList<>();
        stateQueue.addLast(key());
        LinkedList<String> pathQueue = new LinkedList<>();
        pathQueue.addLast("");
        String path = "";
        while (!stateQueue.isEmpty()){
            String c = stateQueue.removeFirst();
            if (visited.contains(c)) {
                pathQueue.removeFirst();
                continue;
            }
            visited.add(c);
            set(c);
            path = pathQueue.removeFirst();
            if (complete())
                break;
            for (int i = 0; i < clockwiseMoves.length; i++) {
                turnClockwise(clockwiseMoves[i]);
                stateQueue.addLast(key());
                pathQueue.addLast(path+(i+clockwiseMoves.length));
                turnClockwise(clockwiseMoves[i]);
                stateQueue.addLast(key());
                pathQueue.addLast(path+i);
                turnClockwise(clockwiseMoves[i]);
            }
        }
        int[] moves = new int[path.length()];
        int i = 0;
        for (char c : path.toCharArray()) {
            moves[i++] = c-'0';
        }
        return moves;
    }
    public int[] unscramble(int d) {
        visited = new HashSet<>();
        movesToUnscramble = new int[d];
        for (int i = 0; i < d; i++) {
            movesToUnscramble[i] = Integer.MIN_VALUE;
        }
        if (unscramble(0,d))
            return Arrays.copyOf(movesToUnscramble,cnt);
        return movesToUnscramble;
    }
    private boolean unscramble(int i, int d){
        String key = key();
        if (visited.contains(key))
            return false;
        if (complete()) {
            done = true;
            cnt = i;
            return true;
        }
        if (done | d == i)
            return false;

        //visited.add(key);

        for (int j = 0; j < clockwiseMoves.length; j++) {   //0 -> "Front Right"
                                                            //1 -> "Front Left"
                                                            //2 -> "Back Left"
                                                            //3 -> "Back Right"
            int[] move = clockwiseMoves[j];
            turnClockwise(move);
            turnClockwise(move);
            if (unscramble(i+1, d)) {
                movesToUnscramble[i] = j;
                return true;
            }
            if (done)
                return false;
            turnClockwise(move);
        }
        return false;
    }

    private void rotateFaceClockwise(int f){
        char tmp = faces[f][TOP_LEFT];
        faces[f][TOP_LEFT] = faces[f][BOTTOM_LEFT];
        faces[f][BOTTOM_LEFT] = faces[f][BOTTOM_RIGHT];
        faces[f][BOTTOM_RIGHT] = faces[f][TOP_RIGHT];
        faces[f][TOP_RIGHT] = tmp;
    }

    private void turnClockwise(int[] move){ //clockwise
        int f1 = move[0];
        int f2 = move[1];
        int f3 = move[2];
        int c1 = move[3];
        int c2 = move[4];
        int c3 = move[5];

        char f1c1b = faces[f1][c1];
        char f2c2b = faces[f2][c2];
        char f3c3b = faces[f3][c3];

        char[] tmp = faces[f1];
        faces[f1] = faces[f3];
        faces[f3] = faces[f2];
        faces[f2] = tmp;
        for (int i = 6; i < 10; i++) {
            rotateFaceClockwise(move[i]);
        }
        faces[f1][c1] = f1c1b;
        faces[f2][c2] = f2c2b;
        faces[f3][c3] = f3c3b;

        int f4 = move[10]; //4 -> 5    5 -> 6    6 -> 4
        int f5 = move[11];
        int f6 = move[12];

        int c4 = move[13];
        int c5 = move[14];
        int c6 = move[15];

        char tmp2 = faces[f4][c4];
        faces[f4][c4] = faces[f6][c6];
        faces[f6][c6] = faces[f5][c5];
        faces[f5][c5] = tmp2;

    }


}
