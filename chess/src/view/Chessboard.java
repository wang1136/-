package view;


import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public ChessGameFrame chessGameFrame;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private  ArrayList<ChessComponent[][]> record = new ArrayList<>();

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initGame();
    }


    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        chess1.repaint();
        chess2.repaint();
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        chessGameFrame.changeColor();
    }

    private void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    private void initEmptySlotOnBoard(int row, int col,ChessColor color){
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
        chessComponent.setRecord(record);
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
        chessComponent.setRecord(record);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        if (loadCheck(chessData)){
            removeAllPoints();
           for (int k=0;k<8;k++){
               for (int m=0;m<8;m++){
                   if (chessData.get(k).charAt(m)=='K'){
                       initKingOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='k'){
                       initKingOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='Q'){
                       initQueenOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='q'){
                       initQueenOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='R'){
                       initRookOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='r'){
                       initRookOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='B'){
                       initBishopOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='b'){
                       initBishopOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='P'){
                       initPawnOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='p'){
                       initPawnOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='N'){
                       initKnightOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='n'){
                       initKnightOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='_'){
                       initEmptySlotOnBoard(k, m, ChessColor.NONE);
                   }
               }
           }
           if (chessData.get(8).charAt(0) == 'w'){
               currentColor = ChessColor.WHITE;
           } else {
               currentColor = ChessColor.BLACK;
           }
           repaint();
       }
    }

    public boolean loadCheck(List<String> chessData){
        if (chessData.size()<=9){
            for (int i=0; i<8; i++){
                if (chessData.get(i).length()!=8){
                    System.out.println("棋盘并非8*8");
                    chessGameFrame.addDialog("棋盘并非8*8");
                    return false;
                }
                for (int j=0; j<8;j++){
                    if (chessData.get(i).charAt(j) != '_'
                            && chessData.get(i).charAt(j) != 'R' && chessData.get(i).charAt(j) != 'r'
                            && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'n'
                            && chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'b'
                            && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'q'
                            && chessData.get(i).charAt(j) != 'K' && chessData.get(i).charAt(j) != 'k'
                            && chessData.get(i).charAt(j) != 'P' && chessData.get(i).charAt(j) != 'p'){
                        System.out.println("棋子并非是六种之一，棋子并非黑白棋子");
                        chessGameFrame.addDialog("棋子并非是六种之一，棋子并非黑白棋子");
                        return false;
                    }
                }
            }
            if (chessData.size()!=9){
                System.out.println("缺少下一步行棋方");
                chessGameFrame.addDialog("缺少下一步行棋方");
                return false;
            }
//缺少判断文件格式
            return true;
        }
        return false;
    }

    public void removeAllPoints(){
        for (int i=0; i<8; i++){
            for (int j=0; j<8;j++){
                initEmptySlotOnBoard(i, j, ChessColor.NONE);
            }
        }
    }

    public void initGame(){
        initiateEmptyChessboard();
        for (int i =2;i<6;i++){
            for (int j=0;j<8;j++){
                initEmptySlotOnBoard(i,j,ChessColor.NONE);
            }
        }
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, 7, ChessColor.BLACK);
        initRookOnBoard(7, 0, ChessColor.WHITE);
        initRookOnBoard(7, 7, ChessColor.WHITE);
        initBishopOnBoard(0, 2,ChessColor.BLACK);
        initBishopOnBoard(0, 5,ChessColor.BLACK);
        initBishopOnBoard(7, 2,ChessColor.WHITE);
        initBishopOnBoard(7, 5,ChessColor.WHITE);
        initKnightOnBoard(0, 1,ChessColor.BLACK);
        initKnightOnBoard(0, 6,ChessColor.BLACK);
        initKnightOnBoard(7, 1,ChessColor.WHITE);
        initKnightOnBoard(7, 6,ChessColor.WHITE);
        initKingOnBoard(0, 4,ChessColor.BLACK);
        initKingOnBoard(7, 4,ChessColor.WHITE);
        initPawnOnBoard(1, 0,ChessColor.BLACK);
        initPawnOnBoard(1, 1,ChessColor.BLACK);
        initPawnOnBoard(1, 2,ChessColor.BLACK);
        initPawnOnBoard(1, 3,ChessColor.BLACK);
        initPawnOnBoard(1, 4,ChessColor.BLACK);
        initPawnOnBoard(1, 5,ChessColor.BLACK);
        initPawnOnBoard(1, 6,ChessColor.BLACK);
        initPawnOnBoard(1, 7,ChessColor.BLACK);
        initPawnOnBoard(6, 0,ChessColor.WHITE);
        initPawnOnBoard(6, 1,ChessColor.WHITE);
        initPawnOnBoard(6, 2,ChessColor.WHITE);
        initPawnOnBoard(6, 3,ChessColor.WHITE);
        initPawnOnBoard(6, 4,ChessColor.WHITE);
        initPawnOnBoard(6, 5,ChessColor.WHITE);
        initPawnOnBoard(6, 6,ChessColor.WHITE);
        initPawnOnBoard(6, 7,ChessColor.WHITE);
        initQueenOnBoard(0, 3,ChessColor.BLACK);
        initQueenOnBoard(7, 3,ChessColor.WHITE);
        record.add(chessComponents);
    }

    public void resetGame(){
       removeAllPoints();
       initGame();
       this.currentColor = ChessColor.WHITE;
        chessGameFrame.changeColor();
       repaint();
    }

    public String stringChessboard(){
        StringBuilder tmp = new StringBuilder();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                tmp.append(chessComponents[i][j].getType());
            }
            tmp.append("\n");
        }
        if (currentColor == ChessColor.BLACK){
            tmp.append('b');
        } else {
            tmp.append('w');
        }
        return tmp.toString();
    }
}
