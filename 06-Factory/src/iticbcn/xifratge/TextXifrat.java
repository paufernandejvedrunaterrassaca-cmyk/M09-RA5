
package iticbcn.xifratge;

import java.util.Arrays;

public class TextXifrat {
    private final byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = Arrays.copyOf(bytes, bytes.length);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
public String toString() {
    return java.util.Base64.getEncoder()
            .encodeToString(bytes);
}
}
