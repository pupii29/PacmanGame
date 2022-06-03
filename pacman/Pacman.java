package pacman;

import javax.swing.JFrame;

public class Pacman extends JFrame{

    public Pacman() {
        add(new Model());
    }

    public static void main(String[] args) {
        Pacman pacman = new Pacman();
        pacman.setVisible(true);
        PlayMusic.playMusic("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\sound\\pacmantheme.wav");
        pacman.setTitle("Pacman game of team CHVN");
        pacman.setSize(380,420);
        pacman.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pacman.setLocationRelativeTo(null);
    }
}
