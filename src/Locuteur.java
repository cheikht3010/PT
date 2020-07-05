import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Locuteur {
	
	String prenom;
	String nom;
	String nomFichier;
	int nbFichier;
	static int nextId = 1; // pseudo-variable globale
	
	Locuteur(String prenom, String nom) {
		this.nom = nom;
        this.prenom = prenom;
        this.nomFichier = prenom + "_" + nom;
        this.nbFichier = 0;
        nextId++;
	}
	
	private boolean enregistrement_wav() {
		boolean retour = false;
		// Trois minutes d'enregistrement
		String command = "arecord --duration=5 ./alize/data/audios/model/";
		command += (this.nomFichier + "_" + (nbFichier + 1)) ;
		System.out.println("Bonjour, " + prenom + " " + nom + ", prononcez un mot. Vous avez 3 minutes");
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
	
	private void fichierAll(String nomFichier) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/lst/all.lst", true)));
		writer.println(nomFichier);
		writer.close();
	}
	

	private void fichierUBM (String nomFichier) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/lst/UBM.lst", true)));
		writer.println(nomFichier);
		writer.close();
	}
	
	private void fichierTrainModel (String idLocuteur, String nomFichier) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/ndx/trainModel.ndx", true)));
		writer.print(idLocuteur);
		writer.print('\t');
		writer.println(nomFichier);
		writer.close();
	}
	
	private void fichier_computetest_gmm_target_seg () {
		try
        { 
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/ndx/computetest_gmm_target-seg.ndx", true)));
			writer.print(" " + this.nomFichier);
			writer.close();
        }
		catch(Exception e)
        { 
            System.out.println(e);
        }
	}
	
	private boolean parametrisation () {
		
		boolean retour = false;
		String command_parametrisation = "sfbcep -F PCM16 -f 16000 -p 19 -e -D -A ./alize/data/audios/model/"+ this.nomFichier + "_" + (nbFichier + 1)  + " ./alize/data/prm/" + this.nomFichier + "_" + (nbFichier + 1) + ".prm";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_parametrisation );
			System.out.println("Parametrisation terminée");
			fichierAll(this.nomFichier + "_" + (nbFichier+1));
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
	
	
	
	private boolean normalisation_1 () {
		boolean retour = false;
		
		String command_normalisation = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_energy_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --debug false --verbose true";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_normalisation );
			System.out.println("Première normalisation terminée");
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
	
	private boolean normalisation_2 () {
		boolean retour = false;
		
		String command_normalisation = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --debug false --verbose true";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_normalisation );
			System.out.println("Deuxième normalisation terminée");
			fichierUBM(this.nomFichier + "_" + (nbFichier+1));
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
	
	private boolean detection_energie () {
		boolean retour = false;
		
		String command_energyDetector = "./alize/bin/EnergyDetector --config ./alize/cfg/EnergyDetector_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --verbose true --debug false";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_energyDetector );
			System.out.println("Détection d'énergie terminée");
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
	
	private boolean train_world () {
		boolean retour = false;
		
		String command_trainWorld = "./alize/bin/TrainWorld --config ./alize/cfg/TrainWorld.cfg --inputFeatureFilename ./alize/lst/UBM.lst";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_trainWorld );
			System.out.println("Entrainement du modele monde terminé");
			fichierTrainModel(this.nomFichier, this.nomFichier + "_" + (nbFichier+1) );
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
	
	private boolean train_target () {
		boolean retour = false;
		
		String command_target = "./alize/bin/TrainTarget --config ./alize/cfg/TrainTarget.cfg --targetIdList ./alize/ndx/trainModel.ndx --inputWorldFilename ./alize/gmm/world.gmm --debug false --verbose true";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_target );
			System.out.println("Entrainement du modele target terminé");
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
	
	
	boolean creer_modele () {
		boolean retour = false;
		if(enregistrement_wav ()) {
			if (parametrisation ()) {
				if (normalisation_1()) {
					if (detection_energie()) {
						if (normalisation_2()) {
							if (train_world ()) {
								if (train_target()) {
									fichier_computetest_gmm_target_seg();
									retour = true;
								}
							}
						}
					}
				}
			}
		}
		return retour;
	}
	

}
