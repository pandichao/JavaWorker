/**
 * Project Name:wcsrt
 * File Name:XmlBuilderTestBean.java
 * Package Name:com.hy.hap.base.pub
 * Date:2017-2-16下午2:42:26
 * Copyright (c) 2017, darkranger@live.com All Rights Reserved.
 *
*/

package com.cgxt.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ClassName:XmlBuilderTestBean
 * Function: TODO ADD FUNCTION. 
 * Date:     2017-2-16
 * author:   darkr
 */

	@XmlType(propOrder={
		    "result","message"
		})
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlRootElement(name="return")
		public class XmlBuilderTestBean {

		    @XmlElement(name="result")
		    private String result;

		    @XmlElement(name="message")
		    private String message;

			public String getResult() {
				return result;
			}

			public void setResult(String result) {
				this.result = result;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			//日期类型
//		    @XmlElement(name="CreateDate")
//		    private Date createDate;
		    
	}


