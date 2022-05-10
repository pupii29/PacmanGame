import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Model extends JPanel implements ActionListener {
    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    private final int PACMAN_SPEED = 6;
    private int[] dx, dy;

    private Image up, down, left, right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy;

    private final int validSpeeds[] = {1,2,3,4,6,8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;

    public Model(){
        loadImages();
    }
    private void drawScore(Graphics2D g){
        g.setFont(smallFont);
        g.setColor(new Color(10,200,90));
        String s ="Scores: "+score;
        g.drawString(s,SCREEN_SIZE/2+96,SCREEN_SIZE+16);
        for(int i=0;i<lives;i++){
            g.drawImage(heart,i*28+8,SCREEN_SIZE+1,this);
        }
    }
    private void loadImages(){
        down = new ImageIcon("/src/images/down.gif").getImage();
        up = new ImageIcon("/src/images/up.gif").getImage();
        left = new ImageIcon("/src/images/left.gif").getImage();
        right = new ImageIcon("/src/images/right.gif").getImage();
    }

    class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if (inGame){
                if (key == KeyEvent.VK_LEFT){
                    req_dx = -1;
                    req_dy = 0;
                }
                else if(key == KeyEvent.VK_RIGHT){
                    req_dx = 1;
                    req_dy = 0;
                }
                else if(key == KeyEvent.VK_UP){
                    req_dx = 0;
                    req_dy = -1;
                }else if(key == KeyEvent.VK_DOWN){
                    req_dx = 0;
                    req_dy = 1;
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
