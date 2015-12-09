package com.lezardino.mybank.ressource;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.lezardino.mybank.enumeration.TypeRSLink;
import org.springframework.data.domain.Sort.Direction;

public class RSLink {

    /** Masque de l'URL */
    private final String uriPattern;

    /** Les liens possible sur les données */
    private Map<String, String> links;


    /** Constructeur par défaut **/
    public RSLink(String uriPattern) {
        super();
        this.uriPattern = uriPattern;
    }

    /**
     * Ajoute un lien
     *
     * @param lien obligatoire
     * @return la ressource pour chaîner les ajouts
     */
    private void addLink(final TypeRSLink typeLien, final String lien) {
        if (null == links) {
            links = new HashMap<>(4);
        }
        links.put(typeLien.getNom(), lien);
    }

    /**
     * Ajoute le lien à la ressource courante.
     *
     * @param typeLien type de lien
     * @param parametres optionnels
     * @return le builder, pour chaîner les ajouts
     */
    private void addLinkUri(final TypeRSLink typeLien, Object... parametres) {
        addLink(typeLien, MessageFormat.format(this.uriPattern, parametres));
    }

    /**
     * Valorisation de l'URL précédente
     *
     * @param offset : de démarrage de la liste
     * @param limit : sur le nombre de tarif retourné
     * @param direction : ordre de tri
     */
    public void setPreviousLink(final int offset, final int limit, final Direction direction) {
        if (offset > 0) {
            int previousOffset = offset - limit;
            int previousLimit = limit;
            if (previousOffset < 0) {
                previousLimit = limit + previousOffset;
                previousOffset = 0;
            }
            this.addLinkUri(TypeRSLink.LIEN_PREVIOUS, previousOffset, previousLimit, direction);
        }
    }

    /**
     * Valorisation de l'URL courante
     *
     * @param offset : de démarrage de la liste
     * @param limit : sur le nombre de tarif retourné
     * @param direction : ordre de tri
     */
    public void setSelfLink(final int offset, final int limit, final Direction direction) {
        this.addLinkUri(TypeRSLink.LIEN_SELF, offset, limit, direction);
    }

    /**
     * Valorisation de l'URL suivante
     *
     * @param offset : de démarrage de la liste
     * @param limit : sur le nombre de tarif retourné
     * @param resultCount : nombre d'enregistrement trouvé
     * @param direction : ordre de tri
     */
    public void setNextLink(final int offset, final int limit, final long resultCount, final Direction direction) {
        final int nextOffset = offset + limit;
        if (nextOffset < resultCount) {
            this.addLinkUri(TypeRSLink.LIEN_NEXT, nextOffset, limit, direction);
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "RSLink{" +
                "uriPattern='" + uriPattern + '\'' +
                ", links=" + links +
                '}';
    }
}
