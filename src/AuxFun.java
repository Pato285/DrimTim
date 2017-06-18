

public class AuxFun {
	
	public static String maxSubstring(String a, String b)
	{
		//Largos de las palabras
		int lenA = a.length();
		int lenB = b.length();
		
		//Matrices de largos y procedencia
		int[][] mat = new int[lenA][lenB];
		int xfin = 0, yfin = 0;
		int max = 0;
		
		//Iteradores
		int i,j;
		
		
		
		//Caso base (0,0)
		mat[0][0] = 0;
		if(a.charAt(0) == b.charAt(0))
		{
			mat[0][0] = 1;
			max = 1;
		}
		
		//Caso base columna 0
		for(i = 1; i < lenA; i++)
		{
			if(a.charAt(i) == b.charAt(0))
			{
				mat[i][0] = 1;
				if(max < mat[i][0])
				{
					max = mat[i][0];
					xfin = i;
					yfin = 0;
				}
				
			}
			else
			{
				mat[i][0] = 0;
			}
		}
		
		//Caso base fila 0
		for(j = 1; j < lenB; j++)
		{
			if(a.charAt(0) == b.charAt(j))
			{
				mat[0][j] = 1;
				if(max < mat[0][j])
				{
					max = mat[0][j];
					xfin = 0;
					yfin = j;
				}
			}
			else
			{
				mat[0][j] = 0;
			}
		}
		
		
		
		//Caso general de encontrar largos
		for(i = 1; i < lenA; i++)
		{
			for(j = 1; j < lenB; j++)
			{
				if(a.charAt(i) == b.charAt(j))
				{
					mat[i][j] = mat[i-1][j-1] + 1;
					if(max < mat[i][j])
					{
						max = mat[i][j];
						xfin = i;
						yfin = j;
					}
				}
				else
				{
					mat[i][j] = 0;
				}
			}
		}
		
		
		//String a retornar
		String fin = "";
		
		
		while(mat[xfin][yfin] > 0 && xfin > 0 && yfin > 0)
		{
			fin = a.charAt(xfin) + fin;
			xfin--;
			yfin--;
		}
		
		if(mat[xfin][yfin] > 0)
		{
			fin = a.charAt(xfin) + fin;
		}
		
		return fin;
	}
	
	public static String maxSubsecuence(String a, String b)
	{
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
		if(a.charAt(0) == b.charAt(0))
		{
			mat[0][0] = 1;
			procedencia[0][0] = 2;
		}
		
		//Caso base columna 0
		for(i = 1; i < lenA; i++)
		{
			if(a.charAt(i) == b.charAt(0))
			{
				mat[i][0] = 1;
				procedencia[i][0] = 2;
				
			}
			else
			{
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
			else
			{
				mat[0][j] = Math.max(mat[0][j-1], 0);
				procedencia[0][j] = 0;
			}
		}
		
		
		
		//Caso general de encontrar largos
		for(i = 1; i < lenA; i++)
		{
			for(j = 1; j < lenB; j++)
			{
				if(a.charAt(i) == b.charAt(j))
				{
					if(mat[i-1][j-1] >= mat[i][j-1] && mat[i-1][j-1] >= mat[i-1][j])
					{
						mat[i][j] = mat[i-1][j-1] + 1;
						procedencia[i][j] = 2;
					}
					else if(mat[i-1][j] >= mat[i][j-1])
					{
						mat[i][j] = mat[i-1][j];
						procedencia[i][j] = 1;
					}
					else
					{
						mat[i][j] = mat[i][j-1];
						procedencia[i][j] = 0;
					}
				}
				else
				{
					if(mat[i-1][j] >= mat[i][j-1])
					{
						mat[i][j] = mat[i-1][j];
						procedencia[i][j] = 1;
					}
					else
					{
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
		while(i > 0 && j > 0)
		{
			if(procedencia[i][j] == 2)
			{
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
		if (procedencia[i][j] == 2)
		{
			fin = a.charAt(i) + fin;
		}
		
		
		return fin;
	}

}
