package com.emis.job2017;

/**
 * Created by jo5 on 02/11/17.
 */

public class ExhibitorsPadiglione {

    private int idPadiglione;
    private String code;
    private String title;

    public ExhibitorsPadiglione(){

    }

    public ExhibitorsPadiglione(int idPadiglione, String code, String title){
        this.idPadiglione = idPadiglione;
        this.code = code;
        this.title = title;
    }

    public int getIdPadiglione() {
        return idPadiglione;
    }

    public void setIdPadiglione(int idPadiglione) {
        this.idPadiglione = idPadiglione;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
