import java.util.Arrays;

public interface AuxFun{
	

	public static String maxSubsecuence(String a, String b){
		//Largos de las palabras
		int lenA = a.length();
		int lenB = b.length();
		
		//Matrices de largos y procedencia
		int[][] mat = new int[lenA][lenB];
		int[][] procedencia = new int[lenA][lenB];
		
		//Iteradores
		int i,j;
		
		
		//Si procedencia[i][j] = 0; vine desde la izquierda
		//Si procedencia[i][j] = 1; vine desde arriba
		//Si procedencia[i][j] = 2; vine desde la diagonal y agrego una letra
		
		//Caso base (0,0)
		mat[0][0] = 0;
		procedencia[0][0] = 0;
		if(a.charAt(0) == b.charAt(0)){
			mat[0][0] = 1;
			procedencia[0][0] = 2;
		}
		
		//Caso base columna 0
		for(i = 1; i < lenA; i++){
			if(a.charAt(i) == b.charAt(0)){
				mat[i][0] = 1;
				procedencia[i][0] = 2;
				
			}
			else{
				mat[i][0] = Math.max(mat[i-1][0], 0);
				procedencia[i][0] = 1;
			}
		}
		
		//Caso base fila 0
		for(j = 1; j < lenB; j++)
		{
			if(a.charAt(0) == b.charAt(j))
			{

				mat[0][j] = 1;
				procedencia[0][j] = 2;
			}
			else{
				mat[0][j] = Math.max(mat[0][j-1], 0);
				procedencia[0][j] = 0;
			}
		}
		
		
		
		//Caso general de encontrar largos
		for(i = 1; i < lenA; i++){
			for(j = 1; j < lenB; j++){
				if(a.charAt(i) == b.charAt(j)){
					if(mat[i-1][j-1] >= mat[i][j-1] && mat[i-1][j-1] >= mat[i-1][j]){
						mat[i][j] = mat[i-1][j-1] + 1;
						procedencia[i][j] = 2;
					}
					else if(mat[i-1][j] >= mat[i][j-1]){
						mat[i][j] = mat[i-1][j];
						procedencia[i][j] = 1;
					}
					else{
						mat[i][j] = mat[i][j-1];
						procedencia[i][j] = 0;
					}
				}
				else{
					if(mat[i-1][j] >= mat[i][j-1]){
						mat[i][j] = mat[i-1][j];
						procedencia[i][j] = 1;
					}
					else{
						mat[i][j] = mat[i][j-1];
						procedencia[i][j] = 0;
					}
				}
			}
		}
		
		
		//String a retornar
		String fin = "";
		
		//generar una de las subsecuencias más largas
		i = lenA -1;
		j = lenB -1;
		
		//si vine de la diagonal agrego una letra al principio de la palabra,
		//en caso contrario me muevo adecuadamente
		while(i > 0 && j > 0){
			if(procedencia[i][j] == 2){
				fin = a.charAt(i) + fin;
				i--;
				j--;
			}
			else if(procedencia[i][j] == 1)
				i--;
			else if(procedencia[i][j] == 0)
				j--;
		}		
		
		//si llego a la fila o columna 0 busco la primera letra que se agrego
		//si no se agrego nada llegare a la posicion (0,0)
		while(procedencia[i][0] < 2 && i > 0)
			i--;
		
		while(procedencia[0][j] < 2 && j > 0)
			j--;
		
		//si se agrego algo en la posicion a la que se llego, agregarlo al principio de la palabra
		if (procedencia[i][j] == 2){
			fin = a.charAt(i) + fin;
		}
		
		
		return fin;
	}

	public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the  array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

	public static String typesOfChars (String a){
		int minus = (!a.equals(a.toUpperCase())) ? 1 : 0;
		int mayus = (!a.equals(a.toLowerCase())) ? 1 : 0;
		int number = (a.matches(".*\\d+.*")) ? 1 : 0; 
		int special = (!a.matches("[A-Za-z0-9]*")) ? 1 : 0;
		return ""+minus+mayus+number+special;
	}
	
	public static String sortString(String a){
		char[] charArray = a.toCharArray();
		Arrays.sort(charArray);
		return new String(charArray);
	}
	
	public static String uniqueChars (String a){
		String sorted = sortString(a);
		String res = "";
		char last = sorted.charAt(0);
		res+=last;
		for(int i = 0; i<sorted.length(); i++){
			char c = sorted.charAt(i);
			if(c!=last){
				last=c;
				res+=last;
			}
		}
		return res;
	}
	
	
}
