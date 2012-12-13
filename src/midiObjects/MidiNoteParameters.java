package midiObjects;

/**
 *
 * @author alkerber
 */
public class MidiNoteParameters {

    public boolean paramIsNoteOn;
    public int paramDelta;
    public int paramChannel;
    public byte paramNotenum;
    public byte paramVelocity;

    public MidiNoteParameters() {
    }

    public MidiNoteParameters(boolean isNoteOn, int delta, int channel, byte notenum,
            byte velocity) {
        this.paramIsNoteOn = isNoteOn;
        this.paramDelta = delta;
        this.paramChannel = channel;
        this.paramNotenum = notenum;
        this.paramVelocity = velocity;
    }
}
