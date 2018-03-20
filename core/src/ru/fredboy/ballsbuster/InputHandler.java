package ru.fredboy.ballsbuster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
//import de.golfgl.gdxgamesvcs.GameServiceException;
import ru.fredboy.ballsbuster.game.GameRenderer;
import ru.fredboy.ballsbuster.game.GameWorld;

public class InputHandler extends InputAdapter {

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public InputHandler(GameRenderer gameRenderer, GameWorld gameWorld) {
        this.gameRenderer = gameRenderer;
        this.gameWorld = gameWorld;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            if (GameScreen.state == GameScreen.GameState.PLAY) {
                GameScreen.state = GameScreen.GameState.PAUSED;
                GameScreen.adsHandler.showBanner(true);
            } else if (GameScreen.state == GameScreen.GameState.MENU_MAIN) {
                Gdx.app.exit();
            } else if (GameScreen.state == GameScreen.GameState.MENU_SCORES) {
                GameScreen.state = GameScreen.GameState.MENU_MAIN;
                GameScreen.adsHandler.showBanner(true);
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX *= (float) GameScreen.getWidth() / Gdx.graphics.getWidth();
        screenY *= (float) GameScreen.getHeight() / Gdx.graphics.getHeight();
        if (GameScreen.state == GameScreen.GameState.PLAY && gameWorld.miss<=10) {
            gameWorld.onClick(screenX, screenY);
        } else if (GameScreen.state == GameScreen.GameState.MENU_MAIN) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    ((screenY-120)/100 >= 0 && (screenY-120)/100 < 3)) {
                if (gameRenderer.buttons.get((screenY-120)/100).getType() == 1) {
                    gameRenderer.buttons.get((screenY-120)/100).setType(2);
                }
            }
            if (screenX>GameScreen.getWidth()-96 && screenY>GameScreen.getHeight()-96) {
                AssetsLoader.useSound = !AssetsLoader.useSound;
                if (AssetsLoader.useSound) {
                    AssetsLoader.GameMusic.setLooping(true);
                    AssetsLoader.GameMusic.play();
                } else {
                    AssetsLoader.GameMusic.stop();
                }
                gameWorld.prefs.putBoolean("sound",AssetsLoader.useSound);
                gameWorld.prefs.flush();
            }
            if (screenY>GameScreen.getHeight()-96 && screenX<96){
                GameScreen.state = GameScreen.GameState.ABOUT;
                GameScreen.adsHandler.showBanner(false);
            }
        } else if (GameScreen.state == GameScreen.GameState.OVER) {
            GameScreen.state = GameScreen.GameState.MENU_MAIN;
        } else if (GameScreen.state == GameScreen.GameState.PAUSED) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>220 && screenY<400) {
                if (screenY<300) gameRenderer.buttons.get(5).setType(2);
                if (screenY>320) gameRenderer.buttons.get(6).setType(2);
            }
            gameRenderer.resumeTime = 300;
        } else if (GameScreen.state == GameScreen.GameState.MENU_SCORES) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>GameScreen.getHeight()-100 && screenY<GameScreen.getHeight()-20) {
                gameRenderer.buttons.get(3).setType(2);
            }
