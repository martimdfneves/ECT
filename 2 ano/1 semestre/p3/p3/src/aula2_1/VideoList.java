package aula2_1;

import java.util.*;

public class VideoList {

    private ArrayList<Video> lista;

    public VideoList() {

        lista = new ArrayList<Video>();
    }

    public void addVideo(Video v) {

        lista.add(v.getId() - 1, v);
    }

    public void delVideo(int in) {

        try {

            lista.set(in - 1, null);
        } catch (IndexOutOfBoundsException e) {

            System.out.printf("Nenhum v�deo para remover.\n");
        }
    }

    public void video(int id) {

        Video tmp;
        try {

            tmp = lista.get(id - 1);
            System.out.printf(tmp.toString());
        } catch (IndexOutOfBoundsException e) {

            System.out.print("V�deo n�o existente\n");
        }
    }

    public void videoDisponivel(int id) {

        Video tmp;
        try {

            tmp = lista.get(id - 1);
            if (tmp.isAvailable())
                System.out.printf("V�deo dispon�vel.\n");
            else
                System.out.printf("V�deo n�o dispon�vel.\n");
        } catch (IndexOutOfBoundsException e) {

            System.out.println("V�deo n�o existente.");
        }
    }

    public void videoDisplay(int age) {

        if (age >= 18) {

            System.out.println("V�deos dispon�veis: \n");
            displayCat("ALL");
            displayCat("M6");
            displayCat("M12");
            displayCat("M16");
            displayCat("M18");
        } else if (age >= 16 && age < 18) {

            System.out.println("V�deos dispon�veis: \n");
            displayCat("ALL");
            displayCat("M6");
            displayCat("M12");
            displayCat("M16");
        } else if (age >= 12 && age < 16) {

            System.out.println("V�deos dispon�veis: \n");
            displayCat("ALL");
            displayCat("M6");
            displayCat("M12");
        } else if (age >= 6 && age < 12) {

            System.out.println("V�deos dispon�veis: \n");
            displayCat("ALL");
            displayCat("M6");
        } else {

            System.out.println("V�deos dispon�veis: \n");
            displayCat("ALL");
        }
    }

    private void displayCat(String age) {

        System.out.printf("Categoria %s:\n", age);
        Video tmp;
        for (int i = 0; i < lista.size(); i++) {

            try {

                tmp = lista.get(i);
                if ((tmp.getIdade()).equals(age))
                    System.out.print(tmp.toString());
            } catch (NullPointerException e) {

                ;
            }
        }
        System.out.println();
    }

    public void replaceVid(Video novo) {

        lista.remove(novo.getId());
        lista.add(novo.getId(), novo);
    }

    public Video getVideoById(int id) {

        return lista.get(id - 1);
    }

    public int movieAmount() {

        return lista.size();
    }

    public void showRated() {

        Video[] tmp = new Video[lista.size()];
        tmp = lista.toArray(tmp);

        boolean swap = true;
        int i;
        Video temp;
        do {

            swap = false;
            for (i = 0; i < tmp.length - 1; i++) {

                if (tmp[i].compareTo(tmp[i + 1]) < 0) {

                    temp = tmp[i];
                    tmp[i] = tmp[i + 1];
                    tmp[i + 1] = temp;
                    swap = false;
                }
            }
        } while (swap);

        for (i = 0; i < tmp.length; i++) {

            System.out.println(tmp[i].toString());
        }
    }
}
