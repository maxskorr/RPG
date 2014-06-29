package game.skill.model;

import game.gameobject.model.GameObject;

/**
 * Created by Max on 6/27/2014.
 *
 * Описывает поведение скилла
 */
public abstract class Skill {
    public abstract void act(GameObject go);
}
