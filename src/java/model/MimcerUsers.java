/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ismael
 */
@Entity
@Table(name = "mimcer_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MimcerUsers.findAll", query = "SELECT m FROM MimcerUsers m")
    , @NamedQuery(name = "MimcerUsers.findByIdUser", query = "SELECT m FROM MimcerUsers m WHERE m.idUser = :idUser")
    , @NamedQuery(name = "MimcerUsers.findByAlias", query = "SELECT m FROM MimcerUsers m WHERE m.alias = :alias")
    , @NamedQuery(name = "MimcerUsers.findByNameu", query = "SELECT m FROM MimcerUsers m WHERE m.nameu = :nameu")
    , @NamedQuery(name = "MimcerUsers.findByPasswordu", query = "SELECT m FROM MimcerUsers m WHERE m.passwordu = :passwordu")})
public class MimcerUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @Column(name = "nameu")
    private String nameu;
    @Column(name = "passwordu")
    private String passwordu;
    @OneToMany(mappedBy = "idUser")
    private List<MimcerGame> mimcerGameList;

    public MimcerUsers() {
    }

    public MimcerUsers(Integer idUser) {
        this.idUser = idUser;
    }

    public MimcerUsers(Integer idUser, String nameu) {
        this.idUser = idUser;
        this.nameu = nameu;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNameu() {
        return nameu;
    }

    public void setNameu(String nameu) {
        this.nameu = nameu;
    }

    public String getPasswordu() {
        return passwordu;
    }

    public void setPasswordu(String passwordu) {
        this.passwordu = passwordu;
    }

    @XmlTransient
    public List<MimcerGame> getMimcerGameList() {
        return mimcerGameList;
    }

    public void setMimcerGameList(List<MimcerGame> mimcerGameList) {
        this.mimcerGameList = mimcerGameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MimcerUsers)) {
            return false;
        }
        MimcerUsers other = (MimcerUsers) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MimcerUsers[ idUser=" + idUser + " ]";
    }
    
}
