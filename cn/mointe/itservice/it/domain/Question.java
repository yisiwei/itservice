package cn.mointe.itservice.it.domain;

public class Question {

	private int id;
	private String catgid;
	private String gradeid;

	private String helperid;
	private String epuipid;
	private String starttime;

	private String endtime;
	private String status;
	private String comments;

	private String category;
	private String categorydetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCatgid() {
		return catgid;
	}

	public void setCatgid(String catgid) {
		this.catgid = catgid;
	}

	public String getGradeid() {
		return gradeid;
	}

	public void setGradeid(String gradeid) {
		this.gradeid = gradeid;
	}

	public String getHelperid() {
		return helperid;
	}

	public void setHelperid(String helperid) {
		this.helperid = helperid;
	}

	public String getEpuipid() {
		return epuipid;
	}

	public void setEpuipid(String epuipid) {
		this.epuipid = epuipid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategorydetail() {
		return categorydetail;
	}

	public void setCategorydetail(String categorydetail) {
		this.categorydetail = categorydetail;
	}

}
