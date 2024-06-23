package Session;

import com.google.gson.annotations.SerializedName;

public class Room {

    int id;
    String building;
    int floor;
    @SerializedName("room_name")
    private String name;


    public Room(int id, String building, int floor, String name) {
        this.id = id;
        this.building = building;
        this.floor = floor;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
