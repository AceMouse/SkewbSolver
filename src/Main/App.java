package Main;

import Cube.Cube;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        char[][] faces1 = new char[][]{
                {'y','y','y','y','y'}, //front
                {'g','g','g','g','g'}, //right
                {'w','w','w','w','w'}, //back
                {'b','b','b','b','b'}, //left
                {'o','o','o','o','o'}, //top
                {'r','r','r','r','r'}, //bottom
        };
        /*char[][] faces1 = new char[][]{
                {'y','r','o','y','b'}, //front
                {'w','o','w','r','o'}, //right
                {'g','o','b','b','y'}, //back
                {'y','g','r','b','b'}, //left
                {'g','w','y','r','g'}, //top
                {'r','w','g','o','w'}, //bottom
        };*/

        Cube cube1 = new Cube(faces1);
        cube1.scramble(100);
        cube1.print();

        char[][] faces2 = new char[faces1.length][faces1[0].length];
        for (int i = 0; i < faces1.length; i++) {
            System.arraycopy(faces1[i], 0, faces2[i], 0, faces2[0].length);
        }

        Cube cube2 = new Cube(faces2);

        int[] moves = cube1.BFSUnscramble();
        cube2.applyMoves(moves);
    }
}
