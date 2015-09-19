package com.mikesantiago.mariofighter.states;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mikesantiago.mariofighter.GlobalVariables;
import com.mikesantiago.mariofighter.assets.AssetManager;

public class TestState 
{
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private AssetManager manager;
	
	private OrthographicCamera b2dcam;
	
	private TiledMap tileMap; //the real map
	private OrthogonalTiledMapRenderer tmr; //renders map
	
	public TestState(AssetManager tm)
	{
		manager = tm;
		world = new World(new Vector2(0, -9.81f), true);
		b2dr = new Box2DDebugRenderer();
		
		//create basic bitch platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160f / PPM, 120f / PPM);
		bdef.type = BodyType.StaticBody;
		
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(150 / PPM, 50 / PPM);
		FixtureDef fixtureDefinition = new FixtureDef();
		fixtureDefinition.shape = shape;
		body.createFixture(fixtureDefinition);
		
		//falling box
		bdef.position.set(160/ PPM, 400f/ PPM);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		shape.setAsBox(10 / PPM, 10 / PPM);
		fixtureDefinition.restitution = 0.2f;
		fixtureDefinition.shape = shape;
		body.createFixture(fixtureDefinition);
		
		//setup box2dcam so we can use our conversion
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, GlobalVariables.V_WIDTH / PPM, GlobalVariables.V_HEIGHT / PPM);
		
		//////////////////////////
		
		//Tiled stuffs
		tileMap = new TmxMapLoader().load("assets/maps/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		String pathToBackground = tmr.getMap().getProperties().get("background").toString();
		Texture bg = new Texture(Gdx.files.getLocalStoragePath() + pathToBackground);
		GlobalVariables.manager.LoadResource("castle_bg", bg);
	}
	
	public void update()
	{
		world.step(1 / 60f, 6, 2);
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			Vector3 newPos = maincamera.position;
			newPos.x += 8;
			maincamera.position.set(newPos);
			maincamera.update();
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			Vector3 newPos = maincamera.position;
			newPos.x -= 8;
			maincamera.position.set(newPos);
			maincamera.update();
		}
	}
	
	public void render(SpriteBatch sb)
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		
		sb.draw(GlobalVariables.manager.GetTexture("castle_bg"), 0, 0);
		
		tmr.setView(maincamera);
		tmr.render();
		
		b2dr.render(world, b2dcam.combined);
		
		manager.GetFont().draw(sb, "top kek", 32, 32);
		sb.end();
	}

}
