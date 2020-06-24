import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestLocuteur {

	private boolean enregistrement_wav_test() {
		boolean retour = false;
		// Trois minutes d'enregistrement
		String command = "arecord --duration=5 ./alize/data/audios/test/test";
		System.out.println("Bonjour, exprimez vous en repétant un mot. Vous avez 3 minutes");
		try
        { 
			Thread.sleep(3000);
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command );
			System.out.println("Enregistrement en cours.... ");
			Thread.sleep(6000);
			retour = true;
        }
		catch(Exception e)
        { 
            System.out.println(e);
            retour = false;
        }
		
		return retour;
	}
	
	private boolean parametrisation_energyDetection_normalisation_test () {
		
		boolean retour = false;
		String command_parametrisation = "sfbcep -F PCM16 -f 16000 -p 19 -e -D -A ./alize/data/audios/test/test ./alize/data/prm/test.prm";
		String command_normalisation_1 = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_energy_SPro.cfg --inputFeatureFilename ./alize/lst/test.lst --debug false --verbose true";
		String command_energyDetector = "./alize/bin/EnergyDetector --config ./alize/cfg/EnergyDetector_SPro.cfg --inputFeatureFilename ./alize/lst/test.lst --verbose true --debug false";
		String command_normalisation_2 = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_SPro.cfg --inputFeatureFilename ./alize/lst/test.lst --debug false --verbose true";
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_parametrisation );
			System.out.println("Parametrisation de test terminée");
			Thread.sleep(3000);
			
			runtime.getRuntime().exec( command_normalisation_1 );
			System.out.println("Première normalisation de test terminée");
			Thread.sleep(3000);
			
			runtime.getRuntime().exec( command_energyDetector );
			System.out.println("Détection d'énergie de test terminée");
			Thread.sleep(3000);
			
			runtime.getRuntime().exec( command_normalisation_2 );
			System.out.println("Deuxième normalisation de test terminée");
			Thread.sleep(3000);
			
			retour = true;
        }
		catch(Exception e)
        { 
            System.out.println(e);
            retour = false;
        }
		
		return retour;
	}
	
	private boolean compute_test() {
		boolean retour = false;
		
		String command_compute = "./alize/bin/ComputeTest --config ./alize/cfg/ComputeTest_GMM.cfg  --ndxFilename ./alize/ndx/computetest_gmm_target-seg.ndx --worldModelFilename world --outputFilename ./alize/res/result.res";
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_compute );
			System.out.println("Execution de  compute test terminée");
			Thread.sleep(3000);
			retour = true;
        }
		catch(Exception e)
        { 
            System.out.println(e);
            retour = false;
        }
		
		return retour;
	}
	
	
	
	   private int nombre_de_ligne()
	   {
	      // Le fichier d'entrée
	      File file = new File("./alize/res/result.res"); 
	      // Initialiser le compteur à zéro
	      int nbrLine = 0;            
	      // Créer l'objet File Reader
	      try {
			  FileReader fr = new FileReader(file);
			  // Créer l'objet BufferedReader 
			  BufferedReader br = new BufferedReader(fr);  
			  String str;
			   // Lire le contenu du fichier
			  while((str = br.readLine()) != null)
			  {
			     //Pour chaque ligne, incrémentez le nombre de lignes
			     nbrLine++;               
			        
			  }
			  fr.close();
	      }
	      catch(Exception e)
	      { 
	    	  System.out.println(e);
	      }
	      
	      return nbrLine; 
	   }
	
	/*
	 * A faire
	 * Analyse du fichier result.res pour determiner le nom de la personne qui a prononcé le mot
	 * Le contenu du fichier doit etre recuperé dans une matrice
	 * */
	private String decision () {
		String nom = "";
		return nom;
	}
	
	/*boolean tester () {
		boolean retour = false;
		if(enregistrement_wav_test ()) {
			if(parametrisation_energyDetection_normalisation_test()) {
				if(compute_test()) {
					retour = true;
				}
			}
		}
		return retour;
	}*/
	
	boolean tester () {
		nombre_de_ligne();
		return true;
	}
	
}
