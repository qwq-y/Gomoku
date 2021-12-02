import java.util.Scanner;

public class Gomoku {
    public static void main(String[] args) {
        Settings settings = new Settings();

// test

        String score1 = "0";
        String score2 = "0";
        int hour = settings.getHour();
        int minute = settings.getMinute();
        int second = settings.getSecond();
        int pause = settings.getPause();

// call


//        settings.settingsWindow();
//        settings.listener();

        StartGame startGame = new StartGame();
        startGame.showWindow();
        startGame.welcomePage();
        startGame.isPlay();
        startGame.play(startGame.isPlay());
        startGame.isSettings();
        startGame.settings(startGame.isSettings());
        //startGame.isAction();
        //startGame.play();

//        Board board = new Board();
//        board.showWindow();
//        board.showPicture();
//        board.showBoard();
//        board.showScores(score1, score2);
//        board.showButtons();
//        board.showTurnTimer(pause);
//        board.showTotalTimer(hour, minute, second);
        //board.showTurnTimer(pause);
    }
}
