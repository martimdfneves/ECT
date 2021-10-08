package mpei;

import java.io.IOException;

public class Armazem {
    private Count_Bloom_Filter stock;
    private Minhash x;

    public Armazem(int stocksize, int counthash, int minhash) {

        stock = new Count_Bloom_Filter(stocksize, counthash);
        x = new Minhash(minhash);
    }

    public void addPc(String refPc) {
        stock.insert(refPc);
    }

    public void removePc(String refPc) {
        stock.delete(refPc);
    }

    public void checkStock(String refPc) {
        if (stock.isMember(refPc)) {
            System.out.print("Quantidade em Stock:");
            System.out.print(stock.count(refPc));
            System.out.print("\n");
            System.out.print("\n");
        } else {
            System.out.println("N�o temos em stock");
        }
    }

    public void compare(String name1, String name2) throws IOException {
        System.out.print(x.compare(name1, name2));

    }

    public int numElem(String refPc) {
        return stock.count(refPc);
    }
}