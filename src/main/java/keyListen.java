import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyListen implements KeyListener {
    public keyListen() {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (!game.keys.contains("keyCode=" + e.getKeyCode())) {
            System.out.println("press:" + e.paramString());
            game.keys.add("keyCode=" + e.getKeyCode());
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.paramString().contains("keyCode=" + e.getKeyCode())) {
            System.out.println("release:" + e.paramString());
            for (int keyIndex = 0; keyIndex <= (game.keys.size() - 1); keyIndex++) {
                if (game.keys.get(keyIndex).contains("keyCode=" + e.getKeyCode())) {
                    game.keys.remove(keyIndex);
                }
            }
        }
    }
}
