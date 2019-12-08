package com.exce.model;

import com.exce.model.parameter.GameCode;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@Entity
public class Game extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 816765560786909407L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private GameCode code;
    @Column(length = 20, nullable = false)
    private String name;
    private Boolean active = false;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public GameCode getCode() {
        return code;
    }

    public void setCode(GameCode code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Game [id=");
        builder.append(this.getId());
        builder.append(", code=");
        builder.append(this.getCode());
        builder.append(", name=");
        builder.append(this.getName());
        builder.append(", active=");
        builder.append(this.getActive());
        builder.append("]");
        return builder.toString();
    }
}
