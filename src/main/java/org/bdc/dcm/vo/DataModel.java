package org.bdc.dcm.vo;

public class DataModel extends DataTab{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4711569052577536538L;

	private Object val;

	public Object getVal() {
		return val;
	}

	public DataModel(Object val,String form) {
		super();
		this.val = val;
		setForm(form);
	}

	
}
