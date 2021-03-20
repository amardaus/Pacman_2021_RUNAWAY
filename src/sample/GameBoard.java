package sample;

public class GameBoard {
    int n, m;
    int[][] board;

    GameBoard(int n, int m){
        this.n = n;
        this.m = m;
        board = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(i==0 || i == n-1 || j == 0 || j == m-1){
                    board[i][j] = 1;
                }
                else{
                    board[i][j] = 0;
                }
            }
        }
        board[n/2][m/2] = 1;

    }
}
