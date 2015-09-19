package com.mikesantiago.mariofighter.states;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;
import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mikesantiago.mariofighter.CustomContactListener;
import com.mikesantiago.mariofighter.GlobalVariables;
public class Stage 
{
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dcam;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;
	private Vector3 bgOffset = new Vector3(0, 0, 1f);
	
	private Texture bgTexture;
	
	int mapWidth, mapHeight;
	
	private String mapPath;
	
	public OrthographicCamera getB2Dcam(){return b2dcam;}
	public void setB2dcam(OrthographicCamera cam){b2dcam = cam;}
	
	public Stage(String pathToMap)
	{
		mapPath = pathToMap;
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(new CustomContactListener());
		b2dr = new Box2DDebugRenderer();
		
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, GlobalVariables.V_WIDTH / PPM, GlobalVariables.V_HEIGHT / PPM);
		
		LoadMap(mapPath);
	}
	
	private void LoadMap(String path)
	{
		tileMap = new TmxMapLoader().load(path);
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		GetMapSizes();
		
		String pathToBackground = tmr.getMap().getProperties().get("background").toString();
		SetupBgTexture(pathToBackground);
		BuildBox2DBodies();
	}
	
	private void GetMapSizes()
	{
		MapProperties properties = tmr.getMap().getProperties();
		int mapWidth = properties.get("width", Integer.class);
		int mapHeight = properties.get("height", Integer.class);
		int tileWidth = properties.get("tilewidth", Integer.class);
		int tileHeight = properties.get("tileheight", Integer.class);
		
		this.mapWidth = mapWidth * tileWidth;
		this.mapHeight = mapHeight * tileHeight;
	}
	
	private void SetupBgTexture(String pathToTexture)
	{
		Pixmap source = new Pixmap(new FileHandle(Gdx.files.getLocalStoragePath() + pathToTexture));
		Pixmap output = new Pixmap(mapWidth, mapHeight, source.getFormat());
		int ymod = 0;
		for(int x = 0; x < mapWidth; x++)
		{
			for(int y = 0; y < mapHeight; y++)
			{
				output.drawPixmap(source, x * source.getWidth(), y * source.getWidth());
			}
		}
		bgTexture = new Texture(output);
		output.dispose();
		System.out.println("Background texture is " + bgTexture.getWidth() + " x " + bgTexture.getHeight());
	}
	
	private void BuildPlayerOne()
	{
		
	}
	
	private void BuildBox2DBodies()
	{
		TiledMapTileLayer floorLayer = (TiledMapTileLayer)tileMap.getLayers().get("floor");
		
		BodyDef bdef = new BodyDef();
		Body body;
		FixtureDef fdef = new FixtureDef();
		Fixture fixture;
		
		ChainShape shapeDef = new ChainShape();
		
		for(int row = 0; row < floorLayer.getHeight(); row++)
		{
			for(int col = 0; col < floorLayer.getWidth(); col++)
			{
				Cell cell = floorLayer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				//create body + fixture from cell
				bdef = new BodyDef();
				bdef.type = BodyType.StaticBody;
				bdef.position.set(((col + .5f) * 32f) / PPM, ((row + .5f) * 32f) / PPM);
				
				Vector2[] v = new Vector2[4];
				v[0] = new Vector2(-32f / 2 / PPM, -32f / 2 / PPM); //bot left
				v[1] = new Vector2(-32f / 2 / PPM, 32f / 2 / PPM); //top left
				v[2] = new Vector2(32f / 2 / PPM, 32f / 2 / PPM); //top right
				v[3] = new Vector2(32f / 2 / PPM, -32f / 2 / PPM); //bot right
				shapeDef = new ChainShape();
				shapeDef.createChain(v);
				fdef.friction = 0;
				fdef.shape = shapeDef;
				fdef.filter.categoryBits = GlobalVariables.GROUND_BIT; //type it is
				fdef.filter.maskBits = GlobalVariables.PLAYER_BIT; //types allowed to collide with; use | to specify multiple
				fdef.isSensor = false;
				
				world.createBody(bdef).createFixture(fdef);
			}
		}
	}
	
	public void update()
	{	
		world.step(1 / 60f, 6, 2);
	}
	
	public void render(SpriteBatch sb)
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!sb.isDrawing())
			sb.begin();
		//Pre world render stuff, like backgrounds
		{
			tmr.setView(GlobalVariables.backgroundcam);
			if(!tmr.getBatch().isDrawing())
				tmr.getBatch().begin();
			tmr.getBatch().draw(bgTexture, 
					(backgroundcam.position.x - (GlobalVariables.V_WIDTH / 2) + bgOffset.x) - 200, 
					(backgroundcam.position.y - (GlobalVariables.V_HEIGHT / 2) + bgOffset.y) - 200, 
					bgTexture.getWidth() * bgOffset.z, 
					bgTexture.getHeight() * bgOffset.z);
			if(tmr.getBatch().isDrawing())
				tmr.getBatch().end();
		}
		
		//misc like box2d debug
		{
			tmr.setView(GlobalVariables.maincamera);
			tmr.render();
			b2dr.render(world, b2dcam.combined);
		}
		
		//render map/foreground
		{
			tmr.setView(GlobalVariables.hudcam);
			if(!tmr.getBatch().isDrawing())
				tmr.getBatch().begin();
			//TODO: hud
			
			if(tmr.getBatch().isDrawing())
				tmr.getBatch().end();
		}
		
		if(sb.isDrawing())
			sb.end();
	}
	
	public void UpdateBgOffset(Vector3 offset) {bgOffset = offset;}
	
	public int getMapHeight(){return mapHeight;}
	public int getMapWidth(){return mapWidth;}
	public Vector3 getBackgroundOffset(){return bgOffset;}
	
}
