package com.holtebu.bosetterne.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class Spill implements Comparable<Spill>{

	private int spillId;
	
	private String navn;
	
	private TypeSpill typeSpill;
	
	private boolean startet;



    private Date datoFom;
	
	private boolean fullfort;

    private Date datoTom;
	
	private int maxPoeng;
	
	private ResourceBundle msg;
	
	public Spill(){}
	
	public void setMsg(ResourceBundle msg){
		this.msg = msg;
	}

	public int getSpillId() {
		return spillId;
	}

	public void setSpillId(int spillId) {
		this.spillId = spillId;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getTypeSpill() {
		if (msg == null) {
			return typeSpill.toString();
		} else {
			return msg.getString(typeSpill.toString());
		}
	}

	public void setTypeSpill(String typeSpill) {
		this.typeSpill = TypeSpill.valueOf(typeSpill);
	}

	public boolean isStartet() {
		return startet;
	}

	public void setStartet(boolean startet) {
		this.startet = startet;
	}

	public boolean isFullfort() {
		return fullfort;
	}

	public void setFullfort(boolean fullfort) {
		this.fullfort = fullfort;
	}
	
	public int getMaxPoeng() {
		return maxPoeng;
	}

	public void setMaxPoeng(int maxPoeng) {
		this.maxPoeng = maxPoeng;
	}

    public Date getDatoFom() {
        return datoFom;
    }

    public String getDatoFomStr() {
        if (getDatoFom() == null) return null;
        DateFormat df = new SimpleDateFormat("dd/MM HH:mm");
        return df.format(getDatoFom());
    }

    public void setDatoFom(Date datoFom) {
        this.datoFom = datoFom;
    }

    public Date getDatoTom() {
        return datoTom;
    }

    public String getDatoTomStr() {
        if (getDatoTom() == null) return null;
        DateFormat df = new SimpleDateFormat("dd/MM HH:mm");
        return df.format(getDatoTom());
    }

    public void setDatoTom(Date datoTom) {
        this.datoTom = datoTom;
    }

	public static class SpillMapper implements ResultSetMapper<Spill> {
	
		public Spill map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			Spill spill = new Spill();
			
			spill.setSpillId(r.getInt(1));
			spill.setTypeSpill(r.getString(2));
			spill.setNavn(r.getString(3));

            spill.setDatoFom(r.getTimestamp(4));
            spill.setStartet(spill.getDatoFom() != null);
            spill.setDatoTom(r.getTimestamp(5));
            spill.setFullfort(spill.getDatoTom() != null);
			spill.setMaxPoeng(r.getInt(6));
			
			return spill;
		}
		
	}

	@Override
	public int compareTo(Spill spill) {
        if(getDatoTom() != null) {
            return compareFerdig(spill);
        } else {
            return compareIkkeFerdig(spill);
        }
	}

    private int compareIkkeFerdig(Spill spill) {
        if(spill.getDatoTom() != null) {
            return -1;
        }
        if (getDatoFom() != null) {
            if(spill.getDatoFom() == null) {
                return 1;
            }
            int compareDatoFom = getDatoFom().compareTo(spill.getDatoFom());
            if (compareDatoFom == 0) {
                return compareId(spill);
            } else {
                return compareDatoFom;
            }
        } else {
            if(spill.getDatoFom() != null) {
                return -1;
            } else {
                return compareId(spill);
            }
        }
    }

    private int compareFerdig(Spill spill) {
        if(spill.getDatoTom() == null) {
            return 1;
        }
        int compareDatoTom = getDatoTom().compareTo(spill.getDatoTom());
        if (compareDatoTom == 0) {
            return compareId(spill);
        } else {
            return compareDatoTom;
        }
    }

    private int compareId(Spill spill) {
        return new Integer(getSpillId()).compareTo(spill.getSpillId());
    }


}

enum TypeSpill {
	BOSETTERNE, BYERRIDDER, SJOFARER, SJOOGLAND, HANDELBARB, EXPLPIRAT
}
