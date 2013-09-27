package general;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class TestMixer {

	
	public static void main(String[] args) {
		try {
		
		  //Recupération de la liste des Mixers à partir du système
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		
		  
		  //pour chaque mixer on va regarder ses caracteristiques
		  for(Mixer.Info a:mixerInfo){			  
			  
			  System.out.println("\n\n"+a+" Description:"+a.getDescription());	
			  
			  System.out.println("--------------------------------");
			  
			  Mixer mix=AudioSystem.getMixer(a);
			 
			  //Visualisation dex controles supportés par chaque Mixer
			  for(Control c:mix.getControls()){				  
				  System.out.println("Controls supported by Mixer:");
				  System.out.println("\t"+c);				  
			  }
			  
			  //Visualisation des SourceLine disponibles pour ce Mixer
			  for(Line.Info i :mix.getSourceLineInfo()){				  
				  System.out.println("\n"+i);
				  
				  //Visualisation dex controles supportés par cette Line
				  System.out.println("Controls supported by Source Line:");				  
				  for(Control c:AudioSystem.getLine(i).getControls()){
					  System.out.println("\t"+c);
				  }
			  }
			  //Visualisation des TargetLine disponibles pour ce Mixer
			  for(Line.Info i :mix.getTargetLineInfo()){				  
				  System.out.println("\n"+i);
				  
				  //Visualisation dex controles supportés par cette Line
				  System.out.println("Controls supported by Target Line:");
				  for(Control c:AudioSystem.getLine(i).getControls()){
					  System.out.println("\t"+c);
				  }
			  }			  
		  }
		 
		} catch (LineUnavailableException e) {			
			e.printStackTrace();
		} 
		
		 
	}

}
