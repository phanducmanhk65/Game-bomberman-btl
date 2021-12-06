package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import java.io.*;
import java.util.Scanner;

public class Map {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static char ar[][] = new char[13][31];
    public static void insertFromFile() throws IOException{
        try {
            FileInputStream x = new FileInputStream("res/levels/Level" + BombermanGame.level +".txt");
            BufferedReader b = new BufferedReader(new InputStreamReader(x));
            Scanner sc = new Scanner(b);
            sc.nextLine();
            for (int i = 0; i < 13; i++) {
                String s = sc.nextLine();
                for (int j = 0; j < 31; j++) {
                    ar[i][j] = s.charAt(j);
                }
            }
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
