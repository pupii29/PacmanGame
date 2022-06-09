package pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;
public class Model extends JPanel implements ActionListener {
    private int count = 0;
    private Dimension d;
    private final Font smallFont = new Font("Times New Roman", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    private final int pacmanSpeed = 6;
    private final int blockSize = 24;
    private final int nBlocks = 15;
    private final int screenSize = nBlocks * blockSize;
    private final int maxGhost = 12;

    private int nGhosts = 6;
    private int lives, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private Image heart, ghost;
    private Image up, down, left, right;
    private Color mazeColor = new Color(5, 100, 5);
    private final Color dotColor = new Color(192, 192, 0);

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy;

    // MAP1
    private final short levelData[] = {
            19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
            17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
            21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

    //MAP2
    private final short levelData_2[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            0, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            0, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            0, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            0, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            0, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            0, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 24, 24, 24, 28
    };
    
    //MAP BOSS
    private final short boss[] = {
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 24, 24, 24, 24, 24, 16, 16, 16, 24, 16, 16, 16, 24, 20,
        21, 0,  0,  0,  0,  0,  17, 16, 20, 0, 25, 16, 20, 0, 21,
        21, 0,  19,  18,  18, 18, 16, 16, 20, 0, 0, 25, 20, 0, 21,
        21, 0,  17, 16, 16, 16, 16, 16, 20, 0, 23, 0, 29, 0, 21,
        21, 0,  25, 24, 24, 24, 16, 16, 20, 0, 17, 22, 0, 0, 21,
        21, 0,  0,  0,  0,  0,  17, 16, 20, 0, 17, 16, 22, 0, 21, 
        17, 18, 18, 18, 18, 18, 16, 16, 16, 18, 16, 16, 16, 18, 20,
        17, 24, 16, 16, 16, 24, 16, 16, 16, 24, 16, 16, 16, 24, 20, 
        21, 0, 17, 16, 20, 0, 17, 16, 20, 0, 17, 16, 20, 0, 21, 
        21, 0, 17, 16, 20, 0, 17, 16, 20, 0, 25, 24, 28, 0, 21, 
        21, 0, 25, 16, 28, 0, 17, 16, 20, 0, 0, 0, 0, 0, 21, 
        17, 22, 0, 29, 0, 19, 16, 16, 20, 0, 19, 18, 22, 0, 21,
        17, 16, 22, 0, 19, 16, 16, 16, 20, 0, 17, 16, 20, 0, 21,
        25, 24, 24, 26, 24, 24, 24, 24, 24, 26, 24, 24, 24, 26, 28
    };

    private final int validSpeeds[] = {1,2,3,4,6,8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;

    private short[] screenData;
    private Timer timer;
    //heart----
private boolean healing =false;
private int maxLives =3;
private int heart_x;
private int heart_y;
private void drawHeart(Graphics2D g2d, int x, int y) {
    g2d.drawImage(heart, x, y, this);
}
private void heal() {
    if(lives==maxLives){
        healing=false;
    }else{
    PlayMusic.playSound("C://Users//DELL//Documents//GitHub//PacmanGame//sound//pop.wav");
    lives++;}
}
public int getRandomNumberUsingNextInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
}
//----
    //setup
    private void showIntroScreen(Graphics2D g2d) {
        /*String hi = "Hello, welcome to our team game !";
        g2d.setColor(Color.yellow);
        g2d.drawString(hi, (screenSize)/6, 100);
         */
        String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (screenSize)/4, 150);
    }

    private void drawScore(Graphics2D g){
        g.setFont(smallFont);
        g.setColor(new Color(10,200,90));
        String s ="Scores: " + score;
        g.drawString(s,screenSize/2+96,screenSize+16);
        for(int i=0; i<lives; i++){
            g.drawImage(heart, i*28+8, screenSize+1, this);
        }
    }


    //DRAW MAP
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < screenSize; y += blockSize) {
            for (x = 0; x < screenSize; x += blockSize) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(5));

