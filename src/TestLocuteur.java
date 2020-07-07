import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestLocuteur {

	/*
		Methode permettant l'enregistrement audio pour le test avec Arecord.
		Le locuteur à tester doit parler durant dix secondes en répétant le mot bonjour.
	*/
	private boolean enregistrement_wav_test() {
		boolean retour = false;
		// dix secondes d'enregistrement
		String command = "arecord --duration=10 ./alize/data/audios/test/test";
		System.out.println("Bonjour, exprimez vous en repétant le mot bonjour. Vous avez 10 secondes");
		try
        { 
        	// On arrete le systeme quelques minutes le temps de l'enregistrement
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
	
	/*
		Methode realisant la parametrisation, la premiere normalisation, la detection d'energie
		et la seconde normalisation.
	*/
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
			// On arrete le systeme quelques minutes le temps de l'enregistrement
			Thread.sleep(3000);
			
			runtime.getRuntime().exec( command_normalisation_1 );
			System.out.println("Première normalisation de test terminée");
			// On arrete le systeme quelques minutes le temps de l'enregistrement
			Thread.sleep(3000);
			
			runtime.getRuntime().exec( command_energyDetector );
			System.out.println("Détection d'énergie de test terminée");
			// On arrete le systeme quelques minutes le temps de l'enregistrement
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
	
	/*
		Methode executant la commande compute_test. Commande qui réalise la comparaison le locuteur 
		a identifier avec les autres modeles de locuteurs
	*/
	private boolean compute_test() {
		boolean retour = false;
		
		String command_compute = "./alize/bin/ComputeTest --config ./alize/cfg/ComputeTest_GMM.cfg  --ndxFilename ./alize/ndx/computetest_gmm_target-seg.ndx --worldModelFilename world --outputFilename ./alize/res/result.res";
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_compute );
			System.out.println("Execution de  compute test terminée");
			// On arrete le systeme quelques minutes le temps de l'enregistrement
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
			Methode qui retourne le nombre de ligne présentes dans le fichier result.res
		*/
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
	   
	   /* Methode qui parcourt le fichier result.res et ajoute renvoie le contenu sous forme de matreice
		Chaque ligne du fichier est represente sous forme de ligne dans la matrice. et chaque mot
		est stoque dans une case.
	   */
	   private String[][] lire_ligne ()
	   {
		   // Le fichier d'entrée
		      File file = new File("./alize/res/result.res");
		   // Initialiser le compteur à zéro
		      int nbrLine = 0; // nombre de lignes dans result.res
		      int indice = 0;
		      nbrLine = nombre_de_ligne();
		     
		      String resultat[][] = new String[nbrLine][5];
		     
		      // Créer l'objet File Reader
		      try {
				  FileReader fr = new FileReader(file);
				  // Créer l'objet BufferedReader 
				  BufferedReader br = new BufferedReader(fr);  
				  String str;
+				   // Lire le contenu du fichier
+				  while((str = br.readLine()) != null)
				  {
					 resultat[indice] = str.split(" ");
				     indice++;               
				  }
				  fr.close();
		      }
		      catch(Exception e)
		      { 
		    	  System.out.println(e);
		      }
		      return resultat;
	   }
	  
	  	/*
	  		Methode qui analyse le contenu du fichier result.res puis decide si 
	  		la personne testée est identifiée ou non. La methode retourne le prenom et le nom de
	  		la personne si elle est identifiée ou retourne null dans le cas contraire.
	  	*/
	   private String decision()
	   {
	   		// recuperation du contenu de result.res dans la matrice resultat
		   String resultat[][] = lire_ligne();
		   double valeurMax = 0.0; // Score max trouve
		   int indiceDuMax = 0;
		   // Pour chaque ligne du fichier result.res
		   for (int i = 0; i < resultat.length; i++) {
		   		// Si le troisieme element de la ligne est un 1
				if(resultat[i][2].equals("1")) {
					// Et si le 5eme element (la note) est superieure à la note maximale précédente
					if (valeurMax < Double.parseDouble(resultat[i][4])) {
						valeurMax = Double.parseDouble(resultat[i][4]); // On met à jour la note max
						indiceDuMax = i; // On sauvegarde l'indice de la note max
					}
				}	
			}

			// Si la note max est superieur à 0.5 la personne est reconnu 
		   if(Double.parseDouble(resultat[indiceDuMax][4]) > 0.5)
		   {
		   		// On extrait le nom et le prenom à partir de l'identifiant
		   		// identifiznt = prenom_nom
			   String prenomNom = resultat[indiceDuMax][1];
			   prenomNom = prenomNom.replace("_", " ");
			   return prenomNom; 
		   }
		   else 
			   return null;
		   
	   }

	/*
		Methode qui appele l'ensemble des methodes necessaires pour la réalisation d'un test
	*/
	boolean tester () {
		
		boolean retour = false;

		String prenomNom = decision();
		
		if(enregistrement_wav_test ()) {
			if(parametrisation_energyDetection_normalisation_test()) {
				if(compute_test()) {
					if(nom == null)
						System.out.println("La personne qui a parlé n'est pas reconnu !");
					else
						System.out.println("La personne qui a parlé est : " + prenomNom);
					retour = true;
				}
			}
		}
		return retour;
	}
	
	
}
