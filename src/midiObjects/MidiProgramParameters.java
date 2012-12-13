package midiObjects;

/**
 *
 * @author alkerber
 */
public class MidiProgramParameters {

    public int paramChannel;
    public int paramProgram;

    public MidiProgramParameters() {
    }

    public MidiProgramParameters(int channel, int program) {
        this.paramChannel = channel;
        this.paramProgram = program;
    }
}