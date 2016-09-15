package io.anuke.pixeleditor.managers;

import io.anuke.pixeleditor.graphics.Palette;
import io.anuke.pixeleditor.modules.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class PaletteManager{
	private Core main;
	private Json json = new Json();
	private Palette currentPalette;
	private ObjectMap<String, Palette> palettes = new ObjectMap<String, Palette>();
	private Array<Palette> palettesort = new Array<Palette>();

	public PaletteManager(Core main){
		this.main = main;
	}

	public Palette getCurrentPalette(){
		return currentPalette;
	}

	public void setCurrentPalette(Palette palette){
		currentPalette = palette;
	}

	public Iterable<Palette> getPalettes(){
		palettesort.clear();
		for(Palette palette : palettes.values())
			palettesort.add(palette);
		palettesort.sort();
		return palettesort;
	}

	public void removePalette(Palette palette){
		palettes.remove(palette.id);
	}

	public void addPalette(Palette palette){
		palettes.put(palette.id, palette);
	}

	public void savePalettes(){
		String string = json.toJson(palettes);
		main.paletteFile.writeString(string, false);
	}

	@SuppressWarnings("unchecked")
	public void loadPalettes(){
		try{
			palettes = json.fromJson(ObjectMap.class, main.paletteFile);

			String id = main.prefs.getString("lastpalette");
			if(id != null){
				currentPalette = palettes.get(id);
			}

			Gdx.app.log("pedebugging", "Palettes loaded.");
		}catch(Exception e){
			e.printStackTrace();
			Gdx.app.error("pedebugging", "Palette file nonexistant or corrupt.");
		}

		if(currentPalette == null){
			currentPalette = new Palette("Untitled", generatePaletteID(), 8);
			palettes.put(currentPalette.id, currentPalette);
			main.prefs.put("lastpalette", currentPalette.id);
		}
	}

	public String generatePaletteID(){
		long id = MathUtils.random(Long.MAX_VALUE - 1);
		return id + "";
	}
}