import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Locuteur {

	String prenom; // prenom du locuteur
	String nom; // nom du locuteur
	String identifiantLocuteur; // identifiant du locuteur
	int nbFichier; // Nombre d'enregistrement du locuteur

	Locuteur(String prenom, String nom) {
		this.nom = nom;
		this.prenom = prenom;
		// L'identifiant est une concatenation du prenom et du nom separés par un tiret
		this.identifiantLocuteur = prenom + "_" + nom;
		this.nbFichier = 0;
	}

	/*
	 * Methode permettant l'enregistrement audio de cinq minutes avec Arecord. Le
	 * locuteur doit parler durant cinq minutes en répétant le mot bonjour.
	 */
	private boolean enregistrement_wav() {
		boolean retour = false;
		// cinq minutes d'enregistrement
		String command = "arecord --duration=300 ./alize/data/audios/model/";
		command += (this.identifiantLocuteur + "_" + (nbFichier + 1));
		System.out.println("Bonjour, " + prenom + " " + nom + ", répétezez le mot bonjour. Vous avez 5 minutes");
		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command);
			System.out.println("Enregistrement en cours.... ");
			// On arrete le systeme quelques minutes le temps de l'enregistrement
			Thread.sleep(301000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * On imprime le nom du fichier PRM créé dans le fichier all.lst
	 */
	private void edition_fichier_all_lst() throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/lst/all.lst", true)));
		writer.println(this.identifiantLocuteur + "_" + (nbFichier + 1));
		writer.close();
	}

	/*
	 * On imprime le nom du fichier PRM dans le fichier UBM.lst
	 */
	private void edition_fichier_UBM_lst() throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/lst/UBM.lst", true)));
		writer.println(this.identifiantLocuteur + "_" + (nbFichier + 1));
		writer.close();
	}

	/*
	 * On imprime l'identifiant du locuteur et le nom de son fichier PRM dans le
	 * fichier trainModel.ndx
	 */
	private void edition_fichier_TrainModel() throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("./alize/ndx/trainModel.ndx", true)));
		writer.print(this.identifiantLocuteur);
		writer.print('\t');
		writer.println(this.identifiantLocuteur + "_" + (nbFichier + 1));
		writer.close();
	}

	/*
	 * On imprime le nom du modele dans le fichier computetest_gmm_target-seg.ndx
	 * pour les tests de locuteur à venir
	 */
	private void edition_fichier_computetest_gmm_target_seg() {
		try {
			PrintWriter writer = new PrintWriter(
					new BufferedWriter(new FileWriter("./alize/ndx/computetest_gmm_target-seg.ndx", true)));
			writer.print(" " + this.identifiantLocuteur);
			writer.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * Méthode permettant la paramétrisation avec la commande sfbcep de SPro.
	 */
	private boolean parametrisation() {

		boolean retour = false;
		String command_parametrisation = "sfbcep -F PCM16 -f 16000 -p 19 -e -D -A ./alize/data/audios/model/"
				+ this.identifiantLocuteur + "_" + (nbFichier + 1) + " ./alize/data/prm/" + this.identifiantLocuteur
				+ "_" + (nbFichier + 1) + ".prm";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_parametrisation);
			System.out.println("Parametrisation terminée");
			edition_fichier_all_lst();
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Méthode permettant une premiere normalisation avec la commande NormFeat de
	 * ALIZE.
	 */
	private boolean normalisation_1() {
		boolean retour = false;

		String command_normalisation = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_energy_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --debug false --verbose true";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_normalisation);
			System.out.println("Première normalisation terminée");
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Méthode permettant une deuxieme normalisation avec la commande NormFeat de
	 * ALIZE (apres la detection d'energie).
	 */
	private boolean normalisation_2() {
		boolean retour = false;

		String command_normalisation = "./alize/bin/NormFeat --config ./alize/cfg/NormFeat_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --debug false --verbose true";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_normalisation);
			System.out.println("Deuxième normalisation terminée");
			edition_fichier_UBM_lst();
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Méthode permettant une la detection d'energie dans le signal de la voix. Les
	 * parties sans discours sont ainsi eliminées. Ce qui donne un fichier parametre
	 * bien plus precis. La commande utilisée est EnergyDetector de ALIZE.
	 */
	private boolean detection_energie() {
		boolean retour = false;

		String command_energyDetector = "./alize/bin/EnergyDetector --config ./alize/cfg/EnergyDetector_SPro.cfg --inputFeatureFilename ./alize/lst/all.lst --verbose true --debug false";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_energyDetector);
			System.out.println("Détection d'énergie terminée");
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Methode permettant l'apprentissage du modele monde avec la commande
	 * TrainWorld de ALIZE.
	 */
	private boolean train_world() {
		boolean retour = false;

		String command_trainWorld = "./alize/bin/TrainWorld --config ./alize/cfg/TrainWorld.cfg --inputFeatureFilename ./alize/lst/UBM.lst";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_trainWorld);
			System.out.println("Entrainement du modele monde terminé");
			edition_fichier_TrainModel();
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Methode permettant l'apprentissage du modele monde avec la commande
	 * TrainTarget de ALIZE.
	 */
	private boolean train_target() {
		boolean retour = false;

		String command_target = "./alize/bin/TrainTarget --config ./alize/cfg/TrainTarget.cfg --targetIdList ./alize/ndx/trainModel.ndx --inputWorldFilename ./alize/gmm/world.gmm --debug false --verbose true";

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec(command_target);
			System.out.println("Entrainement du modele target terminé");
			edition_fichier_computetest_gmm_target_seg();
			// On stoppe l'execution quelques secondes pour laisser le temps au systeme de
			// tout faire
			Thread.sleep(3000);
			retour = true;
		} catch (Exception e) {
			System.out.println(e);
			retour = false;
		}

		return retour;
	}

	/*
	 * Methode qui fait appele à l'ensemble des methodes permettant la creation de
	 * modele. Les methodes sont appelés dans l'ordre l'une après l'autre.
	 */
	boolean creer_modele() {
		boolean retour = false;
		if (enregistrement_wav())
			if (parametrisation())
				if (normalisation_1())
					if (detection_energie())
						if (normalisation_2())
							if (train_world())
								if (train_target())
									retour = true;

		return retour;
	}

}
