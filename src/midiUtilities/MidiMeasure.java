package midiUtilities;

import java.util.Vector;

/**
 *
 * @author alkerber
 */
public class MidiMeasure {

    Vector<MidiVoice> measure = new Vector<MidiVoice>(5);

    public MidiMeasure() {

    }

    public void addVoiceonMeasure(MidiVoice mv) {
        this.measure.addElement(mv);
    }
}
