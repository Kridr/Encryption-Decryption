import java.util.Arrays;

class AsciiCharSequence implements CharSequence {
    byte[] sequence;

    public AsciiCharSequence(byte[] sequence) {
        this.sequence = sequence.clone();
    }

    @Override
    public int length() {
        return sequence.length;
    }

    @Override
    public char charAt(int index) {
        return (char) sequence[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        byte[] sub = Arrays.copyOfRange(sequence, start, end);

        return new AsciiCharSequence(sub);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sequence.length; i++) {
            stringBuilder.append(this.charAt(i));
        }

        return stringBuilder.toString();
    }

}