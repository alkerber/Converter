package midiUtilities;

import generalUtilities.TempoConverter;

/**
 *
 * @author alkerber
 */
public class MidiMetaMessage {

    public MidiMetaMessage() {
    }

    public byte[] setTempo(int tempo) {
        //FF 0x51 03 tttttt - Set tempo tttttt- microseconds/quarter note
        byte[] messageSetTempo = new byte[7];
        byte[] midiTempo;
        TempoConverter tc = new TempoConverter();
        midiTempo = tc.Convert(tempo);
        messageSetTempo[0] = 0x00;
        messageSetTempo[1] = (byte) 0xFF;
        messageSetTempo[2] = 0x51;
        messageSetTempo[3] = 0x03;
        messageSetTempo[4] = midiTempo[0];
        messageSetTempo[5] = midiTempo[1];
        messageSetTempo[6] = midiTempo[2];
        System.out.println("Set tempo");
        return messageSetTempo;

    }

    public byte[] endOfTrack() {
        //FF 0x2F 00 -This event must come at the end of each track
        byte[] b = {0x00, (byte) 0xFF, (byte) (0x2F), 0x00};
        return b;
    }
}
