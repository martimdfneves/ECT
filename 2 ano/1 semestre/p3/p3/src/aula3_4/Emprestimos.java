package aula2_1;

import java.util.*;
import java.time.Year;

public class Emprestimos {

    private Hashtable<String, Emprestimo> emprestimos;

    public Emprestimos() {

        emprestimos = new Hashtable<String, Emprestimo>();
    }

    public boolean newLoanAluno(Data d, Aluno a, Video v) {

        int idade = (Year.now().getValue()) - (a.getBirthdate()).getYear();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1 - (a.getBirthdate()).getMonth();
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - (a.getBirthdate()).getDay();
        if (month < 0 || dia < 0) {

            idade--;
        }

        if (!v.isAvailable() ||
                (v.getIdade().equals("M18") && idade < 18) ||
                (v.getIdade().equals("M16") && idade < 16) ||
                (v.getIdade().equals("M12") && idade < 12) ||
                (v.getIdade().equals("M6") && idade < 6)) {

            System.out.println("N�o pode retirar o filme");
            return false;
        } else {

            emprestimos.put(a.getNsocio() + "-" + v.getId(), new Emprestimo(d, a.getNsocio(), v));
            return v.loan();
        }
    }

    public boolean newLoanFunc(Data d, Funcionario f, Video v) {

        int idade = (Year.now().getValue()) - (f.getBirthdate()).getYear();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1 - (f.getBirthdate()).getMonth();
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - (f.getBirthdate()).getDay();
        if (month < 0 || day < 0) {

            idade--;
        }

        if (!v.isAvailable() ||
                (v.getIdade().equals("M18") && idade < 18) ||
                (v.getIdade().equals("M16") && idade < 16) ||
                (v.getIdade().equals("M12") && idade < 12) ||
                (v.getIdade().equals("M6") && idade < 6)) {

            System.out.println("N�o pode retirar o filme");
            return false;
        } else {

            emprestimos.put(f.getNsocio() + "-" + v.getId(), new Emprestimo(d, f.getNsocio(), v));
            return v.loan();
        }
    }

    public void returnVideoAluno(Data in, Aluno a, Video v, int r) {

        Emprestimo tmp;
        try {

            tmp = emprestimos.get(a.getNsocio() + "-" + v.getId());
            tmp.devolucao(in);
            v.updaterating(r);
            emprestimos.replace(a.getNsocio() + "-" + v.getId(), tmp);
        } catch (NullPointerException e) {

            System.out.printf("N�o existe nenhum empr�stimo do v�deo ao s�cio.\n");
        }
    }

    public void returnVideoFunc(Data in, Funcionario f, Video v, int r) {

        Emprestimo tmp;
        try {

            tmp = emprestimos.get(f.getNsocio() + "-" + v.getId());
            tmp.devolucao(in);
            v.updaterating(r);
            emprestimos.replace(f.getNsocio() + "-" + v.getId(), tmp);
        } catch (NullPointerException e) {

            System.out.printf("N�o existe nenhum empr�stimo do v�deo ao s�cio\n");

        }
    }

    public int[] getVideoHistory(int socios, int videoID, ArrayList<Integer> saltos) {

        int users[] = new int[socios + 1 + saltos.size()];
        int tmp = 0;
        for (int i = 1; i < users.length; i++) {

            if (emprestimos.containsKey(i + "-" + videoID)) {

                users[tmp++] = i;
            }
        }
        int onlyUsed[] = new int[tmp];
        System.arraycopy(users, 0, onlyUsed, 0, tmp);
        return onlyUsed;
    }

    public void remEmprestimosOfSoc(int socio, int videos) {

        for (int i = 1; i <= videos; i++) {

            if (emprestimos.containsKey(socio + "-" + i)) {

                emprestimos.remove(socio + "-" + i);
            }
        }
    }

    public void remEmprestimosOfVid(int video, int socios) {

        for (int i = 1; i <= socios; i++) {

            if (emprestimos.containsKey(i + "-" + video)) {

                emprestimos.remove(i + "-" + video);
            }
        }
    }

    public int[] getSocHistory(int soc, int videos) {

        int all[] = new int[videos + 1];
        int tmp = 0;
        for (int i = 1; i < all.length; i++) {

            if (emprestimos.containsKey(soc + "-" + i)) {

                all[tmp++] = i;
            }
        }
        int onlyUsed[] = new int[tmp];
        System.arraycopy(all, 0, onlyUsed, 0, tmp);
        return onlyUsed;
    }
}
