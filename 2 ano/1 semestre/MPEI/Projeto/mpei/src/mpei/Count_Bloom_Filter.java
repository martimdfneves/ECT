package mpei;

public class Count_Bloom_Filter {

    private int[] array;
    private int numHash;

    public Count_Bloom_Filter(int size, int numHash) {
        this.numHash = numHash;
        array = new int[size];
    }


    public void insert(String str) {

        for (int i = 0; i < numHash; i++) {

            String tmp = Integer.toString(i);
            str = str + tmp;

            int index = Math.abs(str.hashCode());

            index = index % array.length;   // para evitar que o index seja maior que o tamanho do array
            array[index] = array[index] + 1;

        }
    }

    public boolean isMember(String str) {

        boolean tmp = true;


        for (int i = 0; i < numHash; i++) {
            String tmp2 = Integer.toString(i);
            str = str + tmp2;
            int index = Math.abs(str.hashCode());

            index = index % array.length;

            if (array[index] == 0) {
                tmp = false;

            }


        }
        return tmp;
    }

    public void delete(String str) {


        for (int i = 0; i < numHash; i++) {

            String tmp = Integer.toString(i);
            str = str + tmp;

            int index = Math.abs(str.hashCode());

            index = index % array.length;   // para evitar que o index seja maior que o tamanho do array
            array[index] = array[index] - 1;

        }


    }

    public int count(String str) {
        int old = Integer.MAX_VALUE;

        for (int i = 0; i < numHash; i++) {

            String tmp = Integer.toString(i);
            str = str + tmp;

            int index = Math.abs(str.hashCode());
            index = index % array.length;

            if (array[index] < old) {

                old = array[index];
            }
        }

        return old;
    }
}