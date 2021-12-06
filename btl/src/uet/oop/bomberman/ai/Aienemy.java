package uet.oop.bomberman.ai;

import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Aienemy {
    private static int max = Map.WIDTH * Map.HEIGHT;
    int Location;
        public static void Floyd(int [][]arr, int [][]brr) {
            for (int k = 0; k < max ; k++) {
                for (int i = 0; i < max; i++) {
                    for (int j = 0; j < max; j++) {
                        if (arr[i][j] > arr[i][k] + arr[k][j]) {
                            arr[i][j] = arr[i][k] + arr[k][j];
                            brr[i][j] = brr[i][k];
                        }
                    }
                }
            }
        }
        public Aienemy(int X, int Y, int Z) {
            int [][]brr = new int[max][];
            for(int i = 0; i < max; i++) {
                brr[i] = new int[max];
            }
            int [][]crr = new int[max][];
            for(int i = 0; i < max; i++) {
                crr[i] = new int[max];
            }
            for(int i = 0; i < max; i++) {
                for(int j = 0;j < max; j++) {
                    brr[i][j] = 1000;
                }
            }
            for(int i = 1; i < Map.HEIGHT - 1; i++) {
                for(int j = 1; j < Map.WIDTH - 1; j++) {
                    if(Map.ar[i][j] == ' ') {
                        int x = i * Map.WIDTH + j;
                        if(Map.ar[i + 1][j] == ' '){
                            brr[x][x + Map.WIDTH] = 1;
                            brr[x + Map.WIDTH][x] = 1;
                        }
                        if(Map.ar[i - 1][j] == ' ') {
                            brr[x][x - Map.WIDTH] = 1;
                            brr[x - Map.WIDTH][x] = 1;
                        }
                        if(Map.ar[i][j - 1] == ' ') {
                            brr[x][x - 1] = 1;
                            brr[x - 1][x] = 1;
                        }
                        if(Map.ar[i][j + 1] == ' ') {
                            brr[x + 1][x] = 1;
                            brr[x][x + 1] = 1;
                        }
                    }
                }
            }
            for(int i = 0; i < max; i++) {
                for(int j = 0; j < max; j++) {
                    if(brr[i][j] == 1000) crr[i][j] = 0;
                    else crr[i][j] = j;
                }
            }
            int u = Z;
            int v = X/ Sprite.SCALED_SIZE + Y/ Sprite.SCALED_SIZE * Map.WIDTH;
            if(brr[u][v] < max){
                Location =  crr[u][v];
            }

        }
        public int getLocations() {
            return Location;
        }

}
