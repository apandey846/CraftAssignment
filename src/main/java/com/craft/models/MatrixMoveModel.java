package com.craft.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "craft_matrix_move")
public class MatrixMoveModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int mRow;
	private int mColumn;
	private String mValue;
	private String playerName;
	private Date created;
	private Date modified;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userGameId")
	private CraftUserGameModel craftUserGameModel;

	public MatrixMoveModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatrixMoveModel(int mRow, int mColumn, String mValue, String playerName, CraftUserGameModel craftUserGameModel) {
		super();
		this.mRow = mRow;
		this.mColumn = mColumn;
		this.mValue = mValue;
		this.playerName = playerName;
		this.created = new Timestamp(new Date().getTime());
		this.modified = new Timestamp(new Date().getTime());
		this.craftUserGameModel = craftUserGameModel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getmRow() {
		return mRow;
	}

	public void setmRow(int mRow) {
		this.mRow = mRow;
	}

	public int getmColumn() {
		return mColumn;
	}

	public void setmColumn(int mColumn) {
		this.mColumn = mColumn;
	}

	public String getmValue() {
		return mValue;
	}

	public void setmValue(String mValue) {
		this.mValue = mValue;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public CraftUserGameModel getCraftUserGameModel() {
		return craftUserGameModel;
	}

	public void setCraftUserGameModel(CraftUserGameModel craftUserGameModel) {
		this.craftUserGameModel = craftUserGameModel;
	}

	@Override
	public String toString() {
		return "MatrixMoveModel [id=" + id + ", mRow=" + mRow + ", mColumn=" + mColumn + ", mValue=" + mValue
				+ ", playerName=" + playerName + ", created=" + created + ", modified=" + modified
				+ ", craftUserGameModel=" + craftUserGameModel + "]";
	}

}
