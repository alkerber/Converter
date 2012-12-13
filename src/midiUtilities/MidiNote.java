package midiUtilities;

/**
 *
 * @author alkerber
 */
public class MidiNote {

    char step;
    int octave;
    int alter;
    int duration;
    MidiVoice midiVoice; /*important?*/

    public MidiNote(char step, int octave, int alter, int duration) {
        this.step = step;
        this.octave = octave;
        this.alter = alter;
        this.duration = duration;
    }

    public MidiNote() {
    }

    public byte returnNoteNumber() {
        byte note = 0x00;
        /*supondo que o arquivo xml é válido!
         *Não é verificado se os valores das notas são válidos.*/
        //http://tomscarff.110mb.com/midi_analyser/midi_note_numbers_for_octaves.htm
        switch (this.step) {
            case 'C':
                note = 0x0C;
                break;
            case 'D':
                note = 0x0E;
                break;
            case 'E':
                note = 0x10;
                break;
            case 'F':
                note = 0x11;
                break;
            case 'G':
                note = 0x13;
                break;
            case 'A':
                note = 0x15;
                break;
            case 'B':
                note = 0x17;
                break;

        }
        note = (byte) (note + (byte) (this.octave * 12 + alter));
        return note;
    }
}