                if ((levelData_2[i] == 0)) {
                    g2d.fillRect(x, y, blockSize, blockSize);
                }
                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + blockSize - 1);
                }
                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + blockSize - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,
                            y + blockSize - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1,
                            y + blockSize - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillOval(x + 11, y + 11, 3, 3);
                }

                i++;
            }
        }
    }


    private void loadImages() {
        down = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\down.gif").getImage();
        up = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\up.gif").getImage();
        left = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\left.gif").getImage();
        right = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\right.gif").getImage();
        ghost = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\ghost.gif").getImage();
        heart = new ImageIcon("C:\\Users\\DELL\\IdeaProjects\\Pacman\\src\\image\\heart.png").getImage();
    }
    /*
    private void loadImages() {
        down = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/down.gif").getImage();//down
        up = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/up.gif").getImage();//up
        left = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/left.gif").getImage();//left
        right = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/right.gif").getImage();//right
        ghost = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/ghost.gif").getImage();//ghost
        heart = new ImageIcon("/Users/nguyencan/Desktop/clone/PacmanGame/images/heart.png").getImage();//heart
    }*/

    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
            g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        } else {
            g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawScore(g2d);
        drawMaze(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    //gamelogic
    private void movePacman(Graphics2D g2d) {

        int pos;
        short ch;
//heart
drawHeart(g2d, heart_x, heart_y);
        if (pacman_x > (heart_x - 1) && pacman_x < (heart_x + 1)
                && pacman_y > (heart_y - 1) && pacman_y < (heart_y + 1)
                && inGame) {

            if (lives == maxLives) {
                healing = false;
            } else {
                healing = true;
            }
        }
//heart
        if (pacman_x % blockSize == 0 && pacman_y % blockSize == 0) {
            pos = pacman_x / blockSize + nBlocks * (int) (pacman_y / blockSize);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }
            // Check for moving
            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }
            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + pacmanSpeed * pacmand_x;
        pacman_y = pacman_y + pacmanSpeed * pacmand_y;
    }

    private void moveGhosts(Graphics2D g2d) {
        int pos;
        int count;

        for (int i = 0; i < nGhosts; i++) {
            if (ghost_x[i] % blockSize == 0 && ghost_y[i] % blockSize == 0) {
                pos = ghost_x[i] / blockSize + nBlocks * (int) (ghost_y[i] / blockSize);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void death() {
        lives--;
        if (lives == 0) {
            inGame = false;
            PlayMusic.playSound("C://Users//DELL//Documents//GitHub//PacmanGame//sound//gameover.wav");
        }
        continueLevel();
    }

    private void checkMaze() {
        int i = 0;
        boolean finished = true;
        while (i < nGhosts*nBlocks && finished){
            if((screenData[i] != 0)){
                finished=false;
            }
            i++;
        }
        if(finished) {
            score += 50;
            if (nGhosts < maxGhost) {
                nGhosts++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void playGame(Graphics2D g2d) {
//heart---
        if (healing) {
            heart_x =getRandomNumberUsingNextInt(2,10)*blockSize;
            heart_y =getRandomNumberUsingNextInt(2,10)*blockSize;
            heal();
            healing=false;

        }
        //----
        if (dying) {
            death();
        } else {
            movePacman(g2d);
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    //initgame
    private void initVariables() {

        screenData = new short[nBlocks * nBlocks];
        d = new Dimension(400, 400);
        ghost_x = new int[maxGhost];
        ghost_dx = new int[maxGhost];
        ghost_y = new int[maxGhost];
        ghost_dy = new int[maxGhost];
        ghostSpeed = new int[maxGhost];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    private void continueLevel() {

        int dx = 1;
        int random;

        for (int i = 0; i < nGhosts; i++) {

            ghost_y[i] = 8 * blockSize; //start position
            ghost_x[i] = 8 * blockSize;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 10 * blockSize;  //start position
        pacman_y = 10 * blockSize;
        pacmand_x = 0;	//reset direction move
        pacmand_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }
    private void initGame() {
        lives =3;
        score =0;
        initLevel();
        nGhosts=6;
        currentSpeed = 3;
    }
    private void initGame2() {
        PlayMusic x = new PlayMusic();
        lives =3;
        score =0;
        initLevel();
        nGhosts=6;
        currentSpeed = 3;
        if(inGame){
            PlayMusic.playMusic("C://Users//DELL//Documents//GitHub//PacmanGame//sound//theme.wav");
            //PlayMusic.playMusic("/Users/nguyencan/Desktop/clone/PacmanGame/sound/pacmantheme.wav");
        }
    }
    
    private void initLevel(){
        int i;
        for(i=0;i<nBlocks * nBlocks;i++){
            screenData[i] = levelData_2[i]; //<---DRAW MAP2
        }
        continueLevel();
    }

    public Model(){
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    if(count ==0) {
                        inGame = true;
                        initGame2();
                        count++;
                    }
                    else {
                        inGame = true;
                        initGame();
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
