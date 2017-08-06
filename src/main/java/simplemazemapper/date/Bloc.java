package main.java.simplemazemapper.date;

import main.java.simplemazemapper.utile.TipMarcaj;

public class Bloc {
    private double x;
    private double z;
    private TipMarcaj tip;
    private String nota;

    public Bloc(double x, double z, TipMarcaj tip) {
        this.x = x;
        this.z = z;
        this.tip = tip;
    }

    public double getX() { return x; }

    public double getZ() { return z; }

    public TipMarcaj getTip() { return tip; }

    public String getNota() { return nota; }

    public void setNota(String nota) { this.nota = nota; }

    public void setTip(TipMarcaj tip) { this.tip = tip; }
}
