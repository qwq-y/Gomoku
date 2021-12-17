import java.util.Timer;

public class Begin {
    public static void main(String []args){

        Timer timer1 = new Timer();
        TotalTimer totalTimer = new TotalTimer(0,1,3);
        timer1.schedule(totalTimer, 1000);

        System.out.println("ok-1");

        Timer timer2 = new Timer();
        TurnTimer turnTimer = new TurnTimer(3);
        timer2.schedule(turnTimer, 1000);

        System.out.println("ok-2");

        Play play = new Play();
        play.run();
        System.out.println("going on");
    }
}
