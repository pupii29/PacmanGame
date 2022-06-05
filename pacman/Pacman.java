package pacman;

import javax.swing.JFrame;

public class Pacman extends JFrame{

    public Pacman() {
        //PlayMusic.playMusic("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\sound\\pacmantheme.wav");
        //PlayMusic.playMusic("/Users/nguyencan/Desktop/clone/PacmanGame/sound/pacmantheme.wav");
        add(new Model());
    }

    public static void main(String[] args) {
        Pacman pacman = new Pacman();
        pacman.setVisible(true);
        pacman.setTitle("Pacman game of team CHVN");
        pacman.setSize(380,420);
        pacman.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pacman.setLocationRelativeTo(null);
    }
}
