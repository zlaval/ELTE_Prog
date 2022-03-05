package hu.elte.progtech.model.ship;
import hu.elte.progtech.consts.ShipType;
import lombok.Getter;

@Getter
public class ShipUnderConstruct {

    private int constructionTime;

    private ShipType shipType;

    public ShipUnderConstruct(ShipType shipType) {
        this.shipType = shipType;
        this.constructionTime = shipType.getConstructionTime();
    }

    public boolean construct(){
        constructionTime--;
        return constructionTime <= 0;
    }
}
