import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeySpam extends JFrame implements KeyListener {
    private int keyVal = 0;
    private JLabel label;
    private Timer countdownTimer;
    private boolean counting = false;
    private int timeLeft = 5;

    public KeySpam() {
        setTitle("Key Spam Counter");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        label = new JLabel("Press 'SPACE' to start counting...", SwingConstants.CENTER);
        add(label);

        addKeyListener(this);

        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    label.setText("Counting... " + timeLeft + " seconds left");
                } else {
                    countdownTimer.stop();
                    label.setText("Attack Value: " + keyVal);
                    removeKeyListener(KeySpam.this);
                    setKeyVal(keyVal);
                    dispose();
                }	
            }
        });
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        	if (!counting) {
                keyVal = 0;
                timeLeft = 5;
                countdownTimer.start();
                label.setText("Counting... 5 seconds left");
                counting = true;
            }
            keyVal++;
        }
    }
    

	public void setKeyVal(int keyVal) {
		this.keyVal = keyVal;
	}
	
	public int getKeyVal() {
		return this.keyVal;
	}

	public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
