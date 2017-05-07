package alteirac.srmanager.Model;

import java.util.List;

/**
 * Created by Jean on 06/05/2017.
 */

public class Team {

    private int id;
    private String name;
    private List<String> players;
    private byte[] byteImage;

    public Team() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }
}
