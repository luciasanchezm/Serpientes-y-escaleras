import java.util.*;
public class RutinasPOO {
	
	static Random R=new Random();
	static Scanner In=new Scanner(System.in);
	static String []VN ={"Alicia","Maria","Sofia","Antonio","Nereida","Carolina","Rebeca","Javier","Luis"};
	static String [] VA={"Garcia","Lopez","Perez","Urias","Mendoza","Coppel","Diaz"};
static boolean [] Sexo={false,false,false,true,false,false,false,true,true};	
    public static String nextNombre(){
    	return nextNombre(1);
    }
	public static String nextNombre(int Numero){
		String Nom="",NomTra="";
		int Pos;
		boolean Genero=true;;
		for(int i=0;i<Numero;i++){
			Pos=nextInt(VN.length);
			NomTra=VN[Pos];
			if (i==0){
				Genero=Sexo[Pos];
			}
			if( Genero != Sexo[Pos]  || i>0 && Nom.indexOf(NomTra)>-1    ){
				i--;
				continue;
			}	
			Nom+=NomTra+" ";
		}
		String Ap;
		for(byte i=0;i<2;i++){
			Ap=VA[nextInt(VA.length)];
			if(Nom.lastIndexOf(Ap)>-1){
				i--;
				continue;
			}
			Nom+=Ap+" ";
		}
		Nom=Nom.trim();
		return Nom;
	}    
	
	public static int nextInt(int Ini,int Fin){
	 return R.nextInt(Fin-Ini+1)+Ini;	
	}
	public static int nextInt(int Valor){
		return R.nextInt(Valor);
	}
	public static String PonCeros(int Numero,int Cuantos){
		String Texto=Numero+"";
		while(Texto.length()<Cuantos)
			Texto="0"+Texto;
		return Texto;
	}
	public static String PonBlancos(String Texto,int Cuantos){
		while (Texto.length()<Cuantos)
			Texto=Texto+" ";
		return Texto;
	}
	public static int LeeEntero(String Texto,int LimI,int LimS){
		int Numero=0;
		try{
			System.out.println(Texto+ " ("+LimI+"-"+LimS+")");
			Numero=In.nextInt();
			if(Numero<LimI || Numero>LimS)
				throw new MiEx(" el rango válido es de "+LimI+"-"+LimS);
		}
		catch (MiEx E){
			System.out.println("Proporcionó dato fuera de rango, "+E.getMessage());
			return LeeEntero(Texto,LimI,LimS);
		}
		catch (InputMismatchException E){
			System.out.println("Proporcionó TEXTO cuando se requiere valores numéricos "+E.getMessage());
			In.nextLine();
			return LeeEntero(Texto,LimI,LimS);
			
		}
		catch (Exception E){
			System.out.println("Proporcionó INFORMACIÓN INCORRECTA, "+E.getMessage());
			return LeeEntero(Texto,LimI,LimS);
			
		}
		
		return Numero;
		
	}
}
