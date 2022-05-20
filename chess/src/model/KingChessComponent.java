package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KingChessComponent extends ChessComponent{
    private static Image King_WHITE;
    private static Image King_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (King_WHITE == null) {
            King_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }
        if (King_BLACK == null) {
            King_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }
    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = King_WHITE;
            } else if (color == ChessColor.BLACK) {
                    kingImage = King_BLACK;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
            super(chessboardPoint, location, color, listener, size);
            initiateBishopImage(color);
            if (color==ChessColor.WHITE){
                type = 'k';
            } else {
                type = 'K';
            }
        }

        @Override
        public  boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
            return contains(canMoveToPoints(chessComponents),destination);
        }

        public List<ChessboardPoint> canMoveToPoints(ChessComponent[][] chessComponents){
            ArrayList<ChessboardPoint> tmp = new ArrayList<>();
            int tmpX = getChessboardPoint().getY();
            int tmpY = getChessboardPoint().getX();
            if (boundary(tmpY-1,tmpX-1) && checkChess(tmpY-1,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX-1));
            }
            if (boundary(tmpY-1,tmpX) && checkChess(tmpY-1,tmpX,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX));
            }
            if (boundary(tmpY-1,tmpX+1) && checkChess(tmpY-1,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX+1));
            }
            if (boundary(tmpY,tmpX-1) && checkChess(tmpY,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY,tmpX-1));
            }
            if (boundary(tmpY,tmpX+1) && checkChess(tmpY,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY,tmpX+1));
            }
            if (boundary(tmpY+1,tmpX-1) && checkChess(tmpY+1,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX-1));
            }
            if (boundary(tmpY+1,tmpX) && checkChess(tmpY+1,tmpX,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX));
            }
            if (boundary(tmpY+1,tmpX+1) && checkChess(tmpY+1,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX+1));
            }
            tmp.sort(Comparator.comparingInt(ChessboardPoint::getY));
            tmp.sort(Comparator.comparingInt(ChessboardPoint::getX));
            return tmp;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
            g.setColor(Color.BLACK);
            if (isSelected()) { // Highlights the model if selected.
                g.setColor(Color.RED);
                g.drawOval(0, 0, getWidth() , getHeight());
            }
        }


    }

