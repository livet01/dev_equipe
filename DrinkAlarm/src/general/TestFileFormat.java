/* 
 * Created on 22 févr. 2006
 *
 * gNTIC - 2006 
 */

package general;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

public class TestFileFormat {
    
    public static void main(String[] args) {

        for(AudioFileFormat.Type t:AudioSystem.getAudioFileTypes()){            
            System.out.println(t);
        }
        
    }

}
