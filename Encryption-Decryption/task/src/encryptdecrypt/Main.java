package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

interface Encrypt {
    char encryptSingle(char init, int key);
}

interface Decrypt {
    char decryptSingle(char init, int key);

}

interface Crypt extends Encrypt, Decrypt {
    default String cryptWithKey(String message, int key, boolean encrypt) {
        StringBuilder cipheredMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char symbol = message.charAt(i);
            if (encrypt) {
                cipheredMessage.append(encryptSingle(symbol, key));
            } else {
                cipheredMessage.append(decryptSingle(symbol, key));
            }
        }

        return cipheredMessage.toString();
    }
}

class UnicodeAlg implements Crypt {
    @Override
    public char encryptSingle(char init, int key) {
        return (char) (init + key);
    }

    @Override
    public char decryptSingle(char init, int key) {
        return (char) (init - key);
    }

}

class ShiftAlg implements Crypt {
    @Override
    public char encryptSingle(char init, int key) {
        int realKey = key % 26;

        if (init >= 'a' && init <= 'z') {
            if (init + realKey > 'z') {
                int dif = init + realKey - 'z' - 1;
                return (char) (dif + 'a');
            } else {
                return (char) (init + realKey);
            }
        }

        if (init >= 'A' && init <= 'Z') {
            if (init + realKey > 'Z') {
                int dif = init + realKey - 'z' - 1;
                return (char) (dif + 'a');
            } else {
                return (char) (init + realKey);
            }
        }

        return init;
    }

    @Override
    public char decryptSingle(char init, int key) {
        int realKey = key % 26;

        if (init >= 'a' && init <= 'z') {
            if (init - realKey < 'a') {
                int dif = 'a' - (init - realKey) - 1;
                return (char) ('z' - dif);
            } else {
                return (char) (init - realKey);
            }
        }

        if (init >= 'A' && init <= 'Z') {
            if (init - realKey < 'A') {
                int dif = 'A' - (init - realKey) - 1;
                return (char) ('Z' - dif);
            } else {
                return (char) (init - realKey);
            }
        }

        return init;
    }
}

class Controller {
    Crypt crypt;

    public void setMethod(Crypt crypt) {
        this.crypt = crypt;
    }

    public String cryptWithKey(String message, int key, boolean encrypt) {
        return this.crypt.cryptWithKey(message, key, encrypt);
    }

}

class Executor {
    public static int search(String[] mas, String val) {
        int answer = -1;

        for (int i = 0; i < mas.length && answer == -1; i++) {
            if (mas[i].equals(val)) {
                answer = i;
            }
        }

        return answer;
    }

    public static void cryptProc(String[] args) {
        String choice = findChoice(args);

        int key = findKey(args);

        String data = findData(args);

        String alg = findAlg(args);

        String crypt = findCrypt(choice, key, data, alg);

        out(args, crypt);
    }

    private static String findChoice(String[] args) {
        int ch = search(args, "-mode");
        return ch >= 0 ? args[ch + 1] : "enc";
    }

    private static int findKey(String[] args) {
        int k = search(args, "-key");
        return k >= 0 ? Integer.parseInt(args[k + 1]) : 0;
    }

    private static String findData(String[] args) {
        //finding data (from file or from command line)
        //firstly make data equal "" in order to cover the case
        //when there is no -data and -in
        String data = "";
        int d = search(args, "-data");
        if (d >= 0) {
            data = args[d + 1];
        } else {
            int in = search(args, "-in");

            if (in >= 0) {
                try (Scanner scanner = new Scanner(new File(args[in + 1]))) {
                    data = scanner.nextLine();
                } catch (FileNotFoundException e) {
                    System.out.println("Error");
                }
            }
        }

        return data;
    }

    private static String findAlg(String[] args) {
        int a = search(args, "-alg");
        return a >= 0 ? args[a + 1] : "shift";
    }

    private static String findCrypt(String choice, int key, String data, String alg) {
        Controller controller = new Controller();

        switch (alg) {
            case "shift":
                controller.setMethod(new ShiftAlg());
                break;
            case "unicode":
                controller.setMethod(new UnicodeAlg());
                break;
        }

        String answer = "";
        switch (choice) {
            case "enc":
                answer = controller.cryptWithKey(data, key, true);
                break;
            case "dec":
                answer = controller.cryptWithKey(data, key, false);
                break;
            default:
                System.out.println("error");
        }

        return answer;
    }

    private static void out(String[] args, String answer) {
        int out = search(args, "-out");

        if (out >= 0) {
            try (PrintWriter printWriter = new PrintWriter(args[out + 1])) {
                printWriter.println(answer);
            } catch (IOException e) {
                System.out.println("Error");
            }
        } else {
            System.out.println(answer);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Executor.cryptProc(args);
    }
}
