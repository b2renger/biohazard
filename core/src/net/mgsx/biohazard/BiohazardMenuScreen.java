package net.mgsx.biohazard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.biohazard.gfx.SceneGFX;
import net.mgsx.biohazard.model.World;
import net.mgsx.biohazard.utils.StageScreen;

public class BiohazardMenuScreen extends StageScreen
{
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	private SceneGFX gfx;
	private Batch batch;

	private BitmapFont font;
	private BitmapFontCache cache;
	
	private float time;
	
	
	
	public BiohazardMenuScreen() {
		super(new FitViewport(World.WIDTH, World.HEIGHT));
		camera = new OrthographicCamera();
		viewport.setCamera(camera);
		renderer = new ShapeRenderer();
		gfx = new SceneGFX(4);
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
		
		Table main = new Table();
		main.defaults();
		root.add(main).expand().fill();
		
		main.add(createLabel("B I O H A Z A R D", World.homeColor, 5)).padBottom(-20).row();
		main.add(createLabel("A story of three blobs", World.enemyColor, 4)).padBottom(20).row();
		
		main.add(createPlayButton("Easy", 1)).padBottom(20).row();
		main.add(createPlayButton("Normal", 2)).padBottom(20).row();
		main.add(createPlayButton("Hard", 3)).row();
		
		
	}
	
	private Actor createLabel(String text, Color color, float scale){
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = new Color(color);
		Label label = new Label(text, style);
		label.setFontScale(scale);
		return label;
	}
	
	private TextButton createPlayButton(String text, final int level){
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap);
		TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(texture));
		up.setLeftWidth(10);
		up.setRightWidth(10);
		
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		//style.fontColor = new Color(World.enemyColor);
		style.up = up.tint(new Color(1,1,0,.3f));
		style.down = up.tint(new Color(0,1,1,.3f));
		TextButton bt = new TextButton("", style);
		bt.setTransform(true);
		// bt.add(new Image(up.tint(World.rayColor))).size(30);
		bt.add(createLabel(text, World.homeColor, 4));
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BiohazardGame.game().startGame(level);
			}
		});
		
		return bt;
	}
	
	@Override
	public void render(float delta) {
		
		time += delta;
		
		gfx.alpha = MathUtils.sin(time * 3) * MathUtils.sin(time * 300) * 0.03f + 1f + (MathUtils.sin(time) > 0.4f ? MathUtils.random() * 0.5f : 0);
		
		gfx.update(delta);
		
		gfx.begin();

		super.render(delta);
		
		gfx.end();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
