package com.sinosoft.antifake.ibatis.model;

import java.io.Serializable;
import java.util.Map;

import com.sinosoft.antifake.entity.IdEntity;


/**
 * Base class for Model objects.  Child objects should implement toString(), 
 * equals() and hashCode();
 */
public abstract class BaseObject extends IdEntity implements Serializable {
    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    private Map magic;

    //leave for framework extended
	public Map getMagic(){
		return this.magic;
	}
	public void setMagic(Map magic){
		this.magic=magic;
	}
}
