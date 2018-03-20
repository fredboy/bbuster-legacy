package ru.fredboy.ballsbuster.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import ru.fredboy.ballsbuster.BallsBuster;
import ru.fredboy.ballsbuster.IActivityRequestHandler;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler {

    @Override
    public GwtApplicationConfiguration getConfig () {
        final GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1024, 768);
        cfg.preferFlash = false;
        return cfg;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new BallsBuster(this);
    }

    @Override
    public void showBanner(boolean show) {
    }

    @Override
    public void showInterstitial() {
    }
}