package retaliation.game.entities;

import com.google.common.base.Function;
import retaliation.game.entities.listener.SpaceshipShootingListener;
import retaliation.game.geometry.Position;
import retaliation.game.logic.EnemyAI;
import retaliation.game.logic.FlyingLaser;
import retaliation.game.logic.PlayerShipControls;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;
import static retaliation.game.entities.EntityType.Laser;
import static retaliation.game.geometry.Dimension.size;


public class Level implements SpaceshipShootingListener {

    private PlayerShipControls player;
    private final List<EnemyAI> enemies = new ArrayList<EnemyAI>();
    private final List<FlyingLaser> lasers = new ArrayList<FlyingLaser>();

    public Level(Spaceship playerShip) {
        this.player = new PlayerShipControls(playerShip);

        playerShip.registerShootingListener(this);
    }

    public PlayerShipControls getPlayer() {
        return player;
    }

    public void addEnemyShip(Spaceship enemyShip) {
        enemies.add(new EnemyAI(enemyShip));
    }

    public Iterable<EnemyAI> getEnemies() {
        return enemies;
    }

    @Override
    public void fired(Position from) {
        lasers.add(new FlyingLaser(new Entity(Laser, from, size(1, 3))));
    }

    public Iterable<FlyingLaser> getLasers() {
        return lasers;
    }

    public Iterable<? extends Entity> allEntities() {
        Iterable<Spaceship> enemyShips = transform(this.enemies, new Function<EnemyAI, Spaceship>() {
            @Override public Spaceship apply(EnemyAI enemy) { return enemy.getShip(); }
        });
        Iterable<Entity> laserBolts = transform(lasers, new Function<FlyingLaser, Entity>() {
            @Override public Entity apply(FlyingLaser laser) { return laser.getLaser(); }
        });

        return concat(asList(player.getShip()), enemyShips, laserBolts);
    }
}
