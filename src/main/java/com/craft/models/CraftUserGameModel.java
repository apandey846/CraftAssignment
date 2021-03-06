package com.craft.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "craft_user_game")
public class CraftUserGameModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String competitionName;
	private String firstPlayerName;
	private String secondPlayerName;
	private String currentStatus;
	private String playerMove;
	private String firstPlayerSymbol;
	private String secondPlayerSymbol;
	private Date created;
	private Date modified;

	@OneToMany(mappedBy = "craftUserGameModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	List<MatrixMoveModel> matrixMoveModel;

	public CraftUserGameModel() {
		super();
	}

	public CraftUserGameModel(String competitionName, String firstPlayerName, String secondPlayerName,
			String currentStatus, String firstPlayerSymbol, String secondPlayerSymbol) {
		super();
		this.competitionName = competitionName;
		this.firstPlayerName = firstPlayerName;
		this.secondPlayerName = secondPlayerName;
		this.currentStatus = currentStatus;
		this.playerMove = firstPlayerName.length() >= secondPlayerName.length() ? firstPlayerName : secondPlayerName;
		this.created = new Timestamp(new Date().getTime());
		this.modified = new Timestamp(new Date().getTime());
		this.firstPlayerSymbol = firstPlayerSymbol;
		this.secondPlayerSymbol = secondPlayerSymbol;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	public String getFirstPlayerName() {
		return firstPlayerName;
	}

	public void setFirstPlayerName(String firstPlayerName) {
		this.firstPlayerName = firstPlayerName;
	}

	public String getSecondPlayerName() {
		return secondPlayerName;
	}

	public void setSecondPlayerName(String secondPlayerName) {
		this.secondPlayerName = secondPlayerName;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getPlayerMove() {
		return playerMove;
	}

	public void setPlayerMove(String playerMove) {
		this.playerMove = playerMove;
	}

	public String getFirstPlayerSymbol() {
		return firstPlayerSymbol;
	}

	public void setFirstPlayerSymbol(String firstPlayerSymbol) {
		this.firstPlayerSymbol = firstPlayerSymbol;
	}

	public String getSecondPlayerSymbol() {
		return secondPlayerSymbol;
	}

	public void setSecondPlayerSymbol(String secondPlayerSymbol) {
		this.secondPlayerSymbol = secondPlayerSymbol;
	}

	public List<MatrixMoveModel> getMatrixMoveModel() {
		return matrixMoveModel;
	}

	public void setMatrixMoveModel(List<MatrixMoveModel> matrixMoveModel) {
		this.matrixMoveModel = matrixMoveModel;
	}

	@Override
	public String toString() {
		return "CraftUserGameModel [id=" + id + ", competitionName=" + competitionName + ", firstPlayerName="
				+ firstPlayerName + ", secondPlayerName=" + secondPlayerName + ", currentStatus=" + currentStatus
				+ ", playerMove=" + playerMove + ", firstPlayerSymbol=" + firstPlayerSymbol + ", secondPlayerSymbol="
				+ secondPlayerSymbol + ", created=" + created + ", modified=" + modified + ", matrixMoveModel="
				+ matrixMoveModel + "]";
	}

}
