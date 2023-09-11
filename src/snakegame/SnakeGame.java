package snakegame;
import javax.swing.JFrame;

public class SnakeGame extends JFrame{
    SnakeGame(){
        super("Snake Game");
        add( new board());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setResizable(false);
        
        
    }
    public static void main(String[] args) {
         new SnakeGame();
    }
    
}
 