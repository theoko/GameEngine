package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//*********TERRAIN TEXTURE*********
		
	    TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("fern"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flower"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("grassTexture"));
		
		TerrainTexturePack texturepack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendmap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		//*********************************
		
		ModelData data = OBJFileLoader.loadOBJ("tree");
		@SuppressWarnings("unused")
		ModelData data2 = OBJFileLoader.loadOBJ("grassModel");
		@SuppressWarnings("unused")
		ModelData data3 = OBJFileLoader.loadOBJ("fern");
		@SuppressWarnings("unused")
		ModelData data4 = OBJFileLoader.loadOBJ("dragon");
		
		@SuppressWarnings("unused")
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		
		TexturedModel staticModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader),new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));	
		}
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		@SuppressWarnings("unused")
		ModelTexture texture1 = new ModelTexture(loader.loadTexture("grass"));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
//		Entity entity2 = new Entity(staticModel, new Vector3f(0,0,-52),0,0,0,1);
//		Entity entity3 = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
		
		Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1,loader,texturepack,blendmap);
		Terrain terrain2 = new Terrain(-1,-1,loader,texturepack,blendmap);
		
		RawModel bunnyModel = OBJLoader.loadObjModel("person", loader);

		TexturedModel bunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
		
		Player player = new Player(bunny, new Vector3f(0, 0, -50), 0, 0, 0, 1);
		
		MasterRenderer renderer = new MasterRenderer(); 
		
		Camera camera = new Camera(player);
	
		while(!Display.isCloseRequested()) {
			
			camera.move();
			player.move();
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for (int i = 0; i < 500; i++) {
				entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
				entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
				entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));		
			}
//			for(Entity entity : entities) {
			renderer.processEntity(entity);
//			renderer.processEntity(entity2);
//			renderer.processEntity(entity2);
//			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.cleanUP();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}

}


//float[] vertices = {			
//-0.5f,0.5f,-0.5f,	
//-0.5f,-0.5f,-0.5f,	
//0.5f,-0.5f,-0.5f,	
//0.5f,0.5f,-0.5f,		
//
//-0.5f,0.5f,0.5f,	
//-0.5f,-0.5f,0.5f,	
//0.5f,-0.5f,0.5f,	
//0.5f,0.5f,0.5f,
//
//0.5f,0.5f,-0.5f,	
//0.5f,-0.5f,-0.5f,	
//0.5f,-0.5f,0.5f,	
//0.5f,0.5f,0.5f,
//
//-0.5f,0.5f,-0.5f,	
//-0.5f,-0.5f,-0.5f,	
//-0.5f,-0.5f,0.5f,	
//-0.5f,0.5f,0.5f,
//
//-0.5f,0.5f,0.5f,
//-0.5f,0.5f,-0.5f,
//0.5f,0.5f,-0.5f,
//0.5f,0.5f,0.5f,
//
//-0.5f,-0.5f,0.5f,
//-0.5f,-0.5f,-0.5f,
//0.5f,-0.5f,-0.5f,
//0.5f,-0.5f,0.5f
//
//};
//
//float[] textureCoords = {
//
//0,0,
//0,1,
//1,1,
//1,0,			
//0,0,
//0,1,
//1,1,
//1,0,			
//0,0,
//0,1,
//1,1,
//1,0,
//0,0,
//0,1,
//1,1,
//1,0,
//0,0,
//0,1,
//1,1,
//1,0,
//0,0,
//0,1,
//1,1,
//1,0
//
//
//};
//
//int[] indices = {
//0,1,3,	
//3,1,2,	
//4,5,7,
//7,5,6,
//8,9,11,
//11,9,10,
//12,13,15,
//15,13,14,	
//16,17,19,
//19,17,18,
//20,21,23,
//23,21,22
//
//};