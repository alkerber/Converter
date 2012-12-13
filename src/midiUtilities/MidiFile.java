package midiUtilities;

/**
 *
 * @author alkerber
 */

/*important byte conversions*/
import generalUtilities.*;

//java packages
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//own packages
import midiUtilities.MidiTrack;

public class MidiFile {

    String path;
    String newFileName; /*new name with a .musa.mid extension*/

    byte[] deltaTimeTicks; /*lcm of MXML divisions*/

    int numTracks = 0;
    int fileFormat = 0;
    
    


    public MidiFile(String path) {
        this.path = path;
        this.newFileName = this.path.substring(0, path.length() - 3) + "musa.mid"; // new name for the converted file
		System.out.println(this.newFileName);

	    

    }

    public void addNumTracks() {
    	//System.out.println("Track added"); //debug
        this.numTracks++;
    }

    public byte[] returnByteNumTracks() {
        ByteConverter bc = new ByteConverter();
        byte[] byteTrack = new byte[2];
        byteTrack = bc.intToTwoBytes(this.numTracks);
        return byteTrack;
    }

    public void setFileFormat() {
        /*without format 2*/
        if (this.numTracks > 1) {
            this.fileFormat = 1;
        } //else 0
    }

    public byte[] returnFileFormat(int ff) {
        /*without format 2*/
        byte[] toByteFF = new byte[2];
        toByteFF[0] = 0x00;
        switch (ff) {
            case 0:
                toByteFF[1] = 0x00;
                break;
            case 1:
                toByteFF[1] = 0x01;
                break;
        }
        return toByteFF;
    }

    public void setDeltaTimeTicks(int divisions) {
        //status: test
        byte[] dtt = new byte[2];
        ByteConverter bc = new ByteConverter();
        dtt = bc.intToTwoBytes(divisions);
        this.deltaTimeTicks = dtt;

    }

    public byte[] returnHeaderChunck() {
        //Default MIDI header: 4D 54 68 64 00 00 00 06 ff ff nn nn dd dd
        int i;
        byte[] headerChunck = new byte[14];
        byte[] binaryFileFormat = this.returnFileFormat(this.fileFormat);
        byte[] binaryNumTracks = this.returnByteNumTracks();
        byte[] binaryDeltaTimeTicks = this.deltaTimeTicks;
        byte[] ConstHeaderChunck = {0x4D, 0x54, 0x68, 0x64, 0x00, 0x00, 0x00, 0x06};
        for (i = 0; i < 8; i++) {
            headerChunck[i] = ConstHeaderChunck[i];
        }
        headerChunck[8] = binaryFileFormat[0];
        headerChunck[9] = binaryFileFormat[1];
        headerChunck[10] = binaryNumTracks[0];
        headerChunck[11] = binaryNumTracks[1];
        headerChunck[12] = binaryDeltaTimeTicks[0];
        headerChunck[13] = binaryDeltaTimeTicks[1];
       // System.out.println("HEADER CHUNCK: " + headerChunck);
        return headerChunck;
    }

    public boolean writeHeaderChunck() {
        return writeFile(returnHeaderChunck());
    }

    public boolean writeFile(byte[] data) {
        //System.out.println("\nWRITEFILE!!!\n");
		File myFile;
		myFile = new File(newFileName);
        FileOutputStream fOut;

      		try {
    			fOut = new FileOutputStream(myFile,true); // (File file,bool truncate)
      			fOut.write(data, 0, data.length); 
     			fOut.close();
      			return true;
      		}catch (Exception e) {
      			return false;
      		}
      		
    }

    public boolean writeTrackOnFile(MidiTrack mt) {
        return (writeFile(mt.returnTrackChunck()) && writeFile(mt.returnMidiTrack()));
    }

    public boolean createMidiFile() {
        this.setFileFormat();
        boolean result = writeHeaderChunck();
        System.out.println("ESCREVEU O HEADER: " + result);
        return result;
    }

    /*only for tests -> to be removed*/
    public void printParameters() {
        System.out.println("Delta time ticks: " + this.deltaTimeTicks + "\n");
        System.out.println("File Format: " + this.fileFormat + "\n");
        System.out.println("Num tracks: " + this.numTracks + "\n");
        //System.out.println("Delta time ticks: " + this.deltaTimeTicks + "\n");
    }
}

