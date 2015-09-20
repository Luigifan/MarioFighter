package com.mikesantiago.mariofighter;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mikesantiago.mariofighter.assets.Animation;
public class PlayerOne 
{
	public enum Direction
	{
		LEFT, RIGHT, STOP
	}
	
	private BodyDef playerBodyDef;
	private Body playerBody;
	private PolygonShape playerShape;
	private FixtureDef playerFixtureDef;
	private Fixture playerFixture;
	private boolean isMoving = false;
	private Direction currentDirection = Direction.RIGHT;
	private Animation animationRight;
	private Animation animationLeft;

	public boolean getMoving(){return isMoving;}
	public Direction getCurrentDirection(){return currentDirection;}
	
	public PlayerOne(World worldToCreateIn)
	{
		playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(32f / PPM, 256f / PPM));
		
		playerBody = worldToCreateIn.createBody(playerBodyDef);
		
		playerShape = new PolygonShape();
		playerShape.setAsBox((32f / 2) / PPM, (64f / 2) / PPM);
		
		playerFixtureDef = new FixtureDef();
		playerFixtureDef.shape = playerShape;
		playerFixtureDef.filter.categoryBits = GlobalVariables.PLAYER_BIT;
		playerFixtureDef.filter.maskBits = GlobalVariables.GROUND_BIT;
		playerFixture = playerBody.createFixture(playerFixtureDef);
		playerFixture.setUserData("body");
		
		//create foot sensor
		{
			playerShape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -32 / PPM), 0);
			playerFixtureDef.shape = playerShape;
			playerFixtureDef.filter.categoryBits = GlobalVariables.PLAYER_BIT;
			playerFixtureDef.filter.maskBits = GlobalVariables.GROUND_BIT;
			playerFixtureDef.isSensor = true;
			playerBody.createFixture(playerFixtureDef).setUserData("FOOT");
		}
		
		CreateAnimation();
		System.out.println("Player 1 created!");
	}

	private void CreateAnimation()
	{
		float updateInterval = 1 / 20f;
		
		Texture tex = GlobalVariables.manager.GetTexture("mario");
		TextureRegion[] sprites = TextureRegion.split(tex, 16, 28)[0];
		if(animationRight == null)
			animationRight = new Animation();
		animationRight.setFrames(sprites, updateInterval);
		
		if(animationLeft == null)
			animationLeft = new Animation();
		TextureRegion[] leftSprites = TextureRegion.split(tex, 16, 28)[0];
		for(TextureRegion tr : leftSprites)
			tr.flip(true, false);
		
		animationLeft.setFrames(leftSprites, updateInterval);
		
		sprWidth = sprites[0].getRegionWidth();
		sprHeight = sprites[0].getRegionHeight();
	}
	
	private int sprWidth, sprHeight;
	
	public void update(float dt)
	{
		if(this.isMoving)
		{
			animationRight.update(dt);
			animationLeft.update(dt);
		}
	}
	
	public void render(SpriteBatch sb)
	{
		if(!sb.isDrawing())
			sb.begin();
		sb.setProjectionMatrix(GlobalVariables.maincamera.combined);
		if(this.isMoving)
		{
			if(this.currentDirection == Direction.RIGHT)
			{
				sb.draw(animationRight.getFrame(), 
					(playerBody.getPosition().x * PPM) - 16, (playerBody.getPosition().y * PPM) - 34, 
				sprWidth * 2, sprHeight * 2);
			}
			else if(this.currentDirection == Direction.LEFT)
			{
				sb.draw(animationLeft.getFrame(), 
					(playerBody.getPosition().x * PPM) - 16, (playerBody.getPosition().y * PPM) - 34, 
				sprWidth * 2, sprHeight * 2);
			}
		}
		else
		{
			if(this.currentDirection == Direction.RIGHT)
			{
				sb.draw(animationRight.getFrame(0), 
					(playerBody.getPosition().x * PPM) - 16, (playerBody.getPosition().y * PPM) - 34, 
				sprWidth * 2, sprHeight * 2);
			}
			else if(this.currentDirection == Direction.LEFT)
			{
				sb.draw(animationLeft.getFrame(0), 
					(playerBody.getPosition().x * PPM) - 16, (playerBody.getPosition().y * PPM) - 34, 
				sprWidth * 2, sprHeight * 2);
			}
		}
		if(sb.isDrawing())
			sb.end();
	}
	
	public void setMoving(boolean a){this.isMoving = a;}
	public void setCurrentDirection(Direction a){currentDirection = a;}
	
	public BodyDef getPlayerBodyDef() {
		return playerBodyDef;
	}

	public void setPlayerBodyDef(BodyDef playerBodyDef) {
		this.playerBodyDef = playerBodyDef;
	}

	public Body getPlayerBody() {
		return playerBody;
	}

	public void setPlayerBody(Body playerBody) {
		this.playerBody = playerBody;
	}

	public PolygonShape getPlayerShape() {
		return playerShape;
	}

	public void setPlayerShape(PolygonShape playerShape) {
		this.playerShape = playerShape;
	}

	public FixtureDef getPlayerFixtureDef() {
		return playerFixtureDef;
	}

	public void setPlayerFixtureDef(FixtureDef playerFixtureDef) {
		this.playerFixtureDef = playerFixtureDef;
	}

	public Fixture getPlayerFixture() {
		return playerFixture;
	}

	public void setPlayerFixture(Fixture playerFixture) {
		this.playerFixture = playerFixture;
	}
	
	
}
