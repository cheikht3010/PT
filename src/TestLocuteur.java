
public class TestLocuteur {

	private boolean enregistrement_wav_test() {
		boolean retour = false;
		// Trois minutes d'enregistrement
		String command = "arecord --duration=5 ./alize/data/audios/test/test";
		System.out.println("Bonjour, exprimez vous en repétant un mot. Vous avez 3 minutes");
		try
        { 
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
	
	private boolean parametrisation_test () {
		
		boolean retour = false;
		String command_parametrisation = "sfbcep -F PCM16 -f 16000 -p 19 -e -D -A ./alize/data/audios/test/test ./alize/data/prm/test.prm";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_parametrisation );
			System.out.println("Parametrisation de test terminée");
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
	
	/*
	 * A faire
	 * Analyse du fichier result.res pour determiner le nom de la personne qui a prononcé le mot
	 * Le contenu du fichier doit etre recuperé dans une matrice
	 * */
	private String decision () {
		String nom = "";
		return nom;
	}
	
}
