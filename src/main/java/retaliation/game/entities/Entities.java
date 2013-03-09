package retaliation.game.entities;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import retaliation.game.entities.listener.EntityListener;
import retaliation.game.entities.listener.SpaceshipShootingListener;
import retaliation.game.geometry.Position;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.*;
import static retaliation.game.entities.Entity.State.Destroyed;
import static retaliation.game.entities.EntityType.*;
import static retaliation.game.geometry.Dimension.size;

public class Entities implements SpaceshipShootingListener {

    private final List<Entity> allEntities = new ArrayList<Entity>();
    private EntityListener listener;

    public Entities(EntityListener listener) {
        this.listener = listener;
    }

    public void add(Entity entity) {
        allEntities.add(entity);
        notifyListener(entity);
    }

    public void add(Spaceship ship) {
        ship.registerShootingListener(this);
        add((Entity) ship);
    }

    public Iterable<? extends Entity> activeEntities() {
        return allEntities;
    }

    public void clearDestroyed() {
        for (Entity destroyed : filter(allEntities, byState(Destroyed))) {
            listener.entityDestroyed(destroyed);
        }
        removeIf(allEntities, byState(Destroyed));
    }

    private void notifyListener(Entity entity) {
        if (entity instanceof Spaceship) {
            listener.spaceshipCreated((Spaceship) entity);
        } else {
            listener.entityCreated(entity);
        }
    }

    @Override
    public void fired(Position from) {
        add(new Entity(Laser, from, size(1, 3)));
    }

    public Spaceship getPlayerShip() {
        return (Spaceship) find(allEntities, byType(Player));
    }

    public Iterable<Spaceship> allShips() {
        Iterable<Spaceship> enemies = transform(filterByType(Enemy), new Function<Entity, Spaceship>() {
            @Override public Spaceship apply(Entity entity) { return (Spaceship) entity; }
        });
        return enemies;
        //return concat(enemies, asList(getPlayerShip()));
    }

    public Iterable<Entity> filterByType(EntityType type) {
        return filter(allEntities, byType(type));
    }

    private Predicate<Entity> byType(final EntityType type) {
        return new Predicate<Entity>() {
            @Override public boolean apply(Entity entity) { return entity.getType() == type; }
        };
    }

    private Predicate<Entity> byState(final Entity.State state) {
        return new Predicate<Entity>() {
            @Override public boolean apply(Entity entity) { return entity.state() == state; }
        };
    }
}
