package hu.elte.progtech.utils;

public enum Images {

    CRUISER_MENU("cruiser_menu.png", null),
    MENU_BG("menu_bg.jpg", null),
    SPACE_BG("bg.jpg", null),

    COLONY_SHIP("colony_ship.png", Size.get(60, 60)),
    CRUISER("cruiser.png", Size.get(40, 40)),
    BATTLESHIP("battleship.png", Size.get(60, 35)),
    FIGHTER("fighter.png", Size.get(30, 30)),
    MOTHERSHIP("mothership.png", Size.get(90, 90)),
    SHUTTLE("shuttle.png", Size.get(40, 40)),

    ABEIR("abeir.png", Size.get(70, 70)),
    CAPRICA("caprica.png", Size.get(120, 120)),
    KASJYYYK("kasjyyyk.png", Size.get(150, 120)),
    KRYPTON("krypton.png", Size.get(120, 120)),
    LV426("lv426.png", Size.get(160, 160)),
    PROXIMA("proxima.png", Size.get(70, 70)),

    BLACK_HOLE("black_hole.png", Size.get(120, 120)),
    METEOR("meteor.png", Size.get(190, 150)),
    LASER("laser.png", Size.get(20, 20)),
    EXPLOSION("explosion.png", Size.get(64, 64));

    private String path;
    private Size size;

    Images(String path, Size size) {
        this.path = path;
        this.size = size;
    }

    public String path() {
        return path;
    }

    public Size getSize() {
        return size;
    }
}
