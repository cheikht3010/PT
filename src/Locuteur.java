
public class Locuteur {
	
	String id;
	String nom;
	static int nextId = 1; // pseudo-variable globale
	
	Locuteur(String nom) {
		this.nom = nom;
        this.id = nom + nextId;
        nextId++;
	}
	
	private boolean enregistrement_wav() {
		boolean retour = false;
		// Trois minutes d'enregistrement
		String command = "arecord --duration=180 ./src/record/";
		command += this.id;
		System.out.println("Bonjour, " + nom + ", prononcez un mot. Vous avez 5 secondes");
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command );
			System.out.println("Enregistrement en cours.... ");
			Thread.sleep(181000);
			retour = true;
			
        }
		catch(Exception e)
        { 
            System.out.println(e);
            retour = false;
        }
		
		return retour;
	}
	
	private boolean parametrisation () {
		
		boolean retour = false;
		String command_parametrisation = "slpcep -f 16000 ./src/record/"+ this.id + " ./src/rpm/" + this.id + ".prm";
		
		try
        { 
			Runtime runtime = Runtime.getRuntime();
			runtime.getRuntime().exec( command_parametrisation );
			System.out.println("Parametrisation terminée");
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
				retour = true;
			}
		}
		return retour;
	}
	
	
}
+