package game.gameobject.unit.model;

import game.core.GameWorld;
import game.gameobject.model.GameObject;
import game.map.model.Tile;

/**
 * Created by Max & Edik on 6/27/2014.
 *
 * Юнит.
 */
public class Unit extends GameObject {
    private static final String MSG_UNIT_DIED_IN_ASTRAL = "Смерть юнита в междумирье.";
    private int hp;
    private int maxHp;
    private boolean alive;
    private String name;
    private int speedX;
    private int speedY;
    private int maxSpeed;
    private GameWorld gameWorld;

    public Unit(final Integer x, final Integer y, final String spriteFileName,
                boolean alive, String name, int hp,
                int speedX, int speedY, int maxSpeed, GameWorld gameWorld) {
        super(x, y, spriteFileName, gameWorld);
        setGameWorld(gameWorld);
        setAlive(alive);
        setHp(hp);
        setName(name);
        setSpeedX(speedX);
        setSpeedY(speedY);
        setMaxSpeed(maxSpeed);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp <= 0) {
            this.hp = 0;
            onDie();

            return;
        }

        if (hp > getMaxHp())
            this.hp = getMaxHp();
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = (maxHp < 0 ? maxHp : 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeHp(int diff) {
        setHp(getHp() + diff);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void onDie() {
        if (isAlive()) {
            setAlive(false);

            if (gameWorld == null)
                throw new NullPointerException(MSG_UNIT_DIED_IN_ASTRAL);

            setSpeedX(0);
            setSpeedY(0);
            gameWorld.removeGameObject(this);
        }
    }

    public void moveTo(final Integer x, final Integer y) {
        // TODO: сделать перемещение резидента в тайн (x, y)
        final GameObject gameObject = gameWorld.getCurrentLevel()
                .getLevelMap().getTile(getX(), getY()).popGameObject();
        setXY(x, y);
        gameWorld.getCurrentLevel().getLevelMap().getTile(getX(), getY()).pushGameObject(gameObject);
    }

    @Override
    public void update() {
        int vx = getSpeedX();
        int vy = getSpeedY();

        if (vx != 0 || vy != 0) {
            int dx = 0;
            int dy = 0;

            if (vx < 0)
                dx = 1;
            else if (vx > 0)
                dx = -1;

            while (vx != 0) {
                final int newX = getX() - dx;

                if (canGo(newX, getY())) {
                    moveTo(newX, null);
                } else {
                    setSpeedX(0);
                }

                vx += dx;
            }

            if (vy < 0)
                dy = 1;
            else
            if (vy > 0)
                dy = -1;

            while (vy != 0) {
                final int newY = getY() + vy;

                if (canGo(getX(), newY)) {
                    moveTo(null, newY);
                } else {
                    setSpeedY(0);
                }

                vy += dy;
            }
        }
    }

    private boolean canGo(int x, int y) {
        if (gameWorld == null)
            throw new NullPointerException(MSG_UNIT_DIED_IN_ASTRAL);

        return !gameWorld.isOccupied(x, y);
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
}
