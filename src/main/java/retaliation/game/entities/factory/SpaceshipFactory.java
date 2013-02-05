package retaliation.game.entities.factory;

import static retaliation.game.geometry.Dimension.size;
import static retaliation.game.geometry.Position.at;
import retaliation.game.entities.Spaceship;

public class SpaceshipFactory {
    
    public static Spaceship playerFighterAt(float x, float y) {
        return new Spaceship(at(x, y), size(100, 20));
    }

    public static Spaceship enemyShipAt(float x, float y) {
        return new Spaceship(at(x, y), size(40, 40));
    }
}