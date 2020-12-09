package com.quickly.devploment.answer.repos;

/**
 * @Author lidengjin
 * @Date 2020/12/3 3:40 下午
 * @Version 1.0
 */
public class DebitBean {
	private String debitName;
	private int mustPayDate;
	private int GraceDate;
	private int stage;

	public String getDebitName() {
		return debitName;
	}

	public void setDebitName(String debitName) {
		this.debitName = debitName;
	}

	public int getMustPayDate() {
		return mustPayDate;
	}

	public void setMustPayDate(int mustPayDate) {
		this.mustPayDate = mustPayDate;
	}

	public int getGraceDate() {
		return GraceDate;
	}

	public void setGraceDate(int graceDate) {
		GraceDate = graceDate;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "DebitBean{" + "debitName='" + debitName + '\'' + ", mustPayDate=" + mustPayDate + ", GraceDate="
				+ GraceDate + ", stage=" + stage + '}';
	}
}
