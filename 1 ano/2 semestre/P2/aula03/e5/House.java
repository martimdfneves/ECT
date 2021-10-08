package e5;

import static java.lang.System.*;

public class House {

    private String tipo;
    private Room[] divisions;
    private int extras;
    private int salas = 0;

    public House(String type) {
        if (type.toLowerCase() != "house" && type.toLowerCase() != "apartment") {
            tipo = "indefinido";
        } else {
            tipo = type;
        }

        divisions = new Room[8];
        extras = 4;
    }

    public House(String type, int divs, int extraDivs) {
        if (type.toLowerCase() != "house" && type.toLowerCase() != "apartment") {
            tipo = "indefinido";
        } else {
            tipo = type;
        }

        divisions = new Room[divs];
        extras = extraDivs;
    }

    public void addRoom(Room sala) {

        if (salas == divisions.length) {
            maiSalas();
        }

//		System.out.println(divisions[salas].roomType());
        divisions[salas++] = sala;
//		System.out.println(divisions[salas].roomType());
//		System.out.println("Addroom salas n: " + salas);
    }

    public int size() {
        return salas;
    }

    public int maxSize() {
        return divisions.length;
    }

    public Room room(int indice) {
        if (indice < 0 || indice > size()) {
            return divisions[salas];
        } else {
            return divisions[indice];
        }
    }

    public double area() {

        double areaTotal = 0;

        for (int i = 0; i < salas; i++) {
            areaTotal += divisions[i].area();
        }

        return areaTotal;
    }

    public RoomTypeCount[] getRoomTypeCounts() {

        RoomTypeCount[] contagem = new RoomTypeCount[size()];
        int contas = 0;

        for (int i = 0; i < size(); i++) {
            int var4 = -1;

            for (int j = 0; j < contas; j++) {
                if (divisions[i].roomType().equals(contagem[j].roomType)) {
                    var4 = j;
                }
            }

            if (var4 == -1) {
                contagem[contas] = new RoomTypeCount();
                contagem[contas].roomType = divisions[i].roomType();
                contagem[contas].count = 1;
                contas++;
            } else {
                contagem[var4].count++;
            }
        }

        RoomTypeCount[] contagem2 = new RoomTypeCount[contas];
        System.arraycopy(contagem, 0, contagem2, 0, contas);
        return contagem2;
    }

    public void maiSalas() {

        Room[] divs = new Room[divisions.length + extras];
        arraycopy(divisions, 0, divs, 0, divisions.length);
        divisions = divs;
    }

    public double averageRoomDistance() {
        double media = 0;
        int valores = 0;

        for (int i = 0; i < size(); i++) {
            for (int j = i + 1; j < size(); ++j) {
                media += (divisions[i].geomCenter()).distTo(divisions[j].geomCenter());
                ++valores;
            }
        }

        return media / (double) valores;
    }
}
