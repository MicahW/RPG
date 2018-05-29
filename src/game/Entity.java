package game;

import java.awt.Rectangle;
import java.util.ArrayList;

import skills.Skill;

public abstract class Entity {
	//position on board
	private double x_pos;
	private double y_pos;
	
	//state of the entity
	private enum state{IDLE,MOVING,USING_SKILL};
	
	//hitbox for the entity
	private Rectangle hitBox;
	
	//animation for the entitiy
	private Animation animation;
	
	
	private EntityResource health,stamina,mana;
	
	private ArrayList<Skill> skills;
	
	//melle damage
	int strength;
	//health,stamina 
	int consitution;
	//speed
	int agility;
	//mana, skill power
	int inteligence;
	

}
