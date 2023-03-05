import org.graalvm.polyglot.*;
class Polyglot {


    private static int SumCRC(String token) {
        //construim un context care ne permite sa folosim elemente din PYTHON
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei functiei PYTHON, sum()
        //avem voie sa inlocuim anumite elemente din scriptul pe care il construim spre evaluare, aici token provine din JAVA, dar va fi interpretat de PYTHON
        Value result = polyglot.eval("python", "sum(ord(ch) for ch in '" + token + "')");
        //utilizam metoda asInt() din variabila incarcata cu output-ul executiei, pentru a mapa valoarea generica la un Int
        int resultInt = result.asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultInt;
    }

    public static void main(String[] args) {

        Context polyglot = Context.create();
        Value v = polyglot.eval("js", "[\"IF\",\"JE\",\"This\",\"Run\",\"This\"];");
        long nr= v.getArraySize(); //castare in long
        int n = (int) nr;
        boolean[] v2 = new boolean[n];
        for (int i = 0; i < n; i++) {
            v2[i] = false;
        } //sau Arrays.fill

        int[] v3 = new int[n];

        for (int i = 0; i < v.getArraySize(); i++) {
            String element = v.getArrayElement(i).asString();
            int crc = SumCRC(element);
            v3[i] = crc;
            System.out.println(element + " -> " + crc);
        }

//        for (int i = 0; i < n; i++) {
//            System.out.println(array3[i]);
//        }

        for (int i = 0; i < n; i++) {
            if (v2[i] == false) {

                System.out.println("Suma control " + v3[i] + ":");

                for (int j = i; j < n; j++) {
                    if (v3[j] == v3[i]) {
                        System.out.printf("%s ", v.getArrayElement(j).asString());
                        v2[j] = true;
                    }
                }
                System.out.println();
            }
        }
        polyglot.close();
    }
}
