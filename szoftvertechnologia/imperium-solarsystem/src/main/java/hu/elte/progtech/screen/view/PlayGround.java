package hu.elte.progtech.screen.view;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.draw.Drawable;
import hu.elte.progtech.draw.planet.AbierPlanetSprite;
import hu.elte.progtech.draw.planet.BlackHoleSprite;
import hu.elte.progtech.draw.planet.CapricaPlanetSprite;
import hu.elte.progtech.draw.planet.KasjyyykPlanetSprite;
import hu.elte.progtech.draw.planet.KryptonPlanetSprite;
import hu.elte.progtech.draw.planet.Lv426PlanetSprite;
import hu.elte.progtech.draw.planet.MeteorSprite;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.draw.planet.ProximaPlanetSprite;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.planet.Planets;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class PlayGround {

    private Map<Planets, PlanetSprite> planetSprites;
    public static final Map<Planets, PlanetSprite> PLANETS = createPlanets();
    public PlayGround() {
        this.planetSprites = PLANETS;
    }

    public Planet getPlanet(Planets name) {
        return planetSprites.get(name).getPlanet();
    }

    public Collection<PlanetSprite> getPlanetSprites() {
        return planetSprites.values();
    }

    private static Map<Planets, PlanetSprite> createPlanets() {
        var caprica = new CapricaPlanetSprite(new Coord(220, 30));
        var krypton = new KryptonPlanetSprite(new Coord(1350, 800));

        var abeit = new AbierPlanetSprite(new Coord(450, 450));
        var proxima = new ProximaPlanetSprite(new Coord(1200, 450));

        var kasjyyyk = new KasjyyykPlanetSprite(new Coord(750, 130));
        var lv436 = new Lv426PlanetSprite(new Coord(800, 750));

        var meteor = new MeteorSprite(new Coord(1000, 20));
        var blackHole = new BlackHoleSprite(new Coord(850, 500));

        return Map.of(
                Planets.ABEIT, abeit,
                Planets.CAPRICA, caprica,
                Planets.KASJYYYK, kasjyyyk,
                Planets.KRYPTON, krypton,
                Planets.LV436, lv436,
                Planets.PROXIMA, proxima,
                Planets.METEOR, meteor,
                Planets.BLACK_HOLE, blackHole
        );
    }

    public void drawPlayGround() {
        DrawManager.getGraphics().drawImage(ImageContainer.getInstance().image(Images.SPACE_BG), 0, 0, null);
        planetSprites.values().forEach(Drawable::render);
    }

}
