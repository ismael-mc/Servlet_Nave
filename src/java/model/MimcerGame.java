/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ismael
 */
@Entity
@Table(name = "mimcer_game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MimcerGame.findAll", query = "SELECT m FROM MimcerGame m")
    , @NamedQuery(name = "MimcerGame.findByIdGame", query = "SELECT m FROM MimcerGame m WHERE m.idGame = :idGame")
    , @NamedQuery(name = "MimcerGame.findByStartdate", query = "SELECT m FROM MimcerGame m WHERE m.startdate = :startdate")
    , @NamedQuery(name = "MimcerGame.findByEnddate", query = "SELECT m FROM MimcerGame m WHERE m.enddate = :enddate")
    , @NamedQuery(name = "MimcerGame.findByMatches", query = "SELECT m FROM MimcerGame m WHERE m.matches = :matches")})
public class MimcerGame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_game")
    private Integer idGame;
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Column(name = "matches")
    private Integer matches;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne
    private MimcerUsers idUser;

    public MimcerGame() {
    }

    public MimcerGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public MimcerUsers getIdUser() {
        return idUser;
    }

    public void setIdUser(MimcerUsers idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGame != null ? idGame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MimcerGame)) {
            return false;
        }
        MimcerGame other = (MimcerGame) object;
        if ((this.idGame == null && other.idGame != null) || (this.idGame != null && !this.idGame.equals(other.idGame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MimcerGame[ idGame=" + idGame + " ]";
    }
    
}
