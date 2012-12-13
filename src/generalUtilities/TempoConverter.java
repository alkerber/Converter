package generalUtilities;

/**
 *
 * @author alkerber
 */
public class TempoConverter {

    public TempoConverter(){

    }

    public byte[] Convert(int mxmlTempo){
        System.out.println("\nTEMPO CONVERT\n");
        int midiTempo;
        byte[] b_midiTempo = new byte[3];
        //float f_midiTempo;
        ByteConverter bc = new ByteConverter();
        /*temporário até conseguir arredondar a função*/
        midiTempo = mxmlTempo;
        //midiTempo = (60000 / mxmlTempo * 1000);
        midiTempo = ((int)((60000.0 / mxmlTempo * 1000) + 0.5));
        b_midiTempo = bc.intToThreeBytes(midiTempo);
        System.out.println("\nFLOOR INT\n");
        return b_midiTempo;
    }

}