//            if (screenX>GameScreen.getWidth()/2+2 && screenX<GameScreen.getWidth()/2+302 &&
//                    screenY>GameScreen.getHeight()-100 && screenY<GameScreen.getHeight()-20) {
//                gameRenderer.buttons.get(4).setType(2);
//            }
        } else if (GameScreen.state == GameScreen.GameState.HINT) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>GameScreen.getHeight()/2 && screenY<GameScreen.getHeight()/2+80) {
                gameRenderer.buttons.get(7).setType(2);
            }
        } else if (GameScreen.state == GameScreen.GameState.ABOUT) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>GameScreen.getHeight()-100 && screenY<GameScreen.getHeight()-20) {
                gameRenderer.buttons.get(8).setType(2);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX *= (float) GameScreen.getWidth() / Gdx.graphics.getWidth();
        screenY *= (float) GameScreen.getHeight() / Gdx.graphics.getHeight();
        if (GameScreen.state == GameScreen.GameState.MENU_MAIN) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    (screenY-120)/100 >= 0 && (screenY-120)/100 < 3) {
                if (gameRenderer.buttons.get((screenY-120)/100).getType()==2) {
                    if ((screenY - 120) / 100 == 0) {
                        gameWorld.newGame();
                        if (!GameScreen.firstLaunch) {
                            GameScreen.state = GameScreen.GameState.PLAY;
                        } else {
                            GameScreen.state = GameScreen.GameState.HINT;
                        }
                        GameScreen.adsHandler.showBanner(false);
                    }
                    if ((screenY - 120) / 100 == 1) {
                        GameScreen.state = GameScreen.GameState.MENU_SCORES;
                        GameScreen.adsHandler.showBanner(false);
                    }
                    if ((screenY - 120) / 100 == 2) Gdx.app.exit();
                    gameRenderer.buttons.get((screenY - 120) / 100).setType(1);
                }
            }
        } else if (GameScreen.state == GameScreen.GameState.PAUSED) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>220 && screenY<400) {
                if (screenY<300 && gameRenderer.buttons.get(5).getType() == 2) {
                    GameScreen.state = GameScreen.GameState.RESUMED;
                    GameScreen.adsHandler.showBanner(false);
                }
                if (screenY>320 && gameRenderer.buttons.get(6).getType() == 2) {
                    GameScreen.state = GameScreen.GameState.MENU_MAIN;
                    GameScreen.adsHandler.showBanner(true);
                }
            }
            gameRenderer.resumeTime = 240;
        } else if (GameScreen.state == GameScreen.GameState.MENU_SCORES) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>GameScreen.getHeight()-100 && screenY<GameScreen.getHeight()-20) {
                if (gameRenderer.buttons.get(3).getType() == 2) {
                    GameScreen.state = GameScreen.GameState.MENU_MAIN;
                    GameScreen.adsHandler.showBanner(true);
                }
            }
//            if (screenX>GameScreen.getWidth()/2+2 && screenX<GameScreen.getWidth()/2+302 &&
//                    screenY>GameScreen.getHeight()-100 && screenY<GameScreen.getHeight()-20) {
                //if (gameRenderer.buttons.get(4).getType() == 2)
                    //try {
                    //    BallsBuster.GS_CLIENT.showLeaderboards("");
                    //} catch (GameServiceException e) {}
//            }
        } else if (GameScreen.state == GameScreen.GameState.HINT) {
            if (screenX>GameScreen.getWidth()/2-150 && screenX<GameScreen.getWidth()/2+150 &&
                    screenY>GameScreen.getHeight()/2 && screenY<GameScreen.getHeight()/2+80) {
                if (gameRenderer.buttons.get(7).getType() == 2) {
                    GameScreen.state = GameScreen.GameState.PLAY;
                    gameWorld.prefs.putBoolean("first",false);
                    gameWorld.prefs.flush();
                    GameScreen.firstLaunch=false;
                }
            }
        } else if (GameScreen.state == GameScreen.GameState.ABOUT) {
            if (screenX > GameScreen.getWidth() / 2 - 150 && screenX < GameScreen.getWidth() / 2 +150 &&
                    screenY > GameScreen.getHeight() - 100 && screenY < GameScreen.getHeight() - 20) {
                if (gameRenderer.buttons.get(8).getType() == 2) {
                    GameScreen.state = GameScreen.GameState.MENU_MAIN;
                    GameScreen.adsHandler.showBanner(true);
                }
            }
        }
        for (int i=0; i<gameRenderer.buttons.size; i++) {
            if (gameRenderer.buttons.get(i).getType() == 2) {
                gameRenderer.buttons.get(i).setType(1);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

}
