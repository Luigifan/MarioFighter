package com.mikesantiago.mariofighter;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener
{
	//when two fixtures start to collide
	public void beginContact(Contact c)
	{
		System.out.println("Began contact!");
	}
	
	//when two fixtures are no longer colliding
	public void endContact(Contact c){}
	
	//detection then handling
	//presolve is between detection and handling
	//if you want to do weird things you can do that here lol
	public void preSolve(Contact c, Manifold m){}
	
	//postsolve happens after those
	public void postSolve(Contact c, ContactImpulse ci){}

}
