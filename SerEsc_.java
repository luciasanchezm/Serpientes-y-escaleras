import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SerEsc_ extends JFrame implements ActionListener {
	
	private ListaDBL<Casilla> Tablero;
	private JPanel TableroGrafico, PanelJugadores;
	private NodoDBL<Casilla>[] Jugadores;
	private Casilla [] JugadoresAux;
	private JButton BtnJugar, BtnReiniciar;
	
	public SerEsc_() {
		Tablero = new ListaDBL<Casilla>();
		TableroGrafico = new JPanel();
		Jugadores = new NodoDBL[Rutinas.nextInt(2,10)];
		JugadoresAux = new Casilla[Jugadores.length];
		HazInterfaz();
		HazEscuchas();
	}
	
	public void HazInterfaz() {
		setSize(1700,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CrearTablero();
		
		PanelJugadores = new JPanel();
		PanelJugadores.setLayout(new GridLayout(0,1,5,5));

		for (int i = 0; i < Jugadores.length; i++) {
			PanelJugadores.add(new JLabel("Jujador #" + (i+1)));
			Casilla C = new Casilla(0,'N',0);
			C.HazInterfaz();
			JugadoresAux[i] = C;
			PanelJugadores.add(C);
		}
		
		add(PanelJugadores, BorderLayout.EAST);

		add(TableroGrafico, BorderLayout.CENTER);
		
		JPanel PanelBotones = new JPanel();
		
		BtnJugar = new JButton("¡JUGAR!");
		BtnJugar.setPreferredSize(new Dimension(100,30));
		PanelBotones.add(BtnJugar);
		BtnReiniciar = new JButton("REINICIAR");
		BtnReiniciar.setPreferredSize(new Dimension(100,30));
		PanelBotones.add(BtnReiniciar);
		add(PanelBotones, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public void HazEscuchas() {
		BtnJugar.addActionListener(this);
		BtnReiniciar.addActionListener(new ActionListener()
		{   public void actionPerformed(ActionEvent evt) {
			new SerEsc_();
			dispose();
		}
		});
	}
	
	public void actionPerformed(ActionEvent Evt) {    //CLICK AL BOTON CON ENTER
		Jugar();
	}
	
	public void CrearTablero() {
		//Crea el tablero
		for (byte i = 0; i < 100; i++)
			Tablero.InsertaFin(new Casilla(i+1, 'N', 0));
		Crear('E', 15, 70, "Escalera");
		Crear('S', 30, 95, "Serpiente");
		
		//Crea el tablero gráfico
		TableroGrafico.setLayout(new GridLayout(0,10,5,5));
		NodoDBL<Casilla> Aux=Tablero.getFrente();
		while(Aux!=null) {
			Aux.Info.HazInterfaz();
			TableroGrafico.add(Aux.Info);
			Aux=Aux.getSig();
		}
	}
	
	public void Crear(char TC, int LimI, int LimS, String NomImagen) {
		int Casilla, Cuantos;
		NodoDBL<Casilla> Aux;

		for (byte i = 0; i <5; i++) {
			Casilla = Rutinas.nextInt(LimI, LimS);
			Cuantos = Rutinas.nextInt(5,20);
			Aux=Tablero.getFrente();
			while(Aux.Info.NoCasilla!=Casilla)
				Aux=Aux.getSig();
			if(!Intento(Aux, Cuantos, TC)) {
				i--;
				continue;
			}
			Aux.Info.TipoCasilla=TC;
			Aux.Info.Posiciones=Cuantos;
			Aux.Info.NomImagen= NomImagen + ".png";
			for (int j = 0; j < Cuantos; j++)
				if(TC=='E')
					Aux=Aux.getSig();
				else
					Aux=Aux.getAnt();
			Aux.Info.TipoCasilla='T';
			Aux.Info.NomImagen = NomImagen+"I.png";
		}
	}
	
	public boolean Intento(NodoDBL<Casilla> Aux, int Cuantos, char TC) {
		//Verifica primera casilla
		if(Aux.Info.TipoCasilla!='N')
			return false;

		for (byte i = 0; i < Cuantos ; i++) {
			if(Aux.Info.TipoCasilla!='N')
				return false;
			if(TC=='E')
				Aux=Aux.getSig();
			else
				Aux=Aux.getAnt();
		}

		//Verifica última casilla
		if(Aux.Info.TipoCasilla!='N')
			return false;

		return true;
	}

	private int Moverse, Num=-1;
	public void Jugar() {
		Num++;
		Moverse = Rutinas.nextInt(2,12);
		//Antes de avanzar guarda la primera parte en un texto
		String Text = ("Jugador #" + (Num+1) + "    | Puntos: " + Moverse + "    | Inicio: Casilla ");
		if(Jugadores[Num]==null) {
			Text+="0";
			Jugadores[Num] = Tablero.getFrente();
			Moverse--;
		}
		else {
			Text+=(Jugadores[Num].Info.NoCasilla);
			Jugadores[Num].Info.changeBorder(false);
		}

		Avanzar(Moverse, Num);
		Jugadores[Num].Info.changeBorder(true);

		//Después de avanzar imprime todo junto
		Text+=("    | Final: Casilla " + Jugadores[Num].Info.NoCasilla + "  tipo  " + Jugadores[Num].Info.TipoCasilla);

		JOptionPane.showMessageDialog(this, Text);
		
		//Verifica si llegó al final exactamente
		if(Jugadores[Num]==Tablero.getFin()) {
			JOptionPane.showMessageDialog(this, ("\n¡EL JUEGO HA TERMINADO! \n\tGanó Jugador #" + (Num+1)));
			BtnJugar.setEnabled(false);
			AjustarJugadores();
			return;
		}

		//Verifica si hay escalera o serpiente en donde cayó, y si es el caso, imprime el avance o retroceso adicional
		if(Jugadores[Num].Info.TipoCasilla=='E') {
			Text = ("¡ESCALERA!\nAvance adicional: " + Jugadores[Num].Info.Posiciones + "    | Posición final: Casilla " + Jugadores[Num].Info.NoCasilla);
			Avanzar(Jugadores[Num].Info.Posiciones, Num);
			JOptionPane.showMessageDialog(this, Text);
		}
		if(Jugadores[Num].Info.TipoCasilla=='S') {
			Text = ("¡SERPIENTE!\nRetroceso adicional: " + Jugadores[Num].Info.Posiciones + "    | Posición final: Casilla " + Jugadores[Num].Info.NoCasilla);
			Retroceder(Jugadores[Num].Info.Posiciones, Num);
			JOptionPane.showMessageDialog(this, Text);
		}
		
		AjustarJugadores();

		//Si es el último jugador, vuelve a iniciar
		if(Num==Jugadores.length-1)
			Num=-1;
	}

	public void Avanzar(int Moverse, int Num) {
		while(Moverse!=0) {
			Jugadores[Num]=Jugadores[Num].getSig();
			if(Jugadores[Num]==Tablero.getFin() && Moverse!=1) {
				Retroceder(Moverse, Num);
				break;
			}
			Moverse--;
		}
	}

	public void Retroceder(int Moverse, int Num) {
		while(Moverse!=0) {
			Jugadores[Num]=Jugadores[Num].getAnt();
			Moverse--;
		}
	}
	
	public void AjustarJugadores() {
		JugadoresAux[Num].NoCasilla=Jugadores[Num].Info.NoCasilla;
		JugadoresAux[Num].NomImagen=Jugadores[Num].Info.NomImagen;
		JugadoresAux[Num].HazInterfaz();
		PanelJugadores.update(PanelJugadores.getGraphics());
	}
	
	public static void main(String [] a) {
		new SerEsc_();
	}
}
