package com.lezardino.mybank.erreur;

public class ErreurFonctionnelle extends Exception {

    /**
     * Code erreur en cas de donnée inconnue
     */
    public final static String CODE_ERREUR_DONNEEINCONNUE = "NOTFOUND";

    /**
     * Numéro de série de la classe.
     */
    private static final long serialVersionUID = 7355386654854687797L;

    /**
     * Constructeur interne
     *
     */
    public ErreurFonctionnelle(final String codeErreur, final String message, final Throwable cause) {
        super(message, cause);

    }
}
