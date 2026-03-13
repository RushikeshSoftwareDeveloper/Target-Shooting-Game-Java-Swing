package com.GunShooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GunShooting extends JPanel implements MouseListener, ActionListener {

    int targetX = 100;
    int targetY = 100;
    int score = 0;
    int timeLeft = 30;

    boolean gameRunning = false;

    Timer gameTimer;
    Timer moveTimer;

    Random rand = new Random();

    JButton startButton;
    JButton restartButton;

    public GunShooting() {

        setLayout(null);

        startButton = new JButton("START GAME");
        startButton.setBounds(220,200,150,40);
        add(startButton);

        restartButton = new JButton("RESTART");
        restartButton.setBounds(220,250,150,40);
        restartButton.setVisible(false);
        add(restartButton);

        startButton.addActionListener(e -> startGame());
        restartButton.addActionListener(e -> restartGame());

        addMouseListener(this);

        gameTimer = new Timer(1000,this);

        moveTimer = new Timer(700,e -> moveTarget());
    }

    public void startGame() {

        gameRunning = true;
        score = 0;
        timeLeft = 30;

        startButton.setVisible(false);
        restartButton.setVisible(false);

        gameTimer.start();
        moveTimer.start();

        repaint();
    }

    public void restartGame() {

        score = 0;
        timeLeft = 30;

        restartButton.setVisible(false);

        gameTimer.start();
        moveTimer.start();

        gameRunning = true;

        repaint();
    }

    public void moveTarget() {

        targetX = rand.nextInt(500);
        targetY = rand.nextInt(350);

        repaint();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setFont(new Font("Arial",Font.BOLD,20));

        if(gameRunning) {

            g.setColor(Color.RED);
            g.fillOval(targetX,targetY,50,50);

            g.setColor(Color.BLACK);
            g.drawString("Score: "+score,20,30);
            g.drawString("Time: "+timeLeft,450,30);

        } else {

            g.drawString("Shooting Game",230,150);
        }
    }

    public void mouseClicked(MouseEvent e) {

        if(!gameRunning) return;

        int x = e.getX();
        int y = e.getY();

        if(x>=targetX && x<=targetX+50 && y>=targetY && y<=targetY+50) {

            score++;
            moveTarget();
        }

        repaint();
    }

    public void actionPerformed(ActionEvent e) {

        timeLeft--;

        if(timeLeft<=0) {

            gameTimer.stop();
            moveTimer.stop();

            gameRunning = false;

            restartButton.setVisible(true);

            JOptionPane.showMessageDialog(this,"Game Over\nScore: "+score);
        }

        repaint();
    }

    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    public static void main(String[] args) {

        JFrame frame = new JFrame("Java Shooting Game");

        GunShooting game = new GunShooting();

        frame.add(game);

        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}