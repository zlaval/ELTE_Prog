package com.zlrx.elte.slideout.resource;

public enum ImageResource {

    ARROW_UP("arrow_up.png"),
    ARROW_DOWN("arrow_down.png"),
    ARROW_LEFT("arrow_left.png"),
    ARROW_RIGHT("arrow_right.png"),
    ROCK_WHITE("white_rock.png"),
    ROCK_BLACK("black_rock.png");

    private String resource;

    ImageResource(String resource) {
        this.resource = resource;
    }

    public String resource() {
        return resource;
    }
}
