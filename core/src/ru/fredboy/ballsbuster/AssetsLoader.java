package ru.fredboy.ballsbuster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class AssetsLoader{

    private static Texture ExplosionTexture;

    public static boolean useSound = false;
    private static boolean loaded = false;

    public static BitmapFont Roboto;
    public static BitmapFont Moonhouse;
    public static Array<Sprite> Planets = new Array();
    public static Array<Sprite> Explosion = new Array();
    public static Array<Sprite> SmallExplosion = new Array();
    public static Array<Sprite> Button = new Array();
    public static Array<Sprite> SoundBtn = new Array();
    public static Sprite InfoBtn;
    public static Sprite Asteroid;
    public static Sprite Background;
    public static Sprite GameLogo;
    public static Animation ExplosionAnim;
    public static Animation SmallExplosionAnim;

    public static Music GameMusic;
    public static Sound ExplosionSound;

    private static GlyphLayout layout;

    public static int getStringWidth(String s){
        layout.setText(Moonhouse,s);
        return (int)layout.width;
    }

    public static int getFontHeight(){
        layout.setText(Moonhouse,"MOONHOUSE");
        return (int)layout.height;
    }

    public static int getRobotoWidth(String s){
        layout.setText(Roboto,s);
        return (int)layout.width;
    }

    public static int getRobotoHeight(){
        layout.setText(Roboto,"ROBOTO");
        return (int)layout.height;
    }

    public static void load() {
        if (loaded) clear(); else loaded = true;
        layout = new GlyphLayout();
        Gdx.app.debug("AssetsLoader","Loading fonts...");
        Roboto = new BitmapFont(Gdx.files.internal("roboto.fnt"),true);
        Moonhouse = new BitmapFont(Gdx.files.internal("moonhouse.fnt"),true);
        Gdx.app.debug("AssetsLoader","Loading logo.png...");
        GameLogo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
        GameLogo.flip(false,true);
        Gdx.app.debug("AssetsLoader","Loading sprites...");
        Gdx.app.debug("AssetsLoader","Loading asteroid.png...");
        Asteroid = new Sprite(new Texture(Gdx.files.internal("asteroid.png")));
        for (int i=0; i<18; i++) {
            Planets.add(new Sprite(new Texture(Gdx.files.internal("planet"+(i+1)+".png"))));
            Planets.get(i).setSize(180,180);
            Gdx.app.debug("AssetsLoader","planet"+(i+1)+".png");
        }
        ExplosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        Explosion.clear();
        for (int i=0; i<64; i++) {
            Gdx.app.debug("AssetsLoader","Loading explosions... "+(i+1)+"/64");
            Explosion.add(new Sprite(ExplosionTexture,(i%8)*256,(i/8)*256,256,256));
            SmallExplosion.add(new Sprite(ExplosionTexture,(i%8)*256,(i/8)*256,256,256));
            SmallExplosion.get(i).setSize(96,96);
        }
        ExplosionAnim = new Animation(0.01f,Explosion,Animation.PlayMode.NORMAL);
        SmallExplosionAnim = new Animation(0.005f,SmallExplosion,Animation.PlayMode.NORMAL);
        Gdx.app.debug("AssetsLoader","Loading background.png...");
        Background = new Sprite(new Texture(Gdx.files.internal("background.png")));
        Gdx.app.debug("AssetsLoader","Loading buttons...");
        Button.clear();
        for (int i=0; i<3; i++) {
            Gdx.app.debug("AssetsLoader","Loading buttons... "+(i+1)+"/3");
            Button.add(new Sprite(new Texture(Gdx.files.internal("button"+i+".png"))));
            Button.get(i).setSize(300,80);
        }
        SoundBtn.add(new Sprite(new Texture(Gdx.files.internal("sound0.png"))));
        SoundBtn.add(new Sprite(new Texture(Gdx.files.internal("sound1.png"))));
        InfoBtn = new Sprite(new Texture(Gdx.files.internal("info.png")));
        InfoBtn.flip(false,true);
        GameMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        GameMusic.setVolume(0.25f);
        ExplosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
        Gdx.app.debug("AssetsLoader","...done");
    }

    public static void clear() {
        ExplosionTexture.dispose();
        Roboto.dispose();
        Moonhouse.dispose();
        for (Sprite s : Planets) {
            s.getTexture().dispose();
        }
        Planets.clear();
        for (Sprite s : Explosion) {
            s.getTexture().dispose();
        }
        Explosion.clear();
        for (Sprite s : SmallExplosion) {
            s.getTexture().dispose();
        }
        SmallExplosion.clear();
        for (Sprite s : Button) {
            s.getTexture().dispose();
        }
        Button.clear();
        for (Sprite s : SoundBtn) {
            s.getTexture().dispose();
        }
        SoundBtn.clear();
        InfoBtn.getTexture().dispose();
        Asteroid.getTexture().dispose();
        Background.getTexture().dispose();
        GameLogo.getTexture().dispose();
        GameMusic.dispose();
        ExplosionSound.dispose();
    }
}
