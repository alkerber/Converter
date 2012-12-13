package midiUtilities;

import java.util.Vector;

/**
 *
 * @author alkerber
 */
public class MidiVoice {
    //Criar um midiVoice a cada voice e measure

    int midiChannel;
    int midiProgram;
    Vector notesDuration = new Vector(4);

    public MidiVoice(int channel, int program) {
        this.midiChannel = channel;
        this.midiProgram = program;
    }

    public void addNoteOnVoice(MidiNote mn, int duration) {
        //criar um par ordenado
        notesDuration.addElement(mn);
    }

    public Vector returnMidiVoice() {
        return this.notesDuration;
    }

    public int returnVoiceMidiChannel() {
        return this.midiChannel;
    }

    public int returnVoiceMidiProgram() {
        return this.midiProgram;
    }
}

