package aula4_1;

import aula3_1.*;
import aula1_2.*;

public class Teste {

    public static void main(String[] args) {
        Estudante est1 = new Estudante(9855678, new Data(18, 7, 1974), "Andreia");
        Estudante est2 = new Estudante(75266454, new Data(11, 8, 1978), "Monica");
        Estudante est3 = new Estudante(85265426, new Data(15, 2, 1976), "Jose");
        Estudante est4 = new Estudante(85153442, new Data(1, 3, 1973), "Manuel");
        Bolseiro bls1 = new Bolseiro(8976543, new Data(12, 5, 1976), "Maria");
        Bolseiro bls2 = new Bolseiro(872356522, new Data(21, 4, 1975), "Xico");
        Bolseiro bls3 = new Bolseiro(32423512, new Data(6, 1, 1976), "Duarte");
        bls1.setBolsa(745);
        bls2.setBolsa(845);
        bls3.setBolsa(745);
        Professor pf1 = new Professor(11223344, new Data(11, 9, 1969), "Jose Manuel");
        Disciplina dis = new Disciplina("P5", "Informatica", 6, pf1);
        dis.addAluno(est1);
        dis.addAluno(est2);
        dis.addAluno(bls1);
        if (dis.alunoInscrito(est3.getNmec()))
            System.out.println("\n" + est3
                    + " \n\t-> EST� inscrito na Disciplina");
        else
            System.out.println("\n" + est3
                    + " \n\t-> N�O EST� inscrito na Disciplina");
        System.out.println("\nN� de Alunos Inscritos: " + dis.numAlunos());
        dis.addAluno(est3);
        dis.addAluno(bls2);
        dis.addAluno(est4);
        dis.addAluno(bls3);
        if (!dis.addAluno(bls3))
            System.out.println(bls3.getNmec() + ", " + bls3.getName()
                    + " j� est� inscrito nesta disciplina!");
        if (dis.delAluno(bls2.getNmec()))
            System.out.println(bls2.getNmec() + " Removido!");
        System.out.println("\nN� de Alunos Inscritos: " + dis.numAlunos());
        System.out.println(dis + "\n");
        System.out.println("\n Listagem de Estudantes:");
        for (Estudante e : dis.getAlunos())
            System.out.println(e);
        System.out.println("\n Listagem de Bolseiros:");
        for (Estudante e : dis.getAlunos("Bolseiro"))
            System.out.println(e);
    }
}
