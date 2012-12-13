package midiUtilities;

/**
 *
 * @author alkerber
 * revisado em 21/09/10
 */
import java.util.Vector;
import generalUtilities.*;
import midiObjects.MidiNoteParameters;
import midiObjects.MidiProgramParameters;
import midiObjects.MidiTempoParameters;

public class MidiTrack {
    //channel e program agora estão em midivoice

    String trackId;
    public int trackDivisions = 1;
    public Vector sequenceOfMidiEvents = new Vector();
    public Vector sequenceOfMidiObjects = new Vector();

    public MidiTrack(String id) {
        this.trackId = id;
    }

    public void ConvertMidiObjectsToEvent(int generalDivisions) {
        /*feito em 28/10/10*/
        /*não testado*/
        //System.out.println("CONVERT OBJECTS");
        int i;
        ByteConverter bc = new ByteConverter();
        MidiChannelEvent mce = new MidiChannelEvent();
        MidiMetaMessage mmm = new MidiMetaMessage();
        MidiNoteParameters mnp = new MidiNoteParameters();
        MidiProgramParameters mpp = new MidiProgramParameters();
        MidiTempoParameters mtp = new MidiTempoParameters();
        TempoConverter tc = new TempoConverter();
        Class midiNoteClass = mnp.getClass();
        Class midiProgramClass = mpp.getClass();
        Class midiTempoClass = mtp.getClass();
        //System.out.println("CONVERT OBJECTS size: " + this.sequenceOfMidiObjects.size() + "\n");
        for (i = 0; i < this.sequenceOfMidiObjects.size(); i++) {
            if (sequenceOfMidiObjects.elementAt(i).getClass() == midiNoteClass) {
               //System.out.println("\nNOTE\n");
                mnp = (MidiNoteParameters) sequenceOfMidiObjects.elementAt(i);

                if (mnp.paramIsNoteOn) {
                    //System.out.println("\nNOTEON\n");
                    this.AddMidiEventOnTrack(mce.noteOn(bc.intoVariableLength((generalDivisions/trackDivisions)*mnp.paramDelta), mnp.paramChannel, mnp.paramNotenum, mnp.paramVelocity));
                    //System.out.println("\nADICIONOU\n");
                } else {
                    //System.out.println("\nNOTE OFF\n");
                    this.AddMidiEventOnTrack(mce.noteOff((bc.intoVariableLength((generalDivisions/trackDivisions)*mnp.paramDelta)), mnp.paramChannel, mnp.paramNotenum, mnp.paramVelocity));
                }
            } else if (sequenceOfMidiObjects.elementAt(i).getClass() == midiProgramClass) {
                //System.out.println("\nPROGRAM\n");
                mpp = (MidiProgramParameters) sequenceOfMidiObjects.elementAt(i);
                this.AddMidiEventOnTrack(mce.programChange(mpp.paramChannel, mpp.paramProgram));
            } else {
                //System.out.println("\nTEMPO\n");
                mtp = (MidiTempoParameters) sequenceOfMidiObjects.elementAt(i);
                this.AddMidiEventOnTrack(mmm.setTempo(mtp.paramTempo));
            }
        }

    }

    public void AddMidiEventOnTrack(byte[] midiEvent) {
        /*testar*/
        /*revisado em 13/10/10*/
        int i = 0;
        for (i = 0; i < midiEvent.length; i++) {
            Byte data = new Byte(midiEvent[i]);
            this.sequenceOfMidiEvents.addElement(data);
            //System.out.println("addElem: " + i);
        }
    }

    public void AddMidiObjectOnTrack(Object midiEvent) {
        /*testar*/
        this.sequenceOfMidiObjects.addElement(midiEvent);

    }

    public byte[] returnTrackChunck() {
        /*revisado em 21/09/10*/
        byte[] trackChunck = new byte[8];
        byte[] trackEnd = new byte[4];
        /*every object of the Vector is a Byte*/
        /*+8?*/
        /*TODO: SET A CORRECT TRACKSIZE*/
        int trackSize = this.sequenceOfMidiEvents.size() + 4;
        ByteConverter bc = new ByteConverter();
        trackEnd = bc.intToFourBytes(trackSize);
        trackChunck[0] = 0x4D;
        trackChunck[1] = 0x54;
        trackChunck[2] = 0x72;
        trackChunck[3] = 0x6B;
        trackChunck[4] = trackEnd[0];
        trackChunck[5] = trackEnd[1];
        trackChunck[6] = trackEnd[2];
        trackChunck[7] = trackEnd[3];
        return trackChunck;
    }

    public byte[] returnMidiTrack() {
        this.endMidiTrack();
        int i;
        int size = this.sequenceOfMidiEvents.size();
        byte[] completeMidiTrack;
        /*lembrar de verificar se posso usar 'size'...se todos os elementos são bytes*/
        completeMidiTrack = new byte[size];
        for (i = 0; i < size; i++) {
            completeMidiTrack[i] = ((Byte) (this.sequenceOfMidiEvents.elementAt(i))).byteValue();
        }
        return completeMidiTrack;
    }

    public void endMidiTrack() {
        MidiMetaMessage mmm = new MidiMetaMessage();
        this.AddMidiEventOnTrack(mmm.endOfTrack());
    }

    public String returnTrackId() {
        return this.trackId;
    }
}
