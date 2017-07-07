package io.anuke.novix.ui;

import com.badlogic.gdx.Gdx;

import io.anuke.novix.Vars;
import io.anuke.novix.internal.Tool;
import io.anuke.ucore.core.DrawContext;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.layout.Table;

public class BottomMenu extends Table{
	private FlipButton flip;
	private BottomSlider slider;

	public BottomMenu(){
		setFillParent(true);
		setup();
	}
	
	public void toggle(){
		flip.flip();
		slider.slide(!flip.flipped());
	}
	
	public void updateLayerDisplay(){
		slider.display.updateImage();
	}
	
	private void setup(){
		slider = new BottomSlider();
		DrawContext.scene.add(slider);
		
		bottom().left();
		
		int amount = Tool.values().length + 2;
		
		
		flip = new FlipButton(true);
		
		flip.clicked(()->{
			slider.slide(!flip.flipped());
			
			if(Vars.ui.top().open()){
				Vars.ui.top().toggle();
			}
		});
		
		add(flip).colspan(amount).height(60).growX();
		
		row();
		
		float size = Gdx.graphics.getWidth() / (amount);
		
		ButtonGroup<ImageButton> group = new ButtonGroup<>();
		
		defaults().expandX().size(size+0.5f);
		
		for(int i = 0 ; i < Tool.values().length; i ++){
			Tool tool = Tool.values()[i];
			ImageButton button = new ImageButton("icon-" + tool.name(), "toggle");
			button.resizeImage(48);
			button.clicked(()->{
				Vars.control.setTool(tool);
			});
			group.add(button);
			add(button);
		}
		
		ImageButton undo = new ImageButton("icon-undo");
		ImageButton redo = new ImageButton("icon-redo");
		
		undo.resizeImage(48);
		redo.resizeImage(48);
		
		undo.clicked(()->{
			Vars.drawing.undo();
		});
		redo.clicked(()->{
			Vars.drawing.redo();
		});
		
		add(undo);
		add(redo);
		
		pack();
		slider.padBottom(getHeight());
	}
	
	public boolean open(){
		return !flip.flipped();
	}
}