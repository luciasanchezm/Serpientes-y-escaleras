import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Casilla extends JLabel {
	int NoCasilla;
	char TipoCasilla;
	int Posiciones;
	String NomImagen;
	ImageIcon Imagen;

	public Casilla(int NoCasilla, char TipoCasilla, int Posiciones) {
		super();
		this.NoCasilla = NoCasilla;
		this.TipoCasilla = TipoCasilla;
		this.Posiciones = Posiciones;
	}
	public void HazInterfaz() {
		setBorder(raised);
		setText(NoCasilla+"");
		setFont(new Font("Arial", Font.BOLD, 16));
		if(TipoCasilla=='E' || TipoCasilla=='S')
			this.setText(NoCasilla+"      P:"+Posiciones);
		setIcon(Rutinas.AjustarImagen(NomImagen, 70, 70));
	}
	
	private Border raised = BorderFactory.createRaisedBevelBorder();
	private Border lowered = BorderFactory.createLoweredBevelBorder();
	
	public void changeBorder(boolean Band) {
		if(Band) 
			setBorder(lowered);
		else 
			setBorder(raised);
	}
}