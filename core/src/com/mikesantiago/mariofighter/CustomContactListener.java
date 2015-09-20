package com.mikesantiago.mariofighter;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener
{
	private int numFootContact = 0;
	//when two fixtures start to collide
	public void beginContact(Contact c)
	{
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact++;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact++;
		}
		//System.out.println("A: " + c.getFixtureA().getUserData().toString() + "\nB: " + c.getFixtureB().getUserData().toString());
	}
	
	//when two fixtures are no longer colliding
	public void endContact(Contact c)
	{
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("FOOT"))
		{
			numFootContact--;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("FOOT"))
		{
			numFootContact--;
		}
	}
	
	public boolean getPlayerOnGround()
	{
		return numFootContact > 0 ? true : false;
	}
	//detection then handling
	//presolve is between detection and handling
	//if you want to do weird things you can do that here lol
	public void preSolve(Contact c, Manifold m){}
	
	//postsolve happens after those
	public void postSolve(Contact c, ContactImpulse ci){}

}
