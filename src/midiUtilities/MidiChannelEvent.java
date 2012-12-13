package midiUtilities;

import generalUtilities.ByteConverter;

/**
 *
 * @author alkerber
 *
 */
public class MidiChannelEvent {
    

    public MidiChannelEvent() {
        //constructor
    }

    public byte[] noteOn(byte[] delta, int channel, byte notenum, byte velocity) {
        int finalSize = delta.length + 3;
        byte[] b = new byte[finalSize];
        int i = 0;
        //short midiCod = 0x90;
        byte midiCod = (byte)0x90;
        short midiCodChannel;
        ByteConverter bc = new ByteConverter();
        midiCodChannel = bc.twoBytesIntoOneByte(midiCod, (byte) channel);      
        for(i=0; i<delta.length; i++){
           b[i] = delta[i];
        }
        i = delta.length;
        b[i] = (byte) (midiCodChannel);
        b[i + 1] = notenum;
        b[i + 2] = velocity;
        return b;
    }

    public byte[] noteOff(byte[] delta, int channel, byte notenum, byte velocity) {
        int finalSize = delta.length + 3;
        int i = 0;
        short midiCod = 0x80;
        short midiCodChannel;
        ByteConverter bc = new ByteConverter();
        midiCodChannel = bc.twoBytesIntoOneByte((byte) (midiCod), (byte) channel);
        byte[] b = new byte[finalSize];
        while(i < delta.length){
            b[i] = delta[i];
            i++;
        }
        b[i] = (byte) (midiCodChannel);
        b[i + 1] = notenum;
        b[i + 2] = 0x00;
        return b;
    }

    public byte[] programChange(int channel, int program) {
        //test
        short midiCod = 0xC0;
        short midiCodChannel;
        ByteConverter bc = new ByteConverter();
        midiCodChannel = bc.twoBytesIntoOneByte((byte) (midiCod), (byte) channel);
        byte[] b = {0x00, (byte)midiCodChannel,(byte)program};
        return b;
    }
}
