package retaliation;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import retaliation.game.entities.Level;
import retaliation.game.entities.factory.LevelFactory;
import retaliation.ui.renderer.LevelRenderer;
import retaliation.ui.renderer.SlickRenderer;

public class Game extends BasicGame {
    
    private final SlickRenderer renderer;
    private final Level level;

    public Game() {
       super("Space Retaliation");

        level = LevelFactory.sampleLevel();
        renderer = new LevelRenderer(level);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        level.update(gc.getInput(), delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        renderer.render(g);
    }

}
