package com.hl.insmanager.vo.tree;

/**
 * @author ivan.huang
 * @date 2016年8月2日
 */
public class TreeNode {
    private String id;
    private String pId ="0";
    private String name;
    private Boolean checked;
    private Boolean open;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }


}
