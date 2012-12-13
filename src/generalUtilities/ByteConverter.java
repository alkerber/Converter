package generalUtilities;

public class ByteConverter {

    public ByteConverter() {
    }

    public byte[] intToFourBytes(int num) {
        //big endian
        byte[] data = new byte[4];
        data[3] = (byte) (num & 0xFF);
        data[2] = (byte) ((num >> 8) & 0xFF);
        data[1] = (byte) ((num >> 16) & 0xFF);
        data[0] = (byte) ((num >> 24) & 0xFF);
        return data;
    }
    /* http://www.guj.com.br/posts/list/66053.java*/

    public byte[] intToTwoBytes(int num) {
        //big endian - testar
        byte[] data = new byte[2];
        data[1] = (byte) (num & 0xFF);
        data[0] = (byte) ((num >> 8) & 0xFF);
        return data;
    }

    public byte[] intToThreeBytes(int num) {
        //big endian - testar
        byte[] data = new byte[3];
        data[2] = (byte) (num & 0xFF);
        data[1] = (byte) ((num >> 8) & 0xFF);
        data[0] = (byte) ((num >> 16) & 0xFF);
        return data;
    }

    public byte[] intoVariableLength(int value) {
        //testar
        int i = 1;
        if (value < 0) {
            System.out.println("Valor midi nÃ£o suportado");
        } else /*caso explicito parece mais eficiente*/ if (value < 128) {
            i = 1;
        } else if (value < 16384) {
            i = 2;
        } else if (value < 2097152) {
            i = 3;
        } else if (value < 268435456) {
            i = 4;
        } else {
            System.out.println("Valor midi nÃ£o suportado");
        }

        byte[] varLen = new byte[i];
        i--;
        varLen[i] = (byte) (value & 127);
        value = value >> 7;

        while (i > 0) {
            i--;
            varLen[i] = (byte) (value & 127);
            varLen[i] += 128;
            value = value >> 7;
        }
        return varLen;
    }

    public void printVarLen(int value) {
        int i;
        byte[] varLen = intoVariableLength(value);
        System.out.print("\nvalor inicial: " + value + "  " + Integer.toHexString(value) + "  : ");
        for (i = 0; i < varLen.length; i++) {
            System.out.print(Integer.toHexString((varLen[i] + 256) % 256) + " ");
        }
        System.out.print("\n");
    }

    public byte twoBytesIntoOneByte(byte ms, byte ls) {
        /*modificado em 13/10/10*/
        //testar
        //set the first nibble with ms value and the last nibble with ls
        //important to put event type and midi channel at the same byte.
        int tempAns;
        short sms = (short) ms;
        short sls = (short) ls;
        byte ans;
        tempAns = (sms) | (sls);
        ans = (byte) (tempAns);
        return ans;
    }
}

